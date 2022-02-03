package com.opcode.fx_test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

abstract class PhysicsObject {
    private final BigDecimal GRAVITATIONAL_CONSTANT = new BigDecimal(6.67430e-11);

    private BigDecimal mass;
    private BigDecimal radius;
    private Vector3d position;
    private Vector3d rotation;
    private Vector3d rotationalVelocity;
    private Vector3d velocity;

    PhysicsObject(BigDecimal mass, BigDecimal radius, Vector3d position, Vector3d rotation, Vector3d velocity, Vector3d rotationalVelocity) {
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
            return ((op.getMass().equals(getMass())) && (op.getRadius().equals(getRadius())) && (op.getPosition().equals(getPosition())));
        }
        return false;
    }

    BigDecimal getMass() {
        return mass;
    }

    BigDecimal getRadius() {
        return radius;
    }

    BigDecimal getDragArea() {
        // default implementation this is a circle
        return getRadius().pow(2).multiply(new BigDecimal(Math.PI));
    }

    BigDecimal getDragCoefficient() {
        // default implementation this is a sphere
        // see https://en.wikipedia.org/wiki/Drag_coefficient
        return new BigDecimal(0.47);
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

    private BigDecimal forceToAcceleration(BigDecimal force) {
        // a = F/m
        return force.divide(getMass(), MathContext.DECIMAL128);
    }

    Vector3d getDistanceVector(PhysicsObject partner) {
        return partner.getPosition().subtract(getPosition());
    }

    BigDecimal getAltitudeOverEquator(PhysicsObject partner) {
        return getDistanceVector(partner).getLength().subtract(partner.getRadius());
    }

    void addGravity(PhysicsObject partner, BigDecimal deltaTime) {
        // get distance and direction of force
        Vector3d distanceVector = getDistanceVector(partner);
        BigDecimal distance = distanceVector.getLength();
        Vector3d directionVector = distanceVector.normalize();

        // calculate force F = G*m1*m2/d² and convert to acceleration a = F/m1
        BigDecimal gravityForce = GRAVITATIONAL_CONSTANT.multiply(getMass()).multiply(partner.getMass()).divide(distance.pow(2), MathContext.DECIMAL128);
        BigDecimal gravityAcceleration = forceToAcceleration(gravityForce);

        // add the acceleration in the direction times the time since last update
        velocity = velocity.add(directionVector.multiply(gravityAcceleration).multiply(deltaTime));
    }

    void addDrag(Planetoid planetoid, BigDecimal deltaTime) {
        // get distance
        Vector3d distanceVector = getDistanceVector((PhysicsObject)planetoid);
        BigDecimal distance = distanceVector.getLength();

        BigDecimal fluidDensity = planetoid.getAtmosphereDensity(distance);
        if (fluidDensity.doubleValue() > 0) {
            // calculate drag force 
            BigDecimal dragForce = new BigDecimal(0.5).multiply(getDragCoefficient()).multiply(fluidDensity).multiply(getDragArea()).multiply(getVelocity().getLength().pow(2));
            BigDecimal dragAcceleration = forceToAcceleration(dragForce);

            // get reverse of our current velocity
            Vector3d reverseVelocity = new Vector3d().subtract(getVelocity()).normalize();

            // add the acceleration
            velocity = velocity.add(reverseVelocity.multiply(dragAcceleration).multiply(deltaTime)); // multiplying the slowdown with deltaTime will cause problems on greater deltas
        }
    }

    void update(BigDecimal deltaTime) {
        position = position.add(velocity.multiply(deltaTime));
        rotation = rotation.add(rotationalVelocity.multiply(deltaTime));
    }
}