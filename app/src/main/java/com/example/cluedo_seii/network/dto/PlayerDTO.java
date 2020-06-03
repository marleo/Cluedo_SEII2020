package com.example.cluedo_seii.network.dto;

import com.example.cluedo_seii.Player;

public class PlayerDTO extends RequestDTO{
    private Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
