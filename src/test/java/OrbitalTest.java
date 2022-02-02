package com.opcode.fx_test;

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
		Assert.assertEquals(1.2041, v.getAirDensityAtAltitude(0.0), 0.0); 
	}
}
