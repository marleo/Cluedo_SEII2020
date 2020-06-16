package com.example.cluedo_seii.network.dto;

import com.example.cluedo_seii.GameCharacter;

import java.util.HashMap;

public class GameCharacterDTO extends RequestDTO{
    private GameCharacter choosenPlayer;
    private HashMap<String,GameCharacter> availablePlayers;

    public GameCharacter getChoosenPlayer() {
        return choosenPlayer;
    }

    public void setChoosenPlayer(GameCharacter choosenPlayer) {
        this.choosenPlayer = choosenPlayer;
    }

    public void setAvailablePlayers(HashMap<String, GameCharacter> availablePlayers) {
        this.availablePlayers = availablePlayers;
    }

    public HashMap<String, GameCharacter> getAvailablePlayers() {
        return availablePlayers;
    }
}
