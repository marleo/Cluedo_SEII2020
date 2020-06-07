package com.example.cluedo_seii.spielbrett;

import android.content.Intent;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.activities.GameboardScreen;
import com.example.cluedo_seii.activities.SettingScreen;

public abstract class GameboardElement {
    private int xKoordinate;
    private int yKoordinate;
    private ImageButton gameBoardElement;

    private GameboardScreen gameboardScreen;

    public GameboardElement(GameboardScreen gameboardScreen,
                            int xKoordinate, int yKoordinate,
                            LinearLayout.LayoutParams layoutParams, int drawableImage) {
        this.xKoordinate = xKoordinate;
        this.yKoordinate = yKoordinate;

        gameBoardElement = new ImageButton(gameboardScreen);
        gameBoardElement.setLayoutParams(layoutParams);
        gameBoardElement.setImageResource(drawableImage);
        gameBoardElement.setBackgroundResource(0);
        gameBoardElement.setPadding(0,0,0,0);
        gameBoardElement.setTag(yKoordinate + 1 + (xKoordinate * 4));
        gameBoardElement.setId(yKoordinate + 1 + (xKoordinate* 4));
        gameBoardElement.setClickable(true);

        gameBoardElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Test", "Testing");
                movePlayer();
            }
        });
    }

    public void movePlayer() {
        // Für später beim kalkulieren wie viele Schritte erlaubt sind
        Point oldPosition;
        // Welcher Spieler macht gerade den Zug
        for(Player player: gameboardScreen.getPlayerMove()) {
            if(player.getId() == gameboardScreen.getPlayerCurrentlyPlayingId()) {
                // Wo befindet sich der Spieler
                for(GameboardElement gameboardElementTemp: gameboardScreen.getGameboard().getListeGameboardElemente()) {
                    if(player.getPosition().x == gameboardElementTemp.getxKoordinate() &&
                            player.getPosition().y == gameboardElementTemp.getyKoordinate()){
                        oldPosition = player.getPosition();
                        // Lösche den Spieler von der alten Positon
                        positionPlayer(gameboardElementTemp, false, player);
                    }
                }

                // Positoniere den Spieler neu und update das Feld
                for(GameboardElement gameboardElementTemp: gameboardScreen.getGameboard().getListeGameboardElemente()) {
                    if (xKoordinate == gameboardElementTemp.getxKoordinate() &&
                            yKoordinate == gameboardElementTemp.getyKoordinate()) {
                        player.setPosition(new Point(xKoordinate, yKoordinate));
                        positionPlayer(gameboardElementTemp, true, player);
                    }
                }
            }
        }

        //gameboardScreen.getGameboard().updateGameboardScreen(gameboardScreen);
    }

    private void positionPlayer(GameboardElement gameboardElementTemp, boolean isPlayer, Player currentplayer) {
        if(gameboardElementTemp instanceof GameFieldElement) {
            ((GameFieldElement) gameboardElementTemp).positionPlayer(isPlayer);
            if(isPlayer){
                // Update Spieler Position temporär bis der Spieler seinen Zug beendet hat => Server muss das irgendwie verspeichern
            }
        } else if(gameboardElementTemp instanceof RoomElement) {
            ((RoomElement) gameboardElementTemp).positionPlayer(isPlayer);
            if(isPlayer) {
                // Update Spieler Position temporär bis der Spieler seinen Zug beendet hat => Server muss das irgendwie verspeichern
                gameboardScreen.setCurrentPlayerInDoor(currentplayer);
                // TODO: Lock alle anderen Spieler + Öffne Activity
                //if(((RoomElement) gameboardElementTemp).getRoomElementId() == 0) {
                // ODER
                //if(gameboardElementTemp.getxKoordinate() == 0 && gameboardElementTemp.getyKoordinate() == 0) {
                    //Intent intent = new Intent(gameboardScreen, KitchenScreen.class);
                    //gameboardScreen.startActivity(intent);
                //}
            }
        } else if(gameboardElementTemp instanceof StartingpointElement) {
            ((StartingpointElement) gameboardElementTemp).positionPlayer(isPlayer);
            if(isPlayer){
                // Update Spieler Position temporär bis der Spieler seinen Zug beendet hat => Server muss das irgendwie verspeichern
            }
        }
    }

    public GameboardScreen getGameboardScreen() {
        return gameboardScreen;
    }

    public void setGameboardScreen(GameboardScreen gameboardScreen) {
        this.gameboardScreen = gameboardScreen;
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
