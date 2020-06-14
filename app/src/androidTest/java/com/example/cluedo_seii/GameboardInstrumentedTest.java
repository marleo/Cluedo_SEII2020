package com.example.cluedo_seii;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cluedo_seii.activities.GameboardScreen;
import com.example.cluedo_seii.spielbrett.GameFieldElement;
import com.example.cluedo_seii.spielbrett.Gameboard;
import com.example.cluedo_seii.spielbrett.GameboardElement;
import com.example.cluedo_seii.spielbrett.NoneWalkableElement;
import com.example.cluedo_seii.spielbrett.DoorElement;
import com.example.cluedo_seii.spielbrett.StartingpointElement;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class GameboardInstrumentedTest {
    private Activity currentActivity;

    @Before
    public void before(){
        Instrumentation mInstrumentation = InstrumentationRegistry.getInstrumentation();
        // We register our interest in the activity
        Instrumentation.ActivityMonitor monitor = mInstrumentation.addMonitor(GameboardScreen.class.getName(), null, false);
        // We launch it
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(mInstrumentation.getTargetContext(), GameboardScreen.class.getName());
        mInstrumentation.startActivitySync(intent);

        currentActivity = InstrumentationRegistry.getInstrumentation().waitForMonitor(monitor);
        assertNotNull(currentActivity);
    }

    @BeforeClass
    public static void beforeClass(){

    }

    @After
    public void after(){

    }

    @AfterClass
    public static void afterClass(){

    }

    @Test
    public void testGameboardInit() throws IllegalAccessException, InstantiationException {
        /*
            0 = GameField
            1 = NoneWalkableElement
            2 = StartingPoint
            3 = Room
         */
        String gameBoardString = "001223";

        Gameboard gameboard = new Gameboard((GameboardScreen) currentActivity,6,1, gameBoardString);
        Assert.assertEquals(6,gameboard.getListeGameboardElemente().size());

        assertInstances(gameboard, 2,1,2,1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testGameboardInitWrongLaenge() {
        /*
            0 = GameField
            1 = NoneWalkableElement
            2 = StartingPoint
            3 = Room
         */
        String gameBoardString = "001223";

        Gameboard gameboard = new Gameboard((GameboardScreen) currentActivity,5,1, gameBoardString);
    }

    @Test(expected = NullPointerException.class)
    public void testGameboardInitWrongBreite() {
        /*
            0 = GameField
            1 = NoneWalkableElement
            2 = StartingPoint
            3 = Room
         */
        String gameBoardString = "001223";

        Gameboard gameboard = new Gameboard((GameboardScreen) currentActivity,6,2, gameBoardString);
    }

    @Test(expected = NullPointerException.class)
    public void testGameboardInitWrongLaengeAndBreite() {
        /*
            0 = GameField
            1 = NoneWalkableElement
            2 = StartingPoint
            3 = Room
         */
        String gameBoardString = "001223";

        Gameboard gameboard = new Gameboard((GameboardScreen) currentActivity,5,2, gameBoardString);
    }

    private void assertInstances(Gameboard gameboard,
                               int expectedGameFieldElementCount,
                               int expectedNoneWalkableElementCount,
                               int expectedStartingPointElementCount,
                               int expectedRoomElementCount) throws InstantiationException, IllegalAccessException {
        int actualGameFieldElementCount = 0;
        int actualNoneWalkableElementCount = 0;
        int actualStartingPointElementCount = 0;
        int actualRoomElementCount = 0;
        for(GameboardElement gameboardElement: gameboard.getListeGameboardElemente()) {
            if (gameboardElement instanceof GameFieldElement) {
                actualGameFieldElementCount++;
            }

            if(gameboardElement instanceof NoneWalkableElement) {
                actualNoneWalkableElementCount++;
            }


            if(gameboardElement instanceof StartingpointElement) {
                actualStartingPointElementCount++;
            }

            if(gameboardElement instanceof DoorElement) {
                actualRoomElementCount++;
            }
        }

        Assert.assertEquals(expectedGameFieldElementCount, actualGameFieldElementCount);
        Assert.assertEquals(expectedNoneWalkableElementCount, actualNoneWalkableElementCount);
        Assert.assertEquals(expectedRoomElementCount, actualRoomElementCount);
        Assert.assertEquals(expectedStartingPointElementCount, actualStartingPointElementCount);
    }
}
