package com.example.cluedo_seii.network.dto;

public class WinDTO extends RequestDTO {
    private String winner;

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }
}
