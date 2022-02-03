package com.opcode.fx_test;

import java.lang.Math;
import java.math.BigDecimal;

class Planetoid extends PhysicsObject {
    private BigDecimal atmosphereCutoffRadius; // used to prevent calculation of super small interactions
    private BigDecimal atmosphereCondensedRadius;
    private BigDecimal atmosphereDensityAtSealevel;
    private BigDecimal atmosphereBasis;

    Planetoid(BigDecimal mass, BigDecimal radius, Vector3d position, Vector3d rotation, Vector3d velocity, Vector3d rotationalVelocity, BigDecimal atmosphereCutoffRadius, BigDecimal atmosphereCondensedRadius, BigDecimal atmosphereDensityAtSealevel, BigDecimal atmosphereBasis) {
        super(mass, radius, position, rotation, velocity, rotationalVelocity);
        this.atmosphereCutoffRadius = atmosphereCutoffRadius;
        this.atmosphereCondensedRadius = atmosphereCondensedRadius;
        this.atmosphereDensityAtSealevel = atmosphereDensityAtSealevel;
        this.atmosphereBasis = atmosphereBasis;
    }

    public BigDecimal getAtmosphereDensity(BigDecimal distanceToCenter) {
        BigDecimal distanceToEquator = distanceToCenter.subtract(getRadius());
        if (distanceToEquator.doubleValue() < atmosphereCutoffRadius.doubleValue()) {
            return new BigDecimal(Math.pow(atmosphereBasis.doubleValue(), new BigDecimal(0).subtract(distanceToEquator.divide(atmosphereCondensedRadius)).doubleValue())).multiply(atmosphereDensityAtSealevel);
        } else {
            return new BigDecimal(0);
        }
    }
}