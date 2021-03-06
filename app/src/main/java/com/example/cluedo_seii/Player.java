package com.example.cluedo_seii;


import android.util.Log;


import java.io.Serializable;
import java.util.LinkedList;

public class Player implements Serializable {

    private int id;
    private String username;
    private LinkedList<Card> playerCards;
    private MyPoint position;
    private GameCharacter playerCharacter;
    private Boolean madeFalseAccusation;


    private Player() {
        //no arg constructor for deserialization
    }

    public Player(int id, GameCharacter playerCharacter){
        this.id = id;
        playerCards = new LinkedList<>();
        this.position = playerCharacter.getStartingPoint();
        this.playerCharacter = playerCharacter;
        madeFalseAccusation = false;
    }

    public MyPoint getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

    public LinkedList<Card> getPlayerCards() {
        return playerCards;
    }


    public GameCharacter getPlayerCharacter() {
        return playerCharacter;
    }

    public void setMadeFalseAccusation(Boolean madeFalseAccusation) {
        this.madeFalseAccusation = madeFalseAccusation;
    }


    public Boolean getMadeFalseAccusation() {
        return madeFalseAccusation;
    }



    public void addCard(Card card){
        playerCards.add(card);
    }




    //Verdächtigungsfunktion
    public LinkedList<Card> suspect(String suspectedCulprit, String suspectedWeapon, String suspectedLocation, LinkedList<Player> players){
        Log.i("makeSuspicon", "");
        LinkedList<Card>wrongSuspicions = new LinkedList<>();

        for(Player player: players){

            if(suspectedCulprit.equals(player.getPlayerCharacter().getName())){

                for(Card card: player.getPlayerCards())
                {
                    if(card.getDesignation().equals(suspectedCulprit)){
                        wrongSuspicions.add(card);
                    }

                    if(card.getDesignation().equals(suspectedWeapon)){
                        wrongSuspicions.add(card);

                    }

                    if (card.getDesignation().equals(suspectedLocation)) {
                        wrongSuspicions.add(card);
                    }
                }

            }

        }

        return wrongSuspicions;
    }

    public void setPosition(MyPoint position) {
        this.position = position;
    }

    //Anklagefunktion
    public boolean accuse(String accusedPerson, String accusedWeapon, String accusedLocation, InvestigationFile investigationFile){

        if(investigationFile.getCulprit().getDesignation().equals(accusedPerson)
                && investigationFile.getWeapon().getDesignation().equals(accusedWeapon)
                && investigationFile.getRoom().getDesignation().equals(accusedLocation))
        {
            return true;
        }

        else{
           madeFalseAccusation = true;
           return  false;
        }
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
