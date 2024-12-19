package org.example.towerdefense.Units.Enemies;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.towerdefense.Polygon;
import org.example.towerdefense.Units.Level;

import java.util.List;

public class OrdinaryEnemy extends Enemy{
    public OrdinaryEnemy(double hp) {
        super(hp);
    }


    @Override
    public void draw(double x, double y, double size, Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLUEVIOLET);
        gc.fillOval(x, y, size, size);

        gc.setFill(Color.RED);
        gc.fillRect(x, y+size*0.9, size, size*0.1);
        gc.setFill(Color.GREEN);
        gc.fillRect(x, y+size*0.9, (size)*(hp * 100/maxHp)/100, size*0.1);

    }
}
