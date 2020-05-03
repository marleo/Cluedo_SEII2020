package com.example.cluedo_seii;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

    /**
     * Example local unit test, which will execute on the development machine (host).
     *
     * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
     */
    public class CardTest {

        @Test
        public void constructorTest() {
            Card c = new Card(1, "Test", CardType.WEAPON);
            assertEquals(1, c.getId());
            assertEquals("Test", c.getDesignation());
            assertEquals(CardType.WEAPON, c.getType());
        }
    }

