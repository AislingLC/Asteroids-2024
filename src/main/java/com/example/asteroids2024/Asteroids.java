package com.example.asteroids2024;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;


// create subclass of javafx class Application specific to Asteroids
public class Asteroids extends Application {

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

        pane.getChildren().add(ship.getCharacter());
        // use existing Scene class, to hold visual elements and UI hierarchy

        Scene scene = new Scene(pane);
        stage.setTitle("Asteroids!");
        stage.setScene(scene);
        stage.show();
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                ship.rotateLeft();
            }

            if (event.getCode() == KeyCode.RIGHT) {
                ship.rotateRight();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}