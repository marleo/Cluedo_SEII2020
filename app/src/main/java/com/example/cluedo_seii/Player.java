package com.example.cluedo_seii;


import com.example.cluedo_seii.spielbrett.GameboardElement;

import java.util.LinkedList;

public class Player {

    private int id;
    private LinkedList<Card> playerCards;
    private GameboardElement position;
    private String IP;

    public Player(int id, GameboardElement position, String IP){
        this.id = id;
        playerCards = new LinkedList<>();
        this.position = position;
        this.IP = IP;
    }

    public GameboardElement getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

    public LinkedList<Card> getPlayerCards() {
        return playerCards;
    }

    public String getIP() {
        return IP;
    }

    public void addCard(Card card){

        playerCards.add(card);

    }

    public void suspect(){

    }

    public void accuse() {

    }
}
