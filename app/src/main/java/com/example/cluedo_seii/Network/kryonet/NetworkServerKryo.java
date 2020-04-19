package com.example.networktest.network.kryonet;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.example.networktest.network.Callback;
import com.example.networktest.network.NetworkServer;
import com.example.networktest.network.dto.BaseMessage;

import java.io.IOException;

public class NetworkServerKryo implements KryoNetComponent, NetworkServer {
    private Server server;
    private Callback<BaseMessage> messageCallback;

    public NetworkServerKryo() {
        server = new Server();
    }

    public void start() throws IOException {
        server.start();
        server.bind(NetworkConstants.TCP_PORT,NetworkConstants.UDP_PORT);

        server.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (messageCallback != null && object instanceof BaseMessage)
                    messageCallback.callback((BaseMessage) object);
            }
        });
    }

    @Override
    public void registerCallback(Callback<BaseMessage> callback) {
        this.messageCallback = callback;
    }

    @Override
    public void broadcastMessage(BaseMessage message) {
        for (Connection connection: server.getConnections()) {
            connection.sendTCP(message);
        }
    }

    @Override
    public void registerClass(Class c) {
        server.getKryo().register(c);
    }

    public String getAddress() {
        return null;
    }
}
