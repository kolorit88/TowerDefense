package org.example.towerdefense;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.example.towerdefense.Units.Towers.ArchersTower;
import org.example.towerdefense.Units.Towers.BombTower;
import org.example.towerdefense.Units.Towers.Tower;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.paint.Color.GOLD;

public class GameBoard {
    public AnchorPane gameBoardPane;
    public List<Polygon> polygonsList = new ArrayList<Polygon>();
    public List<Polygon> pathPolygonsList = new ArrayList<Polygon>();
    public List<Tower> towersList = new ArrayList<>();
    public double height;
    public double width;
    public Color backGroundColor;
    public Color borderColor;
    public double x;
    public double y;
    public Level level;
    public Button byeArcherButton;
    public Button byeBombButton;
    public Button startButton;
    public Button farmCoinsButton;

    public Text coinsText;

    private double polygonSize;
    private double borderSizeProp;
    private double borderSize;
    private Canvas canvas;



    public GameBoard(AnchorPane bordPane, double borderSize, Color backGroundColor, Color borderColor, Level level, Button byeArcherButton, Button byeBombButton, Button startButton, Button farmCoinsButton, Text coinsText) {
        this.farmCoinsButton = farmCoinsButton;
        this.coinsText = coinsText;
        this.byeArcherButton = byeArcherButton;
        this.byeBombButton = byeBombButton;
        this.startButton = startButton;
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
        this.canvas.setMouseTransparent(true);
        gameBoardPane.getChildren().add(canvas);

        for(int i = 0; i <= 99; i++){
            polygonsList.add(new Polygon(i, polygonSize, canvas, gameBoardPane, this.backGroundColor));
        }
    }

    public void init(){
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

        resizeAndMoveMenu();
    }

    private void clearCanvas(){
        if(this.canvas != null){
            Canvas oldCanvas = this.canvas;

            canvas = new Canvas(getPaneWidth(), getPaneHeight());
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, getPaneWidth(), getPaneHeight());

            gameBoardPane.getChildren().add(canvas);
            this.canvas.setMouseTransparent(true);
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
        this.width = this.height = sidesSizes.stream().min(Double::compare).get() * 0.75;
        this.borderSize = this.width * this.borderSizeProp;
        this.polygonSize = (this.width - this.borderSize * 10) / 10;
    }


    private void updateCoordinatesXY(){
        this.x = (getPaneWidth() - width) / 2;
        this.y = (getPaneHeight() - height) / 1.5;
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

    private void resizeAndMoveMenu(){
        coinsText.setText(Integer.toString(level.coinsQuantity));
        coinsText.setWrappingWidth(width * 0.15);
        coinsText.setFont(new Font( width * 0.032));
        coinsText.relocate(x + (width / 5) * 2 + polygonSize * 0.3, y - height * 0.15);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GOLDENROD);
        gc.fillOval(x + (width / 5) * 2 + polygonSize * 0.2, y - height * 0.15, height * 0.05, height * 0.05);
        gc.setFill(GOLD);
        gc.fillOval(x + (width / 5) * 2 + polygonSize * 0.2 + (height * 0.01) / 2, y - height * 0.15 + (height * 0.01) / 2, height * 0.04, height * 0.04);

        farmCoinsButton.setPrefSize(width * 0.2, height * 0.05);
        farmCoinsButton.relocate(x + (width / 5) * 2, y - height * 0.08);

        byeArcherButton.setPrefSize(width * 0.2, height * 0.05);
        byeArcherButton.relocate(x + (width / 10), y - height * 0.08);
        new ArchersTower(0, 0, 0).draw(x + (width / 10) + polygonSize / 2 + borderSize, y - height * 0.1 - polygonSize, polygonSize, canvas);


        byeBombButton.setPrefSize(width * 0.2, height * 0.05);
        byeBombButton.relocate(x + (width / 10) * 7, y - height * 0.08);
        new BombTower(0, 0, 0).draw(x + (width / 10) * 7 + polygonSize / 2 + borderSize, y - height * 0.1 - polygonSize, polygonSize, canvas);

        startButton.setPrefSize(width * 0.2, height * 0.05);
        startButton.relocate(x + (width / 5) * 2, y + height*1.03);
    }

    private double getPaneWidth(){
        List<Double> sidesSizes = List.of(gameBoardPane.getPrefWidth(), gameBoardPane.getWidth());
        return sidesSizes.stream().max(Double::compare).get();
    }

    private double getPaneHeight(){
        List<Double> sidesSizes = List.of(gameBoardPane.getPrefHeight(), gameBoardPane.getHeight());
        return sidesSizes.stream().max(Double::compare).get();
    }

    public Polygon getPolygonContained(double x, double y){
        for(Polygon polygon:polygonsList){
            if((x >= polygon.x && x <= polygon.x + polygonSize) && (y >= polygon.y && y <= polygon.y + polygonSize)){
                return polygon;
            }
        }
        return null;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public List<Tower> getTowersList() {
        return towersList;
    }
}
