package com.opcode.fx_test;

class Spacecraft extends PhysicsObject {
    Spacecraft(double mass, double radius, Vector3d position, Vector3d rotation, Vector3d velocity, Vector3d rotationalVelocity) {
        super(mass, radius, position, rotation, velocity, rotationalVelocity);
    }
}