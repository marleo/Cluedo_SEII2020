package com.example.cluedo_seii.spielbrett;

import android.widget.LinearLayout;

import com.example.cluedo_seii.R;
import com.example.cluedo_seii.activities.GameboardScreen;

public class StartingpointElement extends GameboardElement {
    private int startingPointId;

    public StartingpointElement(GameboardScreen gameboardScreen, int xKoordinate, int yKoordinate, LinearLayout.LayoutParams layoutParams, int startingPointId) {
        super(gameboardScreen, xKoordinate, yKoordinate, layoutParams, R.drawable.startingpoint_element);
        this.startingPointId = startingPointId;
    }

    public void positionPlayer(boolean isPlayer) {
        if(isPlayer){
            getGameBoardElement().setImageResource(R.drawable.startingpoint_element_player);
        } else {
            getGameBoardElement().setImageResource(R.drawable.startingpoint_element);
        }
    }

    public int getStartingPointId() {
        return startingPointId;
    }
}
