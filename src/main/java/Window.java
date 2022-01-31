package com.opcode.fx_test;

import java.io.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.util.Duration;


import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Window extends Application {

	Parent root;

    Image img_btn_off;
    Image img_btn_on;
    ImageView button;
    
    Media buttonClickSound;
    MediaPlayer mediaPlayer;
    
    TripleMeter meter;

    public void onButtonClick(MouseEvent e) {
    	ImageButton clickedButton = (ImageButton)e.getSource();
    	this.mediaPlayer = new MediaPlayer(this.buttonClickSound); // the mediaplayer seems to be unable to stop so we regenerate it
    	clickedButton.setState(!clickedButton.getState());
    	
    	if (clickedButton.getState()) {
    		clickedButton.setImage(this.img_btn_on);
    		this.meter.mapTopValue(100);
    	}
    	else {
    		clickedButton.setImage(this.img_btn_off);
    		this.meter.mapTopValue(0);
    	}
    	this.mediaPlayer.play();
    }
    
    @Override
    public void start(Stage primaryStage) {
    
    	this.img_btn_off = new Image(new File("res/button_off.png").toURI().toString());
    	this.img_btn_on = new Image(new File("res/button_on.png").toURI().toString());

    	this.buttonClickSound = new Media(new File("res/click.mp3").toURI().toString());
    	this.mediaPlayer = new MediaPlayer(this.buttonClickSound);

	try {
		File inFile = new File("res/root.fxml");
		FileInputStream fIn = new FileInputStream(inFile);
		FXMLLoader loader = new FXMLLoader();
		this.root = loader.load(fIn);
	    	Scene scene = new Scene(this.root);
	    	
	    	String[] nameList = {"#imgButton1", "#imgButton2"}; // reengineer better way to add those infos. this has the elegance of a bison in a china shop
	    	for (String btnName: nameList) {
			ImageButton imgb = (ImageButton)this.root.lookup(btnName);
			imgb.setImage(this.img_btn_off);
			imgb.setOnMouseClicked(e -> this.onButtonClick(e)); // wtf is this syntax from hell???
			imgb.setState(false);
		}
	    	
	    	this.meter = (TripleMeter)this.root.lookup("#tripleMeter");
	    	this.meter.initialize(
	    	new File("res/tm_back.png").toURI().toString(),
	    	new File("res/tm_needle_01.png").toURI().toString(),
	    	new File("res/tm_needle_02.png").toURI().toString(),
	    	new File("res/tm_needle_03.png").toURI().toString(),
	    	new File("res/tm_front.png").toURI().toString(),
	    	0.0,
	    	100.0,
	    	0.0,
	    	10.0,
	    	0.0,
	    	10.0
	    	);
	    	
	    	primaryStage.setTitle("Button test");
	    	primaryStage.setScene(scene);
	    	primaryStage.sizeToScene();
	    	primaryStage.show();
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    }
    
    public static void main(String[] args) {
    	Window.launch(args);
    }
}
