package com.example.asteroids2024;

import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.KeyFrame;


// create subclass of javafx class Application specific to Asteroids
public class Asteroids_Game extends Application {

    public static int WIDTH = 600;
    public static int HEIGHT = 400;

    private Stage stage; // for start screen

    private boolean shipLeft = false;
    private boolean shipRight = false;

    private boolean shipAccelerate = false;
    private boolean shipDecelerate = false;

    private int level = 1; //tracks level
    private Text levelText; // text variable to display level
    private AtomicInteger points = new AtomicInteger();

    private Text pointsText; // text variable to total points accumulated

    private boolean safeJump; // indicates if hyperspace jump is free from immediate collision with asteroids

    private int lives = 5; // int to store player lives
    private Text livesText; // text variable to show player lives

    private int finalScore;

    @Override
    // use existing class Stage - the interface for managing the window

    public void start(Stage stage) throws Exception {
        this.stage = stage;
        showStartScreen();
    }



    private void showStartScreen() {
        Pane startPane = new Pane();
        startPane.setPrefSize(WIDTH, HEIGHT);
        startPane.setStyle("-fx-background-color: black;");
        GameText startText = new GameText("Aisling Presents: \nAtari Asteroids in Java\n\n\nPress ENTER to Start \nPress I for Instructions \nPress H for High Scores",
                150, 50, 30  );

        startPane.getChildren().add(startText);

        Scene startScene = new Scene(startPane);
        stage.setTitle("Asteroids!");
        stage.setScene(startScene);
        stage.show();

        startScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                showGameScreen();
            }
            if (event.getCode() == KeyCode.I) {
                showInstructionScreen();
            }

