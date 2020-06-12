package com.example.cluedo_seii.spielbrett;

import android.widget.LinearLayout;

import com.example.cluedo_seii.R;
import com.example.cluedo_seii.activities.GameboardScreen;
import com.example.cluedo_seii.spielbrett.GameboardElement;

public class Geheimgang extends GameboardElement {

    private int geheimgangId;

    public Geheimgang(GameboardScreen gameboardScreen, int xKoordinate, int yKoordinate, LinearLayout.LayoutParams layoutParams) {
        super(gameboardScreen, xKoordinate, yKoordinate, layoutParams, R.drawable.geheimgang);
    }

    public void positionPlayer(boolean isPlayer) {
        if(isPlayer){
            getGameBoardElement().setImageResource(R.drawable.gamefield_element_player);
        } else {
            getGameBoardElement().setImageResource(R.drawable.geheimgang);
        }
    }

    public int getGeheimgangId() {
        return geheimgangId;
    }

    public void setGeheimgangId(int geheimgangId) {
        this.geheimgangId = geheimgangId;
    }
}
