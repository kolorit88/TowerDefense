package org.example.towerdefense;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.example.towerdefense.Units.Enemies.Enemy;
import org.example.towerdefense.Units.Level;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    public AnchorPane gameBoardPane;
    public List<Polygon> polygonsList = new ArrayList<Polygon>();
    public List<Polygon> pathPolygonsList = new ArrayList<Polygon>();
    public double height;
    public double width;
    public Color backGroundColor;
    public Color borderColor;
    public double x;
    public double y;
    public Level level;

    private double polygonSize;
    private double borderSizeProp;
    private double borderSize;
    private Canvas canvas;



    public GameBoard(AnchorPane bordPane, double borderSize, Color backGroundColor, Color borderColor, Level level) {
        this.gameBoardPane = bordPane;
        this.height = getPaneHeight();
        this.width = getPaneWidth();
        this.borderSize = borderSize;
        this.borderSizeProp = this.borderSize/width;
        this.polygonSize = (this.width - this.borderSize * 10) / 10;
        this.backGroundColor = backGroundColor;
        this.borderColor = borderColor;
        this.level = level;

        this.canvas = new Canvas(getPaneWidth(), getPaneHeight());
        gameBoardPane.getChildren().add(canvas);

        for(int i = 0; i <= 99; i++){
            polygonsList.add(new Polygon(i, polygonSize, canvas, gameBoardPane, this.backGroundColor));
        }

        for(Integer num: level.getPathNumbersList()){
            Polygon polygon = polygonsList.get(num);
            polygon.setColor(Color.PALEGREEN);
            level.addToPathPolygonList(polygon);
        }
    }

    public void updateBoard(){ // запускать в игровом потоке
        updateGameBoardSizes();
        updateCoordinatesXY();

        clearCanvas();

        updateGameBoardPolygons();
        drawBorders();
    }

    private void clearCanvas(){
        if(this.canvas != null){
            Canvas oldCanvas = this.canvas;

            canvas = new Canvas(getPaneWidth(), getPaneHeight());
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, getPaneWidth(), getPaneHeight());

            gameBoardPane.getChildren().add(canvas);
            gameBoardPane.getChildren().remove(oldCanvas);
        }
    }

    private void updateGameBoardPolygons(){
        int count = 0;
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                polygonsList.get(count).setCanvas(canvas);
                polygonsList.get(count).setSize(polygonSize);
                polygonsList.get(count).setCoordinates(x + (borderSize + polygonSize) * j + borderSize/2,
                        y + (borderSize + polygonSize) * i + borderSize/2);
                polygonsList.get(count).paintYourself();

                count++;
            }
        }
    }


    private void updateGameBoardSizes(){
        List<Double> sidesSizes = List.of(getPaneHeight(), getPaneWidth());
        this.width = this.height = sidesSizes.stream().min(Double::compare).get();
        this.borderSize = this.width * this.borderSizeProp;
        this.polygonSize = (this.width - this.borderSize * 10) / 10;
    }


    private void updateCoordinatesXY(){
        this.x = (getPaneWidth() - width) / 2;
        this.y = (getPaneHeight() - height) / 2;
    }

    private void drawBorders(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(borderColor);

        for(int i = 0; i < 11; i++){
            gc.fillRect(x + (width / 10) * i - borderSize / 2, y, borderSize, height);
        }
        for(int i = 0; i < 11; i++){
            gc.fillRect(x, y + (height / 10) * i - borderSize / 2, width, borderSize);
        }

    }

    private double getPaneWidth(){
        List<Double> sidesSizes = List.of(gameBoardPane.getPrefWidth(), gameBoardPane.getWidth());
        return sidesSizes.stream().max(Double::compare).get();
    }

    private double getPaneHeight(){
        List<Double> sidesSizes = List.of(gameBoardPane.getPrefHeight(), gameBoardPane.getHeight());
        return sidesSizes.stream().max(Double::compare).get();
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
