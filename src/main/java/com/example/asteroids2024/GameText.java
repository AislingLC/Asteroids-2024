package com.example.asteroids2024;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
public class GameText extends Text{

    public GameText(String text, double x, double y, int fontsize) {
        super(text);
        this.setFont(Font.font(fontsize));
        this.setFill(Color.WHITE);
        this.setTranslateX(x); // Center the text
        this.setTranslateY(y);
    }

}
