package com.opcode.fx_test;

import java.lang.Math;

public class Moon extends Planetoid {
    Moon(PhysicsObject parent) {
        super(7.348e22, 1737.5e3, parent.getPosition().add(new Vector3d(0, 385000e3, 0).rotateX(Math.toRadians(5.14))), new Vector3d(0, 0, 1).rotateY(Math.toRadians(-6.68)), new Vector3d(1.022e3, 0, 0).add(parent.getVelocity()), new Vector3d(), 0.0, 0.0, 1.0, 1.0);
    }
}
