package com.example.cluedo_seii.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.cluedo_seii.Card;
import com.example.cluedo_seii.DeckOfCards;
import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.R;
import com.example.cluedo_seii.spielbrett.Gameboard;

import java.util.LinkedList;

public class ShowCards extends AppCompatActivity {

    private WifiManager wifiManager;
    private Game game;
    private LinkedList<String> playerHand;
    private Intent intent;
    private Bundle bundle;
    private Gameboard gameboard;
    private ListView listView;
    private LinkedList<Player> players;
    private DeckOfCards deckOfCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Zu Demonstrationszwecken
        /*'deckOfCards = new DeckOfCards();
        players = new LinkedList<>();
        Player player1 = new Player(1, null, "10.0.2.16", null, null);
        Player player2 = new Player(2, null, "null", null, null);
        Player player3 = new Player(3, null, "null", null, null);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        game = new Game(gameboard, deckOfCards, players);
        game.distributeCards();*/


        intent = getIntent();

       game = (Game)intent.getSerializableExtra("game");

        setContentView(R.layout.activity_show_cards);

        playerHand = new LinkedList<>();

        playerHand.add("YOUR CARDS");

        wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);

        String deviceIP = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        //Log.i("scurr", "" + (String)intent.getSerializableExtra("game"));

     for(Player player: game.getPlayers()){

            if(player.getIP().equals(deviceIP)){

                for(Card card: player.getPlayerCards()){

                    playerHand.add(card.getDesignation());


                }

            }

        }

        ArrayAdapter<String> cardListViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, playerHand);
        ListView listView = (ListView) findViewById(R.id.playerHandDisplay);
        listView.setAdapter(cardListViewAdapter);


    }
}
