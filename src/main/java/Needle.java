package com.opcode.fx_test;

import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;

import javafx.geometry.Point3D;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class Needle extends ImageView{
	Image needleImage;
	
	Rotate needleRotation;
	
	double BLUE_TRIGGER = 0.75;
	double RED_TRIGGER = 0.75;
	double GREEN_TRIGGER_BELOW = 0.15;
	
	double startRotation;
	double endRotation;
	
	double startValue;
	double endValue;
	
	public void initialize(String needleFilepath, double startRotation, double endRotation, double startValue, double endValue) {
		this.needleImage = new Image(needleFilepath);
		this.setImage(this.needleImage);
		this.setPickOnBounds(false); // transparent part not clickable. prevents blocking of other stuff due to rotation
		
		Point3D pivot = this.findPivotPoint(this.needleImage);
		
		this.needleRotation = new Rotate(0, pivot.getX(), pivot.getY());
		this.getTransforms().add(this.needleRotation);
		
		this.startRotation = startRotation;
		this.endRotation = endRotation;
		this.startValue = startValue;
		this.endValue = endValue;
		
		this.mapValue(this.startValue);
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
		// we calculate the average midpoint between all registered pixels which have a kind of pinkish color
		
		int hits = 0;
		int px = 0;
		int py = 0;
		
		PixelReader reader = img.getPixelReader();
		Point3D pivot = new Point3D(0, 0, 0);
		
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				if ((reader.getColor(x, y).getRed() > RED_TRIGGER) && (reader.getColor(x, y).getGreen() < GREEN_TRIGGER_BELOW) && (reader.getColor(x, y).getBlue() > BLUE_TRIGGER)) {
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
