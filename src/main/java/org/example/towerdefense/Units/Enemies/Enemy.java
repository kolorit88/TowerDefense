package org.example.towerdefense.Units.Enemies;

import javafx.scene.paint.Color;
import org.example.towerdefense.Level;
import org.example.towerdefense.Polygon;
import org.example.towerdefense.Timer;
import org.example.towerdefense.Units.Castle;
import org.example.towerdefense.Units.GameUnit;

import java.util.HashMap;
import java.util.List;

public class Enemy extends GameUnit {
    public Castle castle;
    public Level level;
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


        if(currentPolygonNumberOnPath == pathPolygonsList.size() - 2) {
            dealDamageToCastle();
            dead = true;
        }
        if(currentPolygonNumberOnPath < pathPolygonsList.size() - 1 && !dead) {
            currentPolygonNumberOnPath ++;
            placeThisOnPolygon(pathPolygonsList.get(currentPolygonNumberOnPath));
        }

    }
    public void dealDamageToCastle() {
        castle.loseOneLive();
    }

    public void takeDamage(double damage){
        hp -= damage;
        pathPolygonsList.get(currentPolygonNumberOnPath).setColor(Color.ORANGERED);
        if(hp <= 0 && !dead) {
            removeThisFromPolygon(pathPolygonsList.get(currentPolygonNumberOnPath));
            dead = true;
            level.coinsQuantity += (int) (maxHp * 0.25);
        }
    }

    public void setCastle(Castle castle) {
        this.castle = castle;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public String getClassName(){
        return "enemy";
    }

    public HashMap<String, Object> toHashMap(){
        HashMap<String, Object> tmpMap = new HashMap<>();
        tmpMap.put("hp", hp);
        return tmpMap;
    }

}
