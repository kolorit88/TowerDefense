package org.example.towerdefense;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
    private Button leadersButton;

    @FXML
    void createGame(ActionEvent event) throws Exception {
        loadGame(true, stage);
    }

    @FXML
    void joinGame(ActionEvent event) throws Exception {
        loadGame(false, stage);
    }

    @FXML
    void leadersButtonAction(ActionEvent event) throws Exception {
        loadLeaderboard();
    }

    @FXML
    void initialize() {
        assert createGameButton != null : "fx:id=\"createGameButton\" was not injected: check your FXML file 'menuUI.fxml'.";
        assert ipLabel != null : "fx:id=\"ipLabel\" was not injected: check your FXML file 'menuUI.fxml'.";
        assert joinGameButton != null : "fx:id=\"joinGameButton\" was not injected: check your FXML file 'menuUI.fxml'.";

    }

    private void loadLeaderboard() throws IOException {
        FXMLLoader leaderboardLoad = new FXMLLoader(getClass().getResource("leaderboardUI.fxml"));
        Scene leaderboardLoadScene = new Scene(leaderboardLoad.load());
        ControllerLeaderboard leaderboardController = leaderboardLoad.getController();
        leaderboardController.setStage(stage);
        stage.setScene(leaderboardLoadScene);
    }

    private void loadGame(boolean isServer, Stage stage) throws Exception {
        FXMLLoader gameLoad = new FXMLLoader(getClass().getResource("gameUI.fxml"));
        Scene gameScene = new Scene(gameLoad.load());
        stage.setScene(gameScene);

        ControllerGamePlay controller = gameLoad.getController();
        controller.transferStage(stage);
        controller.startGameOnline(isServer, ipLabel.getText());
        stage.setResizable(true);
    }

}
