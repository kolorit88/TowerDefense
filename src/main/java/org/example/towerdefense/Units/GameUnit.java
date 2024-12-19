package org.example.towerdefense.Units;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.example.towerdefense.GameBoard;
import org.example.towerdefense.Polygon;

public class GameUnit {

    public void placeThisOnPolygon(Polygon polygon) {
        polygon.setUnit(this);
    }

    public void removeThisFromPolygon(Polygon polygon) {
        polygon.removeUnit();
    }

    public void draw(double x, double y, double size, Canvas canvas){

    }

    public void takeDamage(double damage) {}



}
