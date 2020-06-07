package com.example.cluedo_seii.network.kryonet;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.example.cluedo_seii.DeckOfCards;
import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.network.Callback;
import com.example.cluedo_seii.network.NetworkGlobalHost;
import com.example.cluedo_seii.network.dto.ConnectedDTO;
import com.example.cluedo_seii.network.dto.GameCharacterDTO;
import com.example.cluedo_seii.network.dto.GameDTO;
import com.example.cluedo_seii.network.dto.PlayerDTO;
import com.example.cluedo_seii.network.dto.RegisterClassDTO;
import com.example.cluedo_seii.network.dto.RequestDTO;
import com.example.cluedo_seii.network.dto.SerializedDTO;
import com.example.cluedo_seii.network.dto.TextMessage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

import static android.content.ContentValues.TAG;

public class GlobalNetworkHostKryo implements NetworkGlobalHost, KryoNetComponent {

    //INSTANCE
    private static GlobalNetworkHostKryo INSTANCE = null;

    private Client client;
    private Callback<TextMessage> textMessageCallback;
    private Callback<RequestDTO> callback;
    private Callback<ConnectedDTO> connectionCallback;
    private Callback<GameCharacterDTO> characterCallback;
    private Callback<PlayerDTO> playerCallback;
    private Callback<GameDTO> gameCallback;

    private boolean isConnected;

    private GlobalNetworkHostKryo() {
        client = new Client();
    }


    public static GlobalNetworkHostKryo getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GlobalNetworkHostKryo();
        }

        return INSTANCE;
    }

    public static void deleteInstance() {
        INSTANCE = null;
    }


    @Override
    public void connect(final String host) throws IOException {
        Log.d("GLOBALHOST:", "Connecting to: " + host);
        client.start();



        new Thread("Connection") {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                try {
                    client.connect(5000,host,NetworkConstants.TCP_PORT,NetworkConstants.UDP_PORT);


                    RegisterClassDTO playerRegister = new RegisterClassDTO();
                    playerRegister.setClassToRegister(SerializationHelper.toString(DeckOfCards.class));
                    //sendMessage(playerRegister);
                    sendMessage(new TextMessage(SerializationHelper.toString(DeckOfCards.class)));

                    /*
                    RegisterClassDTO playerDTORegister = new RegisterClassDTO();
                    playerDTORegister.setClassToRegister(PlayerDTO.class);
                    sendMessage(playerDTORegister);

                     */

                    /*
                    ConnectedDTO connectedDTO = new ConnectedDTO();
                    connectedDTO.setConnected(true);
                    sendMessage(connectedDTO);
                     */

                    isConnected = true;
                } catch (IOException e) {
                    Log.e(TAG, "run: ", e);
                }
            }
        }.start();

        client.addListener(new Listener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleRequest(Connection connection, Object object) {
        if (object instanceof TextMessage) {
            //TODO delete
            textMessageCallback.callback((TextMessage) object );
        } else if (object instanceof SerializedDTO) {
            try {
                Log.d("Received Object: ", ((SerializedDTO) object).getSerializedObject());
                Log.d("Deserialized Object:", SerializationHelper.fromString(((SerializedDTO) object).getSerializedObject()).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void registerCallback(Callback<RequestDTO> callback) {
        //TODO implement
    }

    public void registerTextMessageCallback(Callback<TextMessage> callback) {
        this.textMessageCallback = callback;
    }

    public void sendMessage(final RequestDTO requestDTO) {
        new Thread("send") {
            @Override
            public void run() {
                Log.d("Sending Object:", requestDTO.getClass().toString());
                client.sendTCP(requestDTO);
            }
        }.start();

    }

    public void sendObject(final Object object) {
        new Thread("send") {
            @Override
            public void run() {
                Log.d("Sending Object:", object.getClass().toString());
                client.sendTCP(object);
            }
        }.start();

    }

    @Override
    public void registerClass(Class c) {
        client.getKryo().register(c);
    }

    @Override
    public void sendGame(Game game) {
        //TODO implement
    }
}

