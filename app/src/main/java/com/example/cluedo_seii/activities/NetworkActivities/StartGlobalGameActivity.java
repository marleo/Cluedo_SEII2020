package com.example.cluedo_seii.activities.NetworkActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.R;
import com.example.cluedo_seii.network.Callback;
import com.example.cluedo_seii.network.ClientData;
import com.example.cluedo_seii.network.connectionType;
import com.example.cluedo_seii.network.dto.NewGameRoomRequestDTO;
import com.example.cluedo_seii.network.dto.RoomsDTO;
import com.example.cluedo_seii.network.kryonet.GlobalNetworkHostKryo;
import com.example.cluedo_seii.network.kryonet.KryoHelper;
import com.example.cluedo_seii.network.kryonet.NetworkClientKryo;
import com.example.cluedo_seii.network.kryonet.SelectedConType;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class StartGlobalGameActivity extends AppCompatActivity {
    //TODO change to server Ip
    private String serverIP = "192.168.178.20";

    //public static connectionType conType;
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
                SelectedConType.setConnectionType(connectionType.GLOBALCLIENT);

                client = NetworkClientKryo.getInstance();
                KryoHelper.registerClasses(client);

                try {
                    client.connect(serverIP);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        final Button selectHostButton = findViewById(R.id.global_host_button);
        selectHostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectedConType.setConnectionType(connectionType.GLOBALHOST);
                globalHost = GlobalNetworkHostKryo.getInstance();
                KryoHelper.registerClasses(globalHost);

                try {
                    globalHost.connect(serverIP);
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
        if (SelectedConType.getConnectionType() == connectionType.GLOBALHOST) {
            selectHost();
        } else if (SelectedConType.getConnectionType() == connectionType.GLOBALCLIENT) {
            selectClient();
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

        globalHost.registerNewClientCallback(new Callback<LinkedHashMap<Integer, ClientData>>() {
            @Override
            public void callback(LinkedHashMap<Integer, ClientData> argument) {
                final LinkedList<String> usernameList = new LinkedList<>();
                for (ClientData clientData : argument.values()) {
                    usernameList.add(clientData.getUsername());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ListView roomListView = findViewById(R.id.globalClientList);
                        roomListView.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> roomListAdapter = new ArrayAdapter<>(StartGlobalGameActivity.this, android.R.layout.simple_list_item_1, usernameList);
                        roomListView.setAdapter(roomListAdapter);
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

    private void selectClient() {
        final ListView clientList = findViewById(R.id.clientList);

        client.registerRoomCallback(new Callback<RoomsDTO>() {
            @Override
            public void callback(RoomsDTO argument) {
                Log.d("Available Rooms:", argument.getGameRooms().toString());
                updateRoomsList(argument.getGameRooms());
            }
        });

        //the client gets the available rooms after sending an empty rooms dto object
        client.sendMessage(new RoomsDTO());
    }

    private void updateRoomsList(final LinkedList<String> roomList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListView roomListView = findViewById(R.id.globalClientList);
                roomListView.setVisibility(View.VISIBLE);
                ArrayAdapter<String> roomListAdapter = new ArrayAdapter<>(StartGlobalGameActivity.this, android.R.layout.simple_list_item_1, roomList);
                roomListView.setAdapter(roomListAdapter);

                roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItem = (String) parent.getItemAtPosition(position);
                        Log.d("Selected Room: ", selectedItem);

                        int roomID = Integer.parseInt(selectedItem.split(" ")[1]);
                        Log.d("RoomID: ", Integer.toString(roomID));

                        RoomsDTO selectedRoom = new RoomsDTO();
                        selectedRoom.setSelectedRoom(roomID);

                        EditText usernameInput = findViewById(R.id.global_username_input);
                        selectedRoom.setUsername(usernameInput.getText().toString());

                        client.sendMessage(selectedRoom);
                        startActivity(new Intent(StartGlobalGameActivity.this, ChoosePlayerScreen.class));


                    }
                });
            }
        });
    }
}
