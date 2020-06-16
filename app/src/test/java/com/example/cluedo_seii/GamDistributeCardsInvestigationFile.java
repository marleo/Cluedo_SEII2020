package com.example.cluedo_seii;

import android.graphics.Point;

import com.example.cluedo_seii.spielbrett.Gameboard;
import com.example.cluedo_seii.spielbrett.StartingpointElement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(Parameterized.class)
public class GamDistributeCardsInvestigationFile {
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

    private static Game game;

    @Before
    public void intialize(){
        oberstVonGatov = new GameCharacter("Oberst von Gatov", oberstVonGatovStart);
        profBloom = new GameCharacter("Prof. Bloom", profBloomStart);
        reverendGruen = new GameCharacter("Reverend Gruen", reverendGruenStart);
        baroninVonPorz = new GameCharacter("Baronin von Porz", baroninVonPorzStart);
        fraeuleinGloria = new GameCharacter("Fräulein Gloria", fraeuleinGloriaStart);
        frauWeiss = new GameCharacter("Frau Weiss", frauWeissStart);

        player1 = new Player(1,  oberstVonGatov);
        player2 = new Player(2,  profBloom);
        player3 = new Player(3, reverendGruen);
        player4 = new Player(4,  baroninVonPorz);
        player5 = new Player(5,  fraeuleinGloria);
        player6 = new Player(6,  frauWeiss);

        players = new LinkedList<>();

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);
        players.add(player6);

        game = Game.getInstance();
        game.setPlayers(players);
        game.setGameboard(gameboard);


    }

    @After
    public void setBack(){

        Game.reset();

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

        game = null;

        players = null;

    }


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {game},{game},{game},{game}, {game}, {game}, {game}, {game}, {game}, {game}});}

    private int input, expected;

    public GamDistributeCardsInvestigationFile(Game game) {
        this.game = game;
    }

    //Tests für Verteilung der Richtigen Kartentypen in die Ermittlungsakte nach aufruf der Methode distributeCards der Klasse Game
    @Test
    public void test() {

        boolean rightCards = true;

        game.distributeCards();

        if(game.getInvestigationFile().getCulprit().getType()!=CardType.PERSON||
                game.getInvestigationFile().getWeapon().getType()!=CardType.WEAPON||
                game.getInvestigationFile().getRoom().getType()!=CardType.ROOM){

            rightCards = false;

        }
        assertEquals(rightCards, true);
    }


    //Test if the investigation file has different cards than the players
    @Test
    public void playerCardsInvestigationCards(){
        boolean wrongDistribution = false;

        game.distributeCards();

        for(Player test : players) {
            for(Card card : test.getPlayerCards()){
                if (card == game.getInvestigationFile().getCulprit() ||
                        card == game.getInvestigationFile().getRoom() ||
                        card == game.getInvestigationFile().getWeapon()) {
                    wrongDistribution = true;
                    break;
                }
            }
        }
        assertFalse(wrongDistribution);
    }


    // Test if any players have the same cards on hand
    @Test
    public void playerPlayerCards(){
        boolean wrongDistribution = false;

        game.distributeCards();

        for(Player test : players) {
            for(Player test2 : players){
                if(test != test2){
                    for(Card player1 : test.getPlayerCards()){
                        for(Card player2 : test2.getPlayerCards()){
                            if(player1 == player2){
                                wrongDistribution = true;
                            }
                        }
                    }
                }
            }
        }
        assertFalse(wrongDistribution);
    }

}

