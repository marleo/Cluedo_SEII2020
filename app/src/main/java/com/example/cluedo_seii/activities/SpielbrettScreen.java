package com.example.cluedo_seii.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cluedo_seii.R;

import com.example.cluedo_seii.spielbrett.Spielbrett;

public class SpielbrettScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spielbrett_screen);

        Spielbrett spielbrett = new Spielbrett(this,7,12);
        setContentView(spielbrett.getLayout());
    }
}
