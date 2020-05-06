package com.example.cluedo_seii;

import com.example.cluedo_seii.spielbrett.Gameboard;
import com.example.cluedo_seii.spielbrett.StartingpointElement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Random;

import static org.junit.Assert.*;

public class PlayerTest {

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
    private LinkedList<Card>gameCards;

    private Random random;

    private int randomPersonId;
    private int randomWeaponId;
    private int randomRoomId;

    private LinkedList<String>suspectedPlayerHand;

    private InvestigationFile investigationFile;


    @Before
    public void initialize() {
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
        gameCards = deckOfCards.getGameCardsStandard();

        investigationFile = new InvestigationFile();

        suspectedPlayerHand=new LinkedList<>();

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

        players = null;

    }

    @Test
    public void testSuspectGuess3CardsRight() {

        player1.addCard(gameCards.get(0));
        player1.addCard(gameCards.get(9));
        player1.addCard(gameCards.get(16));

        suspectedPlayerHand = player2.suspect("Oberst von Gatov", "Seil", "Musikzimmer", players);

        assertEquals(suspectedPlayerHand.size(), 3);

    }

    @Test
    public void testSuspect2CardsRight(){

        player1.addCard(gameCards.get(0));
        player1.addCard(gameCards.get(9));
        player1.addCard(gameCards.get(16));

        suspectedPlayerHand = player2.suspect("Oberst von Gatov", gameCards.get(8).getDesignation(), gameCards.get(16).getDesignation(),players);

        assertEquals(suspectedPlayerHand.size(), 2);

    }

    @Test
    public void testSuspect1CardRight(){

        player1.addCard(gameCards.get(0));
        player1.addCard(gameCards.get(9));
        player1.addCard(gameCards.get(16));

        suspectedPlayerHand = player2.suspect("Oberst von Gatov", gameCards.get(8).getDesignation(), gameCards.get(19).getDesignation(),players);

        assertEquals(suspectedPlayerHand.size(), 1);


    }

    @Test
    public void testSuspectNoCardRight(){

        player1.addCard(gameCards.get(1));
        player1.addCard(gameCards.get(9));
        player1.addCard(gameCards.get(16));

        suspectedPlayerHand = player2.suspect("Oberst von Gatov", gameCards.get(8).getDesignation(), gameCards.get(19).getDesignation(),players);

        assertEquals(suspectedPlayerHand.size(), 0);

    }

}
