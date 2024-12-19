package org.example.towerdefense;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.example.towerdefense.Units.Enemies.Enemy;
import org.example.towerdefense.Units.Level;

public class Game {
    private AnchorPane mainPane;
    public GameBoard gameBoard;
    private LauncherThread mainThread;
    public Level level;
    public double updateFrequency;
    int timer = 0;

    public Game(GameBoard board, AnchorPane mainPane, Level level, double updateFrequency) {
        this.updateFrequency = updateFrequency;
        this.level = level;
        this.gameBoard = board;
        this.mainPane = mainPane;
        mainThread = new LauncherThread(updateFrequency, new Runnable() {
            @Override
            public void run() {
                updateEnemy(1);
                gameBoard.updateBoard();
            }
        });
    }

    private void updateEnemy(int seconds){
        timer += 1;
        if (timer == seconds * updateFrequency) {
            for(Enemy enemy: level.getEnemyList()){
                enemy.moveOnOnePolygon();
            }
            timer = 0;
        }
    }

    public void start(){
        mainThread.start();
    }

}
