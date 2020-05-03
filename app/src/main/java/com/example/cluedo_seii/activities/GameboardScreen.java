package com.example.cluedo_seii.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;


import com.example.cluedo_seii.Card;
import com.example.cluedo_seii.DeckOfCards;
import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.R;

import com.example.cluedo_seii.activities.playerGameInteraction.SuspectOrAccuse;
import com.example.cluedo_seii.activities.playerGameInteraction.ThrowDice;
import com.example.cluedo_seii.activities.playerGameInteraction.ThrowDiceOrUseSecretPassage;
import com.example.cluedo_seii.spielbrett.Gameboard;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class GameboardScreen extends AppCompatActivity  {

    private Gameboard gameboard;
    private Game game;
    private LinkedList<Player> players;
    private DeckOfCards deckOfCards;
    private FragmentManager manager;
    private String mesaggeDialogTag;
    Bundle bundle;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spielbrett_screen);

        /*
            0 = GameField
            1 = NoneWalkableElement
            2 = StartingPoint
            3 = Room
         */

        String gameBoard =
                "0002000002000" +
                "0000000000000" +
                "0000000000000" +
                "0333300033330" +
                "0333300033330" +
                "0333300033330" +
                "0000000000000" +
                "0200000000020" +
                "0000000000000" +
                "0333300033330" +
                "0333300033330" +
                "0333300033330" +
                "0000000000000" +
                "0000000000000" +
                "0333300033330" +
                "0333300033330" +
                "0333300033330" +
                "0000000000000" +
                "0002000002000";

        gameboard = new Gameboard(this,13,19, gameBoard);
        setContentView(gameboard.getLayout());


        startGame();

        /*final Button notepad_Button = findViewById(R.id.notepadButton);
        notepad_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameboardScreen.this, NotepadScreen.class));
            }
        });*/
    }

    public void startGame(){


        //TODO initialize Game according to GameLobby Settings



        deckOfCards =  new DeckOfCards();
        players = new LinkedList<>();
        Player player1 = new Player(1, null, "10.0.2.16", null, null);
        Player player2 = new Player(2, null, "null", null, null);
        Player player3 = new Player(3, null, "null", null, null);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        game = new Game(gameboard, deckOfCards, players);
        game.distributeCards();

        showCards();

        //throwDice();


    }


    public void throwDice(){

        ThrowDice dialog = new ThrowDice();
        bundle.putSerializable("game", game);
        dialog.setArguments(bundle);
        dialog.show(manager, mesaggeDialogTag);
        bundle = null;
        bundle = dialog.getArguments();
        game = (Game) bundle.getSerializable("game");


        Log.i("currentPlayerActivity",""+ game.getCurrentPlayer().getId());


    }

    public void throwDiceOrUseSecretPassage(){

        ThrowDiceOrUseSecretPassage dialog = new ThrowDiceOrUseSecretPassage();
        bundle.putSerializable("game", game);
        dialog.setArguments(bundle);
        dialog.show(manager, mesaggeDialogTag);

    }

    public void suspectOrAccuse(){

        SuspectOrAccuse dialog = new SuspectOrAccuse();
        bundle.putSerializable("game", game);
        dialog.setArguments(bundle);
        dialog.show(manager, mesaggeDialogTag);

    }

    public void showCards(){
        Intent intent = new Intent(this, ShowCards.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }

}


