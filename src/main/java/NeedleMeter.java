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
	ImageView needle;
	ImageView foreground;
	
	Image backgroundImage;
	Image needleImage;
	Image foregroundImage;
	
	Rotate needleRotation;
	
	double BLUE_TRIGGER = 0.7;
	
	double startRotation;
	double endRotation;
	
	double startValue;
	double endValue;
	
	public void initialize(String backgroundFilepath, String needleFilepath, String foregroundFilepath, double startRotation, double endRotation, double startValue, double endValue) {
		this.backgroundImage = new Image(backgroundFilepath);
		this.needleImage = new Image(needleFilepath);
		this.foregroundImage = new Image(foregroundFilepath);
		
		this.background = new ImageView();
		this.needle = new ImageView();
		this.foreground = new ImageView();
		
		this.background.setImage(this.backgroundImage);
		this.needle.setImage(this.needleImage);
		this.foreground.setImage(this.foregroundImage);
		
		Point3D pivot = this.findPivotPoint(this.needleImage);
		
		this.needleRotation = new Rotate(0, pivot.getX(), pivot.getY());
		this.needle.getTransforms().add(this.needleRotation);
		
		this.startRotation = startRotation;
		this.endRotation = endRotation;
		this.startValue = startValue;
		this.endValue = endValue;
		
		this.mapValue(this.startValue);
		
		this.getChildren().addAll(this.background, this.needle, this.foreground);
	}
	
	public void mapValue(double value) {
		double rotationRange = this.endRotation - this.startRotation;
		double diffToStartValue = value - this.startValue;
		double targetRotation = (rotationRange / this.endValue) * diffToStartValue + this.startRotation;
		
		// prevent values outside of the range
		if (targetRotation < this.startRotation) {
			targetRotation = this.startRotation;
		}
		if (targetRotation > this.endRotation) {
			targetRotation = this.endRotation;
		}
		
		this.setNeedleRotation(targetRotation);
	}
	
	public void setNeedleRotation(double rotation) {
		this.needleRotation.setAngle(rotation);
	}
	
	private Point3D findPivotPoint(Image img) {
		// we calculate the average midpoint between all registered pixels
		
		int hits = 0;
		int px = 0;
		int py = 0;
		
		PixelReader reader = img.getPixelReader();
		Point3D pivot = new Point3D(0, 0, 0);
		
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				if (reader.getColor(x, y).getBlue() > BLUE_TRIGGER) {
					hits = hits + 1;
					px = px + x;
					py = py + y;
				}
			}
		}
		
		if (hits > 0) {
			pivot = new Point3D(px/hits, py/hits, 0);
		}
		return pivot;
	}
}
