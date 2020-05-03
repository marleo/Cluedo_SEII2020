package com.example.cluedo_seii;


import com.example.cluedo_seii.spielbrett.GameboardElement;

import java.io.Serializable;
import java.util.LinkedList;

public class Player implements  Serializable  {

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


    public LinkedList<Card> suspect(String suspectedCulprit, String suspectedWeapon, String suspectedLocation, LinkedList<Player> players){

        LinkedList<Card>wrongSuspicions = new LinkedList<>();

        for(Player player: players){

            if(suspectedCulprit == player.getPlayerCharacter().getName()){

                for(Card card: player.getPlayerCards())
                {
                    if(card.getDesignation() == suspectedCulprit){
                        wrongSuspicions.add(card);
                    }

                    if(card.getDesignation() == suspectedWeapon){
                        wrongSuspicions.add(card);

                    }

                    if (card.getDesignation() == suspectedLocation) {
                        wrongSuspicions.add(card);
                    }
                }

            }

        }

        return wrongSuspicions;
    }

}
