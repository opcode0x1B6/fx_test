package com.opcode.fx_test;

abstract class PhysicsObject {
    private final double GRAVITATIONAL_CONSTANT = 6.67430e-11;

    private double mass;
    private double radius;
    private Vector3d position;
    private Vector3d rotation;
    private Vector3d rotationalVelocity;
    private Vector3d velocity;

    PhysicsObject(double mass, double radius, Vector3d position, Vector3d rotation, Vector3d velocity, Vector3d rotationalVelocity) {
        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.rotation = rotation;
        this.velocity = velocity;
        this.rotationalVelocity = rotationalVelocity;
    }

    public boolean equals(Object o) {
        if (o instanceof PhysicsObject) {
            PhysicsObject op = (PhysicsObject)o;
            return ((op.getMass() == getMass()) && (op.getRadius() == getRadius()) && (op.getPosition().equals(getPosition())));
        }
        return false;
    }

    double getMass() {
        return mass;
    }

    double getRadius() {
        return radius;
    }

    Vector3d getPosition() {
        return position;
    }

    Vector3d getRotation() {
        return rotation;
    }

    Vector3d getRotationalVelocity() {
        return rotationalVelocity;
    }

    Vector3d getVelocity() {
        return velocity;
    }

    private double forceToAcceleration(double force) {
        // a = F/m
        return force / getMass();
    }

    void addGravity(PhysicsObject partner, double deltaTime) {
        // get distance and direction of force
        Vector3d distanceVector = partner.getPosition().subtract(getPosition());
        double distance = distanceVector.getLength();
        Vector3d directionVector = distanceVector.normalize();

        // calculate force F = G*m1*m2/d² and convert to acceleration a = F/m1
        double gravityForce = (GRAVITATIONAL_CONSTANT * getMass() * partner.getMass()) / (distance * distance);
        double gravityAcceleration = forceToAcceleration(gravityForce);

        // add the acceleration in the direction times the time since last update
        velocity = velocity.add(directionVector.multiply(gravityAcceleration).multiply(deltaTime));
    }

    void update(double deltaTime) {
        position = position.add(velocity.multiply(deltaTime));
        rotation = rotation.add(rotationalVelocity.multiply(deltaTime));
    }
}