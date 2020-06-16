package com.example.cluedo_seii.network.kryonet;

import android.os.Handler;
import android.util.Log;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.GameCharacter;
import com.example.cluedo_seii.GameState;
import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.network.Callback;
import com.example.cluedo_seii.network.ClientData;
import com.example.cluedo_seii.network.NetworkServer;
import com.example.cluedo_seii.network.dto.CheatDTO;
import com.example.cluedo_seii.network.dto.ConnectedDTO;
import com.example.cluedo_seii.network.dto.GameCharacterDTO;
import com.example.cluedo_seii.network.dto.GameDTO;
import com.example.cluedo_seii.network.dto.PlayerDTO;
import com.example.cluedo_seii.network.dto.QuitGameDTO;
import com.example.cluedo_seii.network.dto.RequestDTO;
import com.example.cluedo_seii.network.dto.SuspicionAnswerDTO;
import com.example.cluedo_seii.network.dto.SuspicionDTO;
import com.example.cluedo_seii.network.dto.UserNameRequestDTO;
import com.example.cluedo_seii.network.dto.AccusationMessageDTO;
import com.example.cluedo_seii.network.dto.WinDTO;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class NetworkServerKryo implements KryoNetComponent, NetworkServer {
    //SINGLETON_INSTANCE
    private static NetworkServerKryo INSTANCE = null;

    private Server server;
    private Callback<LinkedHashMap<Integer, ClientData>> newClientCallback;
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

    private ClientData host;

    private NetworkServerKryo() {
        server = new Server(8192,4096);
        clientList = new LinkedHashMap<>();
        host = new ClientData();
        host.setId(1);
    }

    public static NetworkServerKryo getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NetworkServerKryo();
        }

        return INSTANCE;
    }

    public static void deleteInstance() {
        INSTANCE = null;
    }


    public void start() throws IOException {
        server.start();
        server.bind(NetworkConstants.TCP_PORT,NetworkConstants.UDP_PORT);

        server.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof RequestDTO)
                    handleRequest(connection, object);
            }
        });
    }

    private void handleRequest(Connection connection, Object object) {
        Log.d("Received Object:",object.getClass().toString());
        if (object instanceof ConnectedDTO) {
            Log.d("test", "ConnectedDTO");
            handleConnectedRequest(connection, (ConnectedDTO) object);
        } else if (object instanceof UserNameRequestDTO) {
            Log.d("test", "UserNameRequestDTO");
            handleUsernameRequest(connection, (UserNameRequestDTO) object);
        } else if (object instanceof GameCharacterDTO) {
            handleGameCharacterRequest(connection, (GameCharacterDTO) object);
        } else if (object instanceof GameDTO) {
            handleGameRequest(connection, (GameDTO) object);
        } else if(object instanceof CheatDTO) {
            handleCheaterRequest(connection, (CheatDTO) object);
        }
        else if(object instanceof AccusationMessageDTO){
            handleAccusationMessageDTO(connection, (AccusationMessageDTO)object);
        }
        else if(object instanceof SuspicionDTO){
            handleSuspicionMessageDTO(connection, (SuspicionDTO)object);
        }
        else if(object instanceof SuspicionAnswerDTO){
            handleSuspicionAnswerDTO(connection, (SuspicionAnswerDTO)object);
        }
        else if(object instanceof WinDTO) {
            handleWinRequest(connection, (WinDTO) object);
        }
    }

    private void handleConnectedRequest(Connection connection, ConnectedDTO connectedDTO) {
        Log.d("network-Server:", "Received Connected Request");
        sendMessageToClient(connectedDTO, connection);
    }

    private void handleCheaterRequest(Connection connection, CheatDTO cheatDTO){
        Log.d("network-Server:","Received a cheater");
        cheater = new Player(cheatDTO.getCheater().getId(), cheatDTO.getCheater().getPlayerCharacter());
        broadcastMessage(cheatDTO);
        if(cheatDTOCallback!=null){
            cheatDTOCallback.callback(cheatDTO);
        }
    }

    private void handleUsernameRequest(Connection connection, UserNameRequestDTO userNameRequestDTO) {
        Log.d("UserNameRequest", userNameRequestDTO.getUsername());
        ClientData client = new ClientData();
        client.setId();
        client.setConnection(connection);
        client.setUsername(userNameRequestDTO.getUsername());

        clientList.put(client.getId(), client);

        newClientCallback.callback(clientList);
    }

    private void handleGameCharacterRequest(Connection connection, GameCharacterDTO gameCharacterDTO) {
        //remove the chosen Player from the List
        GameCharacter chosenCharacter = gameCharacterDTO.getChoosenPlayer();
        gameCharacterDTO.getAvailablePlayers().remove(chosenCharacter.getName());

        //forward the created Player to the Client
        for (ClientData clientData: clientList.values()) {
            if (clientData.getConnection().equals(connection)) {
                Player player = new Player(clientData.getId(), chosenCharacter);
                player.setUsername(clientData.getUsername());
                clientData.setPlayer(player);

                //add Players to local Game Object
                 LinkedList<Player> playerLinkedList = Game.getInstance().getPlayers();
                if (playerLinkedList == null) {
                    playerLinkedList = new LinkedList<>();
                }
                 playerLinkedList.add(player);
                 Game.getInstance().setPlayers(playerLinkedList);

                PlayerDTO playerDTO = new PlayerDTO();
                playerDTO.setPlayer(player);
                sendMessageToClient(playerDTO, connection);
            }
        }

        // forward the remaining characters to the other Clients
        gameCharacterDTO.setChoosenPlayer(null);
        broadcastMessage(gameCharacterDTO);
        // fire callback
        if (gameCharacterDTOCallback != null) {
            gameCharacterDTOCallback.callback(gameCharacterDTO);
        }
    }

    private void handleGameRequest(Connection connection, GameDTO gameDTO) {
        Log.i("receivingGame", gameDTO.getGame().getCurrentPlayer().getId()+ "");
        broadcastMessageWithoutSender(gameDTO,connection);
        Game inGame = gameDTO.getGame();
        Game game = Game.getInstance();
        game.setPlayers(inGame.getPlayers());
        game.setCurrentPlayer(inGame.getCurrentPlayer());
        game.setRound(inGame.getRound());
        game.setGameOver(inGame.getGameOver());
        game.setPlayerIterator(inGame.getPlayerIterator());
        game.setInvestigationFile(inGame.getInvestigationFile());
        game.setWrongAccusers(inGame.getWrongAccusers());
        game.setGameboard(inGame.getGameboard());
        game.changeGameState(inGame.getGameState());
    }

    private void handleAccusationMessageDTO(Connection connection, AccusationMessageDTO accusationMessageDTO){
        broadcastMessageWithoutSender(accusationMessageDTO, connection);
        AccusationMessageDTO accusationMessage = accusationMessageDTO;
        Game game = Game.getInstance();
        game.setMessageForLocalPlayer(accusationMessage.getMessage());
        game.changeGameState(GameState.PASSIVE);
    }

    private void handleSuspicionMessageDTO(Connection connection, SuspicionDTO suspicionDTO){
        broadcastMessageWithoutSender(suspicionDTO, connection);
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

    private void handleSuspicionAnswerDTO(Connection connection, SuspicionAnswerDTO suspicionAnswerDTO){
        broadcastMessageWithoutSender(suspicionAnswerDTO, connection);
        Game game = Game.getInstance();
        if(game.getCurrentPlayer().getId()==game.getLocalPlayer().getId()){
        game.setSuspicionAnswer(suspicionAnswerDTO.getAnswer());
        game.changeGameState(GameState.RECEIVINGANSWER);}
    }

    private void handleWinRequest(Connection connection, WinDTO winDTO) {
        broadcastMessageWithoutSender(winDTO,connection);
        Game.getInstance().setWinner(winDTO.getWinner());
        winnerCallback.callback(winDTO);
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

    public void sendGame(Game game) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setGame(game);
        broadcastMessage(gameDTO);
    }

    public void sendCheat(Player player){
        CheatDTO cheatDTO = new CheatDTO();
        cheatDTO.setCheater(player);
        broadcastMessage(cheatDTO);
        cheatDTOCallback.callback(cheatDTO);
    }

    @Override
    public void broadcastMessage(RequestDTO message) {
        for (Connection connection: server.getConnections()) {
            sendMessageToClient(message, connection);
        }
    }

    public void broadcastMessageWithoutSender(RequestDTO message, Connection clientSenderConnection) {
        for (Connection connection: server.getConnections()) {
            if (connection != clientSenderConnection) sendMessageToClient(message, connection);
        }
    }

    private void sendMessageToClient(final RequestDTO message, final Connection connection) {
        Log.d(TAG, "sendMessageToClient: " + message.toString());

        new Thread("send") {
            @Override
            public void run() {
                connection.sendTCP(message);
            }
        }.start();
    }

    @Override
    public void registerClass(Class c) {
        server.getKryo().register(c);
    }

    //TODO delete if it doesn't work
    public void registerClass(Class c, int id) {
        server.getKryo().register(c,id);
    }

    public String getAddress() {
        return null;
    }

    public void resetNetwork() {
        sendQuitGame();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                server.close();
                server.stop();

                INSTANCE = new NetworkServerKryo();
            }
        });

        clientList.clear();
    }

    private void sendQuitGame() {
        QuitGameDTO quitGame = new QuitGameDTO();
        broadcastMessage(quitGame);
    }

    public Map<Integer, ClientData> getClientList() {
        return clientList;
    }

    public void setHostUsername(String username) {
        host.setUsername(username);
    }

    public ClientData getHost() {
        return host;
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
