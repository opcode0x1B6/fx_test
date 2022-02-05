package com.opcode.fx_test;

class Skydiver extends Spacecraft {
    private boolean horizontal;

    Skydiver(PhysicsObject parent, Vector3d position, double mass, boolean horizontal) {
        super(parent, mass, 1, position, new Vector3d(), new Vector3d(), new Vector3d());
        this.horizontal = horizontal;
    }

    double getDragArea() {
        if (this.horizontal) {
            return 1.0;
        }
        return 0.18; // head first
    }

    double getDragCoefficient() {
        // see https://opentextbc.ca/universityphysicsv1openstax/chapter/6-4-drag-force-and-terminal-speed/
        if (this.horizontal) {
            return 1.0; // horizontal
        }
        return 0.70; // head first
    }
}