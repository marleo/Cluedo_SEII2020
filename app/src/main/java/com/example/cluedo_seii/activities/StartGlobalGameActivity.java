package com.example.cluedo_seii.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.R;
import com.example.cluedo_seii.network.Callback;
import com.example.cluedo_seii.network.connectionType;
import com.example.cluedo_seii.network.dto.NewGameRoomRequestDTO;
import com.example.cluedo_seii.network.kryonet.GlobalNetworkHostKryo;
import com.example.cluedo_seii.network.kryonet.KryoHelper;
import com.example.cluedo_seii.network.kryonet.NetworkClientKryo;

public class StartGlobalGameActivity extends AppCompatActivity {
    public static connectionType conType;
    private GlobalNetworkHostKryo globalHost;
    private NetworkClientKryo client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_global_game);


        findViewById(R.id.globalClientList).setVisibility(View.INVISIBLE);

        Game.reset();

        //TODO add Logic if the game is ready to start
        final Button chooseCharacter = findViewById(R.id.global_choose_player_button);
        chooseCharacter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(StartGlobalGameActivity.this, ChoosePlayerScreen.class));
            }
        });

        final Button selectClientButton = findViewById(R.id.global_client_button);
        selectClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        final Button selectHostButton = findViewById(R.id.global_host_button);
        selectHostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conType = connectionType.GLOBALHOST;
                globalHost = GlobalNetworkHostKryo.getInstance();
                KryoHelper.registerClasses(globalHost);

                try {
                    //TODO change to server ip address
                    globalHost.connect("192.168.178.20");
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        final Button connectButton = findViewById(R.id.connect_global_game_button);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });
    }


    private void connect() {
        if (conType == connectionType.GLOBALHOST) {
            selectHost();
        }
    }

    private void selectHost() {
        final ListView clientList = findViewById(R.id.globalClientList);
        clientList.setVisibility(View.VISIBLE);

        globalHost.registerReceivedGameRoomCallback(new Callback<NewGameRoomRequestDTO>() {
            @Override
            public void callback(final NewGameRoomRequestDTO argument) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView userNameInput = findViewById(R.id.global_username_input);
                        userNameInput.setText(argument.getCreatedRoom());
                    }
                });
            }
        });

        EditText usernameInput = findViewById(R.id.global_username_input);
        String username = usernameInput.getText().toString();

        NewGameRoomRequestDTO newGameRoomRequestDTO = new NewGameRoomRequestDTO();
        newGameRoomRequestDTO.setUsername(username);

        globalHost.sendMessage(newGameRoomRequestDTO);



    }

    
}
