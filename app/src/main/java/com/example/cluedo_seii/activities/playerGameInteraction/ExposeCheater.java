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

/**
 * Die Klasse ExposeCheater öffnet die Activity activity_expose_cheater und dient dazu,
 * jemanden der geschummelt hat zu verdächtigen. Ist die Verdächtigung richtig erhält der
 * Verdächtiger eine weitere Schummelmöglickeit, falls nicht wird ihm eine Schummelmöglichkeit
 * genommen.
 */

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

        //hinzufügen der usernames, der verbundenen Spieler, in die ArrayList userNames
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
                client = NetworkClientKryo.getInstance();
                server = NetworkServerKryo.getInstance();
                if(client.getCheated()==0&&server.getCheated()==0){
                    text="Deine Anschuldigung ist falsch";
                    toast=Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG);
                    toast.show();
                }
                else{accuseCheating();}
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

    /**
     * Prüft connectionType und dann ob die id des selectedPlayer gleich der des Players,
     * der zuletzt geschummelt hat, ist. Zeigt Toast mit einer Nachricht ob die Anschuldigung
     * richtig oder falsch war und ändert Wert der cheated Variable.
     */
    public void accuseCheating() {
        conType = SelectedConType.getConnectionType();
        if(conType==connectionType.HOST) {
            server = NetworkServerKryo.getInstance();
            if (!server.getExposedCheater()){
                if (server.getCheater().getId() == selectedPlayer.getId()) {
                    text = "Deine Anschuldigung ist richtig";
                    toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                    server.guessedCheater();
                    server.setExposedCheater();
                } else {
                    text = "Deine Anschuldigung ist falsch";
                    toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                    server.setCheated(1);
                    server.setExposedCheater();
                }
        }
        } else if(conType==connectionType.CLIENT) {
            client = NetworkClientKryo.getInstance();
            if (!client.getExposedCheater()){
                if (client.getCheater().getId() == selectedPlayer.getId()) {
                    text = "Deine Anschuldigung ist richtig";
                    toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                    client.guessedCheater();
                    client.setExposedCheater();
                } else {
                    text = "Deine Anschuldigung ist falsch";
                    toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                    client.setCheated(1);
                    client.setExposedCheater();
                }
        }
        }
    }

}

