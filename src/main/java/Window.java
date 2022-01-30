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
    
    AbstractPowerplant powerplant;
    NeedleMeter meter;

    public void onButtonClick(MouseEvent e) {
    	ImageButton clickedButton = (ImageButton)e.getSource();
    	this.mediaPlayer = new MediaPlayer(this.buttonClickSound); // the mediaplayer seems to be unable to stop so we regenerate it
    	clickedButton.setState(!clickedButton.getState());
    	
    	if (clickedButton.getId() == "#imgButton1")  {
    		powerplant.setMotorStatus(clickedButton.getState());
    	} else {
    		powerplant.setBurnerStatus(clickedButton.getState());
    	}
    	
    	if (clickedButton.getState()) {
    		clickedButton.setImage(this.img_btn_on);
    	}
    	else {
    		clickedButton.setImage(this.img_btn_off);
    	}
    	this.mediaPlayer.play();
    }
    
    @Override
    public void start(Stage primaryStage) {
    
    	this.powerplant = new AbstractPowerplant();
    	this.powerplant.start();
    
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
    	
    	this.meter = (NeedleMeter)this.root.lookup("#needleMeter");
    	this.meter.initialize(
    	new File("res/nm_back.png").toURI().toString(),
    	new File("res/nm_needle.png").toURI().toString(),
    	new File("res/nm_front.png").toURI().toString(),
    	-90.0,
    	90.0,
    	0.0,
    	1000.0
    	);
    	
    	this.powerplant.registerMeter(this.meter);
    	
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
