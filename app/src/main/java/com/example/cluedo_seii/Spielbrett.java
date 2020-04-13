package com.example.cluedo_seii;

import java.util.ArrayList;
import java.util.List;

public class Spielbrett {

    private List<SpielbrettElement> listeSpielbrettElemente;
    private int laenge;
    private int breite;

    public Spielbrett(int laenge, int breite) {
        this.laenge = laenge;
        this.breite = breite;

        listeSpielbrettElemente = new ArrayList<>();

        for(int x = 0; x < laenge; x++) {
            for(int y = 0; y < breite; y++) {

                // TODO: Definiere welche Felder an welcher Position eingespeichert werden sollen
                // Beispiel: if(x == 1 && y == 2) { listeSpielbrettElemente.add(new Raum(x,y,32,32)); }
                // else => Spielfeld

                listeSpielbrettElemente.add(new Spielfeld(x,y,32,32));
            }
        }
    }

    private void berechneWeg(/* Spieler möchte diese Koordinaten begehen */) {
        /*
            Beispiel: Spieler würfelt 7
            Spieler geht 3 Schritte => Anzeige mit Information (Spieler ist 3 Schritte gegangen und muss noch 4 weitere gehen)
            => erst wenn Spieler die gewürfelte Zahl gegangen ist kann der nächste Spieler dran sein oder ein Ereignis (Raum) passiert
         */
    }
}
