package com.example.cluedo_seii.network.kryonet;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.GameCharacter;
import com.example.cluedo_seii.GameState;
import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.network.Callback;
import com.example.cluedo_seii.network.ClientData;
import com.example.cluedo_seii.network.NetworkGlobalHost;
import com.example.cluedo_seii.network.dto.AccusationMessageDTO;
import com.example.cluedo_seii.network.dto.BroadcastDTO;
import com.example.cluedo_seii.network.dto.CheatDTO;
import com.example.cluedo_seii.network.dto.GameCharacterDTO;
import com.example.cluedo_seii.network.dto.GameDTO;
import com.example.cluedo_seii.network.dto.NewGameRoomRequestDTO;
import com.example.cluedo_seii.network.dto.PlayerDTO;
import com.example.cluedo_seii.network.dto.RequestDTO;
import com.example.cluedo_seii.network.dto.SendToOnePlayerDTO;
import com.example.cluedo_seii.network.dto.SuspicionAnswerDTO;
import com.example.cluedo_seii.network.dto.SuspicionDTO;
import com.example.cluedo_seii.network.dto.UserNameRequestDTO;
import com.example.cluedo_seii.network.dto.WinDTO;


import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import static android.content.ContentValues.TAG;

public class GlobalNetworkHostKryo implements NetworkGlobalHost, KryoNetComponent {

    //INSTANCE
    private static GlobalNetworkHostKryo INSTANCE = null;


    private Client client;
    private Callback<NewGameRoomRequestDTO> newGameRoomCallback;
    private Callback<LinkedHashMap<Integer,ClientData>> newClientCallback;
    private Callback<GameCharacterDTO> gameCharacterDTOCallback;
    private Callback<CheatDTO> cheatDTOCallback;
    private Callback<WinDTO> winnerCallback;

    private Player cheater;
    private int cheated=0;
    public int getCheated(){
        return cheated;
    }
    public void setCheated(int value){
        this.cheated+=value;
    }
    public void guessedCheater(){this.cheated-=1;}

    private ChangeListener changeListener;

    private LinkedHashMap<Integer, ClientData> clientList;
    private ClientData roomHost;


    //Singleton Pattern
    private GlobalNetworkHostKryo() {
        roomHost = new ClientData();
        client = new Client(16384,4096);
        clientList = new LinkedHashMap<>();
    }


