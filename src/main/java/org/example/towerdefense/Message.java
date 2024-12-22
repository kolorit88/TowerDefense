package org.example.towerdefense;

import java.io.Serializable;
import java.util.HashMap;

public class Message implements Serializable {
    public String message;
    public HashMap<String, Object> data;

    public Message(HashMap<String, Object> data, String name) {
        this.message = name;
        this.data = data;
    }

}