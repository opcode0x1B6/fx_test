package com.opcode.fx_test;

import java.lang.Math;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

class Vector3d {
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal z;
    private BigDecimal length;
    private final MathContext context = MathContext.DECIMAL128;

    public boolean equals(Object o) {
        if (o instanceof Vector3d) {
            Vector3d ov = (Vector3d)o;
            return ((ov.getX().equals(this.getX())) && (ov.getY().equals(this.getY())) && (ov.getZ().equals(this.getZ())));
        }
        return false;
    }

    public BigDecimal getX() {
        return this.x;
    }

    public BigDecimal getY() {
        return this.y;
    }

    public BigDecimal getZ() {
        return this.z;
    }

    public BigDecimal getLength() {
        return this.length;
    }

    private void calculateLength() {
        this.length = getX().pow(2).add(getY().pow(2)).add(getZ().pow(2)).sqrt(context);
    }

    Vector3d() {
        this.x = new BigDecimal(0, context);
        this.y = new BigDecimal(0, context);
        this.z = new BigDecimal(0, context);
        calculateLength();
    }

    Vector3d(BigDecimal x, BigDecimal y, BigDecimal z) {
        this.x = x;
        this.y = y;
        this.z = z;
        calculateLength();
    }

    Vector3d(double x, double y, double z) {
        System.out.println("WARNING double conversion for Vector3d used");
        this.x = new BigDecimal(x, context);
        this.y = new BigDecimal(y, context);
        this.z = new BigDecimal(z, context);
        calculateLength();
    }

    Vector3d(Vector3d vector) {
        this.x = vector.getX();
        this.y = vector.getY();
        this.z = vector.getZ();
        calculateLength();
    }

    public Vector3d add(Vector3d vector) {
        return new Vector3d(getX().add(vector.getX()), getY().add(vector.getY()), getZ().add(vector.getZ()));
    }

    public Vector3d subtract(Vector3d vector) {
        return new Vector3d(getX().subtract(vector.getX()), getY().subtract(vector.getY()), getZ().subtract(vector.getZ()));
    }

    public Vector3d multiply(Vector3d vector) {
        return new Vector3d(getX().multiply(vector.getX()), getY().multiply(vector.getY()), getZ().multiply(vector.getZ()));
    }

    public Vector3d multiply(BigDecimal value) {
        return new Vector3d(getX().multiply(value), getY().multiply(value), getZ().multiply(value));
    }

    public Vector3d divide(Vector3d vector) {
        return new Vector3d(getX().divide(vector.getX(), context), getY().divide(vector.getY(), context), getZ().divide(vector.getZ(), context));
    }

    public Vector3d divide(BigDecimal value) {
        return new Vector3d(getX().divide(value, context), getY().divide(value, context), getZ().divide(value, context));
    }

    public Vector3d normalize() {
        return new Vector3d(divide(getLength()));
    }

    public Vector3d rotateZ(double rotationRadians) {
        BigDecimal sinR = new BigDecimal(Math.sin(rotationRadians));
        BigDecimal cosR = new BigDecimal(Math.cos(rotationRadians));
        return new Vector3d(
            this.getX().multiply(cosR).subtract(this.getY().multiply(sinR)),
            this.getX().multiply(sinR).add(this.getY().multiply(cosR)),
            this.getZ()
        );
    }

    public Vector3d rotateY(double rotationRadians) {
        BigDecimal sinR = new BigDecimal(Math.sin(rotationRadians));
        BigDecimal cosR = new BigDecimal(Math.cos(rotationRadians));
        return new Vector3d(
            this.getX().multiply(cosR).add(this.getZ().multiply(sinR)),
            this.getY(),
            this.getZ().multiply(cosR).subtract(this.getX().multiply(sinR))
        );
    }

    public Vector3d rotateX(double rotationRadians) {
        BigDecimal sinR = new BigDecimal(Math.sin(rotationRadians));
        BigDecimal cosR = new BigDecimal(Math.cos(rotationRadians));
        return new Vector3d(
            this.getX(),
            this.getY().multiply(cosR).subtract(this.getZ().multiply(sinR)),
            this.getY().multiply(sinR).add(this.getZ().multiply(cosR))
        );
    }
}