            if (event.getCode() == KeyCode.H) {
                showHighScoresScreen();
            }
        });
    }




    private void showInstructionScreen() {
        Pane instructionPane = new Pane();
        instructionPane.setPrefSize(WIDTH, HEIGHT);
        instructionPane.setStyle("-fx-background-color: black;");


        GameText titleText = new GameText("Game Instructions: ",
                150, 50, 30  );

        instructionPane.getChildren().add(titleText);


        GameText instructionText = new GameText(
                "Press  LEFT to turn anticlockwise \n" +
                "Press RIGHT to turn clockwise \n" +
                "Press UP to accelerate \n" +
                        "Press DOWN to decelerate \n" +
                        "Press SPACE to shoot \n" +
                        "Press ENTER to hyperspace teleport \n \n  \n \n \n" +
                        "To return to START menu hit S" , 150, 120, 20);

        instructionPane.getChildren().add(instructionText);


        Scene instructionScene = new Scene(instructionPane);
        stage.setTitle("Asteroids!");
        stage.setScene(instructionPane.getScene());
        stage.show();

        instructionScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.S) {
                showStartScreen();
            }
        });
    }


    private void showGameOverScreen() {
        Pane gameOverPane = new Pane();
        gameOverPane.setPrefSize(WIDTH, HEIGHT);
        gameOverPane.setStyle("-fx-background-color: black;");

        GameText titleText = new GameText("Game Over ", 200, 100, 30);
        gameOverPane.getChildren().add(titleText);


        GameText finalScoreText = new GameText(
                "You scored: " + finalScore , 200, 150, 20);
        gameOverPane.getChildren().add(finalScoreText);

        GameText newHighScoreText = new GameText("New high score!", 200, 200, 30);

        // logic to retrieve previous high scores and compare new score with highscores
        List<Integer> previousHighScores =  HighScoreReader.readHighScoresAsList("highScores.txt");
        boolean newHighscore = false;
        for (int score : previousHighScores) {
            if (finalScore > score){
                newHighscore = true;
            }
        }
        // get new highscore list if a new highscore has been achieved
        if (newHighscore){
            // remove previous lowest high score
            int lowestHigh = Collections.min(previousHighScores);
            previousHighScores.remove(Integer.valueOf(lowestHigh));
            // add new score
            previousHighScores.add(finalScore);
            // sort
            previousHighScores.sort(Collections.reverseOrder());
            HighScoreWriter.writeHighScores(previousHighScores, "highScores.txt");

            gameOverPane.getChildren().add(newHighScoreText);
        }


        GameText returnToStartText = new GameText("Press S to return to start screen \nPress H to view high scores",
                200, 350, 20);
        gameOverPane.getChildren().add(returnToStartText);

        Scene gameOverScene = new Scene(gameOverPane);
        stage.setTitle("Asteroids!");
        stage.setScene(gameOverPane.getScene());
        stage.show();

        gameOverScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.S) {
                showStartScreen();
            }
            if (event.getCode() == KeyCode.H) {
                showHighScoresScreen();
            }
        });
    }

    private void showHighScoresScreen() {
        Pane highScoresPane = new Pane();
        highScoresPane.setPrefSize(WIDTH, HEIGHT);
        highScoresPane.setStyle("-fx-background-color: black;");

        GameText titleText = new GameText("High Scores: ", 200, 100, 30);
        highScoresPane.getChildren().add(titleText);

        String highScoresString = HighScoreReader.readHighScoresasString("highScores.txt");
        GameText highScoresText = new GameText(highScoresString, 150, 150, 20);
        highScoresPane.getChildren().add(highScoresText);


        GameText returnToStart = new GameText("Press S to return to start screen", 150, 370, 20);
        highScoresPane.getChildren().add(returnToStart);


        Scene highScoreScene = new Scene(highScoresPane);
        stage.setTitle("Asteroids!");
        stage.setScene(highScoresPane.getScene());
        stage.show();

        highScoreScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.S) {
                showStartScreen();
            }
        });
    }






    private void showGameScreen() {
        // use existing class Pane to create layout container
        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);
        // Set the background color to black
        pane.setStyle("-fx-background-color: black;");

        // Create the level text to display on screen
        GameText levelText = new GameText("LEVEL: " + level, 10, 20, 20);
        pane.getChildren().add(levelText);

        // Create the points text to display on screen
        GameText pointsText = new GameText("POINTS: " + points, 10, 370, 20);
        pane.getChildren().add(pointsText);

        // Create the lives text to display on screen
        GameText livesText = new GameText("LIVES: " + "A".repeat(lives), 450, 20, 20);
        pane.getChildren().add(livesText);

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

        // get an empty list for alienShip but don't add to screen yet
        List<AlienShip> alienShips = new ArrayList<>();
        // get an empty list for alien bullets but don't add to screen yet
        List<Bullet> alienBullets = new ArrayList<>();


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

            if (event.getCode() == KeyCode.ENTER) {
                // start hyperspace and keep repeating until landing in a safe location
                do {
                    ship.hyperspace();
                    safeJump = true;
                    for (Asteroid asteroid : asteroids) {
                        if (ship.collision(asteroid)) {
                            safeJump = false;
                            break;
                        }
                    }
                } while (!safeJump);

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


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {

            alienShips.forEach(alienShip -> {

                Bullet bullet = new Bullet((int) alienShip.getCharacter().getTranslateX(), (int) alienShip.getCharacter().getTranslateY());
                double direction = alienShip.aim(ship);
                bullet.setRotate(direction);
                bullet.setSpeed(1); // Set a speed for the bullet
                alienBullets.add(bullet);
                pane.getChildren().add(bullet.getCharacter());

            });
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

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
                        lives--;
                        if (lives  > 0) {
                            livesText.setText("LIVES: " + "A".repeat(lives));


                            do {

                            ship.hyperspace();
                            safeJump = true;
                            for (Asteroid potentialAsteroid : asteroids) {
                                if (ship.collision(potentialAsteroid)) {
                                    safeJump = false;
                                    break;
                                }
                            }
                        } while (!safeJump);}
                            else {stop();
                                finalScore = points.get();
                                showGameOverScreen();

                        }
                    }

                });
                alienBullets.forEach(bullet -> {
                    if(ship.collision(bullet)){
                        {
                            lives--;
                            if (lives  > 0) {
                                livesText.setText("LIVES: " + "A".repeat(lives));

                                do {
                                ship.hyperspace();
                                safeJump = true;
                                for (Asteroid potentialAsteroid : asteroids) {
                                    if (ship.collision(potentialAsteroid)) {
                                        safeJump = false;
                                        break;
                                    }
                                }
                            } while (!safeJump);}
                            else {stop();
                                finalScore = points.get();
                                showGameOverScreen();

                            }
                        }



                    }
                });

                alienShips.forEach(alienShip -> alienShip.move());



                bullets.forEach((bullet -> bullet.move()));
                alienBullets.forEach((bullet -> bullet.move()));

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
                            pointsText.setText("POINTS: " + points.addAndGet(50));
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
                            pointsText.setText("Points: " + points.addAndGet(250));
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


                // similar functionality to remove alien bullets hitting asteroids or the ship
                List<Bullet> usedAlienBullets = alienBullets.stream().filter(bullet -> {
                    List<Asteroid> alienCollisions =  asteroids.stream()
                            .filter(asteroid -> asteroid.collision(bullet))
                            .collect(Collectors.toList());
                    if(alienCollisions.isEmpty()) {
                        return false;
                    }
                    alienCollisions.stream().forEach(hit -> {
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
                usedAlienBullets.forEach(bullet -> {
                    pane.getChildren().remove(bullet.getCharacter());
                    bullets.remove(bullet);
                });

            // ship bullets can kill the alien ship
                List<Bullet> bulletsHitAlien = bullets.stream().filter(bullet -> {
                    List<AlienShip> collisions = alienShips.stream()
                            .filter(alienShip -> alienShip.collision(bullet))
                            .collect(Collectors.toList());

                    if(collisions.isEmpty()) {
                        return false;
                    }

                    collisions.stream().forEach(collided -> {
                        alienShips.remove(collided);
                        pane.getChildren().remove(collided.getCharacter());
                        // add score for killing the alien
                        pointsText.setText("Points: " + points.addAndGet(500));

                    });

                    return true;
                }).collect(Collectors.toList());

                bulletsHitAlien.forEach(bullet -> {
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


                // Spawn in AlienShip approximately every 17 seconds but not if one is already there
                if (rnd.nextDouble() < 0.001) {

                    if (alienShips.isEmpty()) {

                    AlienShip alienShip = new AlienShip(rnd.nextInt(0,600), rnd.nextInt(0,400));
                        if(!alienShip.collision(ship)) {
                            alienShips.add(alienShip);
                            pane.getChildren().add(alienShip.getCharacter());

                        }
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