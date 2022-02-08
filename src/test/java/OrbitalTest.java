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

    @Ignore
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

    @Ignore
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

    @Ignore
    @Test
    public void moonOrbitSpeedupVarTimeTest() {
        /* Sun, earth, moon constelation one year in fast forward time between 1 to 3600 seconds per update */

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
            deltaTime = Math.min(randomTimer.nextDouble() * 3600.0, 1.0);
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
        /* drag forces should govern known terminal velocity of skydiver */

        PhysicsObject sun = new Sun();
        Planetoid earth = new Earth(sun);
        Skydiver diverSpread = new Skydiver(earth, new Vector3d(0, 10000, 0), 50, true);
        Skydiver diverDive = new Skydiver(earth, new Vector3d(10000, 0, 0), 75, false);

        PhysicsSimulator sim = new PhysicsSimulator();
        sim.addObject((PhysicsObject)earth);
        sim.addObject(sun);
        sim.addObject((PhysicsObject)diverSpread);
        sim.addObject((PhysicsObject)diverDive);

        while (diverDive.getAltitudeOverEquator((PhysicsObject)earth) > 0) {
            sim.update(1.0);
            Assert.assertTrue("Terminal velocity of 50kg skydiver horizontal should be 34m/s ", diverSpread.getVelocity().subtract(earth.getVelocity()).getLength() < 40.0);
            Assert.assertTrue("Terminal velocity of 74kg skydiver diving should be 98m/s ", diverDive.getVelocity().subtract(earth.getVelocity()).getLength() < 150.0);
        }
    }

    @Test
    public void vostokOrbitTest() {
        /* vostok orbit must confirm to no input profile (stay in orbit for 8 to 12 days) with random time steps 0.1 to 1.1 */

        PhysicsObject sun = new Sun();
        Planetoid earth = new Earth(sun);
        PhysicsObject moon = new Moon(earth);
        PhysicsObject vostok = new Vostok(earth);

        PhysicsSimulator sim = new PhysicsSimulator();
        sim.addObject((PhysicsObject)earth);
        sim.addObject(moon);
        sim.addObject(sun);
        sim.addObject(vostok);

        Random randomTimer = new Random();
        double totalTime = 0;
        double deltaTime = 0;

       while (totalTime < 8 * 24 * 60 * 60) { 
            deltaTime = randomTimer.nextDouble() + 0.1;
            totalTime += deltaTime;
            sim.update(deltaTime);

            Assert.assertTrue("Orbit stable ", vostok.getAltitudeOverEquator(earth) > 10e3);
            Assert.assertTrue("Orbit stable ", vostok.getAltitudeOverEquator(earth) < 220e3);
        }

        while (totalTime < 12 * 24 * 60 * 60) { 
            deltaTime = randomTimer.nextDouble() + 0.1;
            totalTime += deltaTime;
            sim.update(deltaTime);

            if (vostok.getAltitudeOverEquator(earth) < 100)
            {
                return;
            }
        }
        Assert.assertFalse("Orbit did not decay after 12 days ", true);
    }

    @Test
    public void vostokDeorbitBurnTest() {
        /* vostok orbit must decay within 1 day if main engine is fired retrograde with random time steps 0.1 to 1.1 */

        PhysicsObject sun = new Sun();
        Planetoid earth = new Earth(sun);
        PhysicsObject moon = new Moon(earth);
        Vostok vostok = new Vostok(earth, new Vector3d(1, 0, 0));

        PhysicsSimulator sim = new PhysicsSimulator();
        sim.addObject(earth);
        sim.addObject(moon);
        sim.addObject(sun);
        sim.addObject(vostok);

        Random randomTimer = new Random();
        double totalTime = 0;
        double deltaTime = 0;

        MainEngineS54 mainEngine = (MainEngineS54)vostok.getModulesByName("S5.4").get(0);
        mainEngine.activateEngine();

        while (totalTime < 1 * 24 * 60 * 60) { 
            deltaTime = randomTimer.nextDouble() + 0.1;
            totalTime += deltaTime;
            sim.update(deltaTime);

            if (vostok.getAltitudeOverEquator(earth) < 100)
            {
                return;
            }
        }
        Assert.assertFalse("Deorbit burn did not create a decay after 1 day ", true);
    }
}
