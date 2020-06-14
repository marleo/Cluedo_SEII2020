package com.example.cluedo_seii.activities.playerGameInteraction;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.R;
import com.example.cluedo_seii.network.connectionType;
import com.example.cluedo_seii.network.kryonet.*;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.cluedo_seii.network.kryonet.NetworkServerKryo;

public class ExposeCheater extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Serializable {

    private Button accuseCheaterButton;
    private Toast toast;
    private Spinner cheaterSpinner;
    private String selectedUsername;
    private Game game;
    private ArrayList<String> userNames;
    private String text;
    private Player selectedPlayer;
    private NetworkServerKryo server;
    private NetworkClientKryo client;
    private connectionType conType;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expose_cheater);
        game = Game.getInstance();

        userNames = new ArrayList<>();

        for (Player player : game.getPlayers()) {
            if (player.getUsername() != null) {
                userNames.add(player.getUsername());
            }
        }
        accuseCheaterButton = findViewById(R.id.accuseCheaterButton);
        cheaterSpinner = findViewById(R.id.possibleCheater);
        cheaterSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, userNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cheaterSpinner.setAdapter(adapter);

        View.OnClickListener onButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accuseCheating();
            }
        };
        accuseCheaterButton.setOnClickListener(onButtonClickListener);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        for (Player player : game.getPlayers()) {
            if (parent.getItemAtPosition(position).equals(player.getUsername())) {
                selectedUsername = (String) parent.getItemAtPosition(position);
                for (Player player2 : game.getPlayers()) {
                    if (selectedUsername == player2.getUsername()) {
                        selectedPlayer = player2;

                    }
                }
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void accuseCheating() {
        conType = SelectedConType.getConnectionType();
        if(conType==connectionType.HOST){
            server = NetworkServerKryo.getInstance();
            if(server.getCheater().getId()==selectedPlayer.getId()){
                text = "Deine Anschuldigung ist richtig";
                toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                toast.show();
                server.guessedCheater();
            }else {
                text = "Deine Anschuldigung ist falsch";
                toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                toast.show();
                server.setCheated(1);
            }
        } else if(conType==connectionType.CLIENT){
            client = NetworkClientKryo.getInstance();
            if(client.getCheater().getId()==selectedPlayer.getId()){
                text = "Deine Anschuldigung ist richtig";
                toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                toast.show();
                client.guessedCheater();
            }else {
                text = "Deine Anschuldigung ist falsch";
                toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                toast.show();
                client.setCheated(1);
            }
        }
    }

}

