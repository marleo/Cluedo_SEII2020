package com.example.cluedo_seii.Network;

import com.esotericsoftware.kryonet.Connection;
import com.example.cluedo_seii.Player;

public class ClientData {
    private int id;
    private Player player;
    private Connection connection;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
