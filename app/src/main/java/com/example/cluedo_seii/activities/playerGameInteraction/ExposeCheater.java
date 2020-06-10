package com.example.cluedo_seii.activities.playerGameInteraction;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.esotericsoftware.kryonet.Client;
import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.R;
import com.example.cluedo_seii.activities.StartGameScreen;
import com.example.cluedo_seii.network.Callback;
import com.example.cluedo_seii.network.ClientData;
import com.example.cluedo_seii.network.connectionType;
import com.example.cluedo_seii.network.dto.CheatDTO;
import com.example.cluedo_seii.network.dto.UserNameRequestDTO;
import com.example.cluedo_seii.network.kryonet.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;

import com.example.cluedo_seii.network.kryonet.KryoHelper;
import com.example.cluedo_seii.network.kryonet.NetworkServerKryo;

public class ExposeCheater extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button accuseCheaterButton;
    private Toast toast;
    private Spinner cheaterSpinner;
    private String selectedUsername;
    private Game game;
    private LinkedHashMap<Integer, ClientData> clientList;
    private ArrayList<Integer> ids;
    private String text;
    private Player selectedPlayer;
    private NetworkServerKryo server;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expose_cheater);
        game=Game.getInstance();
        server = NetworkServerKryo.getInstance();
        for(Player player : game.getPlayers()){
            ids.add(player.getId());
        }
        //clientList=networkServerKryo.getClientList();

        accuseCheaterButton=findViewById(R.id.accuseCheaterButton);
        /*Set<Integer> keys = clientList.keySet();
        for(Integer i : keys){
            usernames.add(clientList.get(i).getUsername());
        }*/



        cheaterSpinner=findViewById(R.id.possibleCheater);
        cheaterSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, ids);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cheaterSpinner.setAdapter(adapter);

        View.OnClickListener onButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //accuseCheating(selectedPlayer);


            }
        };
        accuseCheaterButton.setOnClickListener(onButtonClickListener);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       /* for (Player player : game.getPlayers()) {
            if (parent.getItemAtPosition(position).equals(player.getUsername())) {
                selectedUsername = (String) parent.getItemAtPosition(position);
                for (Player player2 : game.getPlayers()) {
                    if (selectedUsername == player2.getUsername()) {
                        selectedPlayer = player2;

                    }
                }
            }

        }*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*public void accuseCheating(Player player){
        CheatDTO cheatDTO = new CheatDTO();

            if (cheatDTO.getCheater()==player){
                if(clientData.getCheated()>0){
                    text = "Deine Anschuldigung ist richtig";
                    toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    text = "Deine Anschuldigung ist falsch";
                    toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();

                }
            }


        }
    }*/

}

