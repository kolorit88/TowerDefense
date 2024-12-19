package org.example.towerdefense.Units;

import org.example.towerdefense.Polygon;
import org.example.towerdefense.Units.Enemies.Enemy;

import java.util.ArrayList;
import java.util.List;

public class Level {
    public List<Integer> pathNumbersList;
    public List<Polygon> pathPolygonList;
    private List<Enemy> enemyList;
    private int coinsQuantity = 0;

    public Level(int coinsQuantity, List<Integer> pathNumbersList, List<Enemy> enemyList) {
        this.coinsQuantity = coinsQuantity;
        this.pathNumbersList = pathNumbersList;
        this.pathPolygonList = new ArrayList<>();
        this.enemyList = enemyList;
        for(Enemy enemy: enemyList){
            enemy.setPathPolygonList(this.pathPolygonList);
        }
    }

    public void addToPathPolygonList(Polygon polygon) {
        pathPolygonList.add(polygon);
    }

    public List<Integer> getPathNumbersList() {
        return pathNumbersList;
    }

    public List<Enemy> getEnemyList(){
        return enemyList;
    }

    public void toHashMap(){}
}
