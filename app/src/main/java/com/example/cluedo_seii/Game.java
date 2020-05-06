package com.example.cluedo_seii;




import com.example.cluedo_seii.spielbrett.Gameboard;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;



public class Game implements Serializable {

    private transient  Gameboard gameboard;
    private transient  DeckOfCards deckOfCards;
    private InvestigationFile investigationFile;
    private LinkedList<Player>players;
    private Boolean gameOver;
    private transient  Random random;
    private int round;

    private int playerIterator;

    private Player currentPlayer;

    public Game(Gameboard gameboard, LinkedList<Player>players){

        this.gameboard = gameboard;
        this.deckOfCards = new DeckOfCards();
        this.players = players;
        investigationFile = new InvestigationFile();
        random = new Random();
        gameOver = false;
        round = 1;
        playerIterator = 0;
        currentPlayer = players.get(playerIterator);
    }

    public InvestigationFile getInvestigationFile() {
        return investigationFile;
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }

    public LinkedList<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
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

    public int getRound() {
        return round;
    }
}
