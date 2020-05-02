package com.example.cluedo_seii;


import com.example.cluedo_seii.spielbrett.GameboardElement;

import java.util.LinkedList;

public class Player {

    private int id;
    private LinkedList<Card> playerCards;
    private GameboardElement position;
    private String IP;
    private GameCharacter playerCharacter;
    private Notepad notepad;
    private Boolean madeFalseAccusation;

    public Player(int id, GameboardElement position, String IP, GameCharacter playerCharacter, Notepad notepad){
        this.id = id;
        playerCards = new LinkedList<>();
        this.position = position;
        this.IP = IP;
        this.playerCharacter = playerCharacter;
        this.notepad = notepad;
        madeFalseAccusation = false;
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

    public GameCharacter getPlayerCharacter() {
        return playerCharacter;
    }

    public void setMadeFalseAccusation(Boolean madeFalseAccusation) {
        this.madeFalseAccusation = madeFalseAccusation;
    }

    public void addCard(Card card){
        playerCards.add(card);
    }
}
