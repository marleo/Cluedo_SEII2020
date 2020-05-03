package com.example.cluedo_seii;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CardTypeTest {

    @Test
    public void enumTest(){
        assertEquals("WEAPON", CardType.WEAPON.name());
        assertEquals("PERSON", CardType.PERSON.name());
        assertEquals("ROOM", CardType.ROOM.name());
    }
}
