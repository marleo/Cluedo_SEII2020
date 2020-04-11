package com.example.cluedo_seii;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;



public class Game {

    private Board board;
    private Cards cards;
    private InvestigationFile investigationFile;
    private LinkedList<Player>players;
    private Boolean gameOver;
    private Random random;

    public Game(Board board, Cards cards, LinkedList<Player>players){

        this.board = board;
        this.cards = cards;
        this.players = players;
        investigationFile = new InvestigationFile();
        random = new Random();
        gameOver = false;

    }

    public void distributeCards(){

        LinkedList<Card> cardStack =  cards.getGameCardsStandard();
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
}