    public static GlobalNetworkHostKryo getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GlobalNetworkHostKryo();
        }

        return INSTANCE;
    }

    public static void deleteInstance() {
        INSTANCE = null;
    }


    //Mit Server Verbinden
    @Override
    public void connect(final String host) throws IOException {
        Log.d("GLOBALHOST:", "Connecting to: " + host);
        client.start();



        new Thread("Connection") {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                try {
                    client.connect(5000,host,NetworkConstants.SERVER_TCP_PORT,NetworkConstants.SERVER_UDP_PORT);

                } catch (IOException e) {
                    Log.e(TAG, "run: ", e);
                }
            }
        }.start();

        client.addListener(new Listener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof RequestDTO) {
                    Log.i(TAG, "received: " + object.toString());
                    handleRequest(object);
                }
            }
        });
    }

    //Handler für Requests
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleRequest(Object object) {
        if (object instanceof NewGameRoomRequestDTO) {
            handleGameRoomResponse((NewGameRoomRequestDTO) object);
        } else if (object instanceof UserNameRequestDTO) {
            handleUsernameRequest((UserNameRequestDTO) object);
        } else if (object instanceof SendToOnePlayerDTO) {
            handleSendToOnePlayeDTO((SendToOnePlayerDTO) object);
        } else if (object instanceof BroadcastDTO) {
            handleBroadcast((BroadcastDTO) object);
        }
    }

    //Handler für requests die nur an den Host gehen
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleSendToOnePlayeDTO(SendToOnePlayerDTO sendToOnePlayerDTO) {
        try {
            Object object = SerializationHelper.fromString(sendToOnePlayerDTO.getSerializedObject());

            if (object instanceof GameCharacterDTO) {
                handleGameCharacterRequest((GameCharacterDTO) object, sendToOnePlayerDTO.getSendingPlayerID());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //handler für requests die an den game Room gebroadcasted werden
    private void handleGameRoomResponse(NewGameRoomRequestDTO newGameRoomRequestDTO) {
        Log.d(TAG,"new Room created:" + newGameRoomRequestDTO.getCreatedRoom());
        roomHost.setId(newGameRoomRequestDTO.getHostID());
        if (newGameRoomCallback != null) {
            newGameRoomCallback.callback(newGameRoomRequestDTO);
        }

    }

    private void handleUsernameRequest(UserNameRequestDTO userNameRequestDTO) {
        Log.d(TAG, "New User joined: " + userNameRequestDTO.getUsername());
        ClientData clientData = new ClientData();
        clientData.setId(userNameRequestDTO.getId());
        clientData.setUsername(userNameRequestDTO.getUsername());

        clientList.put(clientData.getId(),clientData);

        if (newClientCallback != null) {
            newClientCallback.callback(clientList);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleGameCharacterRequest(GameCharacterDTO gameCharacterDTO, int playerID) {
        // remove the chosen Player from the list
        GameCharacter chosenCharacter = gameCharacterDTO.getChoosenPlayer();
        gameCharacterDTO.getAvailablePlayers().remove(chosenCharacter.getName());

        ClientData chosenClient = clientList.get(playerID);
        Player player = new Player(chosenClient.getId(),chosenCharacter);
        player.setUsername(chosenClient.getUsername());
        chosenClient.setPlayer(player);

        LinkedList<Player> playerLinkedList = Game.getInstance().getPlayers();
        if (playerLinkedList == null) playerLinkedList = new LinkedList<>();
        playerLinkedList.add(player);
        Game.getInstance().setPlayers(playerLinkedList);

        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setPlayer(player);
        sendMessageToClient(playerDTO, playerID);


        //broadcast remaining Characters to the other clients
        gameCharacterDTO.setChoosenPlayer(null);
        broadcastToClients(gameCharacterDTO);

        // fire callback
        if (gameCharacterDTOCallback != null) {
            gameCharacterDTOCallback.callback(gameCharacterDTO);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleBroadcast(BroadcastDTO broadcastDTO) {
        try {
            Object object = SerializationHelper.fromString(broadcastDTO.getSerializedObject());

            if (object instanceof GameDTO) {
                handleGameRequest((GameDTO) object);
            } else if(object instanceof CheatDTO) {
                handleCheaterRequest((CheatDTO) object);
            } else if(object instanceof AccusationMessageDTO){
                handleAccusationMessageDTO((AccusationMessageDTO)object);
            } else if(object instanceof SuspicionDTO){
                handleSuspicionMessageDTO((SuspicionDTO)object);
            } else if(object instanceof SuspicionAnswerDTO){
                handleSuspicionAnswerDTO((SuspicionAnswerDTO)object);
            } else if (object instanceof WinDTO) {
                handleWinDTORequest((WinDTO) object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleGameRequest( GameDTO gameDTO) {
        //Game Attribute aktualisieren
        Game inGame = gameDTO.getGame();

        Game game = Game.getInstance();

        game.setPlayers(inGame.getPlayers());
        game.setCurrentPlayer(inGame.getCurrentPlayer());
        game.setRound(inGame.getRound());
        game.setGameOver(inGame.getGameOver());
        game.setPlayerIterator(inGame.getPlayerIterator());
        game.setWrongAccusers(inGame.getWrongAccusers());

        game.changeGameState(inGame.getGameState());

    }

    private void handleCheaterRequest(CheatDTO cheatDTO){
        Log.d("network-Server:","Received a cheater");
        cheater = new Player(cheatDTO.getCheater().getId(), cheatDTO.getCheater().getPlayerCharacter());
        if(cheatDTOCallback!=null){
            cheatDTOCallback.callback(cheatDTO);
        }
    }

    private void handleAccusationMessageDTO(AccusationMessageDTO accusationMessageDTO){
        AccusationMessageDTO accusationMessage = accusationMessageDTO;
        Game game = Game.getInstance();
        game.setMessageForLocalPlayer(accusationMessage.getMessage());
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
        if(game.getCurrentPlayer().getId()==game.getLocalPlayer().getId()){
            game.setSuspicionAnswer(suspicionAnswerDTO.getAnswer());
            game.changeGameState(GameState.RECEIVINGANSWER);}
    }

    private void handleWinDTORequest(WinDTO winDTO) {
        Game.getInstance().setWinner(winDTO.getWinner());
        winnerCallback.callback(winDTO);
    }



    public void registerReceivedGameRoomCallback(Callback<NewGameRoomRequestDTO> callback) {
        this.newGameRoomCallback = callback;
    }

    public void registerNewClientCallback(Callback<LinkedHashMap<Integer, ClientData>> callback) {
        this.newClientCallback = callback;
    }

    public void registerCharacterDTOCallback(Callback<GameCharacterDTO> gameCharacterDTOCallback) {
        this.gameCharacterDTOCallback = gameCharacterDTOCallback;
    }

    public void registerCheatDTOCallback(Callback<CheatDTO> cheatDTOCallback){
        this.cheatDTOCallback = cheatDTOCallback;
    }

    public void registerWinDTOCallback(Callback<WinDTO> winDTOCallback) {
        this.winnerCallback = winDTOCallback;
    }

    public void sendCheat(Player player){
        CheatDTO cheatDTO = new CheatDTO();
        cheatDTO.setCheater(player);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            broadcastToClients(cheatDTO);
        }
        cheatDTOCallback.callback(cheatDTO);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendMessageToClient(RequestDTO requestDTO, int playerID) {
        Log.d("Sending Object to Plyr:", requestDTO.getClass().toString());
        SendToOnePlayerDTO sendToOnePlayerDTO = new SendToOnePlayerDTO();
        sendToOnePlayerDTO.setReceivingPlayerID(playerID);

        try {
            sendToOnePlayerDTO.setSerializedObject(SerializationHelper.toString(requestDTO));
        } catch (Exception e) {
            e.printStackTrace();
        }

        sendMessage(sendToOnePlayerDTO);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void broadcastToClients(RequestDTO requestDTO) {
        Log.d("Broadcasting Object:", requestDTO.getClass().toString());
        BroadcastDTO broadcast = new BroadcastDTO();
        try {
            broadcast.setSerializedObject(SerializationHelper.toString(requestDTO));
        } catch (Exception e) {
            e.printStackTrace();
        }

        sendMessage(broadcast);
    }

    public void sendMessage(final RequestDTO requestDTO) {
        new Thread("send") {
            @Override
            public void run() {
                Log.d("Sending Object:", requestDTO.getClass().toString());
                client.sendTCP(requestDTO);
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

    @Override
    public void sendGame(Game game) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setGame(game);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            broadcastToClients(gameDTO);
        }
    }

    public LinkedHashMap<Integer, ClientData> getClientList() {
        return clientList;
    }

    public void setRoomHostUsername(String username) {
        roomHost.setUsername(username);
    }

    public void setRoomHostPlayer(Player player) {
        roomHost.setPlayer(player);
    }

    public ClientData getRoomHost() {
        return roomHost;
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

