package com.opcode.fx_test;

import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Vostok extends Spacecraft {
    Vostok() {
        super(new BigDecimal(4700), new BigDecimal(2), new Vector3d(BigDecimal.ZERO, new BigDecimal(6371e3 + 215e3), BigDecimal.ZERO), new Vector3d(), new Vector3d(new BigDecimal(7768), BigDecimal.ZERO, BigDecimal.ZERO), new Vector3d());
    }
}
