package com.opcode.fx_test;

import java.util.*; 

class Spacecraft extends PhysicsObject {
    List<SpacecraftModule> modules;

    Spacecraft(double mass, double radius, Vector3d position, Vector3d rotation, Vector3d velocity, Vector3d rotationalVelocity) {
        super(mass, radius, position, rotation, velocity, rotationalVelocity);

        this.modules = new ArrayList<SpacecraftModule>();
    }

    Spacecraft(PhysicsObject parent, double mass, double radius, Vector3d position, Vector3d rotation, Vector3d velocity, Vector3d rotationalVelocity) {
        super(mass, radius, parent.getPosition().add(position), rotation, parent.getVelocity().add(velocity), rotationalVelocity);

        setPosition(getPosition().add(getDistanceVector(parent).normalize().multiply(-1).multiply(parent.getRadius()))); // add parentRadius to place the spacecraft on parentPos + parentRadius + ownPos

        this.modules = new ArrayList<SpacecraftModule>();
    }

    void addModule(SpacecraftModule module) {
        this.modules.add(module);
        module.attach(this);
    }

    void removeModule(SpacecraftModule module) {
        if (this.modules.remove(module)) {
            module.detach(); // only give detach command if this module was attached to us
        }
    }

    List<SpacecraftModule> getModules() {
        return this.modules;
    }

    List<SpacecraftModule> getModulesByName(String moduleName) {
        LinkedList<SpacecraftModule> foundModules = new LinkedList<SpacecraftModule>(); // we always deliver a list because we can not be sure if there are 0 or multiple modules here
        for (SpacecraftModule module: this.modules) {
            if (module.getModuleName().equals(moduleName)) {
                foundModules.add(module);
            }
        }
        return foundModules;
    }

    void update(double deltaTime) {
        for (SpacecraftModule module: this.modules) {
            module.update(deltaTime);
        }
        super.update(deltaTime);
    }

    double getMass() {
        double totalMass = super.getMass();

        for (SpacecraftModule module: this.modules) {
            totalMass += module.getMass();
        }

        return totalMass;
    }
}