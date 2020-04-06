package com.example.cluedo_seii;

public class Kartenstapel {

    private Karte oberstVonGatow = new Karte(1, "Oberst von Gatov", Kartentyp.Person);
    private Karte profBloom = new Karte(2, "Prof. Bloom", Kartentyp.Person);
    private Karte reverendGrün = new Karte(3, "Reverend Grün", Kartentyp.Person);
    private Karte baroninVonPorz = new Karte(4, "Baronin von Porz", Kartentyp.Person);
    private Karte fräuleinGloria = new Karte(5, "Fräulein Gloria", Kartentyp.Person);
    private Karte frauWeiss = new Karte(6, "Frau Weiss", Kartentyp.Person);

    private Karte dolch = new Karte(7, "Dolch", Kartentyp.Waffe);
    private Karte leuchter = new Karte(8, "Leuchter", Kartentyp.Waffe);
    private Karte pistole = new Karte(9, "Pistole", Kartentyp.Waffe);
    private Karte seil = new Karte(10, "Seil", Kartentyp.Waffe);
    private Karte heizungsrohr = new Karte(11, "Heizungsrohr", Kartentyp.Waffe);
    private Karte rohrzange = new Karte(12, "Rohrzange", Kartentyp.Waffe);

    private Karte halle = new Karte(13, "Halle", Kartentyp.Raum);
    private Karte salon = new Karte(14, "Salon", Kartentyp.Raum);
    private Karte speisezimmer = new Karte(15, "Speisezimmer", Kartentyp.Raum);
    private Karte küche = new Karte(16, "Küche", Kartentyp.Raum);
    private Karte musikzimmer = new Karte(17, "Musikzimmer", Kartentyp.Raum);
    private Karte winterzimmer = new Karte(18, "Winterzimmer", Kartentyp.Raum);
    private Karte biliardzimmer = new Karte(19, "Biliardzimmer", Kartentyp.Raum);
    private Karte bibliothek = new Karte(20, "Bibliothek", Kartentyp.Raum);
    private Karte arbeitszimmer = new Karte(21, "Arbeitszimmer", Kartentyp.Raum);

    private Karte[]kartenStapelStandard = {
            oberstVonGatow, profBloom, reverendGrün, baroninVonPorz, fräuleinGloria, frauWeiss,

            dolch, leuchter, pistole, seil, heizungsrohr, rohrzange,

            halle, salon, speisezimmer, küche, musikzimmer, winterzimmer, biliardzimmer, bibliothek, arbeitszimmer};

    public Karte[] getKartenStapelStandard() {
        return kartenStapelStandard;
    }

}
