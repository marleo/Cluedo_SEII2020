package com.example.cluedo_seii;

import android.util.Log;

import com.example.cluedo_seii.spielbrett.Gameboard;
import com.example.cluedo_seii.spielbrett.StartingpointElement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class GameTest {

    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;
    private Player player6;

    private GameCharacter oberstVonGatov;
    private GameCharacter profBloom;
    private GameCharacter reverendGruen;
    private GameCharacter baroninVonPorz;
    private GameCharacter fraeuleinGloria;
    private GameCharacter frauWeiss;
    private DeckOfCards deckOfCards;
    private Gameboard gameboard;

    private StartingpointElement oberstVonGatovStart;
    private StartingpointElement profBloomStart;
    private StartingpointElement reverendGruenStart;
    private StartingpointElement baroninVonPorzStart;
    private StartingpointElement fraeuleinGloriaStart;
    private StartingpointElement frauWeissStart;
    private StartingpointElement deckOfCardsStart;
    private StartingpointElement gameboardStart;

    private LinkedList<Player>players;

    private Game game;

    @Before
    public void initialize(){
        oberstVonGatov = new GameCharacter("Oberst von Gatov", oberstVonGatovStart);
        profBloom = new GameCharacter("Prof. Bloom", profBloomStart);
        reverendGruen = new GameCharacter("Reverend Gruen", reverendGruenStart);
        baroninVonPorz = new GameCharacter("Baronin von Porz", baroninVonPorzStart);
        fraeuleinGloria = new GameCharacter("Fräulein Gloria", fraeuleinGloriaStart);
        frauWeiss = new GameCharacter("Frau Weiss", frauWeissStart);

        player1 = new Player(1, "10.0.0.8", oberstVonGatov);
        player2 = new Player(2, "10.0.0.9", profBloom);
        player3 = new Player(3, "10.0.0.10", reverendGruen);
        player4 = new Player(4, "10.0.0.11", baroninVonPorz);
        player5 = new Player(5, "10.0.0.12", fraeuleinGloria);
        player6 = new Player(6, "10.0.0.13", frauWeiss);

        players = new LinkedList<>();

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);
        players.add(player6);

        deckOfCards = new DeckOfCards();

        game = Game.getInstance();
        game.setPlayers(players);
        game.setGameboard(gameboard);

    }

    @After
    public void setBack(){

        oberstVonGatov = null;
        profBloom = null;
        reverendGruen = null;
        baroninVonPorz = null;
        fraeuleinGloria = null;
        frauWeiss = null;

        player1 = null;
        player2 = null;
        player3 = null;
        player4 = null;
        player5 = null;
        player6 = null;


        deckOfCards = null;

         Game.reset();

        players = null;

    }

    //Test für Methode DistributeCards der Klasse Game
    //Suche nach Duplikaten
    @Test
    public void testDistributeCardsNoDuplicates() {

        boolean sameCardFoundTwice = false;

        game.distributeCards();

        LinkedList<Card>testDistributeCardsHelp = new LinkedList<>();

        for(Card card: player1.getPlayerCards()){
            testDistributeCardsHelp.add(card);
        }
        for(Card card: player2.getPlayerCards()){
            testDistributeCardsHelp.add(card);
        }
        for(Card card: player3.getPlayerCards()){
            testDistributeCardsHelp.add(card);
        }
        for(Card card: player4.getPlayerCards()){
            testDistributeCardsHelp.add(card);
        }
        for(Card card: player5.getPlayerCards()){
            testDistributeCardsHelp.add(card);
        }
        for(Card card: player6.getPlayerCards()){
            testDistributeCardsHelp.add(card);
        }

        for(int i = 0; i<testDistributeCardsHelp.size(); i++){


            for(int j = i+1; j<testDistributeCardsHelp.size(); j++) {

                if (testDistributeCardsHelp.get(i).getDesignation().equals(testDistributeCardsHelp.get(j).getDesignation())){

                 sameCardFoundTwice = true;

                }
            }

            if (testDistributeCardsHelp.get(i).equals(game.getInvestigationFile().getCulprit()) ||
                    testDistributeCardsHelp.get(i).equals(game.getInvestigationFile().getWeapon()) ||
                    testDistributeCardsHelp.get(i).equals(game.getInvestigationFile().getRoom())) {

                sameCardFoundTwice = true;
            }

        }

        assertEquals(sameCardFoundTwice, false);


    }

    //Test nach Verteilung aller Karten
    @Test
    public void testDistributeCardsRightAmount() {

        boolean sameCardFoundTwice = false;

        game.distributeCards();

        LinkedList<Card>testDistributeCardsHelp = new LinkedList<>();

        for(Card card: player1.getPlayerCards()){
            testDistributeCardsHelp.add(card);
        }
        for(Card card: player2.getPlayerCards()){
            testDistributeCardsHelp.add(card);
        }
        for(Card card: player3.getPlayerCards()){
            testDistributeCardsHelp.add(card);
        }
        for(Card card: player4.getPlayerCards()){
            testDistributeCardsHelp.add(card);
        }
        for(Card card: player5.getPlayerCards()){
            testDistributeCardsHelp.add(card);
        }
        for(Card card: player6.getPlayerCards()){
            testDistributeCardsHelp.add(card);
        }

        testDistributeCardsHelp.add(game.getInvestigationFile().getCulprit());
        testDistributeCardsHelp.add(game.getInvestigationFile().getWeapon());
        testDistributeCardsHelp.add(game.getInvestigationFile().getRoom());

        assertEquals(testDistributeCardsHelp.size(), 21);
    }

    //Tests für Methode PlayerIncrementor der Klasse Game

    @Test
    public void testPlayerIncrementor() {
        assertEquals(game.getCurrentPlayer(), player1);
        game.nextPlayer();
        assertEquals(game.getCurrentPlayer(), player2);
    }

    @Test
    public void testRoundIncrementor() {
        game.nextPlayer();
        game.nextPlayer();
        game.nextPlayer();
        game.nextPlayer();
        game.nextPlayer();
        game.nextPlayer();
        assertEquals(game.getCurrentPlayer(),player1);
        assertEquals(game.getRound(), 2);
    }




}
