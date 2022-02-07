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

        clearScreen();
    }

    double getDistanceToCenter(double posX, double posY, double middleX, double middleY) {
        double distX = posX - middleX;
        double distY = posY - middleY;
        double dist = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
        return dist;
    }

    boolean isInCircle(double posX, double posY, double middleX, double middleY, double radius) {
        return getDistanceToCenter(posX, posY, middleX, middleY) <= radius;
    }

    double multiplyOffsetToCenterLinear(double posX, double posY, double middleX, double middleY, double radius) {
        double dist = getDistanceToCenter(posX, posY, middleX, middleY);
        return 1.0 + (dist / radius);
    }

    double multiplyOffsetToCenter(double posX, double posY, double middleX, double middleY, double radius) {
        double dist = getDistanceToCenter(posX, posY, middleX, middleY);

        double distFactor = (dist / radius);
        double lensCorrectionModifier = 0.0; // will create a bigger lens distortion on negative values and lessen the distortion on positive ones
        double curveModifier = 1.0 / (2.0 - distFactor) + lensCorrectionModifier;

        return curveModifier;
    }

    void clearScreen() {
        graphicsContext.setFill(javafx.scene.paint.Color.PINK);
        graphicsContext.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    double longitudeFromMapPosition(double posX, double mapWidth) {
        return (posX/mapWidth * Math.PI * 2.0);
        //return posX/mapWidth*360.0;
    }

    double latitudeFromMapPosition(double posY, double mapHeight) {
        return (posY/mapHeight * Math.PI * 2.0);
        //return posY/mapHeight*360.0-180.0;
    }

    double posXOrthographicProjection(double radius, double longitude, double latitude, double longitudeOrigin) {
        return radius * Math.cos(latitude) * Math.sin(longitude - longitudeOrigin);
    }

    double posYOrthographicProjection(double radius, double longitude, double latitude, double longitudeOrigin, double latitudeOrigin) {
        return radius * ( Math.cos(latitudeOrigin) * Math.sin(latitude) - Math.sin(latitudeOrigin) * Math.cos(latitude) * Math.cos(longitude - longitudeOrigin) );
    }

    public void setFocusPoint(double x, double y) {
        System.out.println("setting focus ");
        clearScreen();

        double middleX = this.getWidth()/2;
        double middleY = this.getHeight()/2;

        int posX = 0;
        int posY = 0;
        int posXWrapOffset = 0;

        int outputPosX = 0;
        int outputPosY = 0;

        double radius = Math.min(middleX, middleY);

        int imgWidth = (int)planetMapImage.getWidth();
        int imgHeight = (int)planetMapImage.getHeight();

        for (int rx = (int)-middleX; rx < (int)middleX; rx++) {
            for (int ry = (int)-middleY; ry < (int)middleY; ry++) {
                //System.out.println("rx " + rx + " ry " + ry);
                outputPosX = (int)(middleX + rx);
                outputPosY = (int)(middleY + ry);
                if (!isInCircle((double)outputPosX, (double)outputPosY, middleX, middleY, radius)) {
                    continue;
                }

                posY = (int)middleY + (int)(ry * multiplyOffsetToCenter((double)rx, (double)ry, 0, 0, radius)) + (int)y;
                //posY = (int)(outputPosY);
                //System.out.println("posY " + posY);
                posXWrapOffset = 0;
                if ((int)(Math.abs(posY) / imgHeight) % 2 == 0) {
                    posXWrapOffset = (int)imgWidth/2;
                    posY = imgHeight - ((int)(Math.abs(posY) % imgHeight) + 1);
                }
                if (posY < 0) { 
                    posY = (int)(Math.abs(posY) % imgHeight);
                }
                if (posY >= imgHeight) {
                    posY = (int)(Math.abs(posY) % imgHeight);
                }
                //System.out.println("posY " + posY);

                //System.out.println("rx " + rx + " ry " + ry);
                posX = (int)middleX + (int)(rx * multiplyOffsetToCenter((double)rx, (double)ry, 0, 0, radius)) + posXWrapOffset + (int)x;
                //posX = (int)(outputPosX);
                //System.out.println("posX " + posX);
                posX = (int)(Math.abs(posX) % imgWidth);
                //System.out.println("posX " + posX);

                this.contextWriter.setColor(outputPosX, outputPosY, mapReader.getColor(posX, posY));
            }
        }
    }
}