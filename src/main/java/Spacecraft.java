package com.opcode.fx_test;

import java.math.BigDecimal;

class Spacecraft extends PhysicsObject {
    Spacecraft(BigDecimal mass, BigDecimal radius, Vector3d position, Vector3d rotation, Vector3d velocity, Vector3d rotationalVelocity) {
        super(mass, radius, position, rotation, velocity, rotationalVelocity);
    }
}