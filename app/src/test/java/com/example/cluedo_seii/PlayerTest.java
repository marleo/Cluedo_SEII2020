package com.example.cluedo_seii;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;
    private Player player6;

    private LinkedList<Player>players;

    private GameCharacter oberstVonGatov;
    private GameCharacter profBloom;
    private GameCharacter reverendGruen;
    private GameCharacter baroninVonPorz;
    private GameCharacter fraeuleinGloria;
    private GameCharacter frauWeiss;

    @Before
    public void initialize(){}

    @After
    public void setBack(){}

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    
}
