package org.example.towerdefense.Controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.example.towerdefense.Game;
import org.example.towerdefense.GameBoard;
import org.example.towerdefense.Units.Enemies.OrdinaryEnemy;
import org.example.towerdefense.Units.Level;

public class ControllerGame {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainPane;

    @FXML
    void initialize() {
        Level level1 = new Level(500, List.of(
                2,
                12, 13, 14, 15, 16,
                                26,
                36, 35, 34, 33, 32,
                42,
                52, 53, 54, 55, 56, 57, 58,
                                        68,
                                        78,
                    88, 87, 86, 85, 84, 83,
                    93), List.of(new OrdinaryEnemy(100), new OrdinaryEnemy(100)));
        GameBoard gameBoard = new GameBoard(mainPane, 5, Color.BISQUE, Color.BLACK, level1);
        Game game = new Game(gameBoard, mainPane, level1, 60);
        game.start();


        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'gameUI.fxml'.";

    }

}