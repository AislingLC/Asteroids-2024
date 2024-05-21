package com.example.asteroids2024;

import javafx.scene.shape.Polygon;


public class Ship extends Character {
    public Ship(int x, int y) {
        super(new Polygon(-5, -5, 10, 0, -5, 5), x, y);
    }
    public void rotateLeft() {
        this.setRotate(this.getRotate() - 5);
    }

    public void rotateRight() {
        this.setRotate(this.getRotate() + 5);
    }

    public void accelerate() {
        super.accelerate();
    }

    public void decelerate() {
        super.decelerate();
    }



}