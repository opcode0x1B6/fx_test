package com.opcode.fx_test;

import java.beans.Transient;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

public class PlanetDisplayTest extends Application {
    /* Check calculations done by the planet display class */

    @Override
    public void start(Stage primaryStage) {
        PlanetDisplay pDisplay = new PlanetDisplay(500, 500);

        pDisplay.initialize(new File("target/res/map.png").toURI().toString()); // because the tests are executed from the root directory

        pDisplay.setFocusPoint(250, 450);

        StackPane root = new StackPane();
        root.getChildren().add(pDisplay);
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }

    @Test
	public void windowTest() {
        this.launch();
    }
}