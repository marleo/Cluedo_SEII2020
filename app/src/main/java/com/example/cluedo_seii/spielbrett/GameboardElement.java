package com.example.cluedo_seii.spielbrett;

import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.example.cluedo_seii.activities.GameboardScreen;

public class GameboardElement {
    private int xKoordinate;
    private int yKoordinate;
    private ImageButton gameBoardElement;

    public GameboardElement(GameboardScreen gameboardScreen,
                            int xKoordinate, int yKoordinate,
                            LinearLayout.LayoutParams layoutParams, int drawableImage) {
        this.xKoordinate = xKoordinate;
        this.yKoordinate = yKoordinate;

        gameBoardElement = new ImageButton(gameboardScreen);
        gameBoardElement.setLayoutParams(layoutParams);
        gameBoardElement.setImageResource(drawableImage);
        gameBoardElement.setTag(yKoordinate + 1 + (xKoordinate * 4));
        gameBoardElement.setId(yKoordinate + 1 + (xKoordinate* 4));
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

    public ImageButton getGameBoardElement() {
        return gameBoardElement;
    }

    public void setGameBoardElement(ImageButton gameBoardElement) {
        this.gameBoardElement = gameBoardElement;
    }
}
