package org.example.towerdefense;

import java.util.HashMap;

public class PlayerInfo {
    private String name;
    private int wins;
    private int losses;
    private double winRate;

    public PlayerInfo(HashMap<String, Object> map) {
        this.name = (String) map.get("name");
        this.wins = (int) map.get("wins");
        this.losses = (int) map.get("losses");
        if(wins + losses == 0){
            winRate = 0;
        }
        else {
            winRate = (double) Math.round((double) wins / (wins + losses) * 100) / 100;
        }
    }

    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }

    public int getLoses() {
        return losses;
    }

    public double getWinRate() {
        return winRate;
    }
}