package org.example.towerdefense.Units.Towers;

import org.example.towerdefense.Polygon;
import org.example.towerdefense.Units.Enemies.Enemy;
import org.example.towerdefense.Units.GameUnit;
import org.example.towerdefense.Timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tower extends GameUnit {
    public List<Polygon> attackingPolygonsList;
    protected double damage;
    protected double attackSpeed;
    protected Enemy target;
    protected Timer timer;
    protected double cost;


    public Tower(double damage, double attackSpeed, double cost) {
        this.attackSpeed = attackSpeed;
        this.damage = damage;
        this.cost = cost;
    }

    public Tower(HashMap<String, Object> data){
        this.attackSpeed = (double) data.get("attackSpeed");
        this.damage = (double) data.get("damage");
        this.cost = (double) data.get("cost");
    }

    public void attack(){

    }

    public void action(double fps){
    }

    @Override
    public String getClassName(){
        return "tower";
    }

    public double getCost(){
        return cost;
    }

    public HashMap<String, Object> toHashMap(){
        HashMap<String, Object> toReturn = new HashMap<>();
        toReturn.put("damage", damage);
        toReturn.put("cost", cost);
        toReturn.put("attackSpeed", attackSpeed);
        toReturn.put("className", getClassName());
        return toReturn;
    }

}
