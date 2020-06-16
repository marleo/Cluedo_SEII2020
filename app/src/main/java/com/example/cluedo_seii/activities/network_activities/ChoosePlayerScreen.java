package com.example.cluedo_seii.activities.network_activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.GameCharacter;
import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.activities.GameboardScreen;
import com.example.cluedo_seii.network.Callback;
import com.example.cluedo_seii.network.connectionType;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cluedo_seii.R;
import com.example.cluedo_seii.network.dto.GameCharacterDTO;
import com.example.cluedo_seii.network.dto.GameDTO;
import com.example.cluedo_seii.network.kryonet.GlobalNetworkHostKryo;
import com.example.cluedo_seii.network.kryonet.NetworkClientKryo;
import com.example.cluedo_seii.network.kryonet.NetworkServerKryo;
import com.example.cluedo_seii.network.kryonet.SelectedConType;

import java.util.HashMap;
import java.util.LinkedList;

public class ChoosePlayerScreen extends AppCompatActivity {
    private connectionType conType;
    private NetworkServerKryo server;
    private NetworkClientKryo client;
    private GlobalNetworkHostKryo globalHost;
    private ListView characterList;
    private TextView waitingForHost;

    private Game game;

    private HashMap<String,GameCharacter> availableCharacters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_player);

        characterList = findViewById(R.id.chooseCharacterListView);
        waitingForHost = findViewById(R.id.waitingForHostText);

        game = Game.getInstance();

        this.conType = SelectedConType.getConnectionType();

        if (conType == connectionType.CLIENT || conType == connectionType.GLOBALCLIENT) {
            client = NetworkClientKryo.getInstance();
            clientChooseCharacter();

        } else if (conType == connectionType.HOST || conType == connectionType.GLOBALHOST) {
            GameCharacter character1 = new GameCharacter("Oberst von Gatov", null);
            GameCharacter character2 = new GameCharacter("Prof. Bloom", null);
            GameCharacter character3 = new GameCharacter("Reverend Grün", null);
            GameCharacter character4 = new GameCharacter("Baronin von Porz", null);
            GameCharacter character5 = new GameCharacter("Fräulein Gloria", null);
            GameCharacter character6 = new GameCharacter("Frau Weiss", null);

            availableCharacters = new HashMap<>();
            availableCharacters.put(character1.getName(),character1);
            availableCharacters.put(character2.getName(),character2);
            availableCharacters.put(character3.getName(),character3);
            availableCharacters.put(character4.getName(),character4);
            availableCharacters.put(character5.getName(),character5);
            availableCharacters.put(character6.getName(),character6);

            GameCharacterDTO gameCharacterDTO = new GameCharacterDTO();
            gameCharacterDTO.setAvailablePlayers(availableCharacters);

            if (conType == connectionType.HOST) {
                server = NetworkServerKryo.getInstance();
                server.broadcastMessage(gameCharacterDTO);
            } else {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    globalHost = GlobalNetworkHostKryo.getInstance();
                    globalHost.broadcastToClients(gameCharacterDTO);
                }
            }


            hostChooseCharacter();
        } else {
            Log.d("ERROR", "Neither Client nor Server at Choose Player Screen");
        }
    }

    private void hostChooseCharacter() {
        waitingForHost.setVisibility(View.INVISIBLE);

        updateCharacterList(availableCharacters);

        // UPDATE Current Players
        if (conType == connectionType.HOST) {
            server.registerCharacterDTOCallback(new Callback<GameCharacterDTO>() {
                @Override
                public void callback(GameCharacterDTO argument) {
                    characterDTOCallbackHelper(argument);
                }
            });
        } else {
            globalHost.registerCharacterDTOCallback(new Callback<GameCharacterDTO>() {
                @Override
                public void callback(GameCharacterDTO argument) {
                    characterDTOCallbackHelper(argument);
                }
            });
        }
    }


    private void characterDTOCallbackHelper(GameCharacterDTO argument) {
        availableCharacters = argument.getAvailablePlayers();
        updateCharacterList(availableCharacters);
    }

    private void clientChooseCharacter() {
        client.registerCharacterCallback(new Callback<GameCharacterDTO>() {
            @Override
            public void callback(GameCharacterDTO argument) {
                updateCharacterList(argument.getAvailablePlayers());
            }
        });

        client.registerGameCallback(new Callback<GameDTO>() {
            @Override
            public void callback(GameDTO argument) {
                startActivity(new Intent(ChoosePlayerScreen.this, GameboardScreen.class));
                client.registerGameCallback(null);
            }
        });

    }

    private void updateCharacterList(final HashMap<String,GameCharacter> gameCharacters) {
        findViewById(R.id.waitingForHostText).setVisibility(View.INVISIBLE);
        
        final LinkedList<String> characterNameList = new LinkedList<>(gameCharacters.keySet());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter<String> characterListAdapter = new ArrayAdapter<>(ChoosePlayerScreen.this, android.R.layout.simple_list_item_1, characterNameList);
                characterList.setAdapter(characterListAdapter);
                if (conType == connectionType.GLOBALHOST || conType == connectionType.HOST) {
                    proceedToGameIfPossible();
                }

                //remove on Click Listener if the character is already chosen
                if (game.getLocalPlayer() == null) {
                    characterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selectedItem = (String) parent.getItemAtPosition(position);
                            GameCharacter selectedCharacter = gameCharacters.get(selectedItem);
                            Log.d("Selected Character", selectedCharacter.toString());


                            if (conType == connectionType.HOST) {
                                //remove Character from List and forward it to all clients
                                gameCharacters.remove(selectedItem);

                                GameCharacterDTO gameCharacterDTO = new GameCharacterDTO();
                                gameCharacterDTO.setAvailablePlayers(gameCharacters);

                                server.broadcastMessage(gameCharacterDTO);

                                //set local Player and add Player to the Players List
                                Player player = new Player(server.getHost().getId(), selectedCharacter);
                                player.setUsername(server.getHost().getUsername());
                                game.setLocalPlayer(player);

                                LinkedList<Player> playerLinkedList = game.getPlayers();
                                if (playerLinkedList == null) playerLinkedList = new LinkedList<>();
                                playerLinkedList.add(player);
                                game.setPlayers(playerLinkedList);

                                updateCharacterList(gameCharacters);
                            } else if (conType == connectionType.CLIENT) {
                                GameCharacterDTO gameCharacterDTO = new GameCharacterDTO();
                                gameCharacterDTO.setChoosenPlayer(selectedCharacter);
                                gameCharacterDTO.setAvailablePlayers(gameCharacters);

                                client.sendMessage(gameCharacterDTO);
                            } else if (conType == connectionType.GLOBALCLIENT) {
                                GameCharacterDTO gameCharacterDTO = new GameCharacterDTO();
                                gameCharacterDTO.setChoosenPlayer(selectedCharacter);
                                gameCharacterDTO.setAvailablePlayers(gameCharacters);

                                client.sendMessageToRoomHost(gameCharacterDTO);

                            } else if (conType == connectionType.GLOBALHOST) {
                                gameCharacters.remove(selectedItem);

                                GameCharacterDTO gameCharacterDTO = new GameCharacterDTO();
                                gameCharacterDTO.setAvailablePlayers(gameCharacters);

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    globalHost.broadcastToClients(gameCharacterDTO);
                                }

                                Player player = new Player(globalHost.getRoomHost().getId(), selectedCharacter);
                                player.setUsername(globalHost.getRoomHost().getUsername());
                                game.setLocalPlayer(player);

                                LinkedList<Player> playerLinkedList = game.getPlayers();
                                if (playerLinkedList == null) playerLinkedList = new LinkedList<>();
                                playerLinkedList.add(player);
                                game.setPlayers(playerLinkedList);

                                updateCharacterList(gameCharacters);
                            }
                        }
                    });
                } else {
                    characterList.setOnItemClickListener(null);
                }
            }
        });
    }

    public void proceedToGameIfPossible() {
        if (everyOneHasChosenACharacter()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final Button proceedToGame = findViewById(R.id.proceedToGameButton);
                    proceedToGame.setVisibility(View.VISIBLE);
                    proceedToGame.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            prepareGame();
                            startActivity(new Intent(ChoosePlayerScreen.this, GameboardScreen.class));
                        }
                    });
                }
            });
        }
    }

    private boolean everyOneHasChosenACharacter() {
        if (SelectedConType.getConnectionType() == connectionType.HOST) {
            return game.getPlayers().size() == server.getClientList().size() + 1;
        } else {
            return game.getPlayers().size() == globalHost.getClientList().size() + 1;
        }
    }

    private void prepareGame() {
        game.distributeCards();
        GameDTO gameDTO = new GameDTO();
        gameDTO.setGame(game);

        if (SelectedConType.getConnectionType() == connectionType.HOST) {
            server.broadcastMessage(gameDTO);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                globalHost.broadcastToClients(gameDTO);
            }
        }
    }



}
