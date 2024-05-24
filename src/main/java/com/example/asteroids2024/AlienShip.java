package com.example.asteroids2024;


import javafx.scene.shape.Polygon;


public class AlienShip extends Character {
    public AlienShip(int x, int y) {
        super(new Polygon(-10, 0, -6, -4, 6, -4, 10, 0, -10, 0, -3, 3, -2, 4, 2, 4, 3, 3, 10, 0), x, y);
    }



}