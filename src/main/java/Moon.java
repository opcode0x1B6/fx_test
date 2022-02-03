package com.opcode.fx_test;

import java.math.BigDecimal;

class Moon extends Planetoid {
    Moon() {
        super(new BigDecimal(7.348e22), new BigDecimal(1737.5), new Vector3d(new BigDecimal(362600e3), BigDecimal.ZERO, BigDecimal.ZERO), new Vector3d(), new Vector3d(BigDecimal.ZERO, new BigDecimal(1024), BigDecimal.ZERO), new Vector3d(), new BigDecimal("1000"), new BigDecimal("100"), new BigDecimal("0.001"), new BigDecimal("3"));
    }
}