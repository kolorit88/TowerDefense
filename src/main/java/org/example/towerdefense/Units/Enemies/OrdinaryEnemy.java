package org.example.towerdefense.Units.Enemies;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.towerdefense.Timer;

public class OrdinaryEnemy extends Enemy{
    public OrdinaryEnemy(double hp) {
        super(hp);
    }

    @Override
    public void action(double fps){
        if(timer == null){
            this.timer = new Timer(1, new Runnable() {
                @Override
                public void run() {
                    moveOnOnePolygon();
                }
            }, fps);
        }
        timer.countingDown();

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
