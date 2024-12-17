package org.example.towerdefense.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.example.towerdefense.GameBoard;
import org.example.towerdefense.LauncherThread;

public class ControllerGame {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainPane;

    @FXML
    void initialize() {

        GameBoard board = new GameBoard(mainPane, 5, Color.BISQUE, Color.BLACK);

        LauncherThread ha = new LauncherThread(60, new Runnable() {
            @Override
            public void run() {
                board.updateBoard();
            }
        });

        ha.start();

        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'gameUI.fxml'.";

    }

}