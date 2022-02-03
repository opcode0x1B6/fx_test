package com.opcode.fx_test;

import java.lang.Math;

import org.junit.Assert;
import org.junit.Test;
 
public class OrbitalTest {
 
 	@Test
 	public void gravitationBasic() {
 		Vostok v = new Vostok(new Vector3d(), new Vector3d(), new Vector3d());
 		Assert.assertEquals(66.743, v.getGravitationalForce(1000000, 1000000, 1), 0.0);
 	}
 	
 	@Test
 	public void velocityBasic() {
 		Vostok v = new Vostok(new Vector3d(), new Vector3d(), new Vector3d());
 		Assert.assertEquals(1.0, v.getVelocityFromForce(v.massVostok, v.massVostok), 0.0);
 	}
 
 	@Test 
 	public void gravitationOrbit() {
 		Vostok v = new Vostok(new Vector3d(), new Vector3d(), new Vector3d());
 		Assert.assertEquals(43207.5434, v.getGravitationalForce(v.massEarth, v.massVostok, 6600000), 200.0);
 	}
 
	@Test
	public void gravitationVelocityEquator() {
		Vostok v = new Vostok(new Vector3d(), new Vector3d(), new Vector3d());
		Assert.assertEquals(9.81, v.getVelocityFromForce(v.getGravitationalForce(v.massEarth, v.massVostok, v.equatorRadius), v.massVostok), 0.3);
	}
	
	@Test
	public void equatorHeight() {
		Vostok v = new Vostok(new Vector3d(0, 6378000 + 215000, 0), new Vector3d(), new Vector3d());
		Assert.assertEquals(215000.0, v.getHeightOverEquator(), 0.0); 
	}
	
	@Test
	public void airDensityAtZero() {
		Vostok v = new Vostok(new Vector3d(), new Vector3d(), new Vector3d());
		Assert.assertEquals(1.0, v.getAirDensityAtAltitude(6378000), 0.0); 
	}
	
	@Test
	public void airDensityAtVostokApogee() {
		Vostok v = new Vostok(new Vector3d(), new Vector3d(), new Vector3d());
		Assert.assertEquals(6.824325784991658E-9, v.getAirDensityAtAltitude(6378000 + 215000), 0.0); 
	}
	
	@Test
	public void timeOfFlight() {
		Vostok v = new Vostok(new Vector3d(0, 6378000 + 215000, 0), new Vector3d(), new Vector3d(7805, 0, 0));
		for (int s = 0; s < 24 * 9 * 60 * 60; s++) { // orbit should be stable for 9 days
			Assert.assertFalse("Second " + s, v.update(1)); 
		}
		
		boolean decayedInTime = false;
		for (int decayAfterSeconds = 0; decayAfterSeconds < 24 * 2 * 60 * 60; decayAfterSeconds++) { // orbit should decay after 10 days
			if (v.update(1)) {
				decayedInTime = true;
				break;
			}
		}
		
		Assert.assertTrue("Decay after 10 days ", decayedInTime); 
	}
	
	@Test
	public void timeIndifferenceOnAngle() {
		Vostok v = new Vostok(new Vector3d(0, 6378000 + 215000, 0), new Vector3d(), new Vector3d(7700, 0, 0));
		
		int secondsToImpactA = 0;
		while (!v.update(1)) {
			secondsToImpactA++;
		}
		
		v = new Vostok(new Vector3d(0, 6378000 + 215000, 0), new Vector3d(), new Vector3d(0, 0, 7700));
		
		int secondsToImpactB = 0;
		while (!v.update(1)) {
			secondsToImpactB++;
		}
		
		Assert.assertEquals(secondsToImpactA, secondsToImpactB, 0);
	}

	@Test
	public void timeOfFlightDeorbitBurn() {
		Vostok v = new Vostok(new Vector3d(0, 6378000 + 215000, 0), new Vector3d(-1, 0, 0), new Vector3d(7805, 0, 0));
		v.igniteMainRocket();
		boolean decayedInTime = false;
		for (int decayAfterSeconds = 0; decayAfterSeconds < 24 * 60 * 60; decayAfterSeconds++) { // orbit should decay in a day
			if (v.update(1)) {
				decayedInTime = true;
				break;
			}
		}
		
		Assert.assertTrue("Deorbit burn ", decayedInTime); 
	}

	@Test
	public void timeOfFlightDeorbitBurnWithManeuver() {
		Vostok v = new Vostok(new Vector3d(0, 6378000 + 215000, 0), new Vector3d(1, 0, 0), new Vector3d(7805, 0, 0));
		v.heading = v.heading.rotateZ(Math.PI);

		Assert.assertEquals(-1.0, v.heading.getX(), 0.001);

		v.igniteMainRocket();
		boolean decayedInTime = false;
		for (int decayAfterSeconds = 0; decayAfterSeconds < 24 * 60 * 60; decayAfterSeconds++) { // orbit should decay in a day
			if (v.update(1)) {
				decayedInTime = true;
				break;
			}
		}
		
		Assert.assertTrue("Deorbit burn ", decayedInTime); 
	}
}
