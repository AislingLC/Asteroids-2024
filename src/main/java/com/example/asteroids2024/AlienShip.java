package com.example.asteroids2024;


import javafx.scene.shape.Polygon;
import java.util.Random;


public class AlienShip extends Character {
    public AlienShip(int x, int y) {
        super(new Polygon(-10, 0,
                -7, -3,
                7, -3,
                10, 0,
                -10, 0,
                -3, 4,
                -2, 5,
                2, 5,
                3, 4,
                10, 0), x, y);
        Random rnd = new Random();
        this.speed = 1;
        this.character.setRotate(rnd.nextDouble() * 360);
        this.updateMovement();



    }




}