package com.example.cluedo_seii;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Game {

    private Board board;
    private Cards cards;
    private InvestigationFile investigationFile;
    private Player[]players;
    private Boolean gameOver;

    public Game(Board board, Cards cards, Player[]players){

        this.board = board;
        this.cards = cards;
        this.players = players;
        investigationFile = new InvestigationFile();
        gameOver = false;
    }

    public void distributeCards(){

        LinkedList<Card> cardStack =  cards.getCards();
        Random random = new Random();
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


    }

}
