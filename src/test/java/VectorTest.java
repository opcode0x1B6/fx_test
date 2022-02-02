package com.opcode.fx_test;

import org.junit.Assert;
import org.junit.Test;

public class VectorTest {

	@Test
	public void percentage() {
		Vector3d vec = new Vector3d(1, 2, 3);
		vec = vec.percentage();

		Assert.assertEquals(0.1666, vec.getX(), 0.01);
		Assert.assertEquals(0.333, vec.getY(), 0.01);
		Assert.assertEquals(0.5, vec.getZ(), 0.01);
	}

	@Test
	public void normalize() {
		Vector3d vec = new Vector3d(1, 2, 3);
        	vec = vec.normalize();

        	Assert.assertEquals(1.0, vec.getLength(), 0.0);
	}
	
	@Test
	public void vectorAddition() {
		Vector3d vec = new Vector3d(1, 2, 3).add(new Vector3d(3, 2, 1));

        	Assert.assertEquals(4, vec.getX(), 0.0);
		Assert.assertEquals(4, vec.getY(), 0.0);
		Assert.assertEquals(4, vec.getZ(), 0.0);
	}
	
	@Test
	public void vectorSubtraction() {
		Vector3d vec = new Vector3d(1, 2, 3).subtract(new Vector3d(3, 2, 1));

        	Assert.assertEquals(-2, vec.getX(), 0.0);
		Assert.assertEquals(0, vec.getY(), 0.0);
		Assert.assertEquals(2, vec.getZ(), 0.0);
	}
	
	@Test
	public void vectorMultiplication() {
		Vector3d vec = new Vector3d(1, 2.5, 3.3).multiply(new Vector3d(1, 2, 3));

        	Assert.assertEquals(1, vec.getX(), 0.0);
		Assert.assertEquals(5, vec.getY(), 0.0);
		Assert.assertEquals(9.9, vec.getZ(), 0.1);
	}
	
	@Test
	public void doubleMultiplication() {
		Vector3d vec = new Vector3d(1, 2.5, 3.3).multiply(2.0);

        	Assert.assertEquals(2, vec.getX(), 0.0);
		Assert.assertEquals(5, vec.getY(), 0.0);
		Assert.assertEquals(6.6, vec.getZ(), 0.0);
	}
	
	@Test
	public void vectorDivision() {
		Vector3d vec = new Vector3d(1, 4, 8).divide(new Vector3d(2, 4, 8));

        	Assert.assertEquals(0.5, vec.getX(), 0.0);
		Assert.assertEquals(1, vec.getY(), 0.0);
		Assert.assertEquals(1, vec.getZ(), 0.0);
	}
	
	@Test
	public void doubleDivision() {
		Vector3d vec = new Vector3d(2, 3, 4).divide(2.0);

        	Assert.assertEquals(1, vec.getX(), 0.0);
		Assert.assertEquals(1.5, vec.getY(), 0.0);
		Assert.assertEquals(2, vec.getZ(), 0.0);
	}
}