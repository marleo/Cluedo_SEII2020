package com.example.cluedo_seii;

import com.example.cluedo_seii.spielbrett.Gameboard;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Game implements Serializable {

    private static Game game = null;

    private transient DeckOfCards deckOfCards;
    private InvestigationFile investigationFile;
    private LinkedList<Player>players;
    private Boolean gameOver;
    private transient Random random;
    private int round;
    private int playerIterator;
    private Player currentPlayer;
    private GameState gameState;
    private Gameboard gameboard;
    private transient ChangeListener changeListener;
    private Player localPlayer;
    private int wrongAccusers;
    private String messageForLocalPlayer;
    private LinkedList<Card>suspicion;

    public LinkedList<Card> getSuspicion() {
        return suspicion;
    }

    public void setSuspicion(LinkedList<Card> suspicion) {
        this.suspicion = suspicion;
    }

    private Game(){
        this.deckOfCards = new DeckOfCards();
        investigationFile = new InvestigationFile();
        random = new Random();
        gameOver = false;
        round = 1;
        playerIterator = 0;
        gameState = GameState.START;
        players = new LinkedList<>();
        wrongAccusers=0;
        messageForLocalPlayer = null;
    }

    //Getter und Setter
    public static Game getInstance(){
        if(game==null){
            game = new Game();
        }
        return game;
    }

    public static void reset(){
        game = null;}

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

    public int getRound() {
        return round;
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setPlayers(LinkedList<Player> players) {
        this.players = players;
        currentPlayer = players.get(playerIterator);
    }

    public void setInvestigationFile(InvestigationFile investigationFile) {
        this.investigationFile = investigationFile;
    }

    public void setLocalPlayer(Player localPlayer) {
        this.localPlayer = localPlayer;
    }

    public Player getLocalPlayer() {
        return localPlayer;
    }

    public void setGameboard(Gameboard gameboard){
        this.gameboard = gameboard;
    }

    // set for Network start
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setPlayerIterator(int playerIterator) {
        this.playerIterator = playerIterator;
    }

    public int getPlayerIterator() {
        return playerIterator;
    }

    public void setWrongAccusers(int wrongAccusers) {
        this.wrongAccusers = wrongAccusers;
    }

    public String getMessageForLocalPlayer() {
        return messageForLocalPlayer;
    }

    public void setMessageForLocalPlayer(String messageForLocalPlayer) {
        this.messageForLocalPlayer = messageForLocalPlayer;
    }

    public int getWrongAccusers() {
        return wrongAccusers;
    }

    public void incrWrongAccusers(){wrongAccusers++;}


    // set for Network end


    //Methoden  zum ändern des Spielstatus und Implementierung von ChangeListener
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
    public void distributeCards(){

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




    //Methode zur Überprüfung der Spielbeendigungsbedingungen
    public boolean checkGameEnd(){
        if(wrongAccusers==game.getPlayers().size())
        {gameOver=true;}
        return gameOver;
        }
}
