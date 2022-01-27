package com.opcode.fx_test;

import java.io.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Window extends Application {

    Image img_btn_off;
    Image img_btn_on;
    ImageView button;
    boolean buttonState;
    
    Media buttonClickSound;
    MediaPlayer mediaPlayer;

    public void onButtonClick(MouseEvent e) {
    	System.out.println("click");
    	this.mediaPlayer = new MediaPlayer(this.buttonClickSound); // the mediaplayer seems to be unable to stop so we regenerate it
    	this.buttonState = !this.buttonState;
    	if (this.buttonState) {
    		this.button.setImage(this.img_btn_on);
    	}
    	else {
    		this.button.setImage(this.img_btn_off);
    	}
    	this.mediaPlayer.play();
    }
    
    @Override
    public void start(Stage primaryStage) {
    	this.img_btn_off = new Image(new File("res/button_off.png").toURI().toString());
    	this.img_btn_on = new Image(new File("res/button_on.png").toURI().toString());
    
    	this.button = new ImageView(this.img_btn_off);
    	this.button.setOnMouseClicked(e -> this.onButtonClick(e)); // wtf is this syntax from hell???
    	this.buttonState = false;
    	this.buttonClickSound = new Media(new File("res/click.mp3").toURI().toString());

    	this.mediaPlayer = new MediaPlayer(this.buttonClickSound);

    	Scene scene = new Scene(new StackPane(this.button));
    	primaryStage.setTitle("Button test");
    	primaryStage.setScene(scene);
    	primaryStage.sizeToScene();
    	primaryStage.show();
    }
    
    public static void main(String[] args) {
    	Window.launch(args);
    }
}
