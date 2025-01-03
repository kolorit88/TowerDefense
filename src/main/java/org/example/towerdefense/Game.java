package org.example.towerdefense;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.example.towerdefense.Online.Connection;
import org.example.towerdefense.Units.Castle;
import org.example.towerdefense.Units.Enemies.Enemy;
import org.example.towerdefense.Units.Towers.ArchersTower;
import org.example.towerdefense.Units.Towers.BombTower;
import org.example.towerdefense.Units.Towers.Tower;

import javax.crypto.MacSpi;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.sleep;

public class Game {
    private AnchorPane mainPane;
    public GameBoard gameBoard;
    private LauncherThread mainThread;
    public Level level;
    public double updateFrequency;
    public List<Enemy> placedEnemy =  new ArrayList<>();
    public Timer timerIntervalsBetweenEnemies;
    public int timerIntervalsCount;
    public Castle castle;
    public Tower pickedTower;
    public BlockingQueue<Message> updateQueue;
    boolean startWaves = false;
    public Connection connection;

    public Game(GameBoard board, AnchorPane mainPane, Level level, Castle castle,  double updateFrequency) {
        this.updateQueue = new LinkedBlockingQueue<>();
        this.castle = castle;
        this.updateFrequency = updateFrequency;
        this.level = level;
        this.gameBoard = board;
        this.mainPane = mainPane;


        mainThread = new LauncherThread(updateFrequency, new Runnable() {
            @Override
            public void run() {
                checkIfCastleDead();
                if(startWaves){
                    placeEnemy();
                }
                updateUnits();
                gameBoard.updateBoard();
                nextWaveIfAllKilled();
            }
        });
    }

    private void nextWaveIfAllKilled(){
        boolean needNextWave = true;
        for(Enemy enemy: level.getEnemyList()){
            if(!enemy.dead){
                needNextWave = false;
            }
        }
        if(needNextWave){
            if(level.nextWave().equals("endGame")){
                endGame();
            };
        }

    }

    private void checkIfCastleDead(){
        if(level.castle.lives <= 0){
            endGame();
        }
    }

    public void endGame(){
        if(connection != null){
            connection.close();
        }
        mainThread.interrupt();
        Database db = new Database("jdbc:sqlite:src/main/resources/org/example/towerdefense/AppDatabase.db");

        if(level.castle.lives <= 0){
            paintAllBoard(Color.CRIMSON);
            db.updatePlayer("You", 0, 1);
        }
        else{
            paintWin(Color.PALEGREEN.darker());
            db.updatePlayer("You", 1, 0);
        }
        gameBoard.updateBoard();

    }



    private void placeEnemy(){
        if(!level.getTimeIntervals().isEmpty()){
            if (timerIntervalsBetweenEnemies == null){
                timerIntervalsBetweenEnemies = new Timer(level.getTimeIntervals().getFirst(), new Runnable() {
                    @Override
                    public void run() {
                        for(Enemy enemy: level.getEnemyList()){
                            if(!placedEnemy.contains(enemy)){
                                placedEnemy.add(enemy);
                                break;
                            }
                        }
                        timerIntervalsCount++;
                        timerIntervalsBetweenEnemies = null;
                    }
                }, updateFrequency);
                if(level.timeIntervals.size() > 1){
                    level.timeIntervals.removeFirst();
                }
            }
            timerIntervalsBetweenEnemies.countingDown();
        }

    }

    private void updateUnits(){
        actionTowers();
        actionEnemies();
    }

    private void actionTowers(){
        for(Tower tower: gameBoard.getTowersList()){
            tower.action(updateFrequency);
        }
    }

    private void actionEnemies(){
        for(Enemy enemy: placedEnemy){
            if(!enemy.dead){
                enemy.action(updateFrequency);
            }
        }
    }

