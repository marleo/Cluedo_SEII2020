package com.example.cluedo_seii.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cluedo_seii.R;

import com.example.cluedo_seii.spielbrett.Gameboard;

public class GameboardScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spielbrett_screen);

        /*
            0 = GameField
            1 = NoneWalkableElement
            2 = StartingPoint
            3 = Room
         */

        String gameBoard =
                "0002000002000" +
                "0000000000000" +
                "0000000000000" +
                "0333300033330" +
                "0333300033330" +
                "0333300033330" +
                "0000000000000" +
                "0200000000020" +
                "0000000000000" +
                "0333300033330" +
                "0333300033330" +
                "0333300033330" +
                "0000000000000" +
                "0000000000000" +
                "0333300033330" +
                "0333300033330" +
                "0333300033330" +
                "0000000000000" +
                "0002000002000";

        Gameboard gameboard = new Gameboard(this,13,19, gameBoard);
        setContentView(gameboard.getLayout());

        /*final Button notepad_Button = findViewById(R.id.notepadButton);
        notepad_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameboardScreen.this, NotepadScreen.class));
            }
        });*/
    }
}
