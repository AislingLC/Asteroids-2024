package com.example.asteroids2024;

import javafx.geometry.Point2D;
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
        // All characters will be white
        polygon.setFill(Color.WHITE);
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);

        this.movement = new Point2D(0, 0);
        this.speed = 0;
    }

    public Polygon getCharacter() {
        return character;
    }

    public void rotateLeft() { character.setRotate(character.getRotate() - 5); }
    public void rotateRight() {
        character.setRotate(character.getRotate() + 5);
    }
    // allow movement

    public void move() {
        this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
        this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());
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


    public void setRotate(double angle) {
        this.character.setRotate(angle);
    }

    public double getRotate() {
        return this.character.getRotate();
    }
    }




