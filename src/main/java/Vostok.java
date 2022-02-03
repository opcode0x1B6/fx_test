package com.opcode.fx_test;

import java.io.*;
import java.net.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Vostok extends Thread {
    Vector3d position;
    Vector3d heading;

    Vector3d velocity;
    Vector3d rotationalVelocity;

    double totalTime;

    // not a real rocket equation but works for the moment
    boolean mainRocketIgnited = false;
    double deltaVStorage = 155;
    double deltaVSecond = 1.55;

    // not a real rcs but works for the moment
    double rcsFuelStorage = 20;
    double rcsFuelPerSecond = 0.1;
    double rcsRotationPerSecond = Math.PI/1000.0;

    Lock modificationLock;

    static final double gravitationalConstant = 6.67430 * Math.pow(10,-11);
    static final double massEarth = 5.972e24;
    static final double massVostok = 4725;
    static final double equatorRadius = 6378000;

    static final double densityAirSealevel = 1.2041;
    static final double zeroDensityPoint = 300000;

    static final double dragArea = 4.15;
    static final double dragCoefficient = 0.5;
    
    static final long UPDATE_SLEEP_TIME_MS = 1000;

    public void run() {
        long start = System.currentTimeMillis();
        long finish = 0;
        while (!this.isInterrupted()) {
            try {
                Thread.sleep(UPDATE_SLEEP_TIME_MS);
                finish = System.currentTimeMillis();
                if (this.update((double)((finish - start) / UPDATE_SLEEP_TIME_MS))) {
                    this.interrupt(); // we crashed, no need to update
                }
                start = System.currentTimeMillis();
            }
            catch (Exception e) {
                this.interrupt();
            }
        }
    }

    Vostok(Vector3d position, Vector3d heading, Vector3d velocity) {
        this.position = position;
        this.heading = heading;
        this.velocity = velocity;
        this.rotationalVelocity = new Vector3d();
        this.totalTime = 0;
        modificationLock = new ReentrantLock();
    }

    private Vector3d getEarthDirection() {
        return new Vector3d().subtract(this.position).normalize();
    }

    private boolean positionInsideEarth(Vector3d vector) {
        return vector.getLength() < equatorRadius;
    }

    public double getHeightOverEquator(Vector3d vector) {
        return getDistanceToEarthCenter(vector) - equatorRadius;
    }

    private double getDistanceToEarthCenter(Vector3d vector) {
        return vector.getLength();
    }

    public double getHeightOverEquator() {
        return getHeightOverEquator(this.position);
    }

    public double getGravitationalForce(double massAKg, double massBKg, double distanceM) {
        return (gravitationalConstant * massAKg * massBKg) / (distanceM * distanceM);
    }

    public double getAirDensityAtAltitude(double altitudeFromCenterM) {
        double altitudeFromEquator = altitudeFromCenterM - equatorRadius;
        
        if (altitudeFromEquator < 100) {
        	return Math.pow(1.3, -altitudeFromEquator / 7000.0 );
        } else {
        	return Math.pow(1.3, -altitudeFromEquator / 3000.0 );
        }
    }

    public double getDragForce(double fluidDensityKgM3, double areaM2, double velocity, double dragCoefficient) {
        // Force = 1/2 Drag Coefficient * Fluid density kg/m3 * Area m2 * (Velocity m/s)²
        return 0.5 * dragCoefficient * fluidDensityKgM3 * areaM2 * (velocity * velocity);
    }

    public double getVelocityFromForce(double forceNewton, double massKg) {
        // a = F / m
        return forceNewton / massKg;
    }

    public void igniteMainRocket() {
        this.modificationLock.lock();
        this.mainRocketIgnited = true;
        System.out.println("firing main rocket");
        this.modificationLock.unlock();
    }

    public boolean update(double deltaTime) {
        this.modificationLock.lock();
        this.totalTime += deltaTime;
        double gravity = getVelocityFromForce(getGravitationalForce(massEarth, massVostok, getDistanceToEarthCenter(this.position)), massVostok);
        this.velocity = this.velocity.add(this.getEarthDirection().multiply(gravity).multiply(deltaTime));

        double orbitDecayDrag = getVelocityFromForce(getDragForce(getAirDensityAtAltitude(getDistanceToEarthCenter(this.position)), dragArea, this.velocity.getLength(), dragCoefficient), massVostok);
        if ((!Double.isNaN(orbitDecayDrag)) && (!Double.isInfinite(orbitDecayDrag)) && (orbitDecayDrag != 0.0)) {
            this.velocity = this.velocity.subtract(this.velocity.normalize().multiply(orbitDecayDrag).multiply(deltaTime));
        }

        if (this.mainRocketIgnited) {
            if (deltaVStorage > 0) {
                double burnTime = deltaTime;
                if (deltaVStorage / deltaVSecond >= deltaTime) {
                    deltaVStorage -= deltaVSecond * deltaTime;
                } else {
                    burnTime = deltaVStorage / deltaVSecond;
                    deltaVStorage = 0;
                }
                //System.out.println("main rocket burn: " + burnTime);
                this.velocity = this.velocity.add(this.heading.normalize().multiply(deltaVSecond).multiply(burnTime));
            }
        }

        Vector3d deltaVelocity = this.velocity.multiply(deltaTime);
        this.position = this.position.add(deltaVelocity);

        //System.out.println("timestamp: " + this.totalTime);
        //System.out.println("pos " + this.position.getX() + " " + this.position.getY() + " " + this.position.getZ());

        this.modificationLock.unlock();

        return positionInsideEarth(this.position);
    }
}
