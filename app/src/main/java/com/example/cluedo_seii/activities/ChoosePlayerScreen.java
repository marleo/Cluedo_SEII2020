package com.example.cluedo_seii.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cluedo_seii.GameCharacter;
import com.example.cluedo_seii.network.connectionType;

import android.os.Bundle;
import android.util.Log;

import com.example.cluedo_seii.R;
import com.example.cluedo_seii.network.dto.GameCharacterDTO;
import com.example.cluedo_seii.network.kryonet.NetworkClientKryo;
import com.example.cluedo_seii.network.kryonet.NetworkServerKryo;
import com.example.cluedo_seii.spielbrett.GameFieldElement;

import java.util.LinkedList;

public class ChoosePlayerScreen extends AppCompatActivity {
    private connectionType conType;
    private NetworkServerKryo server;
    private NetworkClientKryo client;

    private LinkedList<GameCharacter> availableCharacters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_player);

        this.conType = startGameScreen.conType;

        if (conType == connectionType.CLIENT) {
            client = NetworkClientKryo.getInstance();
            //TODO register Callback
        } else if (conType.equals(connectionType.HOST)) {
            server = NetworkServerKryo.getInstance();

            //TODO implement the startingPoint from the Characters
            GameCharacter character1 = new GameCharacter("Character 1", null);
            GameCharacter character2 = new GameCharacter("Character 2", null);
            GameCharacter character3 = new GameCharacter("Character 3", null);
            GameCharacter character4 = new GameCharacter("Character 4", null);
            GameCharacter character5 = new GameCharacter("Character 5", null);
            GameCharacter character6 = new GameCharacter("Character 6", null);

            availableCharacters.add(character1);
            availableCharacters.add(character2);
            availableCharacters.add(character3);
            availableCharacters.add(character4);
            availableCharacters.add(character5);
            availableCharacters.add(character6);

            GameCharacterDTO gameCharacterDTO = new GameCharacterDTO();
            gameCharacterDTO.setAvailablePlayers(availableCharacters);
            server.broadcastMessage(gameCharacterDTO);
            //TODO register Callback
        } else {
            Log.d("ERROR", "Neither Client nor Server at Choose Player Screen");
        }
    }





















}
