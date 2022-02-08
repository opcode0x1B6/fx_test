package com.opcode.fx_test;

abstract class SpacecraftEngine implements SpacecraftModule {
    private double dryMass;
    private double propellantMass;
    
    private boolean engineActive;
    private double fuelPerSecond;
    private double thrustPerSecond;

    private Vector3d thrustVector;
    private Spacecraft craft;

    SpacecraftEngine(Vector3d thrustVector, double dryMass, double propellantMass, double fuelPerSecond, double thrustPerSecond) {
        this.thrustVector = thrustVector;
        this.dryMass = dryMass;
        this.propellantMass = propellantMass;
        this.fuelPerSecond = fuelPerSecond;
        this.thrustPerSecond = thrustPerSecond;
        this.engineActive = false;
    }

    public void update(double deltaTime) {
        if ((this.engineActive) && (this.propellantMass > 0.0)){
            double thrustGenerated = 0.0;
            if (this.propellantMass >= this.fuelPerSecond * deltaTime) {
                this.propellantMass -= this.fuelPerSecond * deltaTime;
                thrustGenerated = thrustPerSecond * deltaTime;
            } else {
                thrustGenerated = this.propellantMass / this.fuelPerSecond;
            }

            this.craft.exertForce(thrustGenerated, this.thrustVector);
        }
    }

    public void attach(Spacecraft craft) {
        this.craft = craft;
    }

    public void detach() {
        this.craft = null;
    }

    public double getMass() {
        return this.dryMass + this.propellantMass;
    }

    public void activateEngine() {
        this.engineActive = true;
    }

    public void deactivateEngine() {
        this.engineActive = false;
    }

    public boolean getStatus() {
        return this.engineActive;
    }
}