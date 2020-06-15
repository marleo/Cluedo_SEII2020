package com.example.cluedo_seii.spielbrett;

import android.graphics.Point;

import com.example.cluedo_seii.MyPoint;

public class StartingPoint {
    private int startingPointId;
    private int playerId; // Überlegen ob hier der Spieler (Klasse) verspeichert wird
    private MyPoint playerPosition;

    public StartingPoint(int startingPointId, int playerId) {
        this.startingPointId = startingPointId;
        this.playerId = playerId;
    }

    public int getStartingPointId() {
        return startingPointId;
    }

    public void setStartingPointId(int startingPointId) {
        this.startingPointId = startingPointId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public MyPoint getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(MyPoint playerPosition) {
        this.playerPosition = playerPosition;
    }
}
