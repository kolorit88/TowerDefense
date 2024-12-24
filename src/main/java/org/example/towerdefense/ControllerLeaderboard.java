package org.example.towerdefense;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerLeaderboard {
    @FXML
    private TableView<PlayerInfo> Leaderboard;

    @FXML
    private TableColumn<PlayerInfo, String> nameCol;

    @FXML
    private TableColumn<PlayerInfo, Integer> lossesCol;

    @FXML
    private TableColumn<PlayerInfo, Integer> winsCol;

    @FXML
    private TableColumn<PlayerInfo, Double> winRateCol;

    @FXML
    private AnchorPane leaderboardPane;

    @FXML
    private Button backButton;

    private Stage stage;

    @FXML
    private void backButtonAction() throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menuUI.fxml"));
        Scene menuUI = new Scene(menuLoader.load());
        stage.setScene(menuUI);
        ControllerMenu controllerMenu = menuLoader.getController();
        controllerMenu.transferStage(stage);
    }

    @FXML
    void LeaderboardSort(ActionEvent event) {
    }

    @FXML
    private void initialize() {
        Database appDataBase = new Database("jdbc:sqlite:src/main/resources/org/example/towerdefense/AppDatabase.db");
        List<HashMap<String, Object>> playersList = appDataBase.readPlayers();
        ObservableList<PlayerInfo> obsPlayersList = getPlayerInfoList(playersList);
        obsPlayersList.sort(Comparator.comparing(PlayerInfo::getWinRate).reversed());

        Leaderboard.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        nameCol.setResizable(false);
        lossesCol.setResizable(false);
        winsCol.setResizable(false);
        winRateCol.setResizable(false);

        nameCol.setSortable(false);
        lossesCol.setSortable(false);
        winsCol.setSortable(false);
        winRateCol.setSortable(false);

        nameCol.setEditable(false);
        lossesCol.setEditable(false);
        winsCol.setEditable(false);
        winRateCol.setEditable(false);

        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        winsCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getWins()).asObject());
        lossesCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getLoses()).asObject());
        winRateCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getWinRate()).asObject());

        Leaderboard.setItems(obsPlayersList);
    }

    private Comparator<PlayerInfo> getWinRateComparator() {
        return (p1, p2) -> Double.compare(p1.getWinRate(), p2.getWinRate());
    }

    private ObservableList<PlayerInfo> getPlayerInfoList(List<HashMap<String, Object>> data) {
        List<PlayerInfo> playerInfos = data.stream().map(PlayerInfo::new).collect(Collectors.toList());
        return FXCollections.observableArrayList(playerInfos);
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}
