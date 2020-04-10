package com.example.cluedo_seii;

import java.util.LinkedList;


public class Cards {

    private Card oberstVonGatow = new Card(0, "Oberst von Gatov", CardType.person);
    private Card profBloom = new Card(1, "Prof. Bloom", CardType.person);
    private Card reverendGruen = new Card(2, "Reverend Gruen", CardType.person);
    private Card baroninVonPorz = new Card(3, "Baronin von Porz", CardType.person);
    private Card fraeuleinGloria = new Card(4, "Fraeulein Gloria", CardType.person);
    private Card frauWeiss = new Card(5, "Frau Weiss", CardType.person);

    private Card dolch = new Card(6, "Dolch", CardType.weapon);
    private Card leuchter = new Card(7, "Leuchter", CardType.weapon);
    private Card pistole = new Card(8, "Pistole", CardType.weapon);
    private Card seil = new Card(9, "Seil", CardType.weapon);
    private Card heizungsrohr = new Card(10, "Heizungsrohr", CardType.weapon);
    private Card rohrzange = new Card(11, "Rohrzange", CardType.weapon);

    private Card halle = new Card(12, "Halle", CardType.room);
    private Card salon = new Card(13, "Salon", CardType.room);
    private Card speisezimmer = new Card(14, "Speisezimmer", CardType.room);
    private Card kueche = new Card(15, "Kueche", CardType.room);
    private Card musikzimmer = new Card(16, "Musikzimmer", CardType.room);
    private Card winterzimmer = new Card(17, "Winterzimmer", CardType.room);
    private Card biliardzimmer = new Card(18, "Biliardzimmer", CardType.room);
    private Card bibliothek = new Card(19, "Bibliothek", CardType.room);
    private Card arbeitszimmer = new Card(20, "Arbeitszimmer", CardType.room);

    private LinkedList<Card> cards = new LinkedList<Card>();

    public LinkedList<Card> getCards(){

        cards.add(oberstVonGatow); cards.add(profBloom); cards.add(reverendGruen); cards.add(baroninVonPorz); cards.add(fraeuleinGloria); cards.add(frauWeiss);

        cards.add(dolch); cards.add(leuchter); cards.add(pistole); cards.add(seil); cards.add(heizungsrohr); cards.add(rohrzange);

        cards.add(halle); cards.add(salon); cards.add(speisezimmer); cards.add(kueche); cards.add(musikzimmer); cards.add(winterzimmer); cards.add(biliardzimmer); cards.add(bibliothek); cards.add(arbeitszimmer);

        return cards;
    }





}
