package com.opcode.fx_test;

import java.beans.Transient;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Ignore;

import java.util.Random;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OrbitalTest {
    /* Physics and orbital mechanics with simulator to check for timing and rounding errors */

    @Test
    @Ignore
    public void moonOrbitTest() {
        /* Sun, earth, moon constelation one year in fixed time steps of 0.1 seconds */

        PhysicsObject sun = new Sun();
        PhysicsObject earth = new Earth(sun);
        PhysicsObject moon = new Moon(earth);

        PhysicsSimulator sim = new PhysicsSimulator();
        sim.addObject(earth);
        sim.addObject(moon);
        sim.addObject(sun);

        for (int i = 0; i < 357 * 24 * 60 * 60 * 10; i++) { 
            sim.update(0.1);

            Assert.assertTrue("Orbit stable ", moon.getAltitudeOverEquator(earth) > 350000e3);
            Assert.assertTrue("Orbit stable ", moon.getAltitudeOverEquator(earth) < 450000e3);

            Assert.assertTrue("Orbit stable ", earth.getAltitudeOverEquator(sun) > 130e9);
            Assert.assertTrue("Orbit stable ", earth.getAltitudeOverEquator(sun) < 170e9);

            /*if (i % (60 * 60 * 24 * 30) == 0) {
                System.out.println("Month " + (i / (60 * 60 * 24 * 30) + 1));
                System.out.println("Moon over earth " + moon.getAltitudeOverEquator(earth));
                System.out.println("Earth over sun " + earth.getAltitudeOverEquator(sun));
                System.out.println("Moon over sun " + moon.getAltitudeOverEquator(sun));
            }*/
        }
    }

    @Test
    @Ignore
    public void moonOrbitVarTimeTest() {
        /* Sun, earth, moon constelation one year in variable time steps between 0.1 and 1.1 seconds */

        Random randomTimer = new Random();

        PhysicsObject sun = new Sun();
        PhysicsObject earth = new Earth(sun);
        PhysicsObject moon = new Moon(earth);

        PhysicsSimulator sim = new PhysicsSimulator();
        sim.addObject(earth);
        sim.addObject(moon);
        sim.addObject(sun);

        double currentTime = 0;
        double deltaTime = 0;

        while (currentTime < 357.0 * 24.0 * 60.0 * 60.0) { 
            deltaTime = randomTimer.nextDouble() + 0.1;
            currentTime += deltaTime;
            sim.update(deltaTime);

            Assert.assertTrue("Orbit stable ", moon.getAltitudeOverEquator(earth) > 350000e3);
            Assert.assertTrue("Orbit stable ", moon.getAltitudeOverEquator(earth) < 450000e3);

            Assert.assertTrue("Orbit stable ", earth.getAltitudeOverEquator(sun) > 130e9);
            Assert.assertTrue("Orbit stable ", earth.getAltitudeOverEquator(sun) < 170e9);

            /*if (i % (60 * 60 * 24 * 30) == 0) {
                System.out.println("Month " + (i / (60 * 60 * 24 * 30) + 1));
                System.out.println("Moon over earth " + moon.getAltitudeOverEquator(earth));
                System.out.println("Earth over sun " + earth.getAltitudeOverEquator(sun));
                System.out.println("Moon over sun " + moon.getAltitudeOverEquator(sun));
            }*/
        }
    }

    @Test
    @Ignore
    public void moonOrbitSpeedupVarTimeTest() {
        /* Sun, earth, moon constelation one year in fast forward time between 0 to 3600 seconds per update */

        Random randomTimer = new Random();

        PhysicsObject sun = new Sun();
        PhysicsObject earth = new Earth(sun);
        PhysicsObject moon = new Moon(earth);

        PhysicsSimulator sim = new PhysicsSimulator();
        sim.addObject(earth);
        sim.addObject(moon);
        sim.addObject(sun);

        double currentTime = 0;
        double deltaTime = 0;

        while (currentTime < 357.0 * 24.0 * 60.0 * 60.0) { 
            deltaTime = randomTimer.nextDouble() * 3600.0;
            currentTime += deltaTime;
            sim.update(deltaTime);

            Assert.assertTrue("Orbit stable ", moon.getAltitudeOverEquator(earth) > 350000e3);
            Assert.assertTrue("Orbit stable ", moon.getAltitudeOverEquator(earth) < 450000e3);

            Assert.assertTrue("Orbit stable ", earth.getAltitudeOverEquator(sun) > 130e9);
            Assert.assertTrue("Orbit stable ", earth.getAltitudeOverEquator(sun) < 170e9);

            /*if (i % (60 * 60 * 24 * 30) == 0) {
                System.out.println("Month " + (i / (60 * 60 * 24 * 30) + 1));
                System.out.println("Moon over earth " + moon.getAltitudeOverEquator(earth));
                System.out.println("Earth over sun " + earth.getAltitudeOverEquator(sun));
                System.out.println("Moon over sun " + moon.getAltitudeOverEquator(sun));
            }*/
        }
    }

    @Test
    public void skydiverDragTest() {
        PhysicsObject sun = new Sun();
        Planetoid earth = new Earth(sun);
        Skydiver diver = new Skydiver(earth, new Vector3d(0, 10000, 0), 50, true);

        PhysicsSimulator sim = new PhysicsSimulator();
        sim.addObject((PhysicsObject)earth);
        sim.addObject(sun);
        sim.addObject((PhysicsObject)diver);

        while (diver.getAltitudeOverEquator((PhysicsObject)earth) > 0) {
            sim.update(1.0);
            Assert.assertTrue("Terminal velocity of 50kg skydiver horizontal should be 34m/s ", diver.getVelocity().subtract(earth.getVelocity()).getLength() < 40.0);
        }
    }

    @Ignore
    @Test
    public void vostokOrbitTest() {
        /* in fixed time steps of 0.1 seconds */

        PhysicsObject sun = new Sun();
        Planetoid earth = new Earth(sun);
        PhysicsObject moon = new Moon(earth);
        PhysicsObject vostok = new Vostok(earth);

        PhysicsSimulator sim = new PhysicsSimulator();
        sim.addObject((PhysicsObject)earth);
        sim.addObject(moon);
        sim.addObject(sun);
        sim.addObject(vostok);

        double periapsis = 900e3;
        double totalSlowdown = 0;

        FileWriter logFile;

        try {
            logFile = new FileWriter("log.csv");

            logFile.write("iteration,altitude over equator,velocity,fluid density,drag force,drag slowdown\n");

            for (int i = 0; i < 356 * 24 * 60 * 60; i++) { 
                sim.update(1.0);

                //Assert.assertTrue("Orbit stable ", vostok.getAltitudeOverEquator(earth) > 50e3);
                //Assert.assertTrue("Orbit stable ", vostok.getAltitudeOverEquator(earth) < 220e3);

                double fluidDensity = earth.getAtmosphereDensity(vostok.getDistanceVector((PhysicsObject)earth).getLength());

                double dragForce = vostok.calculateDragForce(fluidDensity, vostok.getVelocity().getLength());

                double slowdown = vostok.forceToAcceleration(dragForce);
                totalSlowdown += slowdown;

                logFile.write(i + "," + vostok.getAltitudeOverEquator(earth) + "," + (vostok.getVelocity().getLength()) + "," + fluidDensity + "," + dragForce + "," + slowdown + "\n");

                /*
                if (vostok.getAltitudeOverEquator(earth) < periapsis) {
                    periapsis = vostok.getAltitudeOverEquator(earth);
                }

                if (i % (60 * 60) == 0) {
                    System.out.println("Hour " + (i / (60 * 60) + 1));
                    System.out.println("Vostok periapsis km " + (periapsis/1000));
                }*/
            }

            logFile.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("total slowdown " + totalSlowdown);
    }
}
