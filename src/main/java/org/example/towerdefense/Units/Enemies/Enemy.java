package org.example.towerdefense.Units.Enemies;

import org.example.towerdefense.Polygon;
import org.example.towerdefense.Units.GameUnit;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends GameUnit {
    List<Polygon> pathPolygonsList;
    int currentPolygonNumberOnPath;
    public int hp;

    public Enemy(List<Polygon> pathPolygonsList, int hp) {
        this.hp = hp;
        this.pathPolygonsList = pathPolygonsList;
        currentPolygonNumberOnPath = 0;
        placeThisOnPolygon(pathPolygonsList.getFirst());
    }

    public void moveOnOnePolygon(){
        removeThisFromPolygon(pathPolygonsList.get(currentPolygonNumberOnPath));
        currentPolygonNumberOnPath++;
        placeThisOnPolygon(pathPolygonsList.get(currentPolygonNumberOnPath));
    }

    public void takeDamage(int damage){
        hp -= damage;
        if(hp <= 0){
            removeThisFromPolygon(pathPolygonsList.get(currentPolygonNumberOnPath));
        }
    }

}
