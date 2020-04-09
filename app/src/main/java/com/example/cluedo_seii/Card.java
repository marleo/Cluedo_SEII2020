package com.example.cluedo_seii;

public class Card {

    private int id;
    private String bezeichnung;
    private CardType typ;

    public Card(int id, String bezeichnung, CardType typ) {


        this.id = id;
        this.bezeichnung = bezeichnung;
        this.typ = typ;
    }

    public int getId() {
        return id;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public CardType getTyp() {
        return typ;
    }

}
