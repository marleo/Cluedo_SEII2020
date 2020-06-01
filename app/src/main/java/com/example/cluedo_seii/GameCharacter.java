package com.example.cluedo_seii;

import android.graphics.Point;

import com.example.cluedo_seii.spielbrett.GameboardElement;

import java.io.Serializable;

public class GameCharacter implements Serializable {

    private String name;
    private Point startingPoint;


    public GameCharacter() {
        //no arg constructor for deserialization
    }

    public GameCharacter(String name, Point startingPoint){


        this.name = name;
        this.startingPoint = startingPoint;

    }

    public String getName() {
        return name;
    }

    public Point getStartingPoint() { return startingPoint; }
}
