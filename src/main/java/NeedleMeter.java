package com.opcode.fx_test;

import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;

import javafx.geometry.Point3D;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class NeedleMeter extends StackPane{
	ImageView background;
	Needle needle;
	ImageView foreground;
	
	Image backgroundImage;
	Image foregroundImage;
	
	public void initialize(String backgroundFilepath, String needleFilepath, String foregroundFilepath, double startRotation, double endRotation, double startValue, double endValue) {
		this.backgroundImage = new Image(backgroundFilepath);
		this.foregroundImage = new Image(foregroundFilepath);
		
		this.background = new ImageView();
		this.foreground = new ImageView();
		
		this.background.setImage(this.backgroundImage);
		this.foreground.setImage(this.foregroundImage);
		this.background.setPickOnBounds(false); 
		this.foreground.setPickOnBounds(false);
		
		this.needle = new Needle();
		this.needle.initialize(needleFilepath, startRotation, endRotation, startValue, endValue);
		
		this.getChildren().addAll(this.background, this.needle, this.foreground);
	}
	
	public void mapValue(double value) {
		this.needle.mapValue(value);
	}
}
