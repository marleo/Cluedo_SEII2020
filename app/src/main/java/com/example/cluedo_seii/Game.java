package com.example.cluedo_seii;

import com.example.cluedo_seii.spielbrett.Gameboard;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Game implements Serializable {

    private transient DeckOfCards deckOfCards;
    private InvestigationFile investigationFile;
    private LinkedList<Player>players;
    private Boolean gameOver;
    private transient Random random;
    private int round;
    private int playerIterator;
    private Player currentPlayer;
    private GameState gameState;
    private transient ChangeListener changeListener;

    public Game(Gameboard gameboard, LinkedList<Player>players){

        this.deckOfCards = new DeckOfCards();
        this.players = players;
        investigationFile = new InvestigationFile();
        random = new Random();
        gameOver = false;
        round = 1;
        playerIterator = 0;
        currentPlayer = players.get(playerIterator);
        gameState = GameState.START;
    }

    //Getter und Setter

    public InvestigationFile getInvestigationFile() {
        return investigationFile;
    }

    public LinkedList<Player> getPlayers() {
        return players;
    }

    public Boolean getGameOver() {
        return gameOver;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    int getRound() {
        return round;
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }

    //Methode  zum ändern des Spielstatus und Implementierung von ChangeListener

    public void changeGameState(GameState gameState){
        this.gameState = gameState;
        if(changeListener != null) changeListener.onChange();
    }

    public ChangeListener getListener() {
        return changeListener;
    }

    public void setListener(ChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public interface ChangeListener {
        void onChange();
    }

    //Methode zur Kartenverteilung

    void distributeCards(){

        LinkedList<Card> cardStack =  deckOfCards.getGameCardsStandard();

        //Verteilen zufälliger Karten in die Ermittlungsakte

        int randomPersonId = random.nextInt(6);
        int randomWeaponId = 6 + random.nextInt(6);
        int randomRoomId = 12 + random.nextInt(9);

        investigationFile.setCulprit(cardStack.get(randomPersonId));
        investigationFile.setWeapon(cardStack.get(randomWeaponId));
        investigationFile.setRoom(cardStack.get(randomRoomId));

        for(Card card:cardStack){
            if(card.getId()==randomPersonId){
                cardStack.remove(card);
                break;
            }
        }

        for(Card card:cardStack){
            if(card.getId()==randomWeaponId){
                cardStack.remove(card);
                break;
            }
        }

        for(Card card:cardStack){
            if(card.getId()==randomRoomId){
                cardStack.remove(card);
                break;
            }
        }

        Collections.shuffle(cardStack);

        int i = 0;

        //Verteilen der restlichen Karten an die Spieler

        while(i<cardStack.size())
        {
         for(int j = 0; j<players.size(); j++){
             if(i==cardStack.size()) {
                 break;}
             else if(cardStack.get(i)!=null){
                 Player temp = players.get(j);
                 temp.addCard(cardStack.get(i));
                  }
             i++;
         }
        }
    }

    //Methode für Spielerwechsel und Rundenzähler

    void nextPlayer(){
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
}
