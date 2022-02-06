package com.opcode.fx_test;

import java.lang.Math;

import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.image.*;

class PlanetDisplay extends Canvas {
    Image planetMapImage;
    GraphicsContext graphicsContext;
    PixelWriter contextWriter;
    PixelReader mapReader;

    PlanetDisplay(double width, double height) {
        super(width, height);
        this.graphicsContext = this.getGraphicsContext2D();
        this.contextWriter = this.graphicsContext.getPixelWriter();
    }

    public void initialize(String planetMapFilepath) {
        planetMapImage = new Image(planetMapFilepath);
        mapReader = planetMapImage.getPixelReader();

        System.out.println("initialized image " + this.planetMapImage);

        graphicsContext.setFill(javafx.scene.paint.Color.PINK);
        graphicsContext.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    boolean isInCircle(double posX, double posY, double middleX, double middleY, double radius) {
        double distX = posX - middleX;
        double distY = posY - middleY;
        double dist = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
        return dist <= radius;
    }

    public void setFocusPoint(double x, double y) {
        System.out.println("setting focus ");

        int startX = (int)(x - this.getWidth()/2);
        int startY = (int)(y - this.getHeight()/2);

        int posX = 0;
        int posY = 0;

        for (int rx = 0; rx < (int)this.getWidth(); rx++) {
            for (int ry = 0; ry < (int)this.getHeight(); ry++) {
                if (!isInCircle((double)rx, (double)ry, this.getWidth()/2, this.getHeight()/2, this.getWidth()/2)) {
                    continue;
                }
                posX = startX + rx;
                if (posX < 0) { 
                    posX += (int)(planetMapImage.getWidth());
                }
                if (posX >= (int)planetMapImage.getWidth()) { 
                    posX -= (int)(planetMapImage.getWidth());
                }

                posY = startY + ry;
                if (posY < 0) { 
                    posY += (int)(planetMapImage.getHeight());
                }
                if (posY >= (int)planetMapImage.getHeight()) { 
                    posY -= (int)(planetMapImage.getHeight());
                }

                this.contextWriter.setColor(rx, ry, mapReader.getColor(posX, posY));
            }
        }
    }
}