package com.example.cluedo_seii;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class InvestigationFileTest {

    private Card culprit;
    private Card weapon;
    private Card room;

    @Before
    public void init(){
        culprit = new Card(0, "Oberst von Gatov", CardType.PERSON);
        weapon = new Card(6, "Dolch", CardType.WEAPON);
        room = new Card(20, "Arbeitszimmer", CardType.ROOM);
    }
    @Test
    public void constructorTest() {
        InvestigationFile f = new InvestigationFile();
        f.setCulprit(culprit);
        f.setRoom(room);
        f.setWeapon(weapon);

        assertEquals(culprit, f.getCulprit());
        assertEquals(room, f.getRoom());
        assertEquals(weapon, f.getWeapon());
    }

    @Test
    public void wrongInputTest(){
        // TODO: Implement detection of wrong input in InvestigationFile (eg. Weapon instead of Room)
    }
}