package org.example.towerdefense.Units.Enemies;

import org.example.towerdefense.Game;
import org.example.towerdefense.Polygon;
import org.example.towerdefense.Units.GameUnit;
import org.example.towerdefense.Units.Level;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends GameUnit {
    public List<Polygon> pathPolygonsList;
    int currentPolygonNumberOnPath;
    public double hp;
    public double maxHp;


    public Enemy(double hp) {
        this.hp = hp;
        this.maxHp = hp;
        currentPolygonNumberOnPath = 0;
    }

    public void moveOnOnePolygon() {
        removeThisFromPolygon(pathPolygonsList.get(currentPolygonNumberOnPath));

        if(currentPolygonNumberOnPath < pathPolygonsList.size() - 1) {
            currentPolygonNumberOnPath ++;
            placeThisOnPolygon(pathPolygonsList.get(currentPolygonNumberOnPath));
        }

    }

    public void takeDamage(double damage){
        hp -= damage;
        if(hp <= 0){
            removeThisFromPolygon(pathPolygonsList.get(currentPolygonNumberOnPath));
        }
    }

    public void setPathPolygonList(List<Polygon> pathPolygonsList){
        this.pathPolygonsList = pathPolygonsList;
    }

    @Override
    public String getClassName(){
        return "enemy";
    }

}
