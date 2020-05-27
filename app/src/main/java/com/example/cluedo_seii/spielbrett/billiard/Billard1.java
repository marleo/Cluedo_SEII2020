package com.example.cluedo_seii.spielbrett.billiard;

import android.widget.LinearLayout;

import com.example.cluedo_seii.R;
import com.example.cluedo_seii.activities.GameboardScreen;
import com.example.cluedo_seii.spielbrett.GameboardElement;

public class Billard1 extends GameboardElement {

    public Billard1(GameboardScreen gameboardScreen, int xKoordinate, int yKoordinate, LinearLayout.LayoutParams layoutParams) {
        super(gameboardScreen, xKoordinate, yKoordinate, layoutParams, R.drawable.b1);
    }
}
