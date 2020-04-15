package com.example.cluedo_seii.spielbrett;

import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.example.cluedo_seii.activities.SpielbrettScreen;

public class SpielbrettElement {
    private int xKoordinate;
    private int yKoordinate;
    private ImageButton spielBrettElement;

    public SpielbrettElement(SpielbrettScreen spielbrettScreen,
                             int xKoordinate, int yKoordinate,
                             LinearLayout.LayoutParams layoutParams, int drawableImage) {
        this.xKoordinate = xKoordinate;
        this.yKoordinate = yKoordinate;

        spielBrettElement = new ImageButton(spielbrettScreen);
        spielBrettElement.setLayoutParams(layoutParams);
        spielBrettElement.setImageResource(drawableImage);
        spielBrettElement.setTag(yKoordinate + 1 + (xKoordinate * 4));
        spielBrettElement.setId(yKoordinate + 1 + (xKoordinate* 4));
    }

    public int getxKoordinate() {
        return xKoordinate;
    }

    public void setxKoordinate(int xKoordinate) {
        this.xKoordinate = xKoordinate;
    }

    public int getyKoordinate() {
        return yKoordinate;
    }

    public void setyKoordinate(int yKoordinate) {
        this.yKoordinate = yKoordinate;
    }

    public ImageButton getSpielBrettElement() {
        return spielBrettElement;
    }

    public void setSpielBrettElement(ImageButton spielBrettElement) {
        this.spielBrettElement = spielBrettElement;
    }
}
