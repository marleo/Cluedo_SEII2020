package com.example.cluedo_seii.spielbrett;

import android.widget.LinearLayout;

import com.example.cluedo_seii.R;
import com.example.cluedo_seii.activities.SpielbrettScreen;

public class Startpunkt extends SpielbrettElement {
    public Startpunkt(SpielbrettScreen spielbrettScreen, int xKoordinate, int yKoordinate, LinearLayout.LayoutParams layoutParams) {
        super(spielbrettScreen, xKoordinate, yKoordinate, layoutParams, R.drawable.startpunkt_element);
    }
}
