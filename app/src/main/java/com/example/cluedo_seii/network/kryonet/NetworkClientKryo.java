package com.example.cluedo_seii.network.kryonet;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.GameState;
import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.network.Callback;
import com.example.cluedo_seii.network.NetworkClient;
import com.example.cluedo_seii.network.dto.CheatDTO;
import com.example.cluedo_seii.network.connectionType;
import com.example.cluedo_seii.network.dto.AccusationMessageDTO;
import com.example.cluedo_seii.network.dto.BroadcastDTO;
import com.example.cluedo_seii.network.dto.ConnectedDTO;
import com.example.cluedo_seii.network.dto.GameCharacterDTO;
import com.example.cluedo_seii.network.dto.GameDTO;
import com.example.cluedo_seii.network.dto.PlayerDTO;
import com.example.cluedo_seii.network.dto.RequestDTO;
import com.example.cluedo_seii.network.dto.RoomsDTO;
import com.example.cluedo_seii.network.dto.SendToOnePlayerDTO;
import com.example.cluedo_seii.network.dto.SuspicionAnswerDTO;
import com.example.cluedo_seii.network.dto.SuspicionDTO;
import com.example.cluedo_seii.network.dto.UserNameRequestDTO;

import java.io.IOException;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class NetworkClientKryo implements NetworkClient, KryoNetComponent {
    //INSTANCE
    private static NetworkClientKryo INSTANCE = null;

    private Client client;
    private Callback<RequestDTO> callback;
    private Callback<ConnectedDTO> connectionCallback;
    private Callback<GameCharacterDTO> characterCallback;
    private Callback<GameDTO> gameCallback;
    private Callback<CheatDTO> cheatCallback;
    private Callback<RoomsDTO> roomCallback;
    private Player cheater;
    private ChangeListener changeListener;


    private boolean isConnected;
    private int cheated=0;

    private NetworkClientKryo() {
        client = new Client(8192,4096);
    }
    public int getCheated(){
        return cheated;
    }
    public void setCheated(int value){
        this.cheated+=value;
    }
    public void guessedCheater(){this.cheated-=1;}


    public static NetworkClientKryo getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NetworkClientKryo();
        }
        return INSTANCE;
    }

    public static void deleteInstance() {
        INSTANCE = null;
    }


    /**
     * beim Aufruf dieser Methode verbindet sich der Client mit dem Host
     * @param host hostIP address
     * @throws IOException
     */
    @Override
    public void connect(final String host) throws IOException {
        client.start();



        new Thread("Connection") {
            @Override
            public void run() {
                try {
                    if (SelectedConType.getConnectionType() == connectionType.CLIENT) {
                        client.connect(5000,host,NetworkConstants.TCP_PORT,NetworkConstants.UDP_PORT);
                    } else {
                        client.connect(5000,host,NetworkConstants.SERVER_TCP_PORT,NetworkConstants.SERVER_UDP_PORT);
                    }



                    //ConnectedDDTO wird nur gesendet wann es sich um ein locales Spiel handelt
                    if  (SelectedConType.getConnectionType() == connectionType.CLIENT) {
                        Log.d("Connecting: ", "test");
                        ConnectedDTO connectedDTO = new ConnectedDTO();
                        connectedDTO.setConnected(true);
                        sendMessage(connectedDTO);
                    }


                    isConnected = true;
                } catch (IOException e) {
                    Log.e(TAG, "run: ", e);
                }
            }
        }.start();

        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof RequestDTO) {
                    Log.i(TAG, "received: " + object.toString());
                    handleRequest(connection,object);
                }
            }
        });
    }

    private void handleRequest(Connection connection, Object object) {
        if (object instanceof ConnectedDTO) {
            handleConnectionResponse((ConnectedDTO) object);
        } else if (object instanceof GameCharacterDTO) {
            handleGameCharacterResponse((GameCharacterDTO) object);
        } else if (object instanceof  PlayerDTO) {
            handlePlayerResponse((PlayerDTO) object);
        } else if (object instanceof GameDTO) {
            handleGameResponse((GameDTO) object);
        } else if (object instanceof CheatDTO) {
            handleCheatResponse((CheatDTO) object);
        } else if (object instanceof RoomsDTO) {
            handleRoomsResponse((RoomsDTO) object);
        } else if (object instanceof BroadcastDTO) {
            // Server Version only works, if the API Version is higher than 26
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                handleBroadcastResponse(connection, (BroadcastDTO) object);
            }
        } else if (object instanceof SendToOnePlayerDTO) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                handleSendToOnePlayerDTOResponse(connection, (SendToOnePlayerDTO) object);
            }
        }
        else if(object instanceof AccusationMessageDTO){
            handleAccusationMessageDTO((AccusationMessageDTO)object);
        }
        else if(object instanceof SuspicionDTO){
            handleSuspicionMessageDTO((SuspicionDTO)object);
        }
        else if(object instanceof SuspicionAnswerDTO){
            handleSuspicionAnswerDTO((SuspicionAnswerDTO)object);
        }
    }


    private void handleConnectionResponse(ConnectedDTO connectedDTO) {
        if (connectionCallback != null) {
            connectionCallback.callback(connectedDTO);
            // reset connection Callback
            connectionCallback = null;
        }
    }

    private void handleCheatResponse(CheatDTO cheatDTO) {
        Log.d("Player response","cheater entdeckt");
        cheater = new Player(cheatDTO.getCheater().getId(),cheatDTO.getCheater().getPlayerCharacter());
        if (cheatCallback!= null){
            cheatCallback.callback(cheatDTO);
        }
    }

    private void handleGameCharacterResponse(GameCharacterDTO gameCharacterDTO) {
        if (characterCallback != null) {
            characterCallback.callback(gameCharacterDTO);
        }
    }

    private void handlePlayerResponse(PlayerDTO playerDTO) {
        // delete character Callback, because the client already choose his character
        // characterCallback = null;


        //set Player as LocalPlayer
        Game.getInstance().setLocalPlayer(playerDTO.getPlayer());
        Log.d("Player Response:", playerDTO.toString());
    }

    private void handleGameResponse(GameDTO gameDTO) {
        if(gameCallback!=null) {
            gameCallback.callback(gameDTO);
        }


        Game inGame = gameDTO.getGame();
        Game game = Game.getInstance();
        game.setPlayers(inGame.getPlayers());
        game.setCurrentPlayer(inGame.getCurrentPlayer());
        game.setRound(inGame.getRound());
        game.setGameOver(inGame.getGameOver());
        game.setPlayerIterator(inGame.getPlayerIterator());
        game.setInvestigationFile(inGame.getInvestigationFile());
        game.setWrongAccusers(inGame.getWrongAccusers());
        notifyPlayer();
        game.changeGameState(inGame.getGameState());

    }

    private void handleRoomsResponse(RoomsDTO roomsDTO) {
        roomCallback.callback(roomsDTO);
    }

    private void handleAccusationMessageDTO(AccusationMessageDTO accusationMessageDTO){
        Game game = Game.getInstance();
        game.setMessageForLocalPlayer(accusationMessageDTO.getMessage());
        game.changeGameState(GameState.PASSIVE);
    }

    private void handleSuspicionMessageDTO(SuspicionDTO suspicionDTO){
        Game game = Game.getInstance();
        Player suspected=suspicionDTO.getAcusee();
        if(game.getLocalPlayer().getId()==suspected.getId()){
            for(Player player: game.getPlayers()){
                if(game.getLocalPlayer().getId()== player.getId()){
                    player.setPosition(suspicionDTO.getAccuser().getPosition());
                }
            }
            game.setSuspicion(suspicionDTO.getSuspicion());
            game.setAcusee(suspicionDTO.getAccuser());
            notifyPlayer();
            game.changeGameState(GameState.SUSPECTED);
        }
    }

    private void handleSuspicionAnswerDTO(SuspicionAnswerDTO suspicionAnswerDTO){

        Game game = Game.getInstance();
        if(game.getLocalPlayer().getId()==game.getCurrentPlayer().getId()){
        game.setSuspicionAnswer(suspicionAnswerDTO.getAnswer());
        notifyPlayer();
        game.changeGameState(GameState.RECEIVINGANSWER);}
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleBroadcastResponse(Connection connection, BroadcastDTO broadcastDTO) {
        try {
            Object object = SerializationHelper.fromString(broadcastDTO.getSerializedObject());
            handleRequest(connection,object);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleSendToOnePlayerDTOResponse(Connection connection, SendToOnePlayerDTO sendToOnePlayerDTO) {
        try {
            Object object = SerializationHelper.fromString(sendToOnePlayerDTO.getSerializedObject());
            handleRequest(connection, object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void registerConnectionCallback(Callback<ConnectedDTO> callback) {
        this.connectionCallback = callback;
    }

    @Override
    public void registerCharacterCallback(Callback<GameCharacterDTO> callback) {
        this.characterCallback = null;
        this.characterCallback = callback;
    }

    public void registerGameCallback(Callback<GameDTO> callback) {
        this.gameCallback = callback;
    }

    public void registerCheatCallback(Callback<CheatDTO> callback){this.cheatCallback = callback;}

    public void registerRoomCallback(Callback<RoomsDTO> callback) {
        this.roomCallback = null;
        this.roomCallback = callback;
    }

    public void sendGame(Game game) {
        Log.i("sendingGame", "GameSend");
        GameDTO gameDTO = new GameDTO();
        gameDTO.setGame(game);

        if (SelectedConType.getConnectionType() == connectionType.CLIENT) {
            sendMessage(gameDTO);
        } else {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    BroadcastDTO broadcastDTO = new BroadcastDTO();
                    broadcastDTO.setSerializedObject(SerializationHelper.toString(gameDTO));

                    sendMessage(broadcastDTO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendCheat(Player player) {
        CheatDTO cheatDTO = new CheatDTO();
        cheatDTO.setCheater(player);
        if (SelectedConType.getConnectionType() == connectionType.CLIENT) {
            sendMessage(cheatDTO);
        } else {
            broadcastToGameRoom(cheatDTO);
        }

    }

    public void broadcastToGameRoom(RequestDTO requestDTO) {
        BroadcastDTO broadcastDTO = new BroadcastDTO();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                broadcastDTO.setSerializedObject(SerializationHelper.toString(requestDTO));

                sendMessage(broadcastDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageToRoomHost(RequestDTO requestDTO) {
        SendToOnePlayerDTO sendToOnePlayerDTO = new SendToOnePlayerDTO();
        Log.d("Sending Object to Host:", requestDTO.getClass().toString());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                sendToOnePlayerDTO.setSerializedObject(SerializationHelper.toString(requestDTO));
                sendToOnePlayerDTO.setToHost(true);

                sendMessage(sendToOnePlayerDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendMessage(final RequestDTO message) {
        new Thread("send") {
            @Override
            public void run() {
                Log.d("Sending Object:", message.getClass().toString());
                client.sendTCP(message);
            }
        }.start();

    }

    @Override
    public void registerClass(Class c) {
        client.getKryo().register(c);
    }

    public void registerClass(Class c, int id) {
        client.getKryo().register(c,id);
    }

    public void sendUsernameRequest(final UserNameRequestDTO userNameRequestDTO) {
        Log.d("UserNameRequest", userNameRequestDTO.getUsername());
        sendMessage(userNameRequestDTO);
    }

    private void quitGameHandler() {
        client.close();
        client.stop();
    }


    public Player getCheater() {
        return cheater;
    }
    public void notifyPlayer(){
        if(changeListener != null) changeListener.onChange();
    }

    public ChangeListener getListener() {
        return changeListener;
    }

    public void setListener(ChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public interface ChangeListener {
        void onChange();
    }
}
