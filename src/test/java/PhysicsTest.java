package com.opcode.fx_test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import java.beans.Transient;

import org.junit.Assert;
import org.junit.Test;

public class PhysicsTest {
	@Test
	public void equalTest() {
		PhysicsObject po1 = new Planetoid(BigDecimal.ONE, BigDecimal.ONE, new Vector3d(), new Vector3d(), new Vector3d(), new Vector3d(), BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
        PhysicsObject po2 = new Spacecraft(BigDecimal.ONE, BigDecimal.ONE, new Vector3d(), new Vector3d(), new Vector3d(), new Vector3d());

        Assert.assertTrue("Equal physics data ", po1.equals(po2));
        Assert.assertTrue("Equal physics data ", po2.equals(po1));
	}

    @Test
	public void unequalTest() {
		PhysicsObject po1 = new Planetoid(BigDecimal.ONE, BigDecimal.ONE, new Vector3d(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE), new Vector3d(), new Vector3d(), new Vector3d(), BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
        PhysicsObject po2 = new Spacecraft(BigDecimal.ONE, BigDecimal.ONE, new Vector3d(), new Vector3d(), new Vector3d(), new Vector3d());

        Assert.assertFalse("Unequal physics data ", po1.equals(po2));
        Assert.assertFalse("Unequal physics data ", po2.equals(po1));
	}

    @Test
    public void gravityAccelerationTest() {
        PhysicsObject earth = new Earth();
        PhysicsObject vostok = new Vostok();

        PhysicsSimulator sim = new PhysicsSimulator();
        sim.addObject(earth);
        sim.addObject(vostok);
        sim.update(BigDecimal.ONE);

        Assert.assertEquals(9.81, vostok.getVelocity().getLength().doubleValue(), 0.02);
    }

    @Test
    public void orbitStabilityTest() {
        PhysicsObject earth = new Earth();
        PhysicsObject vostok = new Vostok();

        PhysicsSimulator sim = new PhysicsSimulator();
        sim.addObject(earth);
        sim.addObject(vostok);

        // orbit has to be stable for at least 2 days
        for (int i = 0; i < 8 * 24 * 60 * 60; i++) {
            sim.update(BigDecimal.ONE);
            Assert.assertTrue("Orbit stable lower bounds iteration " + i, vostok.getAltitudeOverEquator(earth).doubleValue() > 200e3);
            Assert.assertTrue("Orbit stable upper bounds iteration " + i, vostok.getAltitudeOverEquator(earth).doubleValue() < 500e3);
        }
    }

    @Test
    public void atmosphereTest() {
        // because we only do an approximation we have high tolerances here
        Planetoid earth = new Earth();

        Assert.assertEquals(1.285, earth.getAtmosphereDensity(earth.getRadius()).doubleValue(), 0.001);
        Assert.assertEquals(0.4, earth.getAtmosphereDensity(earth.getRadius().add(new BigDecimal(10e3))).doubleValue(), 0.1);
        Assert.assertEquals(0.09, earth.getAtmosphereDensity(earth.getRadius().add(new BigDecimal(20e3))).doubleValue(), 0.2);
        Assert.assertEquals(5e-7, earth.getAtmosphereDensity(earth.getRadius().add(new BigDecimal(100e3))).doubleValue(), 0.0001);
        Assert.assertEquals(5e-10, earth.getAtmosphereDensity(earth.getRadius().add(new BigDecimal(180e3))).doubleValue(), 0.0001);
        Assert.assertEquals(0.0, earth.getAtmosphereDensity(earth.getRadius().add(new BigDecimal(300e3))).doubleValue(), 0.0);
    }

    @Test
    public void orbitStabilityWithDragTest() {
        PhysicsObject earth = new Earth();
        PhysicsObject vostok = new Vostok();

        PhysicsSimulator sim = new PhysicsSimulator();
        sim.addObject(earth);
        sim.addObject(vostok);

        double lowestPosition = 500e3;
        double highestPosition = 100e3;
        int secondsToDecay = 0;

        // orbit has to be stable for at least 8 days
        for (int i = 0; i < 8 * 24 * 60 * 60; i++) {
            sim.update(BigDecimal.ONE);
            Assert.assertTrue("Orbit stable lower bounds iteration " + i, vostok.getAltitudeOverEquator(earth).doubleValue() > 100e3);
            Assert.assertTrue("Orbit stable upper bounds iteration " + i, vostok.getAltitudeOverEquator(earth).doubleValue() < + 500e3);

            if (highestPosition < vostok.getAltitudeOverEquator(earth).doubleValue()) {
                highestPosition = vostok.getAltitudeOverEquator(earth).doubleValue();
            }
            if (lowestPosition > vostok.getAltitudeOverEquator(earth).doubleValue()) {
                lowestPosition = vostok.getAltitudeOverEquator(earth).doubleValue();
            }

            secondsToDecay++;
        }

        // orbit should decay after 10 days
        boolean orbitDecayed = false;
        for (int i = 0; i < 3 * 24 * 60 * 60; i++) {
            sim.update(BigDecimal.ONE);
            if (vostok.getAltitudeOverEquator(earth).doubleValue() < 1) {
                orbitDecayed = true;
                break;
            }
            secondsToDecay++;
        }
        Assert.assertTrue("Orbit decay in timeframe ", orbitDecayed);
        System.out.println("Deorbit in " + secondsToDecay/60/60 + " hours" + "\nOrbit apoapsis " + (int)(highestPosition/1000) + " km periapsis " + (int)(lowestPosition/1000) + " km");
    }

    @Test
    public void earthMoonTest() {
        double earthRadius = 6371e3;
        PhysicsObject earth = new Earth();
        PhysicsObject moon = new Moon();

        PhysicsSimulator sim = new PhysicsSimulator();
        sim.addObject(earth);
        sim.addObject(moon);

        int lowestPosition = moon.getPosition().getLength().intValue();
        int highestPosition = moon.getPosition().getLength().intValue();

        for (int i = 0; i < 365 * 24 * 60 * 60; i++) {
            sim.update(BigDecimal.ONE);

            if (highestPosition < moon.getAltitudeOverEquator(earth).intValue()) {
                highestPosition = moon.getAltitudeOverEquator(earth).intValue();
            }
            if (lowestPosition > moon.getAltitudeOverEquator(earth).intValue()) {
                lowestPosition = moon.getAltitudeOverEquator(earth).intValue();
            }
        }

        System.out.println("Moon orbit apoapsis " + (int)(highestPosition/1000) + " km periapsis " + (int)(lowestPosition/1000) + " km");
    }
}
