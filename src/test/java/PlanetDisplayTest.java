package com.opcode.fx_test;

import java.beans.Transient;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.input.KeyCode;

import javafx.application.Platform;

public class PlanetDisplayTest extends Application {
    /* Check calculations done by the planet display class */

    int focusX;
    int focusY;
    PlanetDisplay pDisplay;

    @Override
    public void start(Stage primaryStage) {
        pDisplay = new PlanetDisplay(500, 500);

        pDisplay.initialize(new File("target/res/map.png").toURI().toString()); // because the tests are executed from the root directory

        focusX = 250;
        focusY = 450;
        pDisplay.setFocusPoint(focusX, focusY);

        

        StackPane root = new StackPane();
        root.getChildren().add(pDisplay);
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.W) {
                System.out.println("W key was pressed");
                focusY += 20;
            }
            if (e.getCode() == KeyCode.A) {
                System.out.println("A key was pressed");
                focusX -= 20;
            }
            if (e.getCode() == KeyCode.S) {
                System.out.println("S key was pressed");
                focusY -= 20;
            }
            if (e.getCode() == KeyCode.D) {
                System.out.println("D key was pressed");
                focusX += 20;
            }
            System.out.println(focusY);
            pDisplay.setFocusPoint(focusX, focusY);
        });

        primaryStage.show();
    }

    @Test
	public void windowTest() {
        this.launch();
    }
}