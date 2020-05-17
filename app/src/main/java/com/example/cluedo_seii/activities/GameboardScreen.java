package com.example.cluedo_seii.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.R;

import com.example.cluedo_seii.spielbrett.Gameboard;
import com.example.cluedo_seii.spielbrett.StartingPoint;

import java.util.ArrayList;
import java.util.List;

public class GameboardScreen extends AppCompatActivity {

    private Gameboard gameboard;
    private List<StartingPoint> startingPoints;
    private List<Player> players;
    private int playerCurrentlyPlayingId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spielbrett_screen);

        /*
            0 = GameField
            1 = NoneWalkableElement
            2 = StartingPoint
            3 = Room
            //4 = StartingPointPlayer
            //5 = GamefieldPlayer
            //6 = RoomPlayer
         */

        String gameBoard =
                "1112011102111" +
                "1110011100111" +
                "1130011100111" +
                "0000003300111" +
                "1110000003111" +
                "1113000000000" +
                "1113000000002" +
                "1110000000002" +
                "0000000000000" +
                "1111300031111" +
                "1111100001111" +
                "1111300031111" +
                "0000000000000" +
                "1111100031111" +
                "1111100031111" +
                "1111300011111" +
                "0000000000000" +
                "0000031100000" +
                "0020111102000";

        gameboard = new Gameboard(this,13,19, gameBoard);
        setContentView(gameboard.getLayout());

        startingPoints = new ArrayList<>();
        startingPoints.add(new StartingPoint(0, 0));
        startingPoints.add(new StartingPoint(2, 1));

        gameboard.spawnPlayer(startingPoints, this);

        players = new ArrayList<>();
        for(StartingPoint startingPoint: startingPoints) {
            /*Log.i("Test",
                    "StartingPoint Position: " + startingPoint.getPlayerPosition().x + ":"
                            + startingPoint.getPlayerPosition().y);*/
            players.add(
                    new Player(
                            startingPoint.getPlayerId(),
                            startingPoint.getPlayerPosition()
                    )
            );
        }

        // Wenn sich die Id ändert, dann danach updateGameboardScreen machen so wie hier!
        playerCurrentlyPlayingId = 0;
        gameboard.updateGameboardScreen(this);


    }

    public Gameboard getGameboard() {
        return gameboard;
    }

    public void setGameboard(Gameboard gameboard) {
        this.gameboard = gameboard;
    }

    public List<StartingPoint> getStartingPoints() {
        return startingPoints;
    }

    public void setStartingPoints(List<StartingPoint> startingPoints) {
        this.startingPoints = startingPoints;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getPlayerCurrentlyPlayingId() {
        return playerCurrentlyPlayingId;
    }

    public void setPlayerCurrentlyPlayingId(int playerCurrentlyPlayingId) {
        this.playerCurrentlyPlayingId = playerCurrentlyPlayingId;
    }
}
