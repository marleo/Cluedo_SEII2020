package com.example.cluedo_seii.activities.playerGameInteraction;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import com.example.cluedo_seii.GameState;
import com.example.cluedo_seii.R;

import android.content.Intent;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.network.connectionType;
import com.example.cluedo_seii.network.dto.AccusationMessageDTO;
import com.example.cluedo_seii.network.kryonet.GlobalNetworkHostKryo;
import com.example.cluedo_seii.network.kryonet.NetworkClientKryo;
import com.example.cluedo_seii.network.kryonet.NetworkServerKryo;
import com.example.cluedo_seii.network.kryonet.SelectedConType;

public class AccuseSomeone extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerCulprit;
    private Spinner spinnerWeapon;
    private Spinner spinnerRoom;
    private ArrayAdapter<CharSequence> adapterCulprit;
    private ArrayAdapter<CharSequence> adapterWeapon;
    private ArrayAdapter<CharSequence> adapterRoom;
    private String selectedCulprit;
    private String selectedWeapon;
    private String selectedRoom;
    private String[] possibleCulprits;
    private String[] possibleWeapons;
    private String[] possibleRooms;
    private Button suspectButton;
    private Game game;
    private Toast toast;
    private String text;
    private NetworkServerKryo server;
    private NetworkClientKryo client;
    private GlobalNetworkHostKryo globalHost;
    private connectionType conType;
    private AccusationMessageDTO accusationMessageDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accuse_someone);
        initializeNetwork();

        spinnerCulprit = (Spinner) findViewById(R.id.suspectedCulprit);
        spinnerCulprit.setOnItemSelectedListener(this);
        adapterCulprit = ArrayAdapter.createFromResource(this, R.array.culprits, android.R.layout.simple_spinner_item);
        adapterCulprit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCulprit.setAdapter(adapterCulprit);

        spinnerWeapon = (Spinner) findViewById(R.id.suspectedWeapon);
        spinnerWeapon.setOnItemSelectedListener(this);
        adapterWeapon = ArrayAdapter.createFromResource(this, R.array.weapons, android.R.layout.simple_spinner_item);
        adapterWeapon.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeapon.setAdapter(adapterWeapon);

        spinnerRoom = (Spinner) findViewById(R.id.suspectedRoom);
        spinnerRoom.setOnItemSelectedListener(this);
        adapterRoom = ArrayAdapter.createFromResource(this, R.array.rooms, android.R.layout.simple_spinner_item);
        adapterRoom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoom.setAdapter(adapterRoom);

        possibleCulprits = getResources().getStringArray(R.array.culprits);
        possibleWeapons = getResources().getStringArray(R.array.weapons);
        possibleRooms = getResources().getStringArray(R.array.rooms);

        game = Game.getInstance();

        suspectButton = findViewById(R.id.makeAccusationButton);
        suspectButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //Im Falle einer erfolgreichen Anklage

                if (game.getCurrentPlayer().accuse(selectedCulprit, selectedWeapon, selectedRoom, game.getInvestigationFile()) == true) {
                    game.setGameOver(true);
                    text = "Gratuliere, du hast das Spiel gewonnen";
                    toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                    game.setGameOver(true);
                    accusationMessageDTO = new AccusationMessageDTO();
                    accusationMessageDTO.setAccuser(game.getCurrentPlayer());
                    accusationMessageDTO.setWinMessage();
                    //TODO Nachricht an andere Mitspieler verschicken
                    finish();
                }

                //Im Fall einer falschen Anklage

                else {
                    game.getCurrentPlayer().setMadeFalseAccusation(true);
                    text = "Du hast eine falsche Anklage erhoben und kannst das Spiel nicht mehr gewinnen";
                    toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                    game.incrWrongAccusers();
                    game.getCurrentPlayer().setMadeFalseAccusation(true);
                    game.getLocalPlayer().setMadeFalseAccusation(true);
                    accusationMessageDTO = new AccusationMessageDTO();
                    accusationMessageDTO.setAccuser(game.getCurrentPlayer());
                    accusationMessageDTO.setLooseMessage();
                    //TODO Nachricht an andere Mitspieler verschicken
                    sendAccusation();
                    finish();
                }
            }
        });
    }

    public void onStop() {
        super.onStop();
        game.changeGameState(GameState.PLAYERTURNEND);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        for (int i = 0; i < possibleCulprits.length; i++) {
            if (parent.getItemAtPosition(position).equals(possibleCulprits[i])) {
                selectedCulprit = (String) parent.getItemAtPosition(position);
            }
        }

        for (int i = 0; i < possibleWeapons.length; i++) {
            if (parent.getItemAtPosition(position).equals(possibleWeapons[i])) {
                selectedWeapon = (String) parent.getItemAtPosition(position);
            }
        }

        for (int i = 0; i < possibleRooms.length; i++) {
            if (parent.getItemAtPosition(position).equals(possibleRooms[i])) {
                selectedRoom = (String) parent.getItemAtPosition(position);

            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    //Netzwerkfunktionen

    //Netzwerkinitialisierung
    public void initializeNetwork(){
        conType = SelectedConType.getConnectionType();
        if(conType==connectionType.HOST) {
            server = NetworkServerKryo.getInstance();
        }
        else if(conType == connectionType.GLOBALHOST) {
            globalHost = GlobalNetworkHostKryo.getInstance();
        }
        else if(conType==connectionType.CLIENT || conType==connectionType.GLOBALCLIENT){
            client = NetworkClientKryo.getInstance();
        }
    }

    //Verschickung des Anklageergebnisses
    public void sendAccusation( ){
        //TODO add if for globalhost and global Client
        if(conType==connectionType.HOST) {
            server.broadcastMessage(accusationMessageDTO);
        }
        else if(conType==connectionType.CLIENT){
           client.sendMessage(accusationMessageDTO);
        } else if (conType == connectionType.GLOBALCLIENT) {
            client.broadcastToGameRoom(accusationMessageDTO);
        } else if(conType==connectionType.GLOBALHOST) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                globalHost.broadcastToClients(accusationMessageDTO);
            }
        }
    }


}


