package com.example.cluedo_seii.spielbrett;

public class SpielbrettElement {
    private int xKoordinate;
    private int yKoordinate;
    private int breite;
    private int laenge;

    public SpielbrettElement(int xKoordinate, int yKoordinate, int breite, int laenge) {
        this.xKoordinate = xKoordinate;
        this.yKoordinate = yKoordinate;
        this.breite = breite;
        this.laenge = laenge;
    }

    public int getxKoordinate() {
        return xKoordinate;
    }

    public void setxKoordinate(int xKoordinate) {
        this.xKoordinate = xKoordinate;
    }

    public int getyKoordinate() {
        return yKoordinate;
    }

    public void setyKoordinate(int yKoordinate) {
        this.yKoordinate = yKoordinate;
    }

    public int getBreite() {
        return breite;
    }

    public void setBreite(int breite) {
        this.breite = breite;
    }

    public int getLänge() {
        return laenge;
    }

    public void setLänge(int laenge) {
        this.laenge = laenge;
    }
}
