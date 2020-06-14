package com.example.cluedo_seii.activities.playerGameInteraction;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.cluedo_seii.Card;
import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.GameState;
import com.example.cluedo_seii.R;
import com.example.cluedo_seii.network.connectionType;
import com.example.cluedo_seii.network.dto.SuspicionAnswerDTO;
import com.example.cluedo_seii.network.kryonet.NetworkClientKryo;
import com.example.cluedo_seii.network.kryonet.NetworkServerKryo;
import com.example.cluedo_seii.network.kryonet.SelectedConType;

import java.util.ArrayList;
import java.util.LinkedList;

public class SuspicionAnswer extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Game game;
    private LinkedList<Card>suspicion;
    private ArrayList<String> suspicionCards;
    private ArrayAdapter<String>cardsAdapter;
    private Spinner cardSpinner;
    private NetworkServerKryo server;
    private NetworkClientKryo client;
    private connectionType conType;
    private String answer;
    private Button btnAnswer;
    private SuspicionAnswerDTO suspicionAnswerDTO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = Game.getInstance();
        setContentView(R.layout.activity_suspicion_answer);
        suspicion=game.getSuspicion();
        suspicionCards=new ArrayList<String>();
        initializeNetwork();

        for(Card card:suspicion){
            suspicionCards.add(card.getDesignation());
        }

        cardsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, suspicionCards);
        cardSpinner = (Spinner) findViewById(R.id.suspicionAnswer);
        cardSpinner.setOnItemSelectedListener(this);
        cardsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardSpinner.setAdapter(cardsAdapter);

        btnAnswer = findViewById(R.id.accusationAnswerBtn);
        btnAnswer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                suspicionAnswerDTO= new SuspicionAnswerDTO();
                for(Card card: suspicion) {
                    if(card.getDesignation().equals(answer)){
                    suspicionAnswerDTO.setAnswer(card);}
                }
                suspicionAnswerDTO.setAcusee(game.getLocalPlayer());
                suspicionAnswerDTO.setAcusee(game.getCurrentPlayer());
                sendSuspicionAnswer();
                finish();
            }
        });
    }

    public void onStop(){
        super.onStop();
        game.changeGameState(GameState.WAITINGFORANSWER);
    }

    @Override
    //Festhaltung der Spielerauswahl
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        for (String suspicion : suspicionCards) {
            if (parent.getItemAtPosition(position).equals(suspicion)) {
                answer  = (String) parent.getItemAtPosition(position).toString();
                Log.i("selected answer", answer);
            }
        }
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    //Netzwerkfunktionen

    //Netzwerkinitialisierung
    public void initializeNetwork(){
        conType = SelectedConType.getConnectionType();
        if(conType== connectionType.HOST) {
            server = NetworkServerKryo.getInstance();
        }
        else if(conType==connectionType.CLIENT){
            client = NetworkClientKryo.getInstance();
        }
    }

    //Verdachtsverschickung
    public void sendSuspicionAnswer( ){
        //TODO add if for globalhost and global Client
        if(conType==connectionType.HOST) {
            server.broadcastMessage(suspicionAnswerDTO);
        }
        else if(conType==connectionType.CLIENT){
            client.sendMessage(suspicionAnswerDTO);
        }
    }
}
