package org.example.towerdefense.Units.Towers;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.towerdefense.Polygon;
import org.example.towerdefense.Units.Enemies.Enemy;

import java.awt.*;
import java.lang.reflect.Array;

public class ArchersTower extends Tower{

    public ArchersTower(double damage, double attackSpeed) {
        super(damage, attackSpeed);
    }

    @Override
    public void placeThisOnPolygon(Polygon polygon) {
        super.placeThisOnPolygon(polygon);
        for (Polygon p : pathPolygonsList) {
            if(p.id == polygon.id - 10 || p.id == polygon.id + 10 || p.id == polygon.id - 1 || p.id == polygon.id + 1 ||
                    p.id == polygon.id - 11 || p.id == polygon.id + 11 || p.id == polygon.id - 9 || p.id == polygon.id + 9) {
                attackingPolygonsList.add(p);
            }
        }
    }

    @Override
    public void draw(double x, double y, double size, Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.CRIMSON);
        gc.fillRect(x, y, size, size);
        gc.setFill(Color.AZURE);
        gc.fillPolygon(new double[] {x + size*0.5, x + size * 0.9, x + size*0.1}, new double[] {y + size*0.1, y + size * 0.8, y + size *0.8}, 3);
    }

    public void attackPolygons() {
        for(Polygon polygon: attackingPolygonsList){
            if(polygon.unit != null){
                if(polygon.unit.getClass() == Enemy.class){
                    polygon.unit.takeDamage(this.damage);
                }
            }
        }
    }

}
