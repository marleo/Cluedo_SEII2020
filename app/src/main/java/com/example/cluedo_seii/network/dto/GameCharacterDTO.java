package com.example.cluedo_seii.network.dto;

import com.example.cluedo_seii.GameCharacter;

import java.util.LinkedList;

public class GameCharacterDTO extends RequestDTO{
    private GameCharacter choosenPlayer;
    private boolean isAccpeted;
    private LinkedList<GameCharacter> availablePlayers;

    public GameCharacter getChoosenPlayer() {
        return choosenPlayer;
    }

    public void setChoosenPlayer(GameCharacter choosenPlayer) {
        this.choosenPlayer = choosenPlayer;
    }

    public void setAccpeted(boolean accpeted) {
        isAccpeted = accpeted;
    }

    public boolean isAccpeted() {
        return isAccpeted;
    }

    public void setAvailablePlayers(LinkedList<GameCharacter> availablePlayers) {
        this.availablePlayers = availablePlayers;
    }

    public LinkedList<GameCharacter> getAvailablePlayers() {
        return availablePlayers;
    }
}
