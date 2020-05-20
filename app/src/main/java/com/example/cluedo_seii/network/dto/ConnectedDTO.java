package com.example.cluedo_seii.network.dto;

public class ConnectedDTO extends RequestDTO{
    private boolean isConnected;

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public boolean isConnected() {
        return isConnected;
    }
}
