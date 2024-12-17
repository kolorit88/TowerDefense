package org.example.towerdefense.Units.Enemies;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.towerdefense.Polygon;

import java.util.List;

public class OrdinaryEnemy extends Enemy{

    public OrdinaryEnemy(List<Polygon> pathPolygonsList, int hp) {
        super(pathPolygonsList, hp);
    }

    @Override
    public void draw(double x, double y, double size, Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.fillOval(x, y, size, size);
    }
}
