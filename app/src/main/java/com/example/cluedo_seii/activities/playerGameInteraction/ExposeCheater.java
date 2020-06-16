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

    private Game game;
    private Player selectedPlayer;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expose_cheater);
        game = Game.getInstance();

        ArrayList<String> userNames = new ArrayList<>();

        //hinzufügen der usernames, der verbundenen Spieler, in die ArrayList userNames
        for (Player player : game.getPlayers()) {
            if (player.getUsername() != null) {
                userNames.add(player.getUsername());
            }
        }
        Button accuseCheaterButton = findViewById(R.id.accuseCheaterButton);
        //instanzieren und initalisieren des Spinners
        Spinner cheaterSpinner = findViewById(R.id.possibleCheater);
        cheaterSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, userNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cheaterSpinner.setAdapter(adapter);

        View.OnClickListener onButtonClickListener = new View.OnClickListener() {
            //onClick startet accuseCheating() function
            @Override
            public void onClick(View v) {
                accuseCheating();
            }
        };
        accuseCheaterButton.setOnClickListener(onButtonClickListener);


    }

    //speichert Player welcher im Spinner ausgewählt wurde
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        for (Player player : game.getPlayers()) {
            if (parent.getItemAtPosition(position).equals(player.getUsername())) {
                String selectedUsername = (String) parent.getItemAtPosition(position);
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
        connectionType conType = SelectedConType.getConnectionType();
        Toast toast;
        String text;
        if(conType ==connectionType.HOST){
            NetworkServerKryo server = NetworkServerKryo.getInstance();
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

        } else if(conType==connectionType.CLIENT || conType==connectionType.GLOBALCLIENT){
            NetworkClientKryo client = NetworkClientKryo.getInstance();
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
        } else if (conType==connectionType.GLOBALHOST ) {
            GlobalNetworkHostKryo globalHost = GlobalNetworkHostKryo.getInstance();
            if(globalHost.getCheater().getId()==selectedPlayer.getId()){
                text = "Deine Anschuldigung ist richtig";
                toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                toast.show();
                globalHost.guessedCheater();
            }else {
                text = "Deine Anschuldigung ist falsch";
                toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                toast.show();
                globalHost.setCheated(1);
            }
        }
    }

}

