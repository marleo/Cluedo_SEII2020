package com.example.cluedo_seii.spielbrett.WorkingRoom;

import android.widget.LinearLayout;

import com.example.cluedo_seii.R;
import com.example.cluedo_seii.activities.GameboardScreen;
import com.example.cluedo_seii.spielbrett.GameboardElement;

public class WorkingRoom2 extends GameboardElement {

    public WorkingRoom2(GameboardScreen gameboardScreen, int xKoordinate, int yKoordinate, LinearLayout.LayoutParams layoutParams) {
        super(gameboardScreen, xKoordinate, yKoordinate, layoutParams, R.drawable.a2);
    }
}