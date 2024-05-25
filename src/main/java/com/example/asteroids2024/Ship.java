package com.example.asteroids2024;

import javafx.scene.shape.Polygon;
import java.util.Random;


public class Ship extends Character {
    public Ship(int x, int y) {
        super(new Polygon(-5, -5, 10, 0, -5, 5, -2, 0), x, y);
    }

    public void hyperspace() {
        Random rnd = new Random();

        int newX = rnd.nextInt(600);
        int newY = rnd.nextInt(400);
        this.character.setTranslateX(newX);
        this.character.setTranslateY(newY);
    }








}