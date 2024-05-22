package com.example.asteroids2024;

import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;


// create subclass of javafx class Application specific to Asteroids
public class Asteroids_Game extends Application {
    private boolean shipLeft = false;
    private boolean shipRight = false;

    private boolean shipAccelerate = false;
    private boolean shipDecelerate = false;

    @Override
    // use existing class Stage - the interface for managing the window
    public void start(Stage stage) throws Exception {
        // use existing class Pane to create layout container
        Pane pane = new Pane();
        pane.setPrefSize(600, 400);
        // Set the background color to black
        pane.setStyle("-fx-background-color: black;");

        // Instantiate the Ship object
        Ship ship = new Ship(300, 200);
        //Instantiate Asteroid object
        Asteroid asteroid = new Asteroid(50,50);

        pane.getChildren().add(ship.getCharacter());
        pane.getChildren().add(asteroid.getCharacter());
        // use existing Scene class, to hold visual elements and UI hierarchy

        Scene scene = new Scene(pane);
        stage.setTitle("Asteroids!");
        stage.setScene(scene);
        stage.show();
        // handle key inputs
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                shipLeft = true;
            }
            if (event.getCode() == KeyCode.RIGHT) {
                shipRight = true;
            }
            if (event.getCode() == KeyCode.UP) {
                shipAccelerate = true;
            }
            if (event.getCode() == KeyCode.DOWN) {
                shipDecelerate = true;
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                shipLeft = false;
            }
            if (event.getCode() == KeyCode.RIGHT) {
                shipRight = false;
            }
            if (event.getCode() == KeyCode.UP) {
                shipAccelerate = false;
            }
            if (event.getCode() == KeyCode.DOWN) {
                shipDecelerate = false;
            }
        });


        // Create an AnimationTimer to handle the ship rotation
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (shipLeft) {
                    ship.rotateLeft();
                }
                if (shipRight) {
                    ship.rotateRight();
                }
                if (shipAccelerate) {
                    ship.accelerate();
                }
                if (shipDecelerate) {
                    ship.decelerate();
                }

                ship.updateMovement();
                ship.move();
                asteroid.move();

                if (ship.collision(asteroid)) {
                    stop();
                }
            }
        };
        timer.start();
    }

        public static void main (String[]args){
            launch(args);
        }
    }