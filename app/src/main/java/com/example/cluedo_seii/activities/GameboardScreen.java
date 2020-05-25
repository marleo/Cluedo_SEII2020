package com.example.cluedo_seii.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.cluedo_seii.DeckOfCards;
import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.GameCharacter;
import com.example.cluedo_seii.GameState;
import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.R;
import com.example.cluedo_seii.activities.playerGameInteraction.AccuseSomeone;
import com.example.cluedo_seii.activities.playerGameInteraction.MakeSuspicion;
import com.example.cluedo_seii.activities.playerGameInteraction.SuspectOrAccuse;
import com.example.cluedo_seii.activities.playerGameInteraction.ThrowDice;
import com.example.cluedo_seii.activities.playerGameInteraction.ThrowDiceOrUseSecretPassage;
import com.example.cluedo_seii.spielbrett.Gameboard;
import com.example.cluedo_seii.spielbrett.RoomElement;

import java.util.LinkedList;

public class GameboardScreen extends AppCompatActivity  {

    private float x1, x2, y1, y2;
    private Gameboard gameboard;
    private Game game;
    private LinkedList<Player> players;
    private DeckOfCards deckOfCards;
    private FragmentManager manager;
    private String mesaggeDialogTag;
    private Bundle bundle;
    private Intent intent;
    static final int MIN_SWIPE_DISTANCE = 150;

    @SuppressLint("ClickableViewAccessibility")
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

        bundle = new Bundle();
        mesaggeDialogTag = "MessageDialog";
        manager = getSupportFragmentManager();


        startGame();

        /*final Button notepad_Button = findViewById(R.id.notepadButton);
        notepad_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameboardScreen.this, NotepadScreen.class));
            }
        });*/
    }

    private void startGame() {

        //TODO initialize Game according to GameLobby Settings
        //Instanz eines Game-objektes Zu Demonstrationszwecken
        deckOfCards = new DeckOfCards();
        players = new LinkedList<>();
        GameCharacter gameCharacter = new GameCharacter("Prof. Bloom", null);
        GameCharacter gameCharacterAlt = new GameCharacter("Fräulein Weiss", null);
        Player player1 = new Player(1, "10.0.2.16", gameCharacterAlt);
        Player player2 = new Player(2, "null", gameCharacter);
        Player player3 = new Player(3, "null", gameCharacterAlt);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        game = new Game(gameboard, players);

        //Ausführung erfolgt wenn Methode changeGameState der Instanz game aufgerufen wird
        game.setListener(new Game.ChangeListener() {
            @Override

            //Wird ausgeführt wenn Methode aufgerufen wird

            public void onChange() {

                if(game.getGameState().equals(GameState.PLAYERTURNBEGIN)){
                    if(game.getCurrentPlayer().getPosition()instanceof RoomElement) {
                        throwDiceOrUseSecretPassage();
                    } else {
                        throwDice();
                    }
                }

                else if(game.getGameState().equals(GameState.PLAVERMOVEMENT)){

                }

                else if(game.getGameState().equals(GameState.PLAYERACCUSATION)){
                    if(game.getCurrentPlayer().getPosition()instanceof RoomElement){
                        suspectOrAccuse();
                    } else{
                        game.changeGameState(GameState.PLAYERTURNEND);
                    }
                }

                else if(game.getGameState().equals(GameState.PLAYERTURNEND)){
                    int wrongAccusers = 0;


                    //prüfe Spielbeendigungsbedingungen
                    for(Player player: game.getPlayers()){
                        if(player.getMadeFalseAccusation()==true){
                            wrongAccusers++;
                        }
                    }
                    if(wrongAccusers==game.getPlayers().size()){
                        game.setGameOver(true);
                        game.changeGameState(GameState.END);
                    }
                    else if(game.getGameOver()==true){
                        game.changeGameState(GameState.END);
                    }
                }
                else if(game.getGameState().equals(GameState.END)){
                    finish();
                }
            }
        });
        }



    //Aufruf von DialogOptionen
    public void throwDice(){
        ThrowDice dialog = new ThrowDice();
        bundle.putSerializable("game", game);
        dialog.setArguments(bundle);
        dialog.show(manager, mesaggeDialogTag);
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

    //UI Aufruf von Verdächtigung und Anklage
    public void makeSuspicion(){
        intent = new Intent(this, MakeSuspicion.class);
        intent.putExtra("game", game);
        startActivityForResult(intent, 2);
    }

    public void accuseSomeone(){
        intent = new Intent(this, AccuseSomeone.class);
        intent.putExtra("game", game);
        startActivityForResult(intent, 2);
    }

    //Zeigt Karten auf Spielerhand
    public void showCards(){
        intent = new Intent(this, ShowCards.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }

    //CallBack um Resultat aus Methode zu erhalten
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2)
        {
            game = (Game)data.getSerializableExtra("game");
        }
    }

    public void updateGame(Game gameUpdate){
        game = gameUpdate;
    }

    //EventListener für Swipe-Event
    @Override
    public boolean dispatchTouchEvent (MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;

            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                float swipeRight = x2-x1,
                        swipeLeft = x1-x2,
                        swipeDown = y2-y1,
                        swipeUp = y1-y2;


                if(swipeDown > MIN_SWIPE_DISTANCE){
                    startActivity(new Intent(GameboardScreen.this, RollDiceScreen.class));
                }else if(swipeRight > MIN_SWIPE_DISTANCE){
                    startActivity(new Intent(GameboardScreen.this, NotepadScreen.class));
                } else if(swipeLeft > MIN_SWIPE_DISTANCE){
                    showCards();
                }
                break;
        }
        return false;
    }



}





