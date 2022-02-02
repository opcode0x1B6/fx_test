package com.opcode.fx_test;

class Vostok {
    Vector3d position;
    Vector3d heading;
    Vector3d velocity;

    static final double gravitationalConstant = 6.67430 * Math.pow(10,-11);
    static final double massEarth = 5.972e24;
    static final double massVostok = 4725;
    static final double equatorRadius = 6378000;

    static final double densityAirSealevel = 1.2041;
    static final double zeroDensityPoint = 300000;

    static final double dragArea = 4.15;
    static final double dragCoefficient = 0.5;

    double totalTime;

    Vostok(Vector3d position, Vector3d heading, Vector3d velocity) {
        this.position = position;
        this.heading = heading;
        this.velocity = velocity;
        this.totalTime = 0;
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

    public boolean update(double deltaTime) {
        this.totalTime += deltaTime;

        double gravity = getVelocityFromForce(getGravitationalForce(massEarth, massVostok, getDistanceToEarthCenter(this.position)), massVostok);
        //System.out.println("g: " + gravity);
        this.velocity = this.velocity.add(this.getEarthDirection().multiply(gravity).multiply(deltaTime));

        double orbitDecayDrag = getVelocityFromForce(getDragForce(getAirDensityAtAltitude(getDistanceToEarthCenter(this.position)), dragArea, this.velocity.getLength(), dragCoefficient), massVostok);
        if ((!Double.isNaN(orbitDecayDrag)) && (!Double.isInfinite(orbitDecayDrag)) && (orbitDecayDrag != 0.0)) {
            //System.out.println("timestamp " + this.totalTime + "\nheigth: " + getHeightOverEquator() + "\ndensity: "+ getAirDensityAtAltitude(getDistanceToEarthCenter(this.position)) + "\ndrag: " + orbitDecayDrag);
            this.velocity = this.velocity.subtract(this.velocity.normalize().multiply(orbitDecayDrag).multiply(deltaTime));
        }

        Vector3d deltaVelocity = this.velocity.multiply(deltaTime);
        this.position = this.position.add(deltaVelocity);

        return positionInsideEarth(this.position);
    }
}
