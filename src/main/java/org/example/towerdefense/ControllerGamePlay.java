package org.example.towerdefense;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.towerdefense.Online.Client;
import org.example.towerdefense.Online.Server;
import org.example.towerdefense.Units.Castle;
import org.example.towerdefense.Units.Enemies.FastEnemy;
import org.example.towerdefense.Units.Enemies.HugeEnemy;
import org.example.towerdefense.Units.Enemies.OrdinaryEnemy;

public class ControllerGamePlay {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button byeArcherTowerButton;

    @FXML
    private Button byeBombTowerButton;

    @FXML
    private Button startButton;

    @FXML
    private Button farmCoinsButton;

    @FXML
    private Button backButton;

    @FXML
    private Text coinsText;

    private Stage stage;

    private Game game;

    public boolean isServer;

    @FXML
    void initialize() {
        startButton.setVisible(true);
        farmCoinsButton.setVisible(false);
        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'gameUI.fxml'.";
    }

    public void startGameOnline(boolean isServer, String ipPortText) throws Exception{
        boolean checkedAddress = false;
        try {
            String ip = ipPortText.split(":")[0];
            String port = ipPortText.split(":")[1];
            checkedAddress = true;
        }
        catch (Exception e) {
            checkedAddress = false;
            System.out.println("Неверный ip или port");
            game.endGame();
            loadMenu(stage);
        }

        if(checkedAddress){
            String ip = ipPortText.split(":")[0];
            String port = ipPortText.split(":")[1];

            farmCoinsButton.setVisible(false);
            mainPane.setOnMousePressed(this::MousePressed);

            Castle castle = new Castle(3);
            Level level = new Level(400, castle, List.of(
                    2,
                    12, 13, 14, 15, 16,
                    26,
                    36, 35, 34, 33, 32,
                    42,
                    52, 53, 54, 55, 56, 57, 58,
                    68,
                    78,
                    88, 87, 86, 85, 84, 83,
                    93),
                    List.of(List.of(new OrdinaryEnemy(100), new OrdinaryEnemy(100), new OrdinaryEnemy(100)),
                            List.of(new FastEnemy(50), new FastEnemy(50), new FastEnemy(50), new FastEnemy(50), new FastEnemy(50), new FastEnemy(50)),
                            List.of(new HugeEnemy(700), new HugeEnemy(700))),
                    List.of(List.of(1.0, 2.0, 2.0), List.of(0.5, 0.5, 0.5, 0.5, 0.5), List.of(3.0, 6.0)));
            GameBoard gameBoard = new GameBoard(mainPane, 5, Color.BISQUE, Color.BLACK, level, byeArcherTowerButton, byeBombTowerButton, startButton, farmCoinsButton, backButton, coinsText);
            this.game = new Game(gameBoard, mainPane, level, castle, 60);

            if(isServer){
                Server server = new Server(game, port, 60);
                game.setConnection(server);
                server.start();
                game.start();
            }
            else {
                Client client = new Client(game, ip, port,60);
                game.setConnection(client);
                client.start();
            }
            this.isServer = isServer;
        }
    }

    private void MousePressed(MouseEvent event) {

        if(event.isPrimaryButtonDown()){
            game.clickOn(event.getX(), event.getY(), "left");
        }
        else if(event.isSecondaryButtonDown()){
            game.clickOn(event.getX(), event.getY(), "right");
        }
    }

    @FXML
    void byeArcherTowerAction(ActionEvent event) {
        game.byeTower("archers");
    }

    @FXML
    void byeBombTowerAction(ActionEvent event) {
        game.byeTower("bomb");
    }

    @FXML
    void startButtonAction(ActionEvent event) {
        game.startWaves();
    }

    @FXML
    void farmCoinsAction(ActionEvent event) {
        game.plusOneCoin();
    }

    @FXML
    void backButtonAction() throws Exception {
        if(game != null){
            loadMenu(stage);
        }
    }

    public void loadMenu(Stage stage) throws Exception {
        FXMLLoader menuLoad = new FXMLLoader(getClass().getResource("menuUI.fxml"));
        Scene menu = new Scene(menuLoad.load());
        stage.setScene(menu);

        ControllerMenu menuController = menuLoad.getController();
        menuController.transferStage(stage);
    }

    public void transferStage(Stage stage){
        this.stage = stage;
    }

}