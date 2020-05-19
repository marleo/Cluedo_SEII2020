package com.example.cluedo_seii.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cluedo_seii.Network.Callback;
import com.example.cluedo_seii.Network.ClientData;
import com.example.cluedo_seii.Network.connectionType;
import com.example.cluedo_seii.Network.dto.ConnectedDTO;
import com.example.cluedo_seii.Network.dto.QuitGameDTO;
import com.example.cluedo_seii.Network.dto.RequestDTO;
import com.example.cluedo_seii.Network.dto.TextMessage;
import com.example.cluedo_seii.Network.dto.UserNameRequestDTO;
import com.example.cluedo_seii.Network.kryonet.NetworkClientKryo;
import com.example.cluedo_seii.Network.kryonet.NetworkServerKryo;
import com.example.cluedo_seii.R;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class startGameScreen extends AppCompatActivity {
    private connectionType conType;
    private NetworkServerKryo server;
    private NetworkClientKryo client;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game_screen);
    }

    public void selectHost(View view) {
        this.conType = connectionType.HOST;

        server = NetworkServerKryo.getInstance();
        server.registerClass(RequestDTO.class);
        server.registerClass(TextMessage.class);
        server.registerClass(QuitGameDTO.class);
        server.registerClass(ConnectedDTO.class);
        server.registerClass(UserNameRequestDTO.class);

        server.registerNewClientCallback(new Callback<LinkedHashMap<Integer, ClientData>>() {
            @Override
            public void callback(LinkedHashMap<Integer, ClientData> argument) {
                final LinkedList<String> usernameList = new LinkedList<>();
                for (ClientData clientData : argument.values()) {
                    usernameList.add(clientData.getUsername());
                }

                // UPDATE Current Players
                runOnUiThread(new Runnable() {
                    public void run() {
                        TextView userNameInput = findViewById(R.id.usernam_input);
                        userNameInput.setText(usernameList.toString());
                    }
                });
            }
        });

        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        TextView serverResponse = findViewById(R.id.ipAddress);
        serverResponse.setText(ip);
    }

    public void selectClient(View view) {
        try {
            this.conType = connectionType.CLIENT;

            client = NetworkClientKryo.getInstance();


            client.registerClass(RequestDTO.class);
            client.registerClass(TextMessage.class);
            client.registerClass(QuitGameDTO.class);
            client.registerClass(ConnectedDTO.class);
            client.registerClass(UserNameRequestDTO.class);

            //client.connect("localhost");

            EditText ipInput = findViewById(R.id.ipAddress);
            String ip = ipInput.getText().toString();

            if (ip.equals("ip")) {
                //TODO delete hardcoded ip
                ip = "192.168.178.47";
            }

            EditText username_input = findViewById(R.id.usernam_input);
            final UserNameRequestDTO userNameRequestDTO = new UserNameRequestDTO();
            userNameRequestDTO.setUsername(username_input.getText().toString());

            client.registerConnectionCallback(new Callback<ConnectedDTO>() {
                @Override
                public void callback(ConnectedDTO argument) {
                    // ausführung nach erflogreichem verbinden
                    Log.d("Connection Callback", "callback: ");
                    client.sendUsernameRequest(userNameRequestDTO);
                }
            });


            try {
                client.connect(ip);
            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
