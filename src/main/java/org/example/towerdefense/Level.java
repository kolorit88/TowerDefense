package org.example.towerdefense;

import org.example.towerdefense.Units.Castle;
import org.example.towerdefense.Units.Enemies.Enemy;
import org.example.towerdefense.Units.Enemies.FastEnemy;
import org.example.towerdefense.Units.Enemies.HugeEnemy;
import org.example.towerdefense.Units.Enemies.OrdinaryEnemy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Level {
    public List<Integer> pathNumbersList;
    public List<Polygon> pathPolygonList;
    private List<List<Enemy>> wavesList = new ArrayList<>();
    private List<Enemy> enemyList;
    private int waveNumber = 0;
    public int coinsQuantity = 0;
    private List<List<Double>> wavesTimeIntervalsBetweenEnemies;
    public List<Double> timeIntervals;
    public Castle castle;

    public Level(int coinsQuantity, Castle castle, List<Integer> pathNumbersList, List<List<Enemy>> wavesList, List<List<Double>> wavesTimeIntervalsBetweenEnemies) {
        this.castle = castle;
        this.coinsQuantity = coinsQuantity;
        this.pathNumbersList = pathNumbersList;
        this.pathPolygonList = new ArrayList<>();

        this.wavesList = wavesList;
        this.wavesTimeIntervalsBetweenEnemies = wavesTimeIntervalsBetweenEnemies;
        this.timeIntervals = new ArrayList<>(wavesTimeIntervalsBetweenEnemies.get(waveNumber));

        this.enemyList = wavesList.get(waveNumber);


        for(List<Enemy> elem: wavesList){
            for(Enemy enemy: elem){
                enemy.setLevel(this);
                enemy.setCastle(castle);
                enemy.setPathPolygonList(this.pathPolygonList);
            }
        }
    }

    public Level(HashMap<String, Object> data) {
        this.castle = new Castle((HashMap<String, Double>) data.get("castle"));
        this.coinsQuantity = (Integer) data.get("coinsQuantity");
        this.pathNumbersList = (List<Integer>) data.get("pathNumbersList");
        this.pathPolygonList = new ArrayList<>();
        for(List<HashMap<String, Object>> elemEnemyList: (List<List<HashMap<String, Object>>>) data.get("wavesList")){
            List<Enemy> tmpEnemyList = new ArrayList<>();
            for(HashMap<String, Object> hashMapEnemy: elemEnemyList){
                if(hashMapEnemy.get("className").equals("ordinary enemy")){
                    tmpEnemyList.add(new OrdinaryEnemy((double) hashMapEnemy.get("hp")));
                }
                else if(hashMapEnemy.get("className").equals("fast enemy")){
                    tmpEnemyList.add(new FastEnemy((double) hashMapEnemy.get("hp")));
                }
                else if(hashMapEnemy.get("className").equals("huge enemy")){
                    tmpEnemyList.add(new HugeEnemy((double) hashMapEnemy.get("hp")));
                }
            }
            wavesList.add(tmpEnemyList);
        }
        this.wavesTimeIntervalsBetweenEnemies = (List<List<Double>>) data.get("wavesTimeIntervalsBetweenEnemies");
        this.timeIntervals = new ArrayList<>(wavesTimeIntervalsBetweenEnemies.get(waveNumber));
        this.enemyList = wavesList.get(waveNumber);

        for(List<Enemy> elem: wavesList){
            for(Enemy enemy: elem){
                enemy.setLevel(this);
                enemy.setCastle(castle);
                enemy.setPathPolygonList(this.pathPolygonList);
            }
        }
    }

    public void nextWave(){
        if(wavesList.size() > waveNumber){
            enemyList = wavesList.get(waveNumber);
            timeIntervals = new ArrayList<>(wavesTimeIntervalsBetweenEnemies.get(waveNumber));
            waveNumber++;
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

    public List<Double> getTimeIntervals(){
        return timeIntervals;
    }

    public HashMap<String, Object> toHashMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("coinsQuantity", coinsQuantity);
        map.put("pathNumbersList", pathNumbersList);
        map.put("castle", castle.toHashMap());
        map.put("wavesTimeIntervalsBetweenEnemies", wavesTimeIntervalsBetweenEnemies);
        List<List<HashMap<String, Object>>> tmpWavesList = new ArrayList<>();

        for(List<Enemy> elemEnemyList: wavesList){
            List<HashMap<String, Object>> tmpEnemyList = new ArrayList<>();
            for(Enemy enemy: elemEnemyList){
                tmpEnemyList.add(enemy.toHashMap());
            }
            tmpWavesList.add(tmpEnemyList);
        }
        map.put("wavesList", tmpWavesList);

        return map;
    }
}
