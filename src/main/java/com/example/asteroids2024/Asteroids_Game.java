package com.example.asteroids2024;

import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicInteger;



// create subclass of javafx class Application specific to Asteroids
public class Asteroids_Game extends Application {

    public static int WIDTH = 600;
    public static int HEIGHT = 400;
    private boolean shipLeft = false;
    private boolean shipRight = false;

    private boolean shipAccelerate = false;
    private boolean shipDecelerate = false;

    private int level = 1; //tracks level
    private Text levelText; // text variable to display level
    private AtomicInteger points = new AtomicInteger();

    private Text pointsText; // text variable to total points accumulated

    @Override
    // use existing class Stage - the interface for managing the window
    public void start(Stage stage) throws Exception {
        // use existing class Pane to create layout container
        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);
        // Set the background color to black
        pane.setStyle("-fx-background-color: black;");

        // Create the level text to display on screen
        levelText = new Text("LEVEL: " + level);
        levelText.setFont(new Font(20));
        levelText.setFill(javafx.scene.paint.Color.WHITE);
        levelText.setTranslateX(10); // Position at the top-left corner
        levelText.setTranslateY(20);
        pane.getChildren().add(levelText);

        pointsText = new Text("POINTS: " + points);
        pointsText.setFont(new Font(20));
        pointsText.setFill(javafx.scene.paint.Color.WHITE);
        pointsText.setTranslateX(10); // Position at the bottom-left corner
        pointsText.setTranslateY(370);
        pane.getChildren().add(pointsText);


        // Instantiate the Ship object
        Ship ship = new Ship(WIDTH / 2, HEIGHT / 2);
        //Instantiate list of Asteroid objects
        Random rnd = new Random();
        List<Asteroid> asteroids = new ArrayList<>();
        for (int i = 0; i < 1; i++) {

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
                        // add logic to spawn new asteroids one size smaller in for large and medium asteroids
                        if (hit.getSize() == AsteroidSize.LARGE) {
                            pointsText.setText("Points: " + points.addAndGet(250));
                            for (int i = 0; i < 2; i++) {
                                double speedMultiple = 1 + rnd.nextDouble();
                                double newSpeed = hit.getSpeed() * speedMultiple;
                                double angleChange = (0.5 - rnd.nextDouble()) * 60;
                                double newAngle = hit.getRotate() + angleChange;
                                Asteroid newAsteroid = new Asteroid(
                                        (int) hit.getCharacter().getTranslateX(),
                                        (int) hit.getCharacter().getTranslateY(),
                                        AsteroidSize.MEDIUM);
                                newAsteroid.setSpeed(newSpeed);
                                newAsteroid.setRotate(newAngle);
                                asteroids.add(newAsteroid);

                                pane.getChildren().add(newAsteroid.getCharacter());
                            } } else if (hit.getSize() == AsteroidSize.MEDIUM) {
                            pointsText.setText("Points: " + points.addAndGet(100));
                            for (int i = 0; i < 2; i++) {
                                double speedMultiple = 1 + rnd.nextDouble();
                                double newSpeed = hit.getSpeed() * speedMultiple;
                                double angleChange = (0.5 - rnd.nextDouble()) * 60;
                                double newAngle = hit.getRotate() + angleChange;
                                Asteroid newAsteroid = new Asteroid(
                                        (int) hit.getCharacter().getTranslateX(),
                                        (int) hit.getCharacter().getTranslateY(),
                                        AsteroidSize.SMALL);
                                newAsteroid.setSpeed(newSpeed);
                                newAsteroid.setRotate(newAngle);

                                asteroids.add(newAsteroid);
                                pane.getChildren().add(newAsteroid.getCharacter());
                            } } else {
                            pointsText.setText("Points: " + points.addAndGet(25));
                        }


                        asteroids.remove(hit);
                        pane.getChildren().remove(hit.getCharacter());
                    });
                    return true;
                }).collect(Collectors.toList());
                usedBullets.forEach(bullet -> {
                    pane.getChildren().remove(bullet.getCharacter());
                    bullets.remove(bullet);
                });



                // Check if all asteroids are destroyed to increase the level
                if (asteroids.isEmpty()) {
                    level++;
                    levelText.setText("LEVEL: " + level);
                    for (int i = 0; i < level; i++) {
                        Asteroid asteroid = new Asteroid(rnd.nextInt(200), rnd.nextInt(150), AsteroidSize.LARGE);

                        asteroids.add(asteroid);
                        pane.getChildren().add(asteroid.getCharacter());
                    }
                }


                // Spawn in AlienShip approximately every 17 seconds
                if (rnd.nextDouble() < 0.001) {
                    AlienShip alienShip = new AlienShip(100, 100);
                    if(!alienShip.collision(ship)) {
                        pane.getChildren().add(alienShip.getCharacter());
                    }
                }

            }
        };
                timer.start();
            }
            public static void main(String[] args) {
                launch(args);
            }
        }