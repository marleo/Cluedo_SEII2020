package com.example.cluedo_seii.spielbrett;

import android.graphics.Point;
import android.widget.LinearLayout;

import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.R;
import com.example.cluedo_seii.activities.GameboardScreen;

import java.util.ArrayList;
import java.util.List;

public class Gameboard {

    private List<GameboardElement> listeGameboardElemente;
    private int laenge;
    private int breite;
    private LinearLayout layout;
    private String board;
    private int startingPointTotal;

    public Gameboard(GameboardScreen gameboardScreen, int laenge, int breite, String board) {
        this.laenge = laenge;
        this.breite = breite;
        this.board = board;

        listeGameboardElemente = new ArrayList<>();
        init(gameboardScreen);
    }

    private void init(GameboardScreen gameboardScreen) {
        layout = new LinearLayout(gameboardScreen);
        layout.setOrientation(LinearLayout.VERTICAL);

        char[] boardArray = this.board.toCharArray();
        char[][] arrayPerLine = new char[breite][laenge];
        int lineCounter = 0;
        int charCounter = 0;
        for(int i = 1; i < boardArray.length+1; i++) {
            //Log.i("Test", "Board index: " + (i-1) + " value: " + boardArray[i-1]);
            arrayPerLine[lineCounter][charCounter] = boardArray[i-1];
            charCounter++;
            if(i % laenge == 0) {
                lineCounter++;
                charCounter = 0;
            }
        }

        int startingPointCounter = 0;
        for (int y = 0; y < breite; y++) {
            LinearLayout row = new LinearLayout(gameboardScreen);
            row.setLayoutParams(new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            for (int x = 0; x < laenge; x++) {
                char position = arrayPerLine[y][x];
                GameboardElement gameboardElement = null;
                switch(position) {
                    case '0':
                        gameboardElement = new GameFieldElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        break;
                    case '1':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        break;
                    case '2':
                        gameboardElement = new StartingpointElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT), startingPointCounter++);
                        break;
                    case '3':
                        gameboardElement = new RoomElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        break;
                    case '4':
                        gameboardElement = new RoomElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.nonwalkable_element);
                        break;
                    case '5':
                        gameboardElement = new KitchenBlack(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.kitchen_black);
                        break;
                }

                if (gameboardElement != null) {
                    gameboardElement.getGameBoardElement().setTag(y + 1 + (x * 4));
                    gameboardElement.getGameBoardElement().setId(y + 1 + (x * 4));
                    row.addView(gameboardElement.getGameBoardElement());
                }

                listeGameboardElemente.add(gameboardElement);

                // welche Felder an welcher Position eingespeichert werden sollen
                // Beispiel: if(x == 1 && y == 2) { listeSpielbrettElemente.add(new Raum(x,y,32,32)); }
                // else => Spielfeld
            }
            layout.addView(row);
        }

        this.startingPointTotal = startingPointCounter-1;
    }

    public void spawnPlayer(List<StartingPoint> startingPoints, GameboardScreen gameboardScreen) {
        for(GameboardElement gameboardElement: listeGameboardElemente) {
            if(gameboardElement instanceof StartingpointElement) {
                checkAndSetPlayerOnStartingPoints(startingPoints, gameboardElement);
            }
        }

        // Immer bei jeder Methode die Änderungen am Board vornimmt danach ausführen
        updateGameboardScreen(gameboardScreen);
    }

    private void checkAndSetPlayerOnStartingPoints(List<StartingPoint> startingPoints, GameboardElement gameboardElement) {
        for(StartingPoint startingPoint : startingPoints){
            if(startingPoint.getStartingPointId() == ((StartingpointElement) gameboardElement).getStartingPointId()) {
                ((StartingpointElement) gameboardElement).positionPlayer(true);
                startingPoint.setPlayerPosition(
                        new Point(
                            ((StartingpointElement) gameboardElement).getxKoordinate(),
                            ((StartingpointElement) gameboardElement).getyKoordinate()
                        ));

            }
        }
    }

    public void updateGameboardScreen(GameboardScreen gameboardScreen) {
        for(GameboardElement gameboardElement: listeGameboardElemente) {
            gameboardElement.setGameboardScreen(gameboardScreen);
        }
    }

    private void berechneWeg(/* Spieler möchte diese Koordinaten begehen */) {
        /*
            Beispiel: Spieler würfelt 7
            Spieler geht 3 Schritte => Anzeige mit Information (Spieler ist 3 Schritte gegangen und muss noch 4 weitere gehen)
            => erst wenn Spieler die gewürfelte Zahl gegangen ist kann der nächste Spieler dran sein oder ein Ereignis (Raum) passiert
         */
    }

    public List<GameboardElement> getListeGameboardElemente() {
        return listeGameboardElemente;
    }

    public void setListeGameboardElemente(List<GameboardElement> listeGameboardElemente) {
        this.listeGameboardElemente = listeGameboardElemente;
    }

    public LinearLayout getLayout() {
        return layout;
    }

    public void setLayout(LinearLayout layout) {
        this.layout = layout;
    }
}
