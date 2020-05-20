package com.example.cluedo_seii.activities.playerGameInteraction;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cluedo_seii.R;

import android.content.Intent;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cluedo_seii.Game;

public class AccuseSomeone extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerCulprit;
    private Spinner spinnerWeapon;
    private Spinner spinnerRoom;
    private ArrayAdapter<CharSequence>adapterCulprit;
    private ArrayAdapter<CharSequence>adapterWeapon;
    private ArrayAdapter<CharSequence>adapterRoom;
    private String selectedCulprit;
    private String selectedWeapon;
    private String selectedRoom;
    private String[]possibleCulprits;
    private String[]possibleWeapons;
    private String[] possibleRooms;
    private Button suspectButton;
    private Intent intent;
    private Game game;
    private Toast toast;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accuse_someone);

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

        spinnerRoom = (Spinner) findViewById(R.id.suspectedRoom);
        spinnerRoom.setOnItemSelectedListener(this);
        adapterRoom = ArrayAdapter.createFromResource(this, R.array.rooms, android.R.layout.simple_spinner_item);
        adapterRoom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoom.setAdapter(adapterRoom);

        possibleCulprits = getResources().getStringArray(R.array.culprits);
        possibleWeapons = getResources().getStringArray(R.array.weapons);
        possibleRooms = getResources().getStringArray(R.array.rooms);

        intent = getIntent();

        game = (Game)intent.getSerializableExtra("game");

        suspectButton = findViewById(R.id.makeAccusationButton);
        suspectButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //Im Falle einer erfolgreichen Anklage

                if(game.getCurrentPlayer().accuse(selectedCulprit, selectedWeapon, selectedRoom, game.getInvestigationFile()) == true) {
                    game.setGameOver(true);
                    text = "Gratuliere, du hast das Spiel gewonnen";
                    toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                    String message="hello ";
                    intent = new Intent();
                    intent.putExtra("game", game);
                    setResult(1,intent);
                    finish();
                }

                //Im Fall einer falschen Anklage

                else {
                    text = "Du hast eine falsche Anklage erhoben und kannst das Spiel nicht mehr gewinnen";
                    toast = Toast.makeText(getApplicationContext(), text , Toast.LENGTH_SHORT);
                    toast.show();
                    intent = new Intent();
                    intent.putExtra("game", game);
                    setResult(0,intent);
                    finish();
                }
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        for(int i = 0; i<possibleCulprits.length; i++) {
            if (parent.getItemAtPosition(position).equals(possibleCulprits[i])) {
                selectedCulprit = (String) parent.getItemAtPosition(position);
            }
        }

        for(int i = 0; i<possibleWeapons.length; i++)
        {
            if(parent.getItemAtPosition(position).equals(possibleWeapons[i]))
            {
                selectedWeapon = (String)parent.getItemAtPosition(position);
            }
        }

        for(int i = 0; i<possibleRooms.length; i++)
        {
            if(parent.getItemAtPosition(position).equals(possibleRooms[i]))
            {
                selectedRoom = (String)parent.getItemAtPosition(position);

            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

}

