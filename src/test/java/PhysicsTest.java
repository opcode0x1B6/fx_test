package com.opcode.fx_test;

import java.beans.Transient;

import org.junit.Assert;
import org.junit.Test;

public class PhysicsTest {
    /* Physics test without simulator to check if basic numbers match */

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
    public void gravityTest() {
        PhysicsObject earth = new Earth();
        PhysicsObject vostok = new Spacecraft(4700, 2, new Vector3d(earth.getRadius(), 0, 0), new Vector3d(), new Vector3d(), new Vector3d());

        // newtons of force for both partners the same
        Assert.assertEquals(46153.875, earth.getGravitationForce(vostok.getMass(), earth.getRadius()), 0.001);
        Assert.assertEquals(46153.875, vostok.getGravitationForce(earth.getMass(), earth.getRadius()), 0.001);

        // acceleration quite different
        Assert.assertEquals(9.81, vostok.forceToAcceleration(vostok.getGravitationForce(earth.getMass(), earth.getRadius())), 0.02);
        Assert.assertEquals(7e-21, earth.forceToAcceleration(earth.getGravitationForce(vostok.getMass(), earth.getRadius())), 0.0001);
    }

    @Test
    public void atmosphereTest() {
        // because we only do an approximation we have high tolerances here
        Planetoid earth = new Earth();

        Assert.assertEquals(1.285, earth.getAtmosphereDensity(earth.getRadius()), 0.001);
        Assert.assertEquals(0.4, earth.getAtmosphereDensity(earth.getRadius() + 10e3), 0.1);
        Assert.assertEquals(0.09, earth.getAtmosphereDensity(earth.getRadius() + 20e3), 0.2);
        Assert.assertEquals(5e-7, earth.getAtmosphereDensity(earth.getRadius() + 100e3), 0.0001);
        Assert.assertEquals(5e-10, earth.getAtmosphereDensity(earth.getRadius() + 180e3), 0.0001);
        Assert.assertEquals(9e-12, earth.getAtmosphereDensity(earth.getRadius() + 215e3), 0.0001);
        Assert.assertEquals(0.0, earth.getAtmosphereDensity(earth.getRadius() + 300e3), 0.0);
    }

    @Test
    public void dragTest() {
        Planetoid earth = new Earth();
        PhysicsObject vostokCapsule = new Spacecraft(4700, 2, new Vector3d(earth.getRadius(), 0, 0), new Vector3d(), new Vector3d(), new Vector3d());
        PhysicsObject vostokFull = new Vostok(earth);
        vostokFull.setPosition(new Vector3d(earth.getRadius(), 0, 0));

        Assert.assertEquals(15.1789, vostokCapsule.calculateDragForce(earth.getAtmosphereDensity(earth.getRadius()), 2), 0.001);
        Assert.assertEquals(0.0032, vostokCapsule.forceToAcceleration(vostokCapsule.calculateDragForce(earth.getAtmosphereDensity(earth.getRadius()), 2)), 0.001);

        // full vostok has a dragCoefficient of 0.87 and an area of 28.274333882 m²
        Assert.assertEquals(59.5853, vostokFull.calculateDragForce(earth.getAtmosphereDensity(earth.getRadius()), 2), 0.001);
    }
    
}
