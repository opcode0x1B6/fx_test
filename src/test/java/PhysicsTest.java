package com.opcode.fx_test;

import java.beans.Transient;

import org.junit.Assert;
import org.junit.Test;

public class PhysicsTest {

	@Test
	public void equalTest() {
		PhysicsObject po1 = new Planetoid(10, 1, new Vector3d(), new Vector3d(), new Vector3d(), new Vector3d(), 0, 0, 0, 0);
        PhysicsObject po2 = new Spacecraft(10, 1, new Vector3d(), new Vector3d(), new Vector3d(), new Vector3d());

        Assert.assertTrue("Equal physics data ", po1.equals(po2));
        Assert.assertTrue("Equal physics data ", po2.equals(po1));
	}

    @Test
	public void unequalTest() {
		PhysicsObject po1 = new Planetoid(10, 1, new Vector3d(10, 10, 10), new Vector3d(), new Vector3d(), new Vector3d(), 0, 0, 0, 0);
        PhysicsObject po2 = new Spacecraft(10, 1, new Vector3d(), new Vector3d(), new Vector3d(), new Vector3d());

        Assert.assertFalse("Unequal physics data ", po1.equals(po2));
        Assert.assertFalse("Unequal physics data ", po2.equals(po1));
	}

    @Test
    public void gravityAccelerationTest() {
        double earthRadius = 6371e3;
        PhysicsObject earth = new Planetoid(5.972e24, earthRadius, new Vector3d(), new Vector3d(), new Vector3d(), new Vector3d(), 0, 0, 0, 0);
        PhysicsObject vostok = new Spacecraft(4700, 2, new Vector3d(earthRadius, 0, 0), new Vector3d(), new Vector3d(), new Vector3d());

        PhysicsSimulator sim = new PhysicsSimulator();
        sim.addObject(earth);
        sim.addObject(vostok);
        sim.update(1.0);

        Assert.assertEquals(9.81, vostok.getVelocity().getLength(), 0.02);
    }

    @Test
    public void orbitStabilityTest() {
        double earthRadius = 6371e3;
        PhysicsObject earth = new Planetoid(5.972e24, earthRadius, new Vector3d(), new Vector3d(), new Vector3d(), new Vector3d(), 0, 0, 0, 0);
        PhysicsObject vostok = new Spacecraft(4700, 2, new Vector3d(earthRadius + 215e3, 0, 0), new Vector3d(), new Vector3d(0, 7850, 0), new Vector3d());

        PhysicsSimulator sim = new PhysicsSimulator();
        sim.addObject(earth);
        sim.addObject(vostok);

        // orbit has to be stable for at least 8 days
        for (int i = 0; i < 8 * 24 * 60 * 60; i++) {
            sim.update(1.0);
            Assert.assertTrue("Orbit stable lower bounds iteration " + i, vostok.getPosition().getLength() > earthRadius + 200e3);
            Assert.assertTrue("Orbit stable upper bounds iteration " + i, vostok.getPosition().getLength() < earthRadius + 500e3);
        }
    }

    @Test
    public void atmosphereTest() {
        // because we only do an approximation we have high tolerances here
        double earthRadius = 6371e3;
        Planetoid earth = new Planetoid(5.972e24, earthRadius, new Vector3d(), new Vector3d(), new Vector3d(), new Vector3d(), 300000.0, 8000.0, 1.285, 2.6);

        Assert.assertEquals(1.285, earth.getAtmosphereDensity(earthRadius), 0.001);
        Assert.assertEquals(0.4, earth.getAtmosphereDensity(earthRadius + 10e3), 0.1);
        Assert.assertEquals(0.09, earth.getAtmosphereDensity(earthRadius + 20e3), 0.2);
        Assert.assertEquals(5e-7, earth.getAtmosphereDensity(earthRadius + 100e3), 0.0001);
        Assert.assertEquals(5e-10, earth.getAtmosphereDensity(earthRadius + 180e3), 0.0001);
        Assert.assertEquals(0.0, earth.getAtmosphereDensity(earthRadius + 300e3), 0.0);
    }
}
