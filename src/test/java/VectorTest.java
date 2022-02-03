package com.opcode.fx_test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import java.beans.Transient;

import org.junit.Assert;
import org.junit.Test;

public class VectorTest {

	@Test
	public void equalTest() {
		Vector3d vec1 = new Vector3d(0.0, 1.0, 2.0);
		Vector3d vec2 = new Vector3d(0.0, 1.0, 2.0);

		Assert.assertTrue("Equal vector check", vec1.equals(vec2));
		Assert.assertTrue("Equal vector check", vec2.equals(vec1));
	}

	@Test
	public void unequalTest() {
		Vector3d vec1 = new Vector3d(0.0, 1.0, 2.0);
		Vector3d vec2 = new Vector3d(1.0, 1.0, 2.0);

		Assert.assertFalse("Unequal vector check", vec1.equals(vec2));
		Assert.assertFalse("Unequal vector check", vec2.equals(vec1));
	}

	@Test
	public void unequalPercisionTest() {
		Vector3d vec1 = new Vector3d(0.0, 1.0, 2.0);
		Vector3d vec2 = new Vector3d(0.0, 0.999999, 2.0);

		Assert.assertFalse("Unequal percision vector check", vec1.equals(vec2));
		Assert.assertFalse("Unequal percision vector check", vec2.equals(vec1));
	}

	@Test
	public void normalize() {
		Vector3d vec = new Vector3d(1, 2, 3);
        	vec = vec.normalize();

        	Assert.assertEquals(1.0, vec.getLength().doubleValue(), 0.0);
	}
	
	@Test
	public void vectorAddition() {
		Vector3d vec = new Vector3d(1, 2, 3).add(new Vector3d(3, 2, 1));

        	Assert.assertEquals(4, vec.getX().doubleValue(), 0.0);
		Assert.assertEquals(4, vec.getY().doubleValue(), 0.0);
		Assert.assertEquals(4, vec.getZ().doubleValue(), 0.0);
	}
	
	@Test
	public void vectorSubtraction() {
		Vector3d vec = new Vector3d(1, 2, 3).subtract(new Vector3d(3, 2, 1));

        	Assert.assertEquals(-2, vec.getX().doubleValue(), 0.0);
		Assert.assertEquals(0, vec.getY().doubleValue(), 0.0);
		Assert.assertEquals(2, vec.getZ().doubleValue(), 0.0);
	}
	
	@Test
	public void vectorMultiplication() {
		Vector3d vec = new Vector3d(1, 2.5, 3.3).multiply(new Vector3d(1, 2, 3));

        	Assert.assertEquals(1, vec.getX().doubleValue(), 0.0);
		Assert.assertEquals(5, vec.getY().doubleValue(), 0.0);
		Assert.assertEquals(9.9, vec.getZ().doubleValue(), 0.1);
	}
	
	@Test
	public void doubleMultiplication() {
		Vector3d vec = new Vector3d(1, 2.5, 3.3).multiply(new BigDecimal(2));

        	Assert.assertEquals(2, vec.getX().doubleValue(), 0.0);
		Assert.assertEquals(5, vec.getY().doubleValue(), 0.0);
		Assert.assertEquals(6.6, vec.getZ().doubleValue(), 0.0);
	}
	
	@Test
	public void vectorDivision() {
		Vector3d vec = new Vector3d(1, 4, 8).divide(new Vector3d(2, 4, 8));

        	Assert.assertEquals(0.5, vec.getX().doubleValue(), 0.0);
		Assert.assertEquals(1, vec.getY().doubleValue(), 0.0);
		Assert.assertEquals(1, vec.getZ().doubleValue(), 0.0);
	}
	
	@Test
	public void doubleDivision() {
		Vector3d vec = new Vector3d(2, 3, 4).divide(new BigDecimal(2));

        	Assert.assertEquals(1, vec.getX().doubleValue(), 0.0);
		Assert.assertEquals(1.5, vec.getY().doubleValue(), 0.0);
		Assert.assertEquals(2, vec.getZ().doubleValue(), 0.0);
	}

