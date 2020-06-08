package com.example.cluedo_seii.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cluedo_seii.Card;
import com.example.cluedo_seii.DeckOfCards;
import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.GameState;
import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.R;
import com.example.cluedo_seii.spielbrett.Gameboard;

import java.util.LinkedList;

public class ShowCards extends AppCompatActivity {


    float x1, x2;
    static final int MIN_SWIPE_DISTANCE = 150;
    private WifiManager wifiManager;
    private Game game;
    private LinkedList<String> playerHand;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        game = Game.getInstance();

        setContentView(R.layout.activity_show_cards);

        playerHand = new LinkedList<>();

        playerHand.add("YOUR CARDS");

        for (Card card : game.getLocalPlayer().getPlayerCards()) {

            playerHand.add(card.getDesignation());

        }

        ArrayAdapter<String> cardListViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, playerHand);
        listView = findViewById(R.id.playerHandDisplay);
        listView.setAdapter(cardListViewAdapter);

        game.setListener(new Game.ChangeListener() {
                             @Override
                             //Wird ausgeführt wenn Methode changeGameState aufgerufen wird
                             public void onChange() {
                                 finish();
                             }
                         }
        );
    }

    @Override
    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()) {

            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();

                float swipeRight = x2 - x1;

                if (swipeRight > MIN_SWIPE_DISTANCE) {
                    finish();
                    overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                }
                break;
        }
        return false;
    }

}
