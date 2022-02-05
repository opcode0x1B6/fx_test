package com.opcode.fx_test;

import java.io.*;
import java.net.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Vostok extends Spacecraft {
    private boolean serviceModuleAttached;

    Vostok(PhysicsObject parent) {
        super(parent, 4700, 3, new Vector3d(0, 215e3, 0), new Vector3d(), new Vector3d(-7773.5, 0, 0), new Vector3d());
        serviceModuleAttached = true;
    }

    double getDragCoefficient() {
        if (serviceModuleAttached) {
            return 0.82; // cylinder
        } else {
            return 0.47; // sphere
        }
    }
}
