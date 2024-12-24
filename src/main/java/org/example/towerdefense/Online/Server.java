package org.example.towerdefense.Online;

import org.example.towerdefense.Game;
import org.example.towerdefense.Message;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Server extends Connection{
    String port;
    public Server(Game game, String port, double updateFrequency) {
        super(game, updateFrequency);
        this.port = port;

        System.out.println("Сервер создан!");
    }

    @Override
    public void start() throws IOException{
        try{server = new ServerSocket(Integer.parseInt(port), 50, InetAddress.getByName("0.0.0.0"));}
        catch (BindException e){
            server = new ServerSocket(0, 50, InetAddress.getByName("0.0.0.0"));
        }
        initThreads();
    }

    @Override
    protected String createConnection(){
        System.out.println("Ждем 2-го игрока");
        try {
            clientSocket = server.accept();
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            this.setState("connected");
            System.out.println("2-ой игрок подключился!");
        } catch (IOException e) {
            System.out.println("сокет закрыт");
            return "error";
        }
        return "ok";
    }

    @Override
    protected void setStartGameSettings() throws IOException, ClassNotFoundException {
        Message startSettings = new Message(game.level.toHashMap(), "setLevel");

        out.writeObject(startSettings);
        out.flush();

        System.out.println("Resp from client:");
        System.out.println("finish init settings");
    }


    @Override
    public void close(){
        try {
            if(mainThread != null) {
                mainThread.interrupt();
            }
            if(inputThread != null) {
                inputThread.interrupt();
            };
            if (outputThread != null){
                outputThread.interrupt();
            }

            if(server != null){
                server.close();
            }
            if(clientSocket != null){
                clientSocket.close();
            }
            if(in != null){
                in.close();
            }
            if(out != null){
                out.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}