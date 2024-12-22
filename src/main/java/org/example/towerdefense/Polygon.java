package org.example.towerdefense;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.example.towerdefense.Units.Castle;
import org.example.towerdefense.Units.GameUnit;

public class Polygon {
    public double x;
    public double y;
    public double size;
    public int id;
    public Color color;
    public AnchorPane gameBordPane;
    public Canvas canvas;
    public GameUnit unit;

    Polygon(int id, double size, Canvas canvas, AnchorPane gameBordPane, Color color) {
        this.id = id;
        this.color = color;
        this.gameBordPane = gameBordPane;
        this.size = size;
        this.canvas = canvas;
    }

    public void paintYourself(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillRect(x, y, size, size);
        if(this.unit != null){
            unit.draw(x, y, size, canvas);
        }

    }

    public void setUnit(GameUnit unit){
        this.unit = unit;
    }

    public void removeUnit(){
        this.unit = null;
    }


    public void setColor(Color color) {
        this.color = color;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void setCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
