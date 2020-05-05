package com.example.cluedo_seii.Network.kryonet;

import android.util.Log;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.example.cluedo_seii.Network.Callback;
import com.example.cluedo_seii.Network.NetworkServer;
import com.example.cluedo_seii.Network.dto.BaseMessage;

import java.io.IOException;

import static android.content.ContentValues.TAG;

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
            sendMessageToClient(message, connection);
        }
    }

    private void sendMessageToClient(final BaseMessage message, final Connection connection) {
        Log.d(TAG, "sendMessageToClient: ");

        new Thread("send") {
            @Override
            public void run() {
                connection.sendTCP(message);
            }
        }.start();
    }

    @Override
    public void registerClass(Class c) {
        server.getKryo().register(c);
    }

    public String getAddress() {
        return null;
    }
}
