package com.example.cluedo_seii.activities.NetworkActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.network.Callback;
import com.example.cluedo_seii.network.ClientData;
import com.example.cluedo_seii.network.connectionType;
import com.example.cluedo_seii.network.dto.ConnectedDTO;
import com.example.cluedo_seii.network.dto.UserNameRequestDTO;
import com.example.cluedo_seii.network.kryonet.KryoHelper;
import com.example.cluedo_seii.network.kryonet.NetworkClientKryo;
import com.example.cluedo_seii.network.kryonet.NetworkServerKryo;
import com.example.cluedo_seii.R;
import com.example.cluedo_seii.network.kryonet.SelectedConType;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class StartGameScreen extends AppCompatActivity {
    //public static connectionType conType;
    private NetworkServerKryo server;
    private NetworkClientKryo client;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game_screen);

        findViewById(R.id.localConnectButton).setVisibility(View.INVISIBLE);

        //reset Game
        Game.reset();

        final Button chooseCharacter = findViewById(R.id.chooseCharacter);
        chooseCharacter.setVisibility(View.INVISIBLE);
        chooseCharacter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(StartGameScreen.this, ChoosePlayerScreen.class));
            }
        });

        final Button selectClientButton = findViewById(R.id.localSelectClientButton);
        selectClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.localSelectHostButton).setVisibility(View.INVISIBLE);
                SelectedConType.setConnectionType(connectionType.CLIENT);

                client = NetworkClientKryo.getInstance();
                KryoHelper.registerClasses(client);

                final Button connectButton = findViewById(R.id.localConnectButton);
                connectButton.setVisibility(View.VISIBLE);
                connectButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectClient(v);
                    }
                });
            }
        });

        final Button selectHostButton = findViewById(R.id.localSelectHostButton);
        selectHostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.localSelectClientButton).setVisibility(View.INVISIBLE);
                SelectedConType.setConnectionType(connectionType.HOST);

                server = NetworkServerKryo.getInstance();
                KryoHelper.registerClasses(server);

                final Button hostGame = findViewById(R.id.localConnectButton);
                hostGame.setVisibility(View.VISIBLE);
                hostGame.setText(R.string.startHost);
                hostGame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectHost(v);
                        hostGame.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }

    public void selectClient(View view) {
        EditText ipInput = findViewById(R.id.ipAddress);
        String ip = ipInput.getText().toString();


        EditText usernameInput = findViewById(R.id.username_input);
        final UserNameRequestDTO userNameRequestDTO = new UserNameRequestDTO();
        userNameRequestDTO.setUsername(usernameInput.getText().toString());

        client.registerConnectionCallback(new Callback<ConnectedDTO>() {
            @Override
            public void callback(ConnectedDTO argument) {
                // ausführung nach erflogreichem verbinden
                Log.d("Connection Callback", "callback: ");
                client.sendUsernameRequest(userNameRequestDTO);
                // after a succesfull connection the client gets forwarded to the choose Player Activity
                startActivity(new Intent(StartGameScreen.this, ChoosePlayerScreen.class));
            }
        });


        try {
            client.connect(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectHost(View view) {
        final ListView clientList = findViewById(R.id.clientList);

        TextView usernameTextView = findViewById(R.id.username_input);
        String username = usernameTextView.getText().toString();
        server.setHostUsername(username);

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
                        if (usernameList.size() >= 1) {
                            findViewById(R.id.chooseCharacter).setVisibility(View.VISIBLE);
                        }

                        TextView userNameInput = findViewById(R.id.username_input);
                        ArrayAdapter<String> clientListAdapter = new ArrayAdapter<>(StartGameScreen.this, android.R.layout.simple_list_item_1,usernameList);
                        clientList.setAdapter(clientListAdapter);
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

        TextView ipAddressField = findViewById(R.id.ipAddress);
        ipAddressField.setText(ip);

        // hide Username Input Field && show UserList
        findViewById(R.id.username_input).setVisibility(View.INVISIBLE);

        clientList.setVisibility(View.VISIBLE);
    }
}
