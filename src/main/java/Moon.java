package com.opcode.fx_test;

public class Moon extends Planetoid {
    Moon(PhysicsObject parent) {
        super(7.348e22, 1737.5e3, new Vector3d(0, 385000e3, 0).add(parent.getPosition()), new Vector3d(), new Vector3d(1.022e3, 0, 0).add(parent.getVelocity()), new Vector3d(), 0.0, 0.0, 1.0, 1.0);
    }
}
