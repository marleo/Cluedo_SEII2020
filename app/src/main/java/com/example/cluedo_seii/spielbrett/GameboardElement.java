package com.example.cluedo_seii.spielbrett;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.activities.GameboardScreen;

public abstract class GameboardElement {
    private int xKoordinate;
    private int yKoordinate;
    private ImageButton gameBoardElement;

    private GameboardScreen gameboardScreen;
    private Point oldPosition;
    private GameFieldElement oldGameFieldElement;
    private RoomElement oldRoomElement;
    private StartingpointElement oldStartingpointElement;

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

        // Welcher Spieler macht gerade den Zug
        for(Player player: gameboardScreen.getPlayerMove()) {
            if(player.getId() == gameboardScreen.getPlayerCurrentlyPlayingId()) {
                // Wo befindet sich der Spieler
                for(GameboardElement gameboardElementTemp: gameboardScreen.getGameboard().getListeGameboardElemente()) {
                    if(player.getPosition().x == gameboardElementTemp.getxKoordinate() &&
                            player.getPosition().y == gameboardElementTemp.getyKoordinate()){
                        oldPosition = player.getPosition();
                        if(gameboardElementTemp instanceof GameFieldElement) {
                            oldGameFieldElement = (GameFieldElement) gameboardElementTemp;
                        } else if(gameboardElementTemp instanceof StartingpointElement) {
                            oldStartingpointElement = (StartingpointElement) gameboardElementTemp;
                        } else if(gameboardElementTemp instanceof RoomElement) {
                            oldRoomElement = (RoomElement) gameboardElementTemp;
                        }
                        // Lösche den Spieler von der alten Positon
                        positionPlayerForOldPosition(gameboardElementTemp, player);
                    }
                }

                // Positoniere den Spieler neu und update das Feld
                for(GameboardElement gameboardElementTemp: gameboardScreen.getGameboard().getListeGameboardElemente()) {
                    if (xKoordinate == gameboardElementTemp.getxKoordinate() &&
                            yKoordinate == gameboardElementTemp.getyKoordinate()) {
                        player.setPosition(new Point(xKoordinate, yKoordinate));
                        positionPlayerForNewPosition(gameboardElementTemp, player);
                    }
                }
            }
        }

