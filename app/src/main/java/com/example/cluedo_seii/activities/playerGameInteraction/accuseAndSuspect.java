package com.example.cluedo_seii.activities.playerGameInteraction;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.GameState;
import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.R;

import java.util.LinkedList;

public class accuseAndSuspect extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerCulprit;
    private Spinner spinnerWeapon;
    private ArrayAdapter<CharSequence> adapterCulprit;
    private ArrayAdapter<CharSequence>adapterWeapon;
    private String selectedCulprit;
    private String selectedWeapon;
    private String selectedRoom;
    private String[]possibleCulprits;
    private String[]possibleWeapons;
    private String[] possibleRooms;
    private Button suspectButton;
    private ImageView weaponChoice;
    private ImageView characterChoice;
    private Game game;
    private LinkedList<String> suspectedPlayerHand;
    private Toast toast;
    private String text;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accuse_and_suspect);

        game = Game.getInstance();
        //Speicherung der Auswahl des Spielers

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

        TextView currentRoom = findViewById(R.id.currentRoomText);
        currentRoom.setText("Tatort: " + getCurrentRoom());

        weaponChoice = findViewById(R.id.weaponImage);
        characterChoice = findViewById(R.id.suspectImage);

        possibleCulprits = getResources().getStringArray(R.array.culprits);
        possibleWeapons = getResources().getStringArray(R.array.weapons);
        possibleRooms = getResources().getStringArray(R.array.rooms);

        selectedRoom = getCurrentRoom();

        suspectButton = findViewById(R.id.makeSuspicionButton);
        suspectButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                suspectedPlayerHand = game.getCurrentPlayer().suspect(selectedCulprit, selectedWeapon, selectedRoom, game.getPlayers());

                for (Player player : game.getPlayers()) {
                    if (player.getPlayerCharacter().getName() == selectedCulprit) {
                        player.setPosition(game.getCurrentPlayer().getPosition());
                    }
                }

                //Zeigt Spielerkarten des Verdächtigten

                if (suspectedPlayerHand.size() == 0) {
                    text = "Hier gibt es nichts zum sehen";
                    toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }

                //Meldugn falls Verdächtiger keine der Karten auf seiner Hand hat

                else {
                    text = "Der verdächtigte Spieler hat folgende Karten auf der Hand:" + '\n';
                    for (int i = 0; i < suspectedPlayerHand.size(); i++) {
                        text += suspectedPlayerHand.get(i) + '\n';
                    }

                    toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                    finish();

                }
            }
        });

        suspectButton = findViewById(R.id.makeAccusationButton);
        suspectButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //Im Falle einer erfolgreichen Anklage

                if(game.getCurrentPlayer().accuse(selectedCulprit, selectedWeapon, selectedRoom, game.getInvestigationFile())) {
                    game.setGameOver(true);
                    text = "Gratuliere, du hast das Spiel gewonnen";
                    toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                    game.setGameOver(true);
                    //TODO Nachricht an andere Mitspieler verschicken
                    finish();
                }

                //Im Fall einer falschen Anklage

                else {
                    text = "Du hast eine falsche Anklage erhoben und kannst das Spiel nicht mehr gewinnen";
                    toast = Toast.makeText(getApplicationContext(), text , Toast.LENGTH_SHORT);
                    toast.show();
                    //TODO Nachricht an andere Mitspieler verschicken
                    finish();
                }
            }
        });
    }

    public String getCurrentRoom() {
        int playerX = game.getCurrentPlayer().getPosition().x;
        int playerY = game.getCurrentPlayer().getPosition().y;

        if(playerX == 3 && playerY == 2){
            return possibleRooms[0];
        } else if(playerX == 6 && playerY == 2){
            return possibleRooms[1];
        } else if(playerX == 9 && playerY == 2){
            return possibleRooms[2];
        } else if(playerX == 3 && playerY == 5 || playerX == 1 && playerY == 6){
            return possibleRooms[3];
        } else if(playerX == 8 && playerY == 9 || playerX == 8 && playerY == 8){
            return possibleRooms[4];
        } else if(playerX == 1 && playerY == 9 || playerX == 4 && playerY == 10){
            return possibleRooms[5];
        } else if(playerX == 3 && playerY == 13){
            return possibleRooms[6];
        } else if(playerX == 9 && playerY == 13){
            return possibleRooms[7];
        } else if(playerX == 4 && playerY == 17 || playerX == 7 && playerY == 17){
            return possibleRooms[8];
        }

        return "";
    }


    public void onStop(){
        super.onStop();
        game.changeGameState(GameState.PLAYERTURNEND);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        for (String possibleCulprit : possibleCulprits) {
            if (parent.getItemAtPosition(position).equals(possibleCulprit)) {
                selectedCulprit = (String) parent.getItemAtPosition(position);
                if(selectedCulprit.equals(possibleCulprits[0])){
                    characterChoice.setImageResource(R.drawable.gatov);
                } else if(selectedCulprit.equals(possibleCulprits[1])){
                    characterChoice.setImageResource(R.drawable.bloom);
                } else if(selectedCulprit.equals(possibleCulprits[2])){
                    characterChoice.setImageResource(R.drawable.green);
                } else if(selectedCulprit.equals(possibleCulprits[3])){
                    characterChoice.setImageResource(R.drawable.porz);
                } else if(selectedCulprit.equals(possibleCulprits[4])){
                    characterChoice.setImageResource(R.drawable.gloria);
                } else if(selectedCulprit.equals(possibleCulprits[5])){
                    characterChoice.setImageResource(R.drawable.weiss);
                }
            }
        }

        for (String possibleWeapon : possibleWeapons) {
            if (parent.getItemAtPosition(position).equals(possibleWeapon)) {
                selectedWeapon = (String) parent.getItemAtPosition(position);
                if(selectedWeapon.equals(possibleWeapons[0])){
                    weaponChoice.setImageResource(R.drawable.dolch);
                } else if(selectedWeapon.equals(possibleWeapons[1])){
                    weaponChoice.setImageResource(R.drawable.leuchter);
                } else if(selectedWeapon.equals(possibleWeapons[2])){
                    weaponChoice.setImageResource(R.drawable.pistol);
                } else if(selectedWeapon.equals(possibleWeapons[3])){
                    weaponChoice.setImageResource(R.drawable.seil);
                } else if(selectedWeapon.equals(possibleWeapons[4])){
                    weaponChoice.setImageResource(R.drawable.heizungsrohr);
                } else if(selectedWeapon.equals(possibleWeapons[5])){
                    weaponChoice.setImageResource(R.drawable.rohrzange);
                }
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

}
