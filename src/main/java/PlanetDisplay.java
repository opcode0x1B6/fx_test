package com.opcode.fx_test;

import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.image.*;

class PlanetDisplay extends Canvas {
    Image planetMapImage;
    GraphicsContext graphicsContext;
    PixelWriter pixelWriter;

    PlanetDisplay(double width, double height) {
        super(width, height);
        this.graphicsContext = this.getGraphicsContext2D();
        this.pixelWriter = this.graphicsContext.getPixelWriter();
    }

    public void initialize(String planetMapFilepath) {
        this.planetMapImage = new Image(planetMapFilepath);
        graphicsContext.setFill(Color.PINK);
        graphicsContext.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    public void setFocusPoint(double x, double y) {
        this.pixelWriter.setColor((int)x, (int)y, Color.GREEN);
    }
}