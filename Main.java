package com.opcode.fx_test;
/*
import java.io.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;*/

public class Main { /* extends Application {

    Image img_btn_off;
    Image img_btn_on;
    ImageView button;
    
    public void onButtonClick(MouseEvent e) {
    	System.out.println("click");
    	this.button.setImage(this.img_btn_on);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	this.img_btn_off = new Image(new File("/button_off.png").toURI().toString());
    	this.img_btn_on = new Image(new File("/button_on.png").toURI().toString());
    
    	this.button = new ImageView(this.img_btn_off);
    	this.button.setOnMouseClicked(e -> this.onButtonClick(e)); // wtf is this syntax from hell???
    	
    	Scene scene = new Scene(new StackPane(this.button));
    	primaryStage.setTitle("Button test");
    	primaryStage.setScene(scene);
    	primaryStage.sizeToScene();
    	primaryStage.show();
    }
    
    public static void main(String[] args) {
    	launch(args);
    } */
    
    public static void main(String[] args) {
    	System.out.println("" + Main.getName());
    }
}
