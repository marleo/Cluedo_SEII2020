package com.example.cluedo_seii;

import android.telephony.SmsManager;

import com.example.cluedo_seii.spielbrett.GameFieldElement;
import com.example.cluedo_seii.spielbrett.Gameboard;
import com.example.cluedo_seii.spielbrett.RoomElement;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;



public class Game {

    private Gameboard gameboard;
    private DeckOfCards deckOfCards;
    private InvestigationFile investigationFile;
    private LinkedList<Player>players;
    private Boolean gameOver;
    private Random random;
    private int round;

    public Game(Gameboard gameboard, DeckOfCards deckOfCards, LinkedList<Player>players){

        this.gameboard = gameboard;
        this.deckOfCards = deckOfCards;
        this.players = players;
        investigationFile = new InvestigationFile();
        random = new Random();
        gameOver = false;
        round = 1;

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

    public void gameControl(){

        while(gameOver == false) {

            for (Player player : players) {


                //1. Teil eines Zuges - Bewegungsentscheidung

                if (player.getPosition() instanceof GameFieldElement) {

                    //TODO würfeln

                } else if (player.getPosition() instanceof RoomElement) {

                    //TODO Entscheidung ob Geheimgang, falls vorhanden, oder würfeln

                }

                //2. Teil eines Zuges - Anklage oder Verdachtsentscheidung

                if (player.getPosition() instanceof RoomElement) {

                    //TODO Nachricht über Entscheidung nach Anklage oder Verdacht

                }

            }
        }

        //TODO GameEnd Nachricht mit Auskunft über Spielausgang

    }

}
