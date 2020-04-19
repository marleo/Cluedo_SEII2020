package com.example.cluedo_seii.Network.kryonet;

import android.os.AsyncTask;
import android.util.Log;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.example.cluedo_seii.Network.Callback;
import com.example.cluedo_seii.Network.NetworkClient;
import com.example.cluedo_seii.Network.dto.BaseMessage;

import java.io.IOException;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class NetworkClientKryo extends AsyncTask<String, Void, Void> implements NetworkClient, KryoNetComponent {
    private Client client;
    private Callback<BaseMessage> callback;

    public NetworkClientKryo() {
        client = new Client();
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            connect(strings[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public void connect(String host) throws IOException {
        client.start();
        client.connect(5000,host,NetworkConstants.TCP_PORT,NetworkConstants.UDP_PORT);

        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (callback != null && object instanceof BaseMessage)
                    callback.callback((BaseMessage) object);
            }
        });
    }

    @Override
    public void registerCallback(Callback<BaseMessage> callback) {
        this.callback = callback;
    }

    @Override
    public void sendMessage(BaseMessage message) {
        client.sendTCP(message);
    }

    @Override
    public void registerClass(Class c) {
        client.getKryo().register(c);
    }

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
}
