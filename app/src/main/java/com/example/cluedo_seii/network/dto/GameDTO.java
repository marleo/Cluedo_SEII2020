package com.example.cluedo_seii.network.dto;

import com.example.cluedo_seii.Game;

public class GameDTO extends RequestDTO{
    private Game game;

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
