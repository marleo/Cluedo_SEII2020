package com.example.cluedo_seii;




import com.example.cluedo_seii.spielbrett.Gameboard;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;



public class Game implements Serializable {

    private transient Gameboard gameboard;
    private transient DeckOfCards deckOfCards;
    private transient InvestigationFile investigationFile;
    private transient LinkedList<Player>players;
    private transient Boolean gameOver;
    private transient Random random;
    private transient int round;

    private int playerIterator;

    private Player currentPlayer;

    public Game(Gameboard gameboard, DeckOfCards deckOfCards, LinkedList<Player>players){

        this.gameboard = gameboard;
        this.deckOfCards = deckOfCards;
        this.players = players;
        investigationFile = new InvestigationFile();
        random = new Random();
        gameOver = false;
        round = 1;
        playerIterator = 0;
        currentPlayer = players.get(playerIterator);
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public LinkedList<Player> getPlayers() {
        return players;
    }

    public void distributeCards(){

        LinkedList<Card> cardStack =  deckOfCards.getGameCardsStandard();
        int randomPersonId = random.nextInt(6);
        int randomWeaponId = random.nextInt(6) + 6;
        int randomRoomId = random.nextInt(9) + 12;

        investigationFile.setCulprit(cardStack.get(randomPersonId));
        cardStack.remove(randomPersonId);
        investigationFile.setWeapon(cardStack.get(randomWeaponId));
        cardStack.remove(randomWeaponId);
        investigationFile.setRoom(cardStack.get(randomRoomId));
        cardStack.remove(randomRoomId);

        Collections.shuffle(cardStack);

        int i = 0;

        while(i<cardStack.size())
        {
         for(int j = 0; j<players.size(); j++){

             Player temp = players.get(j);

             if(i==cardStack.size())
             {break;}

             temp.addCard(cardStack.get(i));
             i++;
         }

        }

    }

    public void nextPlayer(){

        if(playerIterator==players.size()-1)
        {
            playerIterator=0;
            round++;
        }

        else {
            playerIterator++;
        }

        currentPlayer = players.get(playerIterator);

    }

    public LinkedList<Card> suspectSomeone(String suspectedCulprit, String suspectedWeapon, String suspectedLocation){

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
