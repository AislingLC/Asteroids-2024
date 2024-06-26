package com.example.asteroids2024;

import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;


// superclass for all the shapes that appear on the screen - including asteroids, ship, alien spaceships and bullets
// uses javafx classes Point2D and Polygon
public abstract class Character {

    protected Polygon character;
    protected Point2D movement;
    protected double speed;

    public Character(Polygon polygon, int x, int y) {
        this.character = polygon;
        // All characters will have white outlines per Atari Asteroids game
        polygon.setFill(Color.TRANSPARENT); // No fill
        polygon.setStroke(Color.WHITE); // White outline
        polygon.setStrokeWidth(2); // Set the width of the outline

        this.character.setTranslateX(x);
        this.character.setTranslateY(y);

        this.movement = new Point2D(0, 0);
        this.speed = 0;
    }

    public Polygon getCharacter() {
        return character;
    }

    public void rotateLeft() { character.setRotate(character.getRotate() - 5);
        updateMovement();}
    public void rotateRight() {
        character.setRotate(character.getRotate() + 5);
        updateMovement();
    }
    // allow movement

    public void move() {
        this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
        this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());

        if (this.character.getTranslateX() < 0) {
            this.character.setTranslateX(this.character.getTranslateX() + Asteroids_Game.WIDTH);
        }

        if (this.character.getTranslateX() > Asteroids_Game.WIDTH) {
            this.character.setTranslateX(this.character.getTranslateX() % Asteroids_Game.WIDTH);
        }

        if (this.character.getTranslateY() < 0) {
            this.character.setTranslateY(this.character.getTranslateY() + Asteroids_Game.HEIGHT);
        }

        if (this.character.getTranslateY() > Asteroids_Game.HEIGHT) {
            this.character.setTranslateY(this.character.getTranslateY() % Asteroids_Game.HEIGHT);
        }


    }
    public void accelerate() {
        speed = Math.min(10, speed + 0.05);
        updateMovement();
    }

    public void decelerate() {
        speed = Math.max(0, speed - 0.05);
        updateMovement();
    }
    public void updateMovement() {
        double angle = Math.toRadians(this.character.getRotate());
        double changeX = speed * Math.cos(angle);
        double changeY = speed * Math.sin(angle);
        this.movement = new Point2D(changeX, changeY);
    }
    public Point2D getMovement() {return this.movement;}
    public void setMovement(Point2D movement) {this.movement = movement;}

    public void setRotate(double angle) {
        this.character.setRotate(angle);
    }

    public double getRotate() {
        return this.character.getRotate();
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double newSpeed) {
        this.speed = newSpeed;
        updateMovement(); // Update movement based on new speed
    }

    public boolean collision(Character other) {
        Shape collisionArea = Shape.intersect(this.character, other.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }


    public double aim(Character target) {
        double diffX = target.getCharacter().getTranslateX() - this.getCharacter().getTranslateX();
        double diffY = target.getCharacter().getTranslateY() - this.getCharacter().getTranslateY();
        return Math.toDegrees(Math.atan2(diffY, diffX));
    }
    }




