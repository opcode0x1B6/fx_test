package com.opcode.fx_test;

import java.util.List;
import java.util.ArrayList;

import java.io.*;
import java.net.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PhysicsSimulator extends Thread {
    private final long UPDATE_SLEEP_TIME_MS = 100;

    private List<PhysicsObject> bodies;

    PhysicsSimulator() {
        bodies = new ArrayList<PhysicsObject>();
    }

    public void addObject(PhysicsObject object) {
        bodies.add(object);
    }

    public void run() {
        long start = System.currentTimeMillis();
        long finish = 0;
        while (!this.isInterrupted()) {
            try {
                Thread.sleep(UPDATE_SLEEP_TIME_MS);
                finish = System.currentTimeMillis();
                this.update((double)((finish - start) / UPDATE_SLEEP_TIME_MS));
                start = finish; // System.currentTimeMillis();
            }
            catch (Exception e) {
                this.interrupt();
            }
        }
    }

    public void update(double deltaTime) {
        // calculate gravity and drag for each partner
        for (PhysicsObject body: bodies) {
            for (PhysicsObject partner: bodies) {
                if (!partner.equals(body)) {
                    body.addGravity(partner, deltaTime);
                    if (partner instanceof Planetoid) {
                        body.addDrag((Planetoid)partner, deltaTime);
                    }   
                }
            }
        }

        // apply velocity
        for (PhysicsObject body: bodies) {
            body.update(deltaTime);
        }
    }
}