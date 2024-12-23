package org.example.towerdefense.Units.Towers;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.towerdefense.Polygon;
import org.example.towerdefense.Timer;

import java.util.ArrayList;
import java.util.HashMap;

public class BombTower extends Tower {

    public BombTower(double damage, double attackSpeed, double cost) {
        super(damage, attackSpeed, cost);
        attackingPolygonsList = new ArrayList<>();
    }

    public BombTower(HashMap<String, Object> data){
        super(data);
        attackingPolygonsList = new ArrayList<>();
    }

    @Override
    public void draw(double x, double y, double size, Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GRAY.darker());
        gc.fillRect(x, y, size, size);
        gc.setFill(Color.LIGHTGRAY.darker());
        gc.fillOval(x + size*0.2 / 2, y + size*0.2 / 2, size - size*0.2, size - size*0.2);
    }

    @Override
    public void attack() {
        for(Polygon polygon:attackingPolygonsList){
            if(polygon.unit != null){
                if(polygon.unit.getClassName().contains("enemy")){
                    polygon.unit.takeDamage(damage);
                }
            }
        }
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
    public void action(double fps) {
        if(timer == null){
            timer = new Timer(attackSpeed, new Runnable() {
                public void run() {
                    attack();
                }
            }, fps);
        }
        timer.countingDown();
    }

    @Override
    public String getClassName(){
        return "bomb tower";
    }
}
