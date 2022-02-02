package com.opcode.fx_test;

import java.lang.Math;

class Vector3d {
    private double x;
    private double y;
    private double z;
    private double length;

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public double getLength() {
        return this.length;
    }

    private void calculateLength() {
        this.length = Math.sqrt((getX() * getX()) + (getY() * getY()) + (getZ() * getZ()));
    }

    Vector3d() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        calculateLength();
    }

    Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        calculateLength();
    }

    Vector3d(Vector3d vector) {
        this.x = vector.getX();
        this.y = vector.getY();
        this.z = vector.getZ();
        calculateLength();
    }

    public Vector3d add(Vector3d vector) {
        return new Vector3d(getX() + vector.getX(), getY() + vector.getY(), getZ() + vector.getZ());
    }

    public Vector3d subtract(Vector3d vector) {
        return new Vector3d(getX() - vector.getX(), getY() - vector.getY(), getZ() - vector.getZ());
    }

    public Vector3d multiply(Vector3d vector) {
        return new Vector3d(getX() * vector.getX(), getY() * vector.getY(), getZ() * vector.getZ());
    }

    public Vector3d multiply(double value) {
        return new Vector3d(getX() * value, getY() * value, getZ() * value);
    }

    public Vector3d divide(Vector3d vector) {
        return new Vector3d(getX() / vector.getX(), getY() / vector.getY(), getZ() / vector.getZ());
    }

    public Vector3d divide(double value) {
        return new Vector3d(getX() / value, getY() / value, getZ() / value);
    }

    public Vector3d normalize() {
        return new Vector3d(divide(getLength()));
    }
    
    public Vector3d percentage() {
    	double total = this.getX() + this.getY() + this.getZ();
    	return new Vector3d(divide(total));
    }

    public Vector3d rotateZ(double rotationRadians) {
        double sinR = Math.sin(rotationRadians);
        double cosR = Math.cos(rotationRadians);
        return new Vector3d(
            this.getX() * cosR - this.getY() * sinR,
            this.getX() * sinR + this.getY() * cosR,
            this.getZ()
        );
    }

    public Vector3d rotateY(double rotationRadians) {
        double sinR = Math.sin(rotationRadians);
        double cosR = Math.cos(rotationRadians);
        return new Vector3d(
            this.getX() * cosR + this.getZ() * sinR,
            this.getY(),
            -this.getX() * sinR + this.getZ() * cosR
        );
    }

    public Vector3d rotateX(double rotationRadians) {
        double sinR = Math.sin(rotationRadians);
        double cosR = Math.cos(rotationRadians);
        return new Vector3d(
            this.getX(),
            this.getY() * cosR - this.getZ() * sinR,
            this.getY() * sinR + this.getZ() * cosR
        );
    }
}
