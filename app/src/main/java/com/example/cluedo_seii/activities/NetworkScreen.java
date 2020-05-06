package com.example.cluedo_seii.activities;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cluedo_seii.Network.Callback;
import com.example.cluedo_seii.Network.connectionType;
import com.example.cluedo_seii.Network.dto.BaseMessage;
import com.example.cluedo_seii.Network.dto.TextMessage;
import com.example.cluedo_seii.Network.kryonet.NetworkClientKryo;
import com.example.cluedo_seii.Network.kryonet.NetworkServerKryo;

import com.example.cluedo_seii.R;

import java.io.IOException;

public class NetworkScreen extends AppCompatActivity {
    private connectionType conType;
    private NetworkServerKryo server;
    private NetworkClientKryo client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

    }

    public void selectHost(View view) {
        this.conType = connectionType.HOST;

        TextView txtType = findViewById(R.id.typeText);
        txtType.setText("HOST");

        server = new NetworkServerKryo();
        server.registerClass(BaseMessage.class);
        server.registerClass(TextMessage.class);
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        TextView serverResponse = findViewById(R.id.serverResponse);
        serverResponse.setText(ip);

        server.registerCallback(new Callback<BaseMessage>() {
            @Override
            public void callback(BaseMessage argument) {
                TextView serverResponseT = findViewById(R.id.serverResponse);
                serverResponseT.setText(argument.toString());
            }
        });
    }

    public void sendMessage(View view) {
        if (conType.equals(connectionType.HOST)) {
            EditText msgInput = findViewById(R.id.editMessage);
            String message = msgInput.getText().toString();

            server.broadcastMessage(new TextMessage(message));
        } else if (conType.equals(connectionType.CLIENT)) {
            EditText msgInput = findViewById(R.id.editMessage);
            String message = msgInput.getText().toString();

            client.sendMessage(new TextMessage(message));
        }
    }

    public void selectClient(View view) {
        try {
            this.conType = connectionType.CLIENT;

            TextView txtType = findViewById(R.id.typeText);
            txtType.setText("CLIENT");

            client = NetworkClientKryo.getInstance();

            client.registerClass(BaseMessage.class);
            client.registerClass(TextMessage.class);

            client.registerCallback(new Callback<BaseMessage>() {
                @Override
                public void callback(BaseMessage argument) {
                    TextView serverResponse = findViewById(R.id.serverResponse);
                    serverResponse.setText(argument.toString());
                }
            });

            //client.connect("localhost");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connectToHost(View view) {
        if (conType.equals(connectionType.CLIENT)) {
            EditText ipInput = findViewById(R.id.ipAddressInput);
            String ip = ipInput.getText().toString();

            //TODO delete hardcoded ip
            ip = "192.168.178.47";

            try {
                client.connect(ip);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

}
