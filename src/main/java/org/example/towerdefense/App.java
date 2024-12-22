package org.example.towerdefense;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menuUI.fxml"));
        Scene menuUI = new Scene(menuLoader.load());
        stage.setScene(menuUI);
        ControllerMenu controllerMenu = menuLoader.getController();
        controllerMenu.transferStage(stage);


        stage.setResizable(false);
        stage.setTitle("Tower Defense");
        stage.setMinWidth(600);
        stage.setMinHeight(600);
        stage.show();

    }
    public static void main(String[] args) {launch(args);}

}