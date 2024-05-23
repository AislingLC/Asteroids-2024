package com.example.asteroids2024;

import javafx.scene.shape.Polygon;

import java.util.Random;

public class Asteroid extends Character{
    private AsteroidSize size;
    private static final double SPEED = 0.1;
 //Asteroid radius
    public Asteroid(int x, int y, AsteroidSize size) {
        super(createAsteroidPolygon(size), x, y);
        this.size = size;
        Random rand = new Random();
        double angle = rand.nextDouble() * 360; // Random angle between 0 and 360 degrees
        this.setRotate(angle);
        this.speed = SPEED;
        updateMovement();

    }

    private static Polygon createAsteroidPolygon(AsteroidSize size) {
        int radius = size.getRadius();
        double[] points = PolygonGenerator.generatePoints(radius);
        return new Polygon(points);
    }

    // Add the getSize method to allow game logic
    // to have different responses when different sized asteroids are hit
    public AsteroidSize getSize() {
        return this.size;
    }
}



