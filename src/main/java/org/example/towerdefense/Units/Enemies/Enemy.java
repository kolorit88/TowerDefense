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
    public int hp;
    public Game game;

    public Enemy(int hp) {
        this.hp = hp;
        currentPolygonNumberOnPath = 0;
    }

    public void moveOnOnePolygon() {
        removeThisFromPolygon(pathPolygonsList.get(currentPolygonNumberOnPath));

        if(currentPolygonNumberOnPath != pathPolygonsList.size() - 1) {
            currentPolygonNumberOnPath++;
            placeThisOnPolygon(pathPolygonsList.get(currentPolygonNumberOnPath));
        }

    }

    public void takeDamage(int damage){
        hp -= damage;
        if(hp <= 0){
            removeThisFromPolygon(pathPolygonsList.get(currentPolygonNumberOnPath));
        }
    }

    public void setPathPolygonList(List<Polygon> pathPolygonsList){
        this.pathPolygonsList = pathPolygonsList;
    }

}
