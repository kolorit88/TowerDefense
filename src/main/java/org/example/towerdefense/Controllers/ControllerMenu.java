package org.example.towerdefense.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerMenu {
    Stage stage;

    public void transferStage(Stage stage){
        this.stage = stage;
    }


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button createGameButton;

    @FXML
    private TextField ipLabel;

    @FXML
    private Button joinGameButton;

    @FXML
    void createGame(ActionEvent event) {

    }

    @FXML
    void joinGame(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert createGameButton != null : "fx:id=\"createGameButton\" was not injected: check your FXML file 'menuUI.fxml'.";
        assert ipLabel != null : "fx:id=\"ipLabel\" was not injected: check your FXML file 'menuUI.fxml'.";
        assert joinGameButton != null : "fx:id=\"joinGameButton\" was not injected: check your FXML file 'menuUI.fxml'.";

    }
}
