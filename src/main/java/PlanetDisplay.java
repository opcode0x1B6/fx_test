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

    double multiplyOffsetToCenter(double posX, double posY, double middleX, double middleY, double radius) {
        double dist = getDistanceToCenter(posX, posY, middleX, middleY);
        return 1.0 + (dist / radius);
    }

    void clearScreen() {
        graphicsContext.setFill(javafx.scene.paint.Color.PINK);
        graphicsContext.fillRect(0, 0, this.getWidth(), this.getHeight());
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
                while (posY < 0) { 
                    posY = imgHeight - (posY + imgHeight + 1);
                    posXWrapOffset += imgWidth/2;
                }
                while (posY >= imgHeight) { 
                    posY = imgHeight + (imgHeight - posY - 1);
                    posXWrapOffset += imgWidth/2;
                }

                //System.out.println("rx " + rx + " ry " + ry);
                posX = (int)middleX + (int)(rx * multiplyOffsetToCenter((double)rx, (double)ry, 0, 0, radius)) + posXWrapOffset + (int)x;
                //posX = (int)(outputPosX);
                //System.out.println("posX " + posX);
                while (posX < 0) { 
                    posX += imgWidth;
                }
                while (posX >= imgWidth) { 
                    posX -= imgWidth;
                }

                

                this.contextWriter.setColor(outputPosX, outputPosY, mapReader.getColor(posX, posY));
            }
        }
    }
}