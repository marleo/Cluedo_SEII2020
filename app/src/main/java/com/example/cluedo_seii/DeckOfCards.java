package com.example.cluedo_seii;

import java.io.Serializable;
import java.util.LinkedList;


public class DeckOfCards  {

    private Card oberstVonGatow = new Card(0, "Oberst von Gatov", CardType.PERSON);
    private Card profBloom = new Card(1, "Prof. Bloom", CardType.PERSON);
    private Card reverendGruen = new Card(2, "Reverend Gruen", CardType.PERSON);
    private Card baroninVonPorz = new Card(3, "Baronin von Porz", CardType.PERSON);
    private Card fraeuleinGloria = new Card(4, "Fraeulein Gloria", CardType.PERSON);
    private Card frauWeiss = new Card(5, "Frau Weiss", CardType.PERSON);

    private Card dolch = new Card(6, "Dolch", CardType.WEAPON);
    private Card leuchter = new Card(7, "Leuchter", CardType.WEAPON);
    private Card pistole = new Card(8, "Pistole", CardType.WEAPON);
    private Card seil = new Card(9, "Seil", CardType.WEAPON);
    private Card heizungsrohr = new Card(10, "Heizungsrohr", CardType.WEAPON);
    private Card rohrzange = new Card(11, "Rohrzange", CardType.WEAPON);

    private Card halle = new Card(12, "Halle", CardType.ROOM);
    private Card salon = new Card(13, "Salon", CardType.ROOM);
    private Card speisezimmer = new Card(14, "Speisezimmer", CardType.ROOM);
    private Card kueche = new Card(15, "Kueche", CardType.ROOM);
    private Card musikzimmer = new Card(16, "Musikzimmer", CardType.ROOM);
    private Card winterzimmer = new Card(17, "Winterzimmer", CardType.ROOM);
    private Card biliardzimmer = new Card(18, "Biliardzimmer", CardType.ROOM);
    private Card bibliothek = new Card(19, "Bibliothek", CardType.ROOM);
    private Card arbeitszimmer = new Card(20, "Arbeitszimmer", CardType.ROOM);

    private LinkedList<Card> gameCardsStandard = new LinkedList<Card>();

    public LinkedList<Card> getGameCardsStandard(){

        gameCardsStandard.add(oberstVonGatow); gameCardsStandard.add(profBloom); gameCardsStandard.add(reverendGruen); gameCardsStandard.add(baroninVonPorz); gameCardsStandard.add(fraeuleinGloria); gameCardsStandard.add(frauWeiss);

        gameCardsStandard.add(dolch); gameCardsStandard.add(leuchter); gameCardsStandard.add(pistole); gameCardsStandard.add(seil); gameCardsStandard.add(heizungsrohr); gameCardsStandard.add(rohrzange);

        gameCardsStandard.add(halle); gameCardsStandard.add(salon); gameCardsStandard.add(speisezimmer); gameCardsStandard.add(kueche); gameCardsStandard.add(musikzimmer); gameCardsStandard.add(winterzimmer); gameCardsStandard.add(biliardzimmer); gameCardsStandard.add(bibliothek); gameCardsStandard.add(arbeitszimmer);

        return gameCardsStandard;
    }





}