	@Test
	public void rotateZ() {
		Vector3d vec = new Vector3d(0, 1, 0);
		Vector3d vec90 = vec.rotateZ(Math.PI/2.0);
		Vector3d vec180 = vec.rotateZ(Math.PI);
		Vector3d vec180negative = vec.rotateZ(-Math.PI);
		Vector3d vec45 = vec.rotateZ(Math.PI/4.0);

		Assert.assertEquals(-1.0, vec90.getX().doubleValue(), 0.001);
		Assert.assertEquals(0.0, vec90.getY().doubleValue(), 0.001);
		Assert.assertEquals(0.0, vec90.getZ().doubleValue(), 0.001);

		Assert.assertEquals(0.0, vec180.getX().doubleValue(), 0.001);
		Assert.assertEquals(-1.0, vec180.getY().doubleValue(), 0.001);
		Assert.assertEquals(0.0, vec180.getZ().doubleValue(), 0.001);

		Assert.assertEquals(0.0, vec180negative.getX().doubleValue(), 0.001);
		Assert.assertEquals(-1.0, vec180negative.getY().doubleValue(), 0.001);
		Assert.assertEquals(0.0, vec180negative.getZ().doubleValue(), 0.001);

		// .7 instead of .5 because it is the vector length that counts
		Assert.assertEquals(-0.7071, vec45.getX().doubleValue(), 0.001);
		Assert.assertEquals(0.7071, vec45.getY().doubleValue(), 0.001);
		Assert.assertEquals(0.0, vec45.getZ().doubleValue(), 0.001);
	}

	@Test
	public void rotateY() {
		Vector3d vec = new Vector3d(1, 0, 0);
		Vector3d vec90 = vec.rotateY(Math.PI/2.0);
		Vector3d vec180 = vec.rotateY(Math.PI);
		Vector3d vec180negative = vec.rotateY(-Math.PI);
		Vector3d vec45 = vec.rotateY(Math.PI/4.0);

		Assert.assertEquals(0.0, vec90.getX().doubleValue(), 0.001);
		Assert.assertEquals(0.0, vec90.getY().doubleValue(), 0.001);
		Assert.assertEquals(-1.0, vec90.getZ().doubleValue(), 0.001);

		Assert.assertEquals(-1.0, vec180.getX().doubleValue(), 0.001);
		Assert.assertEquals(0.0, vec180.getY().doubleValue(), 0.001);
		Assert.assertEquals(0.0, vec180.getZ().doubleValue(), 0.001);

		Assert.assertEquals(-1.0, vec180negative.getX().doubleValue(), 0.001);
		Assert.assertEquals(0.0, vec180negative.getY().doubleValue(), 0.001);
		Assert.assertEquals(0.0, vec180negative.getZ().doubleValue(), 0.001);

		// .7 instead of .5 because it is the vector length that counts
		Assert.assertEquals(0.7071, vec45.getX().doubleValue(), 0.001);
		Assert.assertEquals(0.0, vec45.getY().doubleValue(), 0.001);
		Assert.assertEquals(-0.7071, vec45.getZ().doubleValue(), 0.001);
	}

	@Test
	public void rotateX() {
		Vector3d vec = new Vector3d(0, 1, 0);
		Vector3d vec90 = vec.rotateX(Math.PI/2.0);
		Vector3d vec180 = vec.rotateX(Math.PI);
		Vector3d vec180negative = vec.rotateX(-Math.PI);
		Vector3d vec45 = vec.rotateX(Math.PI/4.0);

		Assert.assertEquals(0.0, vec90.getX().doubleValue(), 0.001);
		Assert.assertEquals(0.0, vec90.getY().doubleValue(), 0.001);
		Assert.assertEquals(1.0, vec90.getZ().doubleValue(), 0.001);

		Assert.assertEquals(0.0, vec180.getX().doubleValue(), 0.001);
		Assert.assertEquals(-1.0, vec180.getY().doubleValue(), 0.001);
		Assert.assertEquals(0.0, vec180.getZ().doubleValue(), 0.001);

		Assert.assertEquals(0.0, vec180negative.getX().doubleValue(), 0.001);
		Assert.assertEquals(-1.0, vec180negative.getY().doubleValue(), 0.001);
		Assert.assertEquals(0.0, vec180negative.getZ().doubleValue(), 0.001);

		// .7 instead of .5 because it is the vector length that counts
		Assert.assertEquals(0, vec45.getX().doubleValue(), 0.001);
		Assert.assertEquals(0.7071, vec45.getY().doubleValue(), 0.001);
		Assert.assertEquals(0.7071, vec45.getZ().doubleValue(), 0.001);
	}
}
