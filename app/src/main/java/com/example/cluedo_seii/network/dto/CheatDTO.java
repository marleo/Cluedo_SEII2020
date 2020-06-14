package com.example.cluedo_seii.network.dto;


import com.example.cluedo_seii.Player;

public class CheatDTO extends RequestDTO{
    private Player cheater;

    public Player getCheater(){
        return cheater;
    }

    public void setCheater(Player cheater){
        this.cheater=cheater;
    }

}
