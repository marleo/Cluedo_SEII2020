package com.example.cluedo_seii;

import java.util.LinkedList;


public class DeckOfCards  {


    public Card oberstVonGatow = new Card(0, "Oberst von Gatov", CardType.PERSON);
    public Card profBloom = new Card(1, "Prof. Bloom", CardType.PERSON);
    public Card reverendGruen = new Card(2, "Reverend Gruen", CardType.PERSON);
    public Card baroninVonPorz = new Card(3, "Baronin von Porz", CardType.PERSON);
    public Card fraeuleinGloria = new Card(4, "Fraeulein Gloria", CardType.PERSON);
    public Card frauWeiss = new Card(5, "Frau Weiss", CardType.PERSON);

    public Card dolch = new Card(6, "Dolch", CardType.WEAPON);
    public Card leuchter = new Card(7, "Leuchter", CardType.WEAPON);
    public Card pistole = new Card(8, "Pistole", CardType.WEAPON);
    public Card seil = new Card(9, "Seil", CardType.WEAPON);
    public  Card heizungsrohr = new Card(10, "Heizungsrohr", CardType.WEAPON);
    public Card rohrzange = new Card(11, "Rohrzange", CardType.WEAPON);

    public Card eingangshalle = new Card(12, "Eingangshalle", CardType.ROOM);
    public Card salon = new Card(13, "Salon", CardType.ROOM);
    public Card speisezimmer = new Card(14, "Speisezimmer", CardType.ROOM);
    public Card kueche = new Card(15, "Kueche", CardType.ROOM);
    public Card musikzimmer = new Card(16, "Musikzimmer", CardType.ROOM);
    public Card veranda = new Card(17, "Veranda", CardType.ROOM);
    public Card billardzimmer = new Card(18, "Billardzimmer", CardType.ROOM);
    public Card bibliothek = new Card(19, "Bibliothek", CardType.ROOM);
    public Card arbeitszimmer = new Card(20, "Arbeitszimmer", CardType.ROOM);

    private LinkedList<Card> gameCardsStandard = new LinkedList<Card>();

    public LinkedList<Card> getGameCardsStandard(){

        gameCardsStandard.add(oberstVonGatow); gameCardsStandard.add(profBloom); gameCardsStandard.add(reverendGruen); gameCardsStandard.add(baroninVonPorz); gameCardsStandard.add(fraeuleinGloria); gameCardsStandard.add(frauWeiss);

        gameCardsStandard.add(dolch); gameCardsStandard.add(leuchter); gameCardsStandard.add(pistole); gameCardsStandard.add(seil); gameCardsStandard.add(heizungsrohr); gameCardsStandard.add(rohrzange);

        gameCardsStandard.add(eingangshalle); gameCardsStandard.add(salon); gameCardsStandard.add(speisezimmer); gameCardsStandard.add(kueche); gameCardsStandard.add(musikzimmer); gameCardsStandard.add(veranda); gameCardsStandard.add(billardzimmer); gameCardsStandard.add(bibliothek); gameCardsStandard.add(arbeitszimmer);

        return gameCardsStandard;
    }





}
