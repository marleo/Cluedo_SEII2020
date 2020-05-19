package com.example.cluedo_seii.Network.kryonet;

import android.os.Handler;
import android.util.Log;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.example.cluedo_seii.Network.Callback;
import com.example.cluedo_seii.Network.ClientData;
import com.example.cluedo_seii.Network.NetworkServer;
import com.example.cluedo_seii.Network.dto.QuitGameDTO;
import com.example.cluedo_seii.Network.dto.RequestDTO;
import com.example.cluedo_seii.Network.dto.TextMessage;
import com.example.cluedo_seii.Network.dto.UserNameRequestDTO;

import java.io.IOException;
import java.util.LinkedHashMap;

import static android.content.ContentValues.TAG;

public class NetworkServerKryo implements KryoNetComponent, NetworkServer {
    //SINGLETON_INSTANCE
    private static NetworkServerKryo INSTANCE = null;

    private Server server;
    private Callback<RequestDTO> messageCallback;

    private LinkedHashMap<Integer, ClientData> clientList;

    private NetworkServerKryo() {
        server = new Server();
    }

    public static NetworkServerKryo getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NetworkServerKryo();
        }

        return INSTANCE;
    }

    public void start() throws IOException {
        server.start();
        server.bind(NetworkConstants.TCP_PORT,NetworkConstants.UDP_PORT);

        server.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (messageCallback != null && object instanceof RequestDTO)
                    handleRequest(connection, object);
                    //messageCallback.callback((RequestDTO) object);
            }
        });
    }

    private void handleRequest(Connection connection, Object object) {
        if (object instanceof TextMessage) {
            messageCallback.callback((TextMessage) object);
        } else if (object instanceof UserNameRequestDTO) {
            handleUsernameRequest(connection, (UserNameRequestDTO) object);
        }
    }

    private void handleUsernameRequest(Connection connection, UserNameRequestDTO userNameRequestDTO) {
        // TODO implement
        Log.i("UserNameRequest", userNameRequestDTO.getUsername());
        ClientData client = new ClientData();
        client.setConnection(connection);

        clientList.put(client.getId(), client);
    }


    @Override
    public void registerCallback(Callback<RequestDTO> callback) {
        this.messageCallback = callback;
    }

    @Override
    public void broadcastMessage(RequestDTO message) {
        for (Connection connection: server.getConnections()) {
            sendMessageToClient(message, connection);
        }
    }

    private void sendMessageToClient(final RequestDTO message, final Connection connection) {
        Log.d(TAG, "sendMessageToClient: " + message.toString());

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

    //TODO delete Users from List
    public void resetNetwork() {
        sendQuitGame();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                server.close();
                server.stop();

                INSTANCE = new NetworkServerKryo();
            }
        });

        clientList.clear();
    }

    private void sendQuitGame() {
        QuitGameDTO quitGame = new QuitGameDTO();
        broadcastMessage(quitGame);
    }
}
