package com.opcode.fx_test;

import java.beans.Transient;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class OrbitalTest {
    /* Physics and orbital mechanics with simulator to check for timing and rounding errors */

    @Test
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
}
