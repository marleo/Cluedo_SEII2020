package com.example.cluedo_seii;

import com.example.cluedo_seii.spielbrett.GameboardElement;

import java.io.Serializable;

public class GameCharacter implements Serializable {

    private String name;
    private GameboardElement startingPoint;

    public GameCharacter(String name, GameboardElement startingPoint){

        this.name = name;
        this.startingPoint = startingPoint;

    }

    public String getName() {
        return name;
    }
}
