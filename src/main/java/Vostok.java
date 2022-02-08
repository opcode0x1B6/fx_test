package com.opcode.fx_test;

import java.io.*;
import java.net.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Vostok extends Spacecraft {
    static Vector3d START_FORWARD = new Vector3d(-1, 0, 0);

    private boolean serviceModuleAttached;

    Vostok(PhysicsObject parent) {
        super(parent, 4360, 3, new Vector3d(0, 215e3, 0), START_FORWARD, new Vector3d(-7773.5, 0, 0), new Vector3d());
        this.addModule(new MainEngineS54(START_FORWARD)); // engines will provide thrust perfectly centered forward
        serviceModuleAttached = true;
    }

    Vostok(PhysicsObject parent, Vector3d rotation) {
        super(parent, 4360, 3, new Vector3d(0, 215e3, 0), rotation.normalize(), new Vector3d(-7773.5, 0, 0), new Vector3d());
        this.addModule(new MainEngineS54(rotation.normalize()));
        serviceModuleAttached = true;
    }

    double getDragCoefficient() {
        if (serviceModuleAttached) {
            return 0.82; // cylinder
        } else {
            return 0.47; // sphere
        }
    }

    void update(double deltaTime) {
        super.update(deltaTime);
    }
}
