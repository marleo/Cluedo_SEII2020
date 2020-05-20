package com.example.cluedo_seii.Network.kryonet;

import android.util.Log;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.example.cluedo_seii.Network.Callback;
import com.example.cluedo_seii.Network.NetworkClient;
import com.example.cluedo_seii.Network.dto.ConnectedDTO;
import com.example.cluedo_seii.Network.dto.RequestDTO;
import com.example.cluedo_seii.Network.dto.TextMessage;
import com.example.cluedo_seii.Network.dto.UserNameRequestDTO;

import java.io.IOException;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class NetworkClientKryo implements NetworkClient, KryoNetComponent {
    //INSTANCE
    private static NetworkClientKryo INSTANCE = null;

    private Client client;
    private Callback<RequestDTO> callback;
    private Callback<ConnectedDTO> connectionCallback;

    private boolean isConnected;

    private NetworkClientKryo() {
        client = new Client();
    }

    public static NetworkClientKryo getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NetworkClientKryo();
        }

        return INSTANCE;
    }

    @Override
    public void connect(final String host) throws IOException {
        client.start();



        new Thread("Connection") {
            @Override
            public void run() {
                try {
                    client.connect(5000,host,NetworkConstants.TCP_PORT,NetworkConstants.UDP_PORT);

                    Log.d("WTF", "WTF");
                    ConnectedDTO connectedDTO = new ConnectedDTO();
                    connectedDTO.setConnected(true);
                    sendMessage(connectedDTO);

                    isConnected = true;
                } catch (IOException e) {
                    Log.e(TAG, "run: ", e);
                }
            }
        }.start();

        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof RequestDTO) {
                    Log.i(TAG, "received: " + object.toString());
                    handleRequest(connection,object);
                    //callback.callback((RequestDTO) object );
                }
            }
        });
    }

    private void handleRequest(Connection connection, Object object) {
        if (object instanceof TextMessage) {
            callback.callback((RequestDTO) object );
        } else if (object instanceof ConnectedDTO) {
            if (connectionCallback != null) {
                connectionCallback.callback((ConnectedDTO) object);
            }
        }
    }

    @Override
    public void registerCallback(Callback<RequestDTO> callback) {
        this.callback = callback;
    }

    public void registerConnectionCallback(Callback<ConnectedDTO> callback) {
        System.out.println("Callback registered");
        this.connectionCallback = callback;
    }

    @Override
    public void sendMessage(final RequestDTO message) {
        new Thread("send") {
            @Override
            public void run() {
                Log.d("Sending Object:", message.getClass().toString());
                client.sendTCP(message);
            }
        }.start();

    }

    @Override
    public void registerClass(Class c) {
        client.getKryo().register(c);
    }

    public void sendUsernameRequest(final UserNameRequestDTO userNameRequestDTO) {
        Log.d("UserNameRequest", userNameRequestDTO.getUsername());
        sendMessage(userNameRequestDTO);
    }

    //TODO delete
    public void getNetworks() {
        client.start();
        List<InetAddress> hosts = client.discoverHosts(NetworkConstants.UDP_PORT,10000);

        Log.i(TAG, "getNetworks: length:" + hosts.size());

        List<String> hostsOut = new LinkedList<>();
        for (InetAddress host: hosts) {
            Log.i(TAG, "getNetworks: " + host.toString());
            hostsOut.add( host.getHostAddress());
        }


        client.stop();
        //return hostsOut;
    }

    private void quitGameHandler() {
        client.close();
        client.stop();
    }


    public boolean isConnected() {
        return isConnected;
    }
}
