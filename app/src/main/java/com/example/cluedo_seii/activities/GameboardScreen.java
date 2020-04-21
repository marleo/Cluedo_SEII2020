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

        Gameboard gameboard = new Gameboard(this,7,12);
        setContentView(gameboard.getLayout());
    }
}
