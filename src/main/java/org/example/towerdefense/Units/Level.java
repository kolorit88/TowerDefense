package org.example.towerdefense.Units;

import org.example.towerdefense.Polygon;
import org.example.towerdefense.Units.Enemies.Enemy;

import java.util.List;

public class Level {
    private List<Integer> pathNumbersList;
    private List<Enemy> enemyList;
    private int coinsQuantity = 0;

    public Level(int coinsQuantity, List<Integer> pathNumbersList, List<Enemy> enemyList) {
        this.coinsQuantity = coinsQuantity;
        this.pathNumbersList = pathNumbersList;
        this.enemyList = enemyList;
    }

    public List<Integer> getPathNumbersList() {
        return pathNumbersList;
    }

    public List<Enemy> getEnemyList(){
        return enemyList;
    }

    public void toHashMap(){}
}
