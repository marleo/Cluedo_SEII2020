package com.example.cluedo_seii;


import android.graphics.Point;

import java.util.LinkedList;

public class Player {

    private int id;
    private LinkedList<Card> playerCards;
    private Point position;

    public Player(int id, Point position) {
        this.id = id;
        this.position = position;
    }

    public void addCard(Card card){

        playerCards.add(card);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
