package com.example.cluedo_seii.spielbrett;

import android.widget.LinearLayout;

import com.example.cluedo_seii.R;
import com.example.cluedo_seii.activities.GameboardScreen;

public class RoomElement extends GameboardElement {
    public RoomElement(GameboardScreen gameboardScreen, int xKoordinate, int yKoordinate, LinearLayout.LayoutParams layoutParams) {
        super(gameboardScreen, xKoordinate, yKoordinate, layoutParams, R.drawable.room_element);
    }

    public void positionPlayer(boolean isPlayer) {
        if(isPlayer){
            getGameBoardElement().setImageResource(R.drawable.room_element_player);
        } else {
            getGameBoardElement().setImageResource(R.drawable.room_element);
        }
    }
}
