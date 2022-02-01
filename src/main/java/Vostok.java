package com.opcode.fx_test;

class Vostok {
    Vector3d position;
    Vector3d heading;
    Vector3d velocity;

    static final double gravity = 9.81;
    static final double equatorRadius = 6378000;
    static final double orbitDecay = 0.01;

    Vostok(Vector3d position, Vector3d heading, Vector3d velocity) {
        this.position = position;
        this.heading = heading;
        this.velocity = velocity;
    }
    
    Vostok() {
    	this.position = new Vector3d(0, (215 + 6378) * 1000, 0); // 215km
        this.heading = new Vector3d();
        this.velocity = new Vector3d(3000, 0, 3000);
    }

    private Vector3d getEarthDirection() {
        return new Vector3d().subtract(this.position).normalize();
    }

    private boolean positionInsideEarth(Vector3d vector) {
        return vector.getLength() < equatorRadius;
    }

    private double getHeightOverEquator(Vector3d vector) {
        return vector.getLength() - equatorRadius;
    }

    public double getHeightOverEquator() {
        return getHeightOverEquator(this.position);
    }

    public boolean update(double deltaTime) {
        this.velocity = this.velocity.add(this.getEarthDirection().multiply(gravity));
        this.velocity = this.velocity.subtract(this.velocity.normalize().multiply(orbitDecay));
        Vector3d deltaVelocity = this.velocity.multiply(deltaTime);
        this.position = this.position.add(deltaVelocity);

        return positionInsideEarth(this.position);
    }
}
