package com.example.cluedo_seii;

public class Cards {

    private Card oberstVonGatow = new Card(1, "Oberst von Gatov", CardType.person);
    private Card profBloom = new Card(2, "Prof. Bloom", CardType.person);
    private Card reverendGruen = new Card(3, "Reverend Gruen", CardType.person);
    private Card baroninVonPorz = new Card(4, "Baronin von Porz", CardType.person);
    private Card fraeuleinGloria = new Card(5, "Fraeulein Gloria", CardType.person);
    private Card frauWeiss = new Card(6, "Frau Weiss", CardType.person);

    private Card dolch = new Card(7, "Dolch", CardType.weapon);
    private Card leuchter = new Card(8, "Leuchter", CardType.weapon);
    private Card pistole = new Card(9, "Pistole", CardType.weapon);
    private Card seil = new Card(10, "Seil", CardType.weapon);
    private Card heizungsrohr = new Card(11, "Heizungsrohr", CardType.weapon);
    private Card rohrzange = new Card(12, "Rohrzange", CardType.weapon);

    private Card halle = new Card(13, "Halle", CardType.room);
    private Card salon = new Card(14, "Salon", CardType.room);
    private Card speisezimmer = new Card(15, "Speisezimmer", CardType.room);
    private Card kueche = new Card(16, "Kueche", CardType.room);
    private Card musikzimmer = new Card(17, "Musikzimmer", CardType.room);
    private Card winterzimmer = new Card(18, "Winterzimmer", CardType.room);
    private Card biliardzimmer = new Card(19, "Biliardzimmer", CardType.room);
    private Card bibliothek = new Card(20, "Bibliothek", CardType.room);
    private Card arbeitszimmer = new Card(21, "Arbeitszimmer", CardType.room);

    private Card[] cardsStandard = {

            oberstVonGatow, profBloom, reverendGruen, baroninVonPorz, fraeuleinGloria, frauWeiss,

            dolch, leuchter, pistole, seil, heizungsrohr, rohrzange,

            halle, salon, speisezimmer, kueche, musikzimmer, winterzimmer, biliardzimmer, bibliothek, arbeitszimmer};

    public Card[] getCardsStandard() {
        return cardsStandard;
    }

}
