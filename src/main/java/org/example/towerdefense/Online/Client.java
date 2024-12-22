package org.example.towerdefense.Online;

import org.example.towerdefense.Game;
import org.example.towerdefense.Level;
import org.example.towerdefense.Message;
import org.example.towerdefense.Units.Castle;
import org.example.towerdefense.Units.Enemies.Enemy;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Client extends Connection{
    String IpAddress;
    String port;

    public Client(Game game, String IpAddress, String port, double updateFrequency) {
        super(game, updateFrequency);
        this.IpAddress = IpAddress;
        this.port = port;
    }

    @Override
    protected String createConnection() throws IOException {
        System.out.println("Ожидаем подключение к серверу");

        while (true){
            try {
                clientSocket = new Socket(IpAddress, Integer.parseInt(port));
                break;
            }
            catch (ConnectException | RuntimeException e) {
                System.out.println("Указанный сервер не найден");
                return "error";
            }
        }

        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());

        this.setState("connected");
        System.out.println("Успешно подключился к серверу");
        return "ok";
    }

    @Override
    protected void setStartGameSettings() throws IOException, ClassNotFoundException {
        @SuppressWarnings("unchecked")
        Message settings = (Message) in.readObject();

        game.setLevel(new Level(settings.data));

        System.out.println("я получил настройки" + settings);

        out.writeObject("клиент принял настройки" + settings);
        out.flush();
        System.out.println("finish init settings");
    }

    @Override
    public void close(){
        try {
            if(mainThread != null && inputThread != null && outputThread != null) {
                mainThread.interrupt();
                inputThread.interrupt();
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
