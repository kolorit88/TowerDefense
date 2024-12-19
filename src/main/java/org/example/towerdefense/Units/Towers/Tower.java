package org.example.towerdefense.Units.Towers;

import org.example.towerdefense.Polygon;
import org.example.towerdefense.Units.GameUnit;

import java.util.ArrayList;
import java.util.List;

public class Tower extends GameUnit {
    public List<Polygon> pathPolygonsList;
    public List<Polygon> attackingPolygonsList;
    protected double damage;
    protected double attackSpeed;

    public Tower(double damage, double attackSpeed) {
        this.attackSpeed = attackSpeed;
        this.damage = damage;
        this.pathPolygonsList = new ArrayList<Polygon>();
    }



}
