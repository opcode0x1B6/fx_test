package com.opcode.fx_test;

import javafx.scene.image.ImageView;

public class ImageButton extends ImageView {
	boolean state;
	
	public void setState(boolean state) {
		this.state = state;
	}
	
	public boolean getState() {
		return this.state;
	}
}
