package com.example.asteroids2024;
import javafx.scene.shape.Polygon;
public class Bullet extends Character {


    public Bullet(int x, int y) {
        // shape is a small rectangle
        super(new Polygon(2, -1, 2, 1, -2, 1, -2, -1), x, y);


    }
}
