package com.example.cluedo_seii.spielbrett;

import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.cluedo_seii.R;
import com.example.cluedo_seii.activities.SpielbrettScreen;

import java.util.ArrayList;
import java.util.List;

public class Spielbrett {

    private List<SpielbrettElement> listeSpielbrettElemente;
    private int laenge;
    private int breite;
    private LinearLayout layout;

    public Spielbrett(SpielbrettScreen spielbrettScreen, int laenge, int breite) {
        this.laenge = laenge;
        this.breite = breite;

        listeSpielbrettElemente = new ArrayList<>();
        init(spielbrettScreen);
    }

    private void init(SpielbrettScreen spielbrettScreen) {
        layout = new LinearLayout(spielbrettScreen);
        layout.setOrientation(LinearLayout.VERTICAL);
        for (int x = 0; x < breite; x++) {
            LinearLayout row = new LinearLayout(spielbrettScreen);
            row.setLayoutParams(new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            for (int y = 0; y < laenge; y++) {
                if(x == 1 && y == 1) {
                    SpielbrettElement spielbrettElement = new Startpunkt(spielbrettScreen, x, y, new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT));
                    spielbrettElement.getSpielBrettElement().setTag(y + 1 + (x * 4));
                    spielbrettElement.getSpielBrettElement().setId(y + 1 + (x * 4));
                    row.addView(spielbrettElement.getSpielBrettElement());
                    listeSpielbrettElemente.add(spielbrettElement);
                } else {
                    SpielbrettElement spielbrettElement = new Spielfeld(spielbrettScreen, x, y, new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.MATCH_PARENT));
                    spielbrettElement.getSpielBrettElement().setTag(y + 1 + (x * 4));
                    spielbrettElement.getSpielBrettElement().setId(y + 1 + (x * 4));
                    row.addView(spielbrettElement.getSpielBrettElement());
                    listeSpielbrettElemente.add(spielbrettElement);
                }

                // TODO: Definiere welche Felder an welcher Position eingespeichert werden sollen
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

    public List<SpielbrettElement> getListeSpielbrettElemente() {
        return listeSpielbrettElemente;
    }

    public void setListeSpielbrettElemente(List<SpielbrettElement> listeSpielbrettElemente) {
        this.listeSpielbrettElemente = listeSpielbrettElemente;
    }

    public LinearLayout getLayout() {
        return layout;
    }

    public void setLayout(LinearLayout layout) {
        this.layout = layout;
    }
}
