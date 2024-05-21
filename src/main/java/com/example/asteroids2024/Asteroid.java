package com.example.asteroids2024;

import javafx.scene.shape.Polygon;

import java.util.Random;

public class Asteroid extends Character{
    private double SPEED = 0.1;
    public Asteroid(int x, int y) {

        super(new Polygon(20, -20, 20, 20, -20, 20, -20, -20), x, y);
        Random rand = new Random();
        double angle = rand.nextDouble() * 360; // Random angle between 0 and 360 degrees
        this.setRotate(angle);
        this.speed = SPEED;
        updateMovement();
    }



}
