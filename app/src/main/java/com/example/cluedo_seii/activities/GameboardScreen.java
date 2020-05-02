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
                "1112011102111" +
                "1110011100111" +
                "1130011100111" +
                "0000003300111" +
                "1110000003111" +
                "1113000000000" +
                "1113000000002" +
                "1110000000002" +
                "0000000000000" +
                "1111300031111" +
                "1111100001111" +
                "1111300031111" +
                "0000000000000" +
                "1111100031111" +
                "1111100031111" +
                "1111300011111" +
                "0000000000000" +
                "0000031100000" +
                "0020111102000";

        Gameboard gameboard = new Gameboard(this,13,19, gameBoard);
        setContentView(gameboard.getLayout());
    }
}
