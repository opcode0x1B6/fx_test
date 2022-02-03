package com.opcode.fx_test;

import java.lang.Math;

class Planetoid extends PhysicsObject {
    private double atmosphereCutoffRadius; // used to prevent calculation of super small interactions
    private double atmosphereCondensedRadius;
    private double atmosphereDensityAtSealevel;
    private double atmosphereBasis;

    Planetoid(double mass, double radius, Vector3d position, Vector3d rotation, Vector3d velocity, Vector3d rotationalVelocity, double atmosphereCutoffRadius, double atmosphereCondensedRadius, double atmosphereDensityAtSealevel, double atmosphereBasis) {
        super(mass, radius, position, rotation, velocity, rotationalVelocity);
        this.atmosphereCutoffRadius = atmosphereCutoffRadius;
        this.atmosphereCondensedRadius = atmosphereCondensedRadius;
        this.atmosphereDensityAtSealevel = atmosphereDensityAtSealevel;
        this.atmosphereBasis = atmosphereBasis;
    }

    public double getAtmosphereDensity(double distanceToCenter) {
        double distanceToEquator = distanceToCenter - getRadius();
        if (distanceToEquator < atmosphereCutoffRadius) {
            return Math.pow(atmosphereBasis, -(distanceToEquator / atmosphereCondensedRadius)) * atmosphereDensityAtSealevel;
        } else {
            return 0.0;
        }
    }
}