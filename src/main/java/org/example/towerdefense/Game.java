package org.example.towerdefense;

import javafx.scene.layout.AnchorPane;
import org.example.towerdefense.Units.Enemies.Enemy;
import org.example.towerdefense.Units.Towers.Tower;

public class Game {
    private AnchorPane mainPane;
    public GameBoard gameBoard;
    private LauncherThread mainThread;
    public Level level;
    public double updateFrequency;

    public Game(GameBoard board, AnchorPane mainPane, Level level, double updateFrequency) {
        this.updateFrequency = updateFrequency;
        this.level = level;
        this.gameBoard = board;
        this.mainPane = mainPane;
        mainThread = new LauncherThread(updateFrequency, new Runnable() {
            @Override
            public void run() {
                updateUnits();
                gameBoard.updateBoard();
            }
        });
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
        for(Enemy enemy: level.getEnemyList()){
            enemy.action(updateFrequency);
        }
    }

    public void start(){
        mainThread.start();
    }

}
