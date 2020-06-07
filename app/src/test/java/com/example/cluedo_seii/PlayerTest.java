package com.example.cluedo_seii;

import android.graphics.Point;

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

    private Point oberstVonGatovStart;
    private Point profBloomStart;
    private Point reverendGruenStart;
    private Point baroninVonPorzStart;
    private Point fraeuleinGloriaStart;
    private Point frauWeissStart;
    private Point deckOfCardsStart;
    private Point gameboardStart;

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

        player1 = new Player(1,  oberstVonGatov);
        player2 = new Player(2,  profBloom);
        player3 = new Player(3,  reverendGruen);
        player4 = new Player(4,  baroninVonPorz);
        player5 = new Player(5, fraeuleinGloria);
        player6 = new Player(6,  frauWeiss);

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

    //Tests für Methode accuse der Klasse Player
    @Test
    public void accuseRight(){

        investigationFile.setCulprit(gameCards.get(1));
        investigationFile.setWeapon(gameCards.get(9));
        investigationFile.setRoom(gameCards.get(16));

        assertEquals(player1.accuse(gameCards.get(1).getDesignation(), gameCards.get(9).getDesignation(), gameCards.get(16).getDesignation(), investigationFile), true);
        assertEquals(player1.getMadeFalseAccusation(), false);
    }

    @Test
    public void accuseWrongCase1(){

        investigationFile.setCulprit(gameCards.get(1));
        investigationFile.setWeapon(gameCards.get(9));
        investigationFile.setRoom(gameCards.get(16));

        assertEquals(player1.accuse(gameCards.get(1).getDesignation(), gameCards.get(9).getDesignation(), gameCards.get(17).getDesignation(), investigationFile), false);
        assertEquals(player1.getMadeFalseAccusation(), true);


    }

    @Test
    public void accuseWrongCase2(){

        investigationFile.setCulprit(gameCards.get(1));
        investigationFile.setWeapon(gameCards.get(9));
        investigationFile.setRoom(gameCards.get(16));

        assertEquals(player1.accuse(gameCards.get(1).getDesignation(), gameCards.get(7).getDesignation(), gameCards.get(17).getDesignation(), investigationFile), false);
        assertEquals(player1.getMadeFalseAccusation(), true);
    }

    @Test
    public void accuseWrongCase3(){

        investigationFile.setCulprit(gameCards.get(1));
        investigationFile.setWeapon(gameCards.get(9));
        investigationFile.setRoom(gameCards.get(16));

        assertEquals(player1.accuse(gameCards.get(4).getDesignation(), gameCards.get(10).getDesignation(), gameCards.get(18).getDesignation(), investigationFile), false);
        assertEquals(player1.getMadeFalseAccusation(), true);
    }
}
