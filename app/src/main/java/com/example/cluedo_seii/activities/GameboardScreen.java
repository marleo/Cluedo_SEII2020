package com.example.cluedo_seii.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.cluedo_seii.DeckOfCards;
import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.GameCharacter;
import com.example.cluedo_seii.GameState;
import com.example.cluedo_seii.Notepad;
import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.R;
import com.example.cluedo_seii.activities.playerGameInteraction.AccuseSomeone;
import com.example.cluedo_seii.activities.playerGameInteraction.MakeSuspicion;
import com.example.cluedo_seii.activities.playerGameInteraction.SuspectOrAccuse;
import com.example.cluedo_seii.activities.playerGameInteraction.ThrowDice;
import com.example.cluedo_seii.activities.playerGameInteraction.ThrowDiceOrUseSecretPassage;
import com.example.cluedo_seii.spielbrett.Gameboard;
import com.example.cluedo_seii.spielbrett.StartingPoint;
import com.example.cluedo_seii.spielbrett.RoomElement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
    private List<StartingPoint> startingPoints;
    private List<Player> playerMove;
    private int playerCurrentlyPlayingId;
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
            4 = Entrace1
            5 = Entrace2
            6 = Entrace3
            7 = Entrace4
            8 = Entrace5
            9 = Entrace6
            a = Entrace7
            b = Entrace9
            c = WorkingRoom1
            d = WorkingRoom2
            e = WorkingRoom3
            f = WorkingRoom4
            g = WorkingRoom5
            h = WorkingRoom6
            i = WorkingRoom7
            j = WorkingRoom8
            A = Geheimgang
            k = Workingroom9
            l = Bib1
            m = Bib2
            n = Bib3
            o = Bib4
            p = Bib5
            q = Bib6
            r = Bib7
            s = Bib8
            t = Bib9
            u = Bib10
            v = Billard1
            w = Billard2
            x = Billard3
            y = Billard4
            z = Billard5
            B = Billard6
            C = Billard7
            D = Billard8
            E = Billard9
            F = Billard10
            G = Billard11
            H = Billard13
            I = Billard12
            J
            K


         */

        String gameBoard =
                "cdef045621111" +
                "ghij078901111" +
                "Akk30a3b03111" +
                "2000000000000" +
                "lmt0000000000" +
                "opq3000000002" +
                "r3u0000000000" +
                "0000000000002" +
                "0000000011111" +
                "v3wxy00031111" +
                "zBCD300111111" +
                "EFGHI00000000" +
                "0000000000000" +
                "1111100031111" +
                "1111100031111" +
                "1111300011111" +
                "0000000000000" +
                "0000111100000" +
                "0020111102000";

        gameboard = new Gameboard(this,13,19, gameBoard);
        setContentView(gameboard.getLayout());

        bundle = new Bundle();
        mesaggeDialogTag = "MessageDialog";
        manager = getSupportFragmentManager();


        startGame();


        startingPoints = new ArrayList<>();
        startingPoints.add(new StartingPoint(0, 0));
        startingPoints.add(new StartingPoint(2, 1));

        gameboard.spawnPlayer(startingPoints, this);

        playerMove = new ArrayList<>();
        for(StartingPoint startingPoint: startingPoints) {
            /*Log.i("Test",
                    "StartingPoint Position: " + startingPoint.getPlayerPosition().x + ":"
                            + startingPoint.getPlayerPosition().y);*/
            /*
            playerMove.add(
                    new Player(
                            startingPoint.getPlayerId(),
                            startingPoint.getPlayerPosition()
                    )
            );
            */
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

    public List<Player> getPlayerMove() {
        return playerMove;
    }

    public void setPlayerMove(List<Player> playerMove) {
        this.playerMove = playerMove;
    }

    public int getPlayerCurrentlyPlayingId() {
        return playerCurrentlyPlayingId;
    }


    public void setPlayerCurrentlyPlayingId(int playerCurrentlyPlayingId) {
        this.playerCurrentlyPlayingId = playerCurrentlyPlayingId;
    }

    private void startGame(){
            //TODO initialize Game according to GameLobby Settings
            //Instanz eines Game-objektes Zu Demonstrationszwecken
            deckOfCards = new DeckOfCards();
            players = new LinkedList<>();

            GameCharacter gameCharacter = new GameCharacter("Prof. Bloom", null);
            GameCharacter gameCharacterAlt = new GameCharacter("Fräulein Weiss", null);
            Player player1 = new Player(1, gameCharacterAlt);
            Player player2 = new Player(2,  gameCharacter);
            Player player3 = new Player(3,  gameCharacterAlt);
            players.add(player1);
            players.add(player2);
            players.add(player3);
            //game = new Game(gameboard, players);
            game = Game.getInstance();
            game.setGameboard(gameboard);
            game.setPlayers(players);
            game.setLocalPlayer(player2);
            game.distributeCards(); //um Notepad cheatFunction zu demonstrieren


        //Ausführung erfolgt wenn Methode changeGameState der Instanz game aufgerufen wird
        game.setListener(new Game.ChangeListener() {
            @Override

            //Wird ausgeführt wenn Methode aufgerufen wird

            public void onChange() {
/*
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
                }*/
            }
        });
    }





    //Aufruf von DialogOptionen
    public void throwDice(){
        ThrowDice dialog = new ThrowDice();
        dialog.show(manager, mesaggeDialogTag);
    }

    public void throwDiceOrUseSecretPassage(){
        ThrowDiceOrUseSecretPassage dialog = new ThrowDiceOrUseSecretPassage();
        dialog.show(manager, mesaggeDialogTag);
    }

    public void suspectOrAccuse(){
        SuspectOrAccuse dialog = new SuspectOrAccuse();
        dialog.show(manager, mesaggeDialogTag);
    }

    //UI Aufruf von Würfeln, Verdächtigung und Anklage
    public void rollDice(){
        //TODO Aufruf von Würfelfunktion
    }

    public void makeSuspicion(){
        startActivity(new Intent(this, MakeSuspicion.class));
    }

    public void startNotepad(){
        intent = new Intent(this, NotepadScreen.class);
        //intent.putExtra("game",game);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);

    }

    public void accuseSomeone(){
        startActivity(new Intent(this, AccuseSomeone.class));
    }

    //Zeigt Karten auf Spielerhand
    public void showCards(){
        intent = new Intent(this, ShowCards.class);
        //intent.putExtra("game", game);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    //CallBack um Resultat aus Methode zu erhalten
  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2)
        {
            game = (Game)data.getSerializableExtra("game");
        }
    }*/

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
                    overridePendingTransition(R.anim.slide_down_in, R.anim.slide_down_out);
                }else if(swipeRight > MIN_SWIPE_DISTANCE){
                    startNotepad();
                } else if(swipeLeft > MIN_SWIPE_DISTANCE){
                    showCards();
                }
                break;
        }
        return false;
    }



}





