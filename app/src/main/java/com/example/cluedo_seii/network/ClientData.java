package com.example.cluedo_seii.network;

import com.esotericsoftware.kryonet.Connection;
import com.example.cluedo_seii.Player;

public class ClientData {
    private static int currentID = 2;

    private int id;
    private Player player;
    private Connection connection;
    private String username;

    public int getId() {
        return id;
    }

    public void setId() {
        this.id = currentID++;
    }

    public void setId(int id){
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
