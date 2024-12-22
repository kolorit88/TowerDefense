package org.example.towerdefense.Units;

import java.util.HashMap;

public class Castle extends GameUnit{
    public double lives;
    public Castle(double lives){
        this.lives = lives;
    }
    public Castle(HashMap<String, Double> map){
        this.lives = map.get("lives");
    }
    public void loseOneLive(){
        lives--;
        System.out.println(lives);
    }

    @Override
    public String getClassName(){
        return "castle";
    }

    public HashMap<String, Double> toHashMap(){
        HashMap<String, Double> tmpMap = new HashMap<>();
        tmpMap.put("lives", lives);
        return tmpMap;
    }
}
