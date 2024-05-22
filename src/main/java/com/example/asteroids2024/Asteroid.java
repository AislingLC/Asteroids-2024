package com.example.asteroids2024;

import javafx.scene.shape.Polygon;

import java.util.Random;

public class Asteroid extends Character{
    private static final double SPEED = 0.1;
    private static final double RADIUS = 20; // Define the radius for the asteroid shape
 //Asteroid radius
    public Asteroid(int x, int y) {
        super(createAsteroidPolygon(), x, y);
        Random rand = new Random();
        double angle = rand.nextDouble() * 360; // Random angle between 0 and 360 degrees
        this.setRotate(angle);
        this.speed = SPEED;
        updateMovement();
    }

    private static Polygon createAsteroidPolygon() {
        double[] points = PolygonGenerator.generatePoints(RADIUS);
        return new Polygon(points);
    }
}



