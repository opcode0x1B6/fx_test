package com.opcode.fx_test;

import java.math.BigDecimal;

class Earth extends Planetoid {
    Earth() {
        super(new BigDecimal("5.972e24"), new BigDecimal("6371e3"), new Vector3d(), new Vector3d(), new Vector3d(), new Vector3d(), new BigDecimal("300000"), new BigDecimal("8000"), new BigDecimal("1.285"), new BigDecimal("2.6"));
    }
}