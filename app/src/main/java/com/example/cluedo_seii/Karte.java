package com.example.cluedo_seii;

public class Karte {

    private int id;
    private String bezeichnung;
    private Kartentyp typ;

    public Karte(int id, String bezeichnung, Kartentyp typ) {


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

    public Kartentyp getTyp() {
        return typ;
    }

}
