package org.example.towerdefense.Online;

import org.example.towerdefense.Game;
import org.example.towerdefense.LauncherThread;
import org.example.towerdefense.LoopedThread;
import org.example.towerdefense.Message;
import org.example.towerdefense.Units.Towers.ArchersTower;
import org.example.towerdefense.Units.Towers.BombTower;
import org.example.towerdefense.Units.Towers.Tower;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Connection {
    protected Game game;
    protected static Socket clientSocket;
    protected static ServerSocket server;
    protected ObjectInputStream in;
    protected ObjectOutputStream out;
    protected LoopedThread mainThread;
    protected LoopedThread inputThread;
    protected LoopedThread outputThread;
    protected final BlockingQueue<Message> sendUpdateQueue = new LinkedBlockingQueue<Message>();
    protected final double updateFrequency;
    private String state = "not connected";
    private boolean mainThreadStarted = false;



    public Connection(Game game, double updateFrequency) { //ow server
        this.game = game;
        this.updateFrequency = updateFrequency;
    }

    public void start() throws IOException { //ow server
        initThreads();
    }

    protected String createConnection() throws IOException {
        return "ok";
    }

    protected synchronized void setStartGameSettings() throws IOException, ClassNotFoundException { //ow

    }

    protected void sendUpdates() throws IOException, ClassNotFoundException {
        if (!sendUpdateQueue.isEmpty()) {
            List<Message> dataList = new ArrayList<Message>(sendUpdateQueue);
            out.writeObject(dataList);
            out.flush();
            sendUpdateQueue.clear();
        }
    }

    protected void takeUpdates() throws IOException, ClassNotFoundException {
        @SuppressWarnings("unchecked")
        List<Message> updatesQueueFromRemotePlayer = (List<Message>) in.readObject();

        for (Message update : updatesQueueFromRemotePlayer) {
            HashMap<String, Object> data = update.data;

            if (update.message.equals("insert tower")) {
                Tower tower = null;
                if(data.get("className").equals("archers tower")){
                    tower = new ArchersTower((HashMap<String, Object>)data);
                }
                else if (data.get("className").equals("bomb tower")) {
                    tower = new BombTower((HashMap<String, Object>)data);
                }
                assert tower != null;
                tower.setPathPolygonList(game.level.pathPolygonList);
                tower.placeThisOnPolygon(game.gameBoard.polygonsList.get((int) data.get("polygonId")));
                game.gameBoard.towersList.add(tower);
            }

            else if(update.message.equals("remove tower")){
                game.gameBoard.towersList.remove(game.gameBoard.polygonsList.get((int) data.get("polygonId")).unit);
                game.gameBoard.polygonsList.get((int) data.get("polygonId")).unit = null;
            }

            else if (update.message.equals("change coins")) {
                game.level.coinsQuantity += (Integer) data.get("coins");
            }

            else if (update.message.equals("start waves")) {
                game.startWaves();
            }
        }
    }

    protected void createInputThread() throws IOException {
        inputThread = new LoopedThread(updateFrequency, new Runnable() {
            @Override
            public void run() {
                try {
                    takeUpdates();
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("inputThread: " + e.getMessage());
                }
            }

        });
    }

    protected void createOutputThread() throws IOException {
        outputThread = new LoopedThread( updateFrequency, new Runnable() {
            @Override
            public void run() {
                try {
                    sendUpdates();
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("outputThread: " + e.getMessage());
                }
            }
        });
    }


    protected void initThreads() {
        mainThread = new LoopedThread(updateFrequency, new Runnable() {
            @Override
            public void run() {
                if(!mainThreadStarted){
                    try {
                        if(createConnection().equals("ok")){
                            createOutputThread();
                            createInputThread();
                            setStartGameSettings();
                            outputThread.start();
                            inputThread.start();
                            mainThreadStarted = true;
                        };

                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                update();
            }
        });
        mainThread.start();
    }


    private void takeUpdateQueueFromGame() {
        if (sendUpdateQueue.isEmpty()) {
            sendUpdateQueue.addAll(game.getUpdateQueue());
        }
        game.getUpdateQueue().clear();
    }

    private void update() {
        takeUpdateQueueFromGame();
    }


    public void close() {
        System.out.println("close");
    }

    public String getState(){
        return state;
    }

    protected void setState(String state){
        this.state = state;
    }
}