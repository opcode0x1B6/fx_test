package com.opcode.fx_test;

interface SpacecraftModule {
    String getModuleName();
    void update(double deltaTime);
    void attach(Spacecraft craft);
    void detach();
    double getMass();
}