        oldGameFieldElement = null;
        oldStartingpointElement = null;
        oldRoomElement = null;
        //gameboardScreen.getGameboard().updateGameboardScreen(gameboardScreen);
    }

    public GameboardElement() {
        super();
    }

    private void positionPlayerForNewPosition(GameboardElement newGameboardElement, Player currentPlayer) {
        if(newGameboardElement instanceof GameFieldElement) {
            // Update Spieler Position temporär bis der Spieler seinen Zug beendet hat => Server muss das irgendwie verspeichern
            int diceValueTotal = gameboardScreen.getDiceValueOne() + gameboardScreen.getDiceValueTwo();
            int playerStepsTotal = calculatePlayerStepsTotal(currentPlayer);
            //Vergleich mit dem gewürfeltem
            if(diceValueTotal < playerStepsTotal) {
                // Der Spieler hat zu viele Schritte gemacht => Position wird zurückgesetzt
                currentPlayer.setPosition(oldPosition);
                setPlayerToOldPositon();
                showAlertDialog(playerStepsTotal, diceValueTotal, currentPlayer, true);
            } else if (diceValueTotal > playerStepsTotal) {
                // Der Spieler hat zu wenig Schritte gemacht => Position wir zurückgesetzt
                currentPlayer.setPosition(oldPosition);
                setPlayerToOldPositon();
                showAlertDialog(diceValueTotal, playerStepsTotal, currentPlayer, false);
            } else if (diceValueTotal == playerStepsTotal) {
                // Gegangener Wert stimmt mit gewürfeltem Wert überein => Spieler Bewegung abgeschlossen
                ((GameFieldElement) newGameboardElement).positionPlayer(true);
            }
        } else if(newGameboardElement instanceof RoomElement) {
            // Update Spieler Position temporär bis der Spieler seinen Zug beendet hat => Server muss das irgendwie verspeichern
            int diceValueTotal = gameboardScreen.getDiceValueOne() + gameboardScreen.getDiceValueTwo();
            int playerStepsTotal = calculatePlayerStepsTotal(currentPlayer);
            //Vergleich mit dem gewürfeltem
            if(diceValueTotal < playerStepsTotal) {
                // Der Spieler hat zu viele Schritte gemacht => Position wird zurückgesetzt
                currentPlayer.setPosition(oldPosition);
                setPlayerToOldPositon();
                showAlertDialog(playerStepsTotal, diceValueTotal, currentPlayer, true);
            } else if (diceValueTotal > playerStepsTotal) {
                // Der Spieler hat zu wenig Schritte gemacht => Position wir zurückgesetzt
                currentPlayer.setPosition(oldPosition);
                setPlayerToOldPositon();
                showAlertDialog(diceValueTotal, playerStepsTotal, currentPlayer, false);
            } else if (diceValueTotal == playerStepsTotal) {
                // Gegangener Wert stimmt mit gewürfeltem Wert überein => Spieler Bewegung abgeschlossen
                ((RoomElement) newGameboardElement).positionPlayer(true);
                gameboardScreen.setCurrentPlayerInDoor(currentPlayer);
                // TODO: Lock alle anderen Spieler + Öffne Activity
                //if(((RoomElement) gameboardElementTemp).getRoomElementId() == 0) {
                // ODER
                //if(gameboardElementTemp.getxKoordinate() == 0 && gameboardElementTemp.getyKoordinate() == 0) {
                //Intent intent = new Intent(gameboardScreen, KitchenScreen.class);
                //gameboardScreen.startActivity(intent);
                //}
            }
        } else if(newGameboardElement instanceof StartingpointElement) {
            // Update Spieler Position temporär bis der Spieler seinen Zug beendet hat => Server muss das irgendwie verspeichern
            int diceValueTotal = gameboardScreen.getDiceValueOne() + gameboardScreen.getDiceValueTwo();
            int playerStepsTotal = calculatePlayerStepsTotal(currentPlayer);
            //Vergleich mit dem gewürfeltem
            if(diceValueTotal < playerStepsTotal) {
                // Der Spieler hat zu viele Schritte gemacht => Position wird zurückgesetzt
                currentPlayer.setPosition(oldPosition);
                setPlayerToOldPositon();
                showAlertDialog(playerStepsTotal, diceValueTotal, currentPlayer, true);
            } else if (diceValueTotal > playerStepsTotal) {
                // Der Spieler hat zu wenig Schritte gemacht => Position wir zurückgesetzt
                currentPlayer.setPosition(oldPosition);
                setPlayerToOldPositon();
                showAlertDialog(diceValueTotal, playerStepsTotal, currentPlayer, false);
            } else if (diceValueTotal == playerStepsTotal) {
                // Gegangener Wert stimmt mit gewürfeltem Wert überein => Spieler Bewegung abgeschlossen
                ((StartingpointElement) newGameboardElement).positionPlayer(true);
            }
        }
    }

    private void setPlayerToOldPositon(){
        if(oldGameFieldElement != null) {
            ((GameFieldElement) oldGameFieldElement).positionPlayer(true);
        } else if(oldStartingpointElement != null) {
            ((StartingpointElement) oldStartingpointElement).positionPlayer(true);
        } else if (oldRoomElement != null) {
            ((RoomElement) oldRoomElement).positionPlayer(true);
        }
    }

    private void positionPlayerForOldPosition(GameboardElement gameboardElementTemp, Player currentPlayer) {
        if(gameboardElementTemp instanceof GameFieldElement) {
            ((GameFieldElement) gameboardElementTemp).positionPlayer(false);
        } else if(gameboardElementTemp instanceof RoomElement) {
            ((RoomElement) gameboardElementTemp).positionPlayer(false);
        } else if(gameboardElementTemp instanceof StartingpointElement) {
            ((StartingpointElement) gameboardElementTemp).positionPlayer(false);
        }
    }

    private int calculatePlayerStepsTotal(Player currentPlayer) {
        Point oldPositionCalc = oldPosition;
        Point newPositionCalc = currentPlayer.getPosition();

        int diffX, diffY;

        if(oldPositionCalc.x < newPositionCalc.x) {
            diffX = newPositionCalc.x - oldPositionCalc.x;
        } else {
            diffX = oldPositionCalc.x - newPositionCalc.x;
        }

        if(oldPositionCalc.y < newPositionCalc.y) {
            diffY = newPositionCalc.y - oldPositionCalc.y;
        } else {
            diffY = oldPositionCalc.y - newPositionCalc.y;
        }

        return diffX + diffY;
    }

    private void showAlertDialog(int greaterValue, int smallerValue, Player currentPlayer, boolean toMuchSteps){
        int diff = greaterValue - smallerValue;

        String messagePart = "";

        if(toMuchSteps) {
            messagePart = " Schritte zu viel gemacht";
        } else {
            messagePart = " Schritte zu wenig gemacht";
        }

        new AlertDialog.Builder(gameboardScreen)
                .setTitle("Error")
                .setMessage("Spieler hat " + diff + messagePart)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Hier passiert nichts da die Position sowieso zurück gesetzt werden muss
                    }
                })
                .show();
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
