package org.example.towerdefense.Units.Towers;

import org.example.towerdefense.Polygon;
import org.example.towerdefense.Units.Enemies.Enemy;
import org.example.towerdefense.Units.GameUnit;
import org.example.towerdefense.Timer;

import java.util.ArrayList;
import java.util.List;

public class Tower extends GameUnit {
    public List<Polygon> attackingPolygonsList;
    protected double damage;
    protected double attackSpeed;
    protected Enemy target;
    protected Timer timer;


    public Tower(double damage, double attackSpeed) {
        this.attackSpeed = attackSpeed;
        this.damage = damage;
    }

    public void attack(){

    }

    public void action(double fps){
    }

    @Override
    public String getClassName(){
        return "enemy";
    }



}
