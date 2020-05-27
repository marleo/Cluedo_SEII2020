package com.example.cluedo_seii.spielbrett.entrace;

import android.widget.LinearLayout;

import com.example.cluedo_seii.R;
import com.example.cluedo_seii.activities.GameboardScreen;
import com.example.cluedo_seii.spielbrett.GameboardElement;

public class Entrace7 extends GameboardElement {

    public Entrace7(GameboardScreen gameboardScreen, int xKoordinate, int yKoordinate, LinearLayout.LayoutParams layoutParams) {
        super(gameboardScreen, xKoordinate, yKoordinate, layoutParams, R.drawable.e7);
    }
}
