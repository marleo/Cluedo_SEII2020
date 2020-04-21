package com.example.cluedo_seii.activities;

import android.os.Bundle;

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
                "1120000000211" +
                "1300000000031" +
                "0000000000000" +
                "0000000000000" +
                "0000000000000" +
                "0000000000000" +
                "1300000000031" +
                "1100000000011" +
                "1300000000031" +
                "0000000000000" +
                "2000000000002" +
                "1300000000031" +
                "1100000000011" +
                "0000000000000" +
                "0000000000000" +
                "0000000000000" +
                "0000000000000" +
                "1300000000031" +
                "1120000000211";

        Gameboard gameboard = new Gameboard(this,13,19, gameBoard);
        setContentView(gameboard.getLayout());
    }
}
