package com.example.cluedo_seii;

public class Cards {

    private Card oberstVonGatow = new Card(1, "Oberst von Gatov", CardType.Person);
    private Card profBloom = new Card(2, "Prof. Bloom", CardType.Person);
    private Card reverendGruen = new Card(3, "Reverend Gruen", CardType.Person);
    private Card baroninVonPorz = new Card(4, "Baronin von Porz", CardType.Person);
    private Card fraeuleinGloria = new Card(5, "Fraeulein Gloria", CardType.Person);
    private Card frauWeiss = new Card(6, "Frau Weiss", CardType.Person);

    private Card dolch = new Card(7, "Dolch", CardType.Waffe);
    private Card leuchter = new Card(8, "Leuchter", CardType.Waffe);
    private Card pistole = new Card(9, "Pistole", CardType.Waffe);
    private Card seil = new Card(10, "Seil", CardType.Waffe);
    private Card heizungsrohr = new Card(11, "Heizungsrohr", CardType.Waffe);
    private Card rohrzange = new Card(12, "Rohrzange", CardType.Waffe);

    private Card halle = new Card(13, "Halle", CardType.Raum);
    private Card salon = new Card(14, "Salon", CardType.Raum);
    private Card speisezimmer = new Card(15, "Speisezimmer", CardType.Raum);
    private Card kueche = new Card(16, "Küche", CardType.Raum);
    private Card musikzimmer = new Card(17, "Musikzimmer", CardType.Raum);
    private Card winterzimmer = new Card(18, "Winterzimmer", CardType.Raum);
    private Card biliardzimmer = new Card(19, "Biliardzimmer", CardType.Raum);
    private Card bibliothek = new Card(20, "Bibliothek", CardType.Raum);
    private Card arbeitszimmer = new Card(21, "Arbeitszimmer", CardType.Raum);

    private Card[]kartenStapelStandard = {

            oberstVonGatow, profBloom, reverendGruen, baroninVonPorz, fraeuleinGloria, frauWeiss,

            dolch, leuchter, pistole, seil, heizungsrohr, rohrzange,

            halle, salon, speisezimmer, kueche, musikzimmer, winterzimmer, biliardzimmer, bibliothek, arbeitszimmer};

    public Card[] getKartenStapelStandard() {
        return kartenStapelStandard;
    }

}
