package com.example.cluedo_seii.spielbrett;

import android.widget.LinearLayout;

import com.example.cluedo_seii.R;
import com.example.cluedo_seii.activities.GameboardScreen;

public class DoorElement extends GameboardElement {

    private int doorElementId;

    public DoorElement(GameboardScreen gameboardScreen, int xKoordinate, int yKoordinate, LinearLayout.LayoutParams layoutParams, int roomElementId) {
        super(gameboardScreen, xKoordinate, yKoordinate, layoutParams, R.drawable.room_element);
        this.doorElementId = roomElementId;
    }

    public void positionPlayer(boolean isPlayer) {
        if(isPlayer){
            getGameBoardElement().setImageResource(R.drawable.room_element_player);
        } else {
            getGameBoardElement().setImageResource(R.drawable.room_element);
        }
    }

    public int getDoorElementId() {
        return doorElementId;
    }

    public void setDoorElementId(int doorElementId) {
        this.doorElementId = doorElementId;
    }
}
