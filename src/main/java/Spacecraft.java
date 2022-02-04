package com.opcode.fx_test;

class Spacecraft extends PhysicsObject {
    Spacecraft(double mass, double radius, Vector3d position, Vector3d rotation, Vector3d velocity, Vector3d rotationalVelocity) {
        super(mass, radius, position, rotation, velocity, rotationalVelocity);
    }

    Spacecraft(PhysicsObject parent, double mass, double radius, Vector3d position, Vector3d rotation, Vector3d velocity, Vector3d rotationalVelocity) {
        super(mass, radius, parent.getPosition().add(position), rotation, parent.getVelocity().add(velocity), rotationalVelocity);

        setPosition(getPosition().add(getDistanceVector(parent).normalize().multiply(-1).multiply(parent.getRadius()))); // add parentRadius to place the spacecraft on parentPos + parentRadius + ownPos
    }
}