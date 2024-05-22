package com.example.asteroids2024;

import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


// create subclass of javafx class Application specific to Asteroids
public class Asteroids_Game extends Application {

    public static int WIDTH = 600;
    public static int HEIGHT = 400;
    private boolean shipLeft = false;
    private boolean shipRight = false;

    private boolean shipAccelerate = false;
    private boolean shipDecelerate = false;




    @Override
    // use existing class Stage - the interface for managing the window
    public void start(Stage stage) throws Exception {
        // use existing class Pane to create layout container
        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);
        // Set the background color to black
        pane.setStyle("-fx-background-color: black;");

        // Instantiate the Ship object
        Ship ship = new Ship(WIDTH / 2, HEIGHT / 2);
        //Instantiate list of Asteroid objects
        Random rnd = new Random();
        List<Asteroid> asteroids = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            Asteroid asteroid = new Asteroid(rnd.nextInt(200), rnd.nextInt(150), AsteroidSize.LARGE);
            asteroids.add(asteroid);
        }

        // get an empty list for bullets but don't add to screen yet
        List<Bullet> bullets = new ArrayList<>();



        pane.getChildren().add(ship.getCharacter());
        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));
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
            if (event.getCode() == KeyCode.SPACE) {
                // Shoot a bullet
                Bullet bullet = new Bullet((int) ship.getCharacter().getTranslateX(), (int) ship.getCharacter().getTranslateY());
                bullet.getCharacter().setRotate(ship.getCharacter().getRotate());
                bullets.add(bullet);
                bullet.accelerate();
                bullet.setMovement(bullet.getMovement().multiply(50));

                pane.getChildren().add(bullet.getCharacter());
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

                ship.move();
                asteroids.forEach(asteroid -> asteroid.move());

                asteroids.forEach(asteroid -> {
                    if (ship.collision(asteroid)) {
                        stop();
                    }

                });
                bullets.forEach((bullet -> bullet.move()));

                List<Bullet> usedBullets = bullets.stream().filter(bullet -> {
                    List<Asteroid> collisions =  asteroids.stream()
                                                .filter(asteroid -> asteroid.collision(bullet))
                                                .collect(Collectors.toList());
                    if(collisions.isEmpty()) {
                        return false;
                    }
                    collisions.stream().forEach(hit -> {
                        asteroids.remove(hit);
                        pane.getChildren().remove(hit.getCharacter());
                    });
                    return true;
                }).collect(Collectors.toList());
                usedBullets.forEach(bullet -> {
                    pane.getChildren().remove(bullet.getCharacter());
                    bullets.remove(bullet);
                });



            }
        };
                timer.start();
            }
            public static void main(String[] args) {
                launch(args);
            }
        }