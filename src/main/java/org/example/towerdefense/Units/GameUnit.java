package org.example.towerdefense.Units;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.towerdefense.GameBoard;
import org.example.towerdefense.Polygon;

import java.util.List;

public class GameUnit {
    public List<Polygon> pathPolygonsList;
    public void placeThisOnPolygon(Polygon polygon) {
        polygon.setUnit(this);
    }

    public void removeThisFromPolygon(Polygon polygon) {
        polygon.removeUnit();
        polygon.setColor(Color.PALEGREEN);
    }

    public void draw(double x, double y, double size, Canvas canvas){

    }

    public void takeDamage(double damage) {}
    public String getClassName(){
        return "gameUnit";
    }
    public void action(double fps){

    }

    public void setPathPolygonList(List<Polygon> pathPolygonsList){
        this.pathPolygonsList = pathPolygonsList;
    }


    public double getCost() {
        return 0;
    }
}
