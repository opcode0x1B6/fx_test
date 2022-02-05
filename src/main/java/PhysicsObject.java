package com.opcode.fx_test;

abstract class PhysicsObject {
    private final double GRAVITATIONAL_CONSTANT = 6.67430e-11;

    private double mass;
    private double radius;
    private Vector3d position;
    private Vector3d rotation;
    private Vector3d rotationalVelocity;
    private Vector3d velocity;

    protected void setPosition(Vector3d position) {
        this.position = position;
    }

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

    double getDragArea() {
        // default implementation this is a circle
        return getRadius() * getRadius() * Math.PI;
    }

    double getDragCoefficient() {
        // default implementation this is a sphere
        // see https://en.wikipedia.org/wiki/Drag_coefficient
        return 0.47;
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

    double forceToAcceleration(double force) {
        // a = F/m
        return force / getMass();
    }

    Vector3d getDistanceVector(PhysicsObject partner) {
        return partner.getPosition().subtract(getPosition());
    }

    double getAltitudeOverEquator(PhysicsObject partner) {
        return getDistanceVector(partner).getLength() - partner.getRadius();
    }

    double getGravitationForce(double partnerMass, double distance) {
        return (GRAVITATIONAL_CONSTANT * getMass() * partnerMass) / (distance * distance);
    }

    void addGravity(PhysicsObject partner, double deltaTime) {
        // get distance and direction of force
        Vector3d distanceVector = getDistanceVector(partner);
        double distance = distanceVector.getLength();
        Vector3d directionVector = distanceVector.normalize();

        // calculate force F = G*m1*m2/d² and convert to acceleration a = F/m1
        double gravityForce = getGravitationForce(partner.getMass(), distance);
        //assert gravityForce != Double.NaN;
        double gravityAcceleration = forceToAcceleration(gravityForce);
        //assert gravityAcceleration != Double.NaN;

        // add the acceleration in the direction times the time since last update
        velocity = velocity.add(directionVector.multiply(gravityAcceleration).multiply(deltaTime));
    }

    double calculateDragForce(double fluidDensity, double velocity) {
        // calculate drag force 
        double dragForce = getDragCoefficient() * ((fluidDensity * (velocity * velocity)) / 2.0) * getDragArea();

        return dragForce;
    }

    void addDrag(Planetoid planetoid, double deltaTime) {
        // get distance
        Vector3d distanceVector = getDistanceVector((PhysicsObject)planetoid);
        double distance = distanceVector.getLength();

        double fluidDensity = planetoid.getAtmosphereDensity(distance);
        if (fluidDensity > 0) {
            Vector3d velocityWithoutPlanetoidVelocity = velocity.subtract(planetoid.getVelocity());

            double dragForce = calculateDragForce(fluidDensity, velocityWithoutPlanetoidVelocity.getLength());

            double dragSlowdown = forceToAcceleration(dragForce);
            
            Vector3d slowdownVector = velocityWithoutPlanetoidVelocity.normalize().multiply(-1.0).multiply(dragSlowdown).multiply(deltaTime);

            velocity = velocity.add(slowdownVector);
        }
    }

    void update(double deltaTime) {
        position = position.add(velocity.multiply(deltaTime));
        rotation = rotation.add(rotationalVelocity.multiply(deltaTime));
    }
}