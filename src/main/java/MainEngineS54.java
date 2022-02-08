package com.opcode.fx_test;

class MainEngineS54 extends SpacecraftEngine {
    /* The S5.4 has a deltaV of 155 m/s on the Vostok 1 (Wgt 4700kg) with a burn time of 45 seconds */

    MainEngineS54(Vector3d thrustVector) {
        super(thrustVector, 98.0, 250.0, 5.5556, 15.83e3);
    }

    public String getModuleName() {
        return "S5.4";
    }
}