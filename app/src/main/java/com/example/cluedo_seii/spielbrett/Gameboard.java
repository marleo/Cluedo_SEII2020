package com.example.cluedo_seii.spielbrett;

import android.widget.LinearLayout;

import com.example.cluedo_seii.activities.GameboardScreen;

import java.util.ArrayList;
import java.util.List;

public class Gameboard {

    private List<GameboardElement> listeGameboardElemente;
    private int laenge;
    private int breite;
    private LinearLayout layout;

    public Gameboard(GameboardScreen gameboardScreen, int laenge, int breite) {
        this.laenge = laenge;
        this.breite = breite;

        listeGameboardElemente = new ArrayList<>();
        init(gameboardScreen);
    }

    private void init(GameboardScreen gameboardScreen) {
        layout = new LinearLayout(gameboardScreen);
        layout.setOrientation(LinearLayout.VERTICAL);
        for (int x = 0; x < breite; x++) {
            LinearLayout row = new LinearLayout(gameboardScreen);
            row.setLayoutParams(new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            for (int y = 0; y < laenge; y++) {
                if(x == 1 && y == 1) {
                    GameboardElement gameboardElement = new Startingpoint(gameboardScreen, x, y, new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT));
                    gameboardElement.getGameBoardElement().setTag(y + 1 + (x * 4));
                    gameboardElement.getGameBoardElement().setId(y + 1 + (x * 4));
                    row.addView(gameboardElement.getGameBoardElement());
                    listeGameboardElemente.add(gameboardElement);
                } else {
                    GameboardElement gameboardElement = new GameField(gameboardScreen, x, y, new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.MATCH_PARENT));
                    gameboardElement.getGameBoardElement().setTag(y + 1 + (x * 4));
                    gameboardElement.getGameBoardElement().setId(y + 1 + (x * 4));
                    row.addView(gameboardElement.getGameBoardElement());
                    listeGameboardElemente.add(gameboardElement);
                }

                // welche Felder an welcher Position eingespeichert werden sollen
                // Beispiel: if(x == 1 && y == 2) { listeSpielbrettElemente.add(new Raum(x,y,32,32)); }
                // else => Spielfeld
            }
            layout.addView(row);
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
