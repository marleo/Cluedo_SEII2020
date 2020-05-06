package com.example.cluedo_seii;


import java.io.Serializable;

public class Card implements Serializable {

    private int id;
    private String designation;
    private CardType type;

    public Card(int id, String designation, CardType type) {
        this.id = id;
        this.designation = designation;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getDesignation() {
        return designation;
    }

    public CardType getType() {
        return type;
    }



}
