package com.example.cluedo_seii.network.dto;

public class SendToOnePlayerDTO extends RequestDTO {
    private int playerID;
    private String serializedObject;

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getSerializedObject() {
        return serializedObject;
    }

    public void setSerializedObject(String serializedObject) {
        this.serializedObject = serializedObject;
    }
}
