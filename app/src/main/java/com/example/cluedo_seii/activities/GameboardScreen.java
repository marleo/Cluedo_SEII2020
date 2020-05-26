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
                "1111300031111" +
                "1111100011111" +
                "1111300000000" +
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

        //Zu Demonstrationszwecken
        /*deckOfCards = new DeckOfCards();
        players = new LinkedList<>();
        GameCharacter gameCharacter = new GameCharacter("Prof. Bloom", null);
        GameCharacter gameCharacterAlt = new GameCharacter("Fräulein Weiss", null);
        Player player1 = new Player(1, "10.0.2.16", gameCharacterAlt, null);
        Player player2 = new Player(2,  "null", gameCharacter, null);
        Player player3 = new Player(3, "null", gameCharacterAlt, null);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        game = new Game(gameboard, deckOfCards, players);
        game.distributeCards();*/
        //suspectOrAccuse();
        // makeSuspicion();
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
    public boolean dispatchTouchEvent (MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();

                break;

            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();

                if(x1 < x2){
                    startActivity(new Intent(GameboardScreen.this, NotepadScreen.class));
                }else if(x1 > x2){
                    showCards();
                }
                break;
        }
        return false;
    }
}
