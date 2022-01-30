package com.opcode.fx_test;

import java.io.*;
import java.net.*;

public class AbstractPowerplant extends Thread {

	long UPDATE_SLEEP_TIME_MS = 1000;

	double rpmTurbine; // measured turbine/generator rpm
	
	double rpmMotorMax;
	double rpmMotorGain;
	
	double rpmTurbineGain;
	double rpmTurbineBypassLoss; // loss of rpm because of bypass bleed
	
	double rpmTurbineFrictionLoss;
	
	boolean motorActive;
	boolean bypassOpen;
	boolean burnerActive;
	
	NeedleMeter feedbackMeter;
	
	AbstractPowerplant() {
		this.rpmTurbine = 0.0;
		
		this.rpmMotorMax = 700.0;
		this.rpmMotorGain = 15.0;
		
		this.rpmTurbineGain = 25.0;
		this.rpmTurbineBypassLoss = 13.5;
		
		this.rpmTurbineFrictionLoss = 1.0;
		
		this.motorActive = false;
		this.bypassOpen = true;
		this.burnerActive = false;
	}
	
	public void registerMeter(NeedleMeter m) {
		this.feedbackMeter = m;
	}
	
	public void run() {
		long start = System.currentTimeMillis();
		long finish = 0;
		while (!this.isInterrupted()) {
			try {
				Thread.sleep(UPDATE_SLEEP_TIME_MS);
				finish = System.currentTimeMillis();
				this.update((double)((finish - start) / UPDATE_SLEEP_TIME_MS));
				start = System.currentTimeMillis();
			}
			catch (Exception e) {
				this.interrupt();
			}
		}
        }
	
	public void update(double deltaTime) { // deltatime is the time since last update in seconds
		if (this.motorActive) {
			if (this.rpmTurbine < this.rpmMotorMax) {
				this.rpmTurbine = this.rpmTurbine + this.rpmMotorGain * deltaTime;
			}
		}
		
		if (this.burnerActive) {
			this.rpmTurbine = this.rpmTurbine + this.rpmTurbineGain * deltaTime;
			if (this.bypassOpen) {
				this.rpmTurbine = this.rpmTurbine - this.rpmTurbineBypassLoss * deltaTime;
			}
		}
		
		this.rpmTurbine = this.rpmTurbine - this.rpmTurbineFrictionLoss * deltaTime;
		
		if (this.rpmTurbine < 0.0) {
			this.rpmTurbine = 0.0;
		}
		
		System.out.println("rpm " + this.rpmTurbine);
		
		if (this.feedbackMeter != null) {
			System.out.println(this.feedbackMeter);
			this.feedbackMeter.mapValue(this.rpmTurbine);
		}
	}
	
	public boolean getMotorStatus() {
		return this.motorActive;
	}
	
	public boolean getBypassStatus() {
		return this.bypassOpen;
	}
	
	public boolean getBurnerStatus() {
		return this.burnerActive;
	}
	
	public void setMotorStatus(boolean status) {
		this.motorActive = status;
	}
	
	public void setBypassStatus(boolean status) {
		this.bypassOpen = status;
	}
	
	public void setBurnerStatus(boolean status) {
		this.burnerActive = status;
	}
	
	public double getRPM() {
		return this.rpmTurbine;
	}
}
