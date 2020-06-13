package com.example.cluedo_seii.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cluedo_seii.Card;
import com.example.cluedo_seii.CardType;
import com.example.cluedo_seii.DeckOfCards;
import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.GameCharacter;
import com.example.cluedo_seii.GameState;
import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.R;
//import com.example.cluedo_seii.activities.playerGameInteraction.AccuseSomeone;
//import com.example.cluedo_seii.activities.playerGameInteraction.MakeSuspicion;
import com.example.cluedo_seii.activities.playerGameInteraction.AccuseSomeone;
import com.example.cluedo_seii.activities.playerGameInteraction.NotifyPlayerWon;
import com.example.cluedo_seii.activities.playerGameInteraction.PlayerTurnNotification;
import com.example.cluedo_seii.activities.playerGameInteraction.SuspectOrAccuse;
import com.example.cluedo_seii.activities.playerGameInteraction.MakeSuspicion;
import com.example.cluedo_seii.activities.playerGameInteraction.SuspicionShowCard;
import com.example.cluedo_seii.activities.playerGameInteraction.ThrowDice;
import com.example.cluedo_seii.activities.playerGameInteraction.ThrowDiceOrUseSecretPassage;
import com.example.cluedo_seii.network.Callback;
import com.example.cluedo_seii.network.dto.CheatDTO;
import com.example.cluedo_seii.network.connectionType;
import com.example.cluedo_seii.network.kryonet.NetworkClientKryo;
import com.example.cluedo_seii.network.kryonet.NetworkServerKryo;
import com.example.cluedo_seii.network.kryonet.SelectedConType;
import com.example.cluedo_seii.network.dto.SuspicionAnswerDTO;
import com.example.cluedo_seii.spielbrett.Gameboard;
import com.example.cluedo_seii.spielbrett.StartingPoint;

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
    private LinkedList<Card> suspicionCards;
    private connectionType conType;
    private NetworkServerKryo server;
    private NetworkClientKryo client;
    private Player currentPlayerInDoor;// TODO: Aufräumen und vielleicht nur mehr das Player Objekt anstatt id und Player Objekt
    private int playerCurrentlyPlayingId;
    static final int MIN_SWIPE_DISTANCE = 150;
    private int diceValueOne = 2, diceValueTwo = 2;
    private Handler messageHandler;
    private Handler mainThreadHandler;
    private Toast toast;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spielbrett_screen);
        game = Game.getInstance();
        initializeGameboard();
        initializeNetwork();
        setChangeGameStateChangeListener();

        //zu Demonstrationszwecken SpielerPosition wird gesetzt auf Raum
        for(Player player: game.getPlayers()){
         player.setPosition(new Point(6,2));
        Log.i("gameCharacter", player.getId() + "" + player.getPlayerCharacter().getName());}
         kickOffGame();
         Log.i("gameStarted", "gameCreated");
        for(Card card: game.getPlayers().get(1).getPlayerCards()){
            Log.i("Card", card.getDesignation());
        }
            }

     /////////////////////////////////////
    //Methoden zur Spielinitialisierung//
    /////////////////////////////////////

    //Spielbrettinitialisierung
    public void initializeGameboard(){

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
            J = Musikzimmer1
            K = Musikzimmer3
            L = Musikzimmer5
            M = Musikzimmer6
            N = Musikzimmer7
            O = Musikzimmer8
            P = Musikzimmer9
            Q = Musikzimmer10
            R = Musikzimmer11
            T = Musikzimmer12
            S = Salon1
            U = Salon2
            V = Salon3
            W = Salon4
            X = Salon5
            Y = Salon6
            Z = Kueche1
            ! = Kueche2
            ä = Kueche3
            ö = Kueche4
            ü = Kueche5
            $ = Kueche6
            % = Kueche7
            & = Kueche8
            / = Kueche9
            ( = Kueche10
            ) = Kueche11
            + = Kueche12
            - = Kueche13
            * = Speisezimmer2
            . = Speisezimmer3
            , = Speisezimmer4
            # = Speisezimmer5
            : = Speisezimmer6
            ; = Speisezimmer7
            < = Speisezimmer8
            > = Speisezimmer9
            = = Speisezimmer10 --frei!
            @ = Speisezimmer11
            [ = Speisezimmer12
            ] = Speisezimmer13
            ^ = Speisezimmer 14
            = = Veranda1
            _ = Veranda2
            { = Veranda3
            } = Veranda4
            ~ = Veranda5
            € = Veranda6
            Ü = Veranda7
            Ö = Veranda8
            Ä = Veranda9




         */

        String gameBoard =
                "cdef04562=_{}" +
                "ghij07890~€ÜÖ" +
                "Akk30a3b03ÄÄA" +
                "2000000000000" +
                "lmt0000000000" +
                "opq3000000002" +
                "r3u0000000000" +
                "0000000000002" +
                "000000003*.,#" +
                "v3wxy0003:;<>" +
                "zBCD30000@[]^" +
                "EFGHI00000000" +
                "0000000000000" +
                "JAK30000Z3!äö" +
                "LMNO0000ü$%&-" +
                "PQRT0000A/()+" +
                "0000000000000" +
                "00003SU300000" +
                "0000VWXY00000" +
                "0020000002000";

        // Init Starting Points
        startingPoints = new ArrayList<>();
        startingPoints.add(new StartingPoint(0, 0));
        startingPoints.add(new StartingPoint(2, 1));

        // Init Player Ids and PlayerMove-Array
        int countPlayerIds = 0;
        playerMove = new ArrayList<>();

        gameboard = new Gameboard(this,13,20, gameBoard);
        setContentView(gameboard.getLayout());

        bundle = new Bundle();
        mesaggeDialogTag = "MessageDialog";
        //

        //TODO delete
       //startGame();

        gameboard.spawnPlayer(startingPoints, this);

        for(StartingPoint startingPoint: startingPoints) {
            Log.i("Test",
                    "StartingPoint Position: " + startingPoint.getPlayerPosition().x + ":"
                            + startingPoint.getPlayerPosition().y);
            GameCharacter gameCharacter = new GameCharacter("Player_"+countPlayerIds, startingPoint.getPlayerPosition());
            playerMove.add(
                    new Player(countPlayerIds++, gameCharacter)
            );
        }

        // Wenn sich die Id ändert, dann danach updateGameboardScreen machen so wie hier!
        playerCurrentlyPlayingId = 0;
        gameboard.updateGameboardScreen(this);
        server = NetworkServerKryo.getInstance();
        client= NetworkClientKryo.getInstance();
        client.registerCheatCallback(new Callback<CheatDTO>() {
            @Override
            public void callback(CheatDTO argument) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast toast;
                        toast = Toast.makeText(getApplicationContext(),"Jemand hat geschummelt", Toast.LENGTH_LONG);
                        toast.show();

                    }
                });

            }
        });
        server.registerCheatDTOCallback(new Callback<CheatDTO>() {
            @Override
            public void callback(CheatDTO argument) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast toast;
                        toast = Toast.makeText(getApplicationContext(),"Jemand hat geschummelt", Toast.LENGTH_LONG);
                        toast.show();

                    }
                });

            }
        });
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

    public Player getCurrentPlayerInDoor() {
        return currentPlayerInDoor;
    }

    public void setCurrentPlayerInDoor(Player currentPlayerInDoor) {
        this.currentPlayerInDoor = currentPlayerInDoor;
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //ChangeListener - Code wird ausgeführt wenn game.changeGameState()ausgeführt wird//
    ////////////////////////////////////////////////////////////////////////////////////

    public void setChangeGameStateChangeListener(){


        game.setListener(new Game.ChangeListener() {
            @Override
            //Wird ausgeführt wenn Methode changeGameState aufgerufen wird
            public void onChange() {


                Log.i("currentPlayer", game.getCurrentPlayer().getId()+"");
                Log.i("currentGameState", game.getGameState().toString());


                //Ausgeführt bei GameState.PLAYERTURNBEGIN)
                if(game.getGameState().equals(GameState.PLAYERTURNBEGIN) ){
                    //Überprüfung ob der am Gerät lokal gespeicherte Spieler sich am Zug befindet
                    if(game.getCurrentPlayer().getId()==game.getLocalPlayer().getId()){
                        turnBegin();
                    }
                    else{//Spieler localPlayer ist nicht am Zug
                        game.setMessageForLocalPlayer( "Spieler " + game.getCurrentPlayer().getId() + " ist am Zug" );
                        showToast(game.getMessageForLocalPlayer(), 1000);
                    }
                }

                //Ausgefürt bei GameState.PLAVERMOVEMENT
                else if(game.getGameState().equals(GameState.PLAVERMOVEMENT)){
                    if(game.getCurrentPlayer().getId()==game.getLocalPlayer().getId()){
                        int playerX = game.getCurrentPlayer().getPosition().x;
                        int playerY = game.getCurrentPlayer().getPosition().y;
                        //Prüfe ob Spieler sich in einen Raum befindet
                        if (playerX == 3 && playerY==2  ||
                                playerX == 6 && playerY==2  ||
                                playerX == 9 && playerY==2  ||
                                playerX == 3 && playerY==5  ||
                                playerX == 1 && playerY==6  ||
                                playerX == 1 && playerY==9  ||
                                playerX == 4 && playerY==10 ||
                                playerX == 3 && playerY==13 ||
                                playerX == 4 && playerY==17 ||
                                playerX == 7 && playerY==17 ||
                                playerX == 9 && playerY==13 ||
                                playerX == 8 && playerY==9  ||
                                playerX == 8 && playerY==8){
                            throwDiceOrUseSecretPassage();
                        }
                        else{
                            throwDice();
                        }
                    }
                }

                //Ausgeführt bei GameState.PLAYERACCUSATION
                else if(game.getGameState().equals(GameState.PLAYERACCUSATION)){
                    if(game.getCurrentPlayer().getId()==game.getLocalPlayer().getId()){
                        int playerX = game.getCurrentPlayer().getPosition().x;
                        int playerY = game.getCurrentPlayer().getPosition().y;
                        if(game.getLocalPlayer().getMadeFalseAccusation()==false){
                        //Prüfe ob Spieler sich in einen Raum befindet
                        if(playerX == 3 && playerY==2  ||
                                playerX == 6 && playerY==2  ||
                                playerX == 9 && playerY==2  ||
                                playerX == 3 && playerY==5  ||
                                playerX == 1 && playerY==6  ||
                                playerX == 1 && playerY==9  ||
                                playerX == 4 && playerY==10 ||
                                playerX == 3 && playerY==13 ||
                                playerX == 4 && playerY==17 ||
                                playerX == 7 && playerY==17 ||
                                playerX == 9 && playerY==13 ||
                                playerX == 8 && playerY==9  ||
                                playerX == 8 && playerY==8){
                            suspectOrAccuse();}
                        } else{ //Wenn der sich am Zug befindende sich Spieler nicht in einen Raum befindet
                            game.changeGameState(GameState.PLAYERTURNEND);
                          }
                    }
                }

                //Ausgeführt bei GameState Suspected - wenn ein Spieler Ziel einer Verdächtigung ist
                else if(game.getGameState().equals(GameState.SUSPECTED)){
                    suspicionShowCard(game.getSuspicion());
                }

                //Ausgeführt bei Gamestate.RECEIVINGANSWER - Antwort auf Verdachtäußerung
                else if(game.getGameState().equals(GameState.RECEIVINGANSWER)){
                  if(game.getSuspicionAnswer().getDesignation().equals("goAway")){
                      game.setMessageForLocalPlayer("Der Verdächtige Spieler hatte keine der Karten auf der Hand");
                      showToast(game.getMessageForLocalPlayer(), 1000);
                      game.changeGameState(GameState.PLAYERTURNEND);
                  }
                  else{
                    game.setMessageForLocalPlayer("Der verdächtigte Spieler hat folgende Karte auf der Hand: " + game.getSuspicionAnswer().getDesignation());
                    showToast(game.getMessageForLocalPlayer(), 1000);
                    game.changeGameState(GameState.PLAYERTURNEND);
                  }
                }

                //Ausgeführt bei GameState.PLAYERTURNEND
                else if(game.getGameState().equals(GameState.PLAYERTURNEND)) {
                    if (game.getCurrentPlayer().getId() == game.getLocalPlayer().getId()) {

                        //Prüfe ob Abbruchbedingungen zutreffen
                        if(game.checkGameEnd()==true){
                            game.changeGameState(GameState.END);
                        }

                        //wenn Abbruchbedingungen nicht zutreffen
                        else{//nächster Spieler
                            game.nextPlayer();
                            game.changeGameState(GameState.PLAYERTURNBEGIN);
                            updateGame();
                        }
                    }
                }

                //Ausgeführt bei GameState.END - Ende des Spiels
                else if(game.getGameState().equals(GameState.END)){
                    updateGame();
                    finish();
                    // notifyPlayersWon();
                    // Intent intent = new Intent(GameboardScreen.this, MainActivity.class);
                    //  startActivity(intent);
                }

            }
        });
    }

    ////////////////////////////////////////////////////////
    //Aufruf von DialogOptionen für Spielerentscheidungen//
    ///////////////////////////////////////////////////////

    //Dialog für SpielerBenachrichtigung bei Rundenbeginn
    public void turnBegin() {
       /* manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.player_game_interaction_layout,new PlayerTurnNotification(), mesaggeDialogTag);
        transaction.addToBackStack(null);
        transaction.commit();*/


        PlayerTurnNotification dialog = new PlayerTurnNotification();
        manager = getSupportFragmentManager();
        dialog.show(manager, mesaggeDialogTag);
    }

    //Dialog Würfel werfen
    public void throwDice(){
        ThrowDice dialog = new ThrowDice();
        manager = getSupportFragmentManager();
        dialog.show(manager, mesaggeDialogTag);
    }

    //Dialog Würfel werfen oder Geheimgang verwenden
    public void throwDiceOrUseSecretPassage(){
        ThrowDiceOrUseSecretPassage dialog = new ThrowDiceOrUseSecretPassage();
        manager = getSupportFragmentManager();
        dialog.show(manager, mesaggeDialogTag);
    }

    //Dialog Anklagen oder Verdächtigen
    public void suspectOrAccuse(){
        SuspectOrAccuse dialog = new SuspectOrAccuse();
        manager = getSupportFragmentManager();
        dialog.show(manager, mesaggeDialogTag);
    }

    //Benachrichtigung bei Spielende
    public void notifyPlayersWon(){
        NotifyPlayerWon playerWon = new NotifyPlayerWon();
        manager = getSupportFragmentManager();
        playerWon.show(manager, mesaggeDialogTag);
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    //UI Aufruf von Würfeln, Verdächtigung, Anklage, Spielerhand, Kartenauswahl bei Verdacht//
    //Aufruf von Würfeln                                                                    //
    //////////////////////////////////////////////////////////////////////////////////////////

    //Aufruf von Würfeln
    public void rollDice(){
        startActivity(new Intent(this, RollDiceScreen.class));
    }

    //Aufruf Detektivnotizblock
    public void startNotepad(){
        intent = new Intent(this, NotepadScreen.class);
        //intent.putExtra("game",game);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    //Zeigt Karten auf Spielerhand
    public void showCards(){
        startActivity(new Intent(this, ShowCards.class));
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    //Aufruf von Verdächtigung
    public void makeSuspicion(){
        startActivity(new Intent(this, MakeSuspicion.class));
    }

    //Aufruf von Anklage
    public void accuseSomeone(){startActivity(new Intent(this, AccuseSomeone.class));}


    //Zeigt Kartenauswahl auf Spielerhand bei Verdacht
    public void suspicionShowCard(LinkedList<Card>suspicion){

        //Im Falle dessen, das der Spieler eine der VerdachtsKarten auf der Hand hat
        if(checkSuspicionCard(suspicion).size()>0) {
            SuspicionShowCard dialog = new SuspicionShowCard();

            manager = getSupportFragmentManager();
            dialog.show(manager, mesaggeDialogTag);
        }

        //Falls der Verdächtigte Spieler keine VerdachtsKarte auf der Hand hat
        else{
            game.setMessageForLocalPlayer("Du wurdest verdächtigt, hast aber keine der Karten, die Teil deines Verdachts sind, auf deiner Hand.");
            showToast(game.getMessageForLocalPlayer(), 1000);
            SuspicionAnswerDTO suspicionAnswerDTO = new SuspicionAnswerDTO();
            Card nothingAnswer = new Card(64, "goAway", CardType.ROOM);
            suspicionAnswerDTO.setAnswer(nothingAnswer);
            sendSuspicionAnswer(suspicionAnswerDTO);
        }
    }

    //Prüft ob Eine der Verdachtskarten sich auf der Spielerhand befindet
    public List<Card> checkSuspicionCard(List<Card> cards){
        LinkedList<Card>localPlayerSuspCards = new LinkedList<>();
        for(Card cardSusp: cards){
            for(Card cardLocal: game.getLocalPlayer().getPlayerCards()){
                if(cardSusp.getDesignation().equals(cardLocal.getDesignation())){
                    localPlayerSuspCards.add(cardSusp);
                }
            }
        }
        suspicionCards = localPlayerSuspCards;
        return localPlayerSuspCards;
    }

    ///////////////////////////////////
    //Methoden für Netzwerkfunktionen//
    ///////////////////////////////////

    //Netzwerkinitialisierung
    public void initializeNetwork(){
        conType = SelectedConType.getConnectionType();
        if(conType==connectionType.HOST) {
            server = NetworkServerKryo.getInstance();
        }

        else if(conType==connectionType.CLIENT){
            client = NetworkClientKryo.getInstance();
        }
    }

    //Spielverschicken über Netzwerk
    public void updateGame( ){
        //TODO add if for globalhost and global Client
        if(conType==connectionType.HOST) {
            server.sendGame(game);
        }
        else if(conType==connectionType.CLIENT){
            client.sendGame(game);
        }
    }

    //Antwort auf Verdacht
    public void sendSuspicionAnswer(SuspicionAnswerDTO suspicionAnswerDTO){
        if(conType==connectionType.HOST) {
            server.broadcastMessage(suspicionAnswerDTO);
        }
        else if(conType==connectionType.CLIENT){
            client.sendMessage(suspicionAnswerDTO);
        }
    }

    //KickoffMethode für Spielstart
    public void kickOffGame(){
        if(conType==connectionType.HOST){
            game.changeGameState(GameState.PLAYERTURNBEGIN);
        }
        if(conType==connectionType.CLIENT){
            game.changeGameState(GameState.PLAYERTURNBEGIN);
            for(Player player: game.getPlayers()){
                if(player.getId() == game.getLocalPlayer().getId()){
                    for(Card card: player.getPlayerCards()){
                        game.getLocalPlayer().addCard(card);
                    }
                }
            }
        }
    }

    //EventListener für Swipe-Event
    @Override
    public boolean onTouchEvent (MotionEvent touchEvent){
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

    //Handler zum Zeigen von ToastNachrichten

    public void showToast(final String message, final int duration) {
        getMainThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                if (message!=null) {
                    if(message!=null||message!="") {
                        toast = Toast.makeText(GameboardScreen.this, message, duration);
                    }
                    toast.show();
                }
                game.setMessageForLocalPlayer(null);
            }
        });
    }

    public Handler getMainThreadHandler() {
        if (mainThreadHandler == null) {
            mainThreadHandler = new Handler(Looper.getMainLooper());
        }
        return mainThreadHandler;
    }

    public int getDiceValueOne() {
        return diceValueOne;
    }

    public void setDiceValueOne(int diceValueOne) {
        this.diceValueOne = diceValueOne;
    }

    public int getDiceValueTwo() {
        return diceValueTwo;
    }

    public void setDiceValueTwo(int diceValueTwo) {
        this.diceValueTwo = diceValueTwo;
    }


}