    public void byeTower(String towerName){
        if(pickedTower == null){
            if(towerName.equals("archers")){
                if(level.coinsQuantity >= 150){
                    pickedTower = new ArchersTower(5, 0.3, 150);
                    level.coinsQuantity -= (int) pickedTower.getCost();
                    messageChangeCoins(-150);
                }
            }
            else if(towerName.equals("bomb")){
                if(level.coinsQuantity >= 250){
                    pickedTower = new BombTower(10, 1, 250);
                    level.coinsQuantity -= (int) pickedTower.getCost();

                    messageChangeCoins(-250);
                }
            }
        }
    }

    public void clickOn(double x, double y, String button){
        Polygon polygon = gameBoard.getPolygonContained(x, y);
        if(button.equals("left")){
            if(polygon != null && pickedTower != null){
                if(polygon.unit == null && !level.pathPolygonList.contains(polygon)){
                    messageInsertTower(pickedTower, polygon.id);

                    pickedTower.setPathPolygonList(level.pathPolygonList);
                    pickedTower.placeThisOnPolygon(polygon);
                    gameBoard.towersList.add(pickedTower);
                    pickedTower = null;
                }
            }
        }
        else if(button.equals("right")){
            if(polygon != null){
                if(polygon.unit != null){
                    if(polygon.unit.getClassName().contains("tower")){
                        messageChangeCoins((int) (polygon.unit.getCost() * 0.8));
                        messageRemoveTower(polygon.id);

                        level.coinsQuantity += (int) (polygon.unit.getCost() * 0.8);
                        gameBoard.towersList.remove(polygon.unit);
                        polygon.unit = null;
                    }
                }
            }
        }
    }

    public void messageInsertTower(Tower tower, int polygonId){
        HashMap<String, Object> data = tower.toHashMap();
        data.put("polygonId", polygonId);
        updateQueue.add(new Message(data, "insert tower"));
    }

    public void messageChangeCoins(int coinsChange){
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("coins", coinsChange);
        updateQueue.add(new Message(data, "change coins"));
    }

    public void messageRemoveTower(int polygonId){
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("polygonId", polygonId);
        updateQueue.add(new Message(data, "remove tower"));
    }

    public void messageStartWaves(){
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("started", true);
        updateQueue.add(new Message(data, "start waves"));
    }

    public void plusOneCoin(){
        level.coinsQuantity += 1;
        messageChangeCoins(+1);
    }


    public void start(){
        gameBoard.init();
        mainThread.start();
    }

    public void startWaves(){
        messageStartWaves();
        startWaves = true;
        gameBoard.startButton.setVisible(false);
        gameBoard.farmCoinsButton.setVisible(true);
    }

    public void setLevel(Level level){
        this.level = level;
        gameBoard.setLevel(level);
    }

    public BlockingQueue<Message> getUpdateQueue(){
        return updateQueue;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    private void paintAllBoard(Color color){
        for(Polygon polygon: gameBoard.polygonsList){
            try {
                polygon.setColor(color);
                polygon.unit = null;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void paintWin(Color color){
        paintAllBoard(color.darker());
        gameBoard.polygonsList.get(30).setColor(color);
        gameBoard.polygonsList.get(40).setColor(color);
        gameBoard.polygonsList.get(50).setColor(color);
        gameBoard.polygonsList.get(61).setColor(color);
        gameBoard.polygonsList.get(52).setColor(color);
        gameBoard.polygonsList.get(42).setColor(color);
        gameBoard.polygonsList.get(63).setColor(color);
        gameBoard.polygonsList.get(54).setColor(color);
        gameBoard.polygonsList.get(44).setColor(color);
        gameBoard.polygonsList.get(34).setColor(color);

        gameBoard.polygonsList.get(65).setColor(color);
        gameBoard.polygonsList.get(55).setColor(color);
        gameBoard.polygonsList.get(35).setColor(color);

        gameBoard.polygonsList.get(67).setColor(color);
        gameBoard.polygonsList.get(57).setColor(color);
        gameBoard.polygonsList.get(47).setColor(color);
        gameBoard.polygonsList.get(37).setColor(color);
        gameBoard.polygonsList.get(38).setColor(color);
        gameBoard.polygonsList.get(49).setColor(color);
        gameBoard.polygonsList.get(59).setColor(color);
        gameBoard.polygonsList.get(69).setColor(color);
    }
}
