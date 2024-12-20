package org.example.towerdefense.Units.Enemies;

import org.example.towerdefense.Polygon;
import org.example.towerdefense.Timer;
import org.example.towerdefense.Units.GameUnit;

import java.util.List;

public class Enemy extends GameUnit {
    int currentPolygonNumberOnPath;
    public double hp;
    public double maxHp;
    public double speed;
    public Timer timer;
    public boolean dead;


    public Enemy(double hp) {
        this.hp = hp;
        this.maxHp = hp;
        currentPolygonNumberOnPath = 0;

    }

    public void moveOnOnePolygon() {
        removeThisFromPolygon(pathPolygonsList.get(currentPolygonNumberOnPath));

        if(currentPolygonNumberOnPath < pathPolygonsList.size() - 1 && !dead) {
            currentPolygonNumberOnPath ++;
            placeThisOnPolygon(pathPolygonsList.get(currentPolygonNumberOnPath));
        }

    }

    public void takeDamage(double damage){
        hp -= damage;
        if(hp <= 0){
            removeThisFromPolygon(pathPolygonsList.get(currentPolygonNumberOnPath));
            dead = true;
        }
    }

    @Override
    public String getClassName(){
        return "enemy";
    }

}
