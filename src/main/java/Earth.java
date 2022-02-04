package com.opcode.fx_test;

public class Earth extends Planetoid {
    Earth(PhysicsObject parent) {
        super(5.972e24, 6371e3, new Vector3d(0, 150e9, 0).add(parent.getPosition()), new Vector3d(0, 0, 1).rotateY(Math.toRadians(23.44)), new Vector3d(29.78e3, 0, 0).add(parent.getVelocity()), new Vector3d(), 300000.0, 8000.0, 1.285, 2.6);
    }
}
