package com.opcode.fx_test;

import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;

import javafx.geometry.Point3D;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class TripleMeter extends StackPane{
	Needle needleTop;
	Needle needleLeft;
	Needle needleRight;

	ImageView background;
	ImageView foreground;
	
	Image backgroundImage;
	Image foregroundImage;
	
	public void initialize(String backgroundFilepath, String needleFilepathTop, String needleFilepathLeft, String needleFilepathRight, String foregroundFilepath, double topMinValue, double topMaxValue, double leftMinValue, double leftMaxValue, double rightMinValue, double rightMaxValue) {
		this.backgroundImage = new Image(backgroundFilepath);
		this.foregroundImage = new Image(foregroundFilepath);
		
		this.background = new ImageView();
		this.foreground = new ImageView();
		this.background.setPickOnBounds(false); 
		this.foreground.setPickOnBounds(false); 
		
		this.background.setImage(this.backgroundImage);
		this.foreground.setImage(this.foregroundImage);

		this.needleTop = new Needle();
		this.needleLeft = new Needle();
		this.needleRight = new Needle();
		
		this.needleTop.initialize(needleFilepathTop, -80, 80, topMinValue, topMaxValue);
		this.needleLeft.initialize(needleFilepathLeft, 20, 160, leftMinValue, leftMaxValue);
		this.needleRight.initialize(needleFilepathRight, -20, -160, rightMinValue, rightMaxValue);
		
		this.getChildren().addAll(this.background, this.needleTop, this.needleLeft, this.needleRight, this.foreground);
	}
	
	public void mapTopValue(double value) {
		this.needleTop.mapValue(value);
	}
	
	public void mapLeftValue(double value) {
		this.needleLeft.mapValue(value);
	}
	
	public void mapRightValue(double value) {
		this.needleRight.mapValue(value);
	}
}
