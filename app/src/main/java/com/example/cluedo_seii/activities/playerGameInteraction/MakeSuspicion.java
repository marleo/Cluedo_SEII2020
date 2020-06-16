package com.example.cluedo_seii.activities.playerGameInteraction;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cluedo_seii.Card;
import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.GameState;
import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.R;
import com.example.cluedo_seii.network.connectionType;
import com.example.cluedo_seii.network.dto.SuspicionDTO;
import com.example.cluedo_seii.network.kryonet.GlobalNetworkHostKryo;
import com.example.cluedo_seii.network.kryonet.NetworkClientKryo;
import com.example.cluedo_seii.network.kryonet.NetworkServerKryo;
import com.example.cluedo_seii.network.kryonet.SelectedConType;
import java.util.ArrayList;
import java.util.LinkedList;

public class MakeSuspicion extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerCulprit;
    private Spinner spinnerWeapon;
   // private ArrayAdapter<CharSequence> adapterCulprit;
    private ArrayAdapter<String>adapterCulprit;
    private ArrayAdapter<CharSequence>adapterWeapon;
    private String selectedCulprit;
    private String selectedWeapon;
    private String selectedRoom;
    private String[]possibleCulprits;
    private String[]possibleWeapons;
    private String[] possibleRooms;
    private Button suspectButton;
    private ImageView weaponChoice;
    private ImageView characterChoice;
    private Game game;
    private LinkedList<Card> suspectedPlayerHand;
    private Toast toast;
    private String text;
    ArrayList<String> gameCharacters;
    private String[]getPossibleCulprits;
    ArrayAdapter<String> adapter;
    private NetworkServerKryo server;
    private NetworkClientKryo client;
    private GlobalNetworkHostKryo globalHost;
    private connectionType conType;
    Player suspectedPlayer;
    SuspicionDTO suspicionDTO;
    TextView currentRoom;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accuse_and_suspect);
        gameCharacters=new ArrayList<String>();
        game = Game.getInstance();
        initializeNetwork();

        for(Player player: game.getPlayers()){
            if(player.getId()!=game.getLocalPlayer().getId()){
            gameCharacters.add(player.getPlayerCharacter().getName());}
        }

        adapterCulprit = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,gameCharacters);
        spinnerCulprit = (Spinner) findViewById(R.id.suspectedCulprit);
        spinnerCulprit.setOnItemSelectedListener(this);;
        adapterCulprit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCulprit.setAdapter(adapterCulprit);


        spinnerWeapon = (Spinner) findViewById(R.id.suspectedWeapon);
        spinnerWeapon.setOnItemSelectedListener(this);
        adapterWeapon = ArrayAdapter.createFromResource(this, R.array.weapons, android.R.layout.simple_spinner_item);
        adapterWeapon.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeapon.setAdapter(adapterWeapon);

        //TextView currentRoom = findViewById(R.id.currentRoomText);
        //currentRoom.setText("Tatort: " + getCurrentRoom());

        weaponChoice = findViewById(R.id.weaponImage);
        characterChoice = findViewById(R.id.suspectImage);

        //possibleCulprits = getResources().getStringArray(R.array.culprits);
        possibleCulprits=new String[gameCharacters.size()];
        for(int i=0; i<possibleCulprits.length;i++){
            possibleCulprits[i]=gameCharacters.get(i);
        }
        possibleWeapons = getResources().getStringArray(R.array.weapons);
        possibleRooms = getResources().getStringArray(R.array.rooms);

        selectedRoom = getCurrentRoom();
        currentRoom = (TextView)findViewById(R.id.currentRoom);
        currentRoom.setText(selectedRoom);

        suspectButton = findViewById(R.id.makeSuspicionButton);
        suspectButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
        suspectedPlayerHand = game.getCurrentPlayer().suspect(selectedCulprit, selectedWeapon, selectedRoom, game.getPlayers());

                for (Player player : game.getPlayers()) {
                    if (player.getPlayerCharacter().getName().equals(selectedCulprit)) {
                       // player.setPosition(game.getCurrentPlayer().getPosition());
                        suspectedPlayer = player;
                    }
                }

                //Falls der Verdächtigte keine der Karten auf der Hand hat
                if (suspectedPlayerHand.size() == 0) {
                    suspicionDTO = new SuspicionDTO();
                    suspicionDTO.setAccuser(game.getCurrentPlayer());
                    suspicionDTO.setAcusee(suspectedPlayer);
                    suspicionDTO.setSuspicion(suspectedPlayerHand);
                    sendSuspicion();
                    finish();
                }

                //Falls der Verdächtigte eine Karte auf der Hand hat
                else {
                  //  text = "Der verdächtigte Spieler hat folgende Karten auf der Hand:" + '\n';
                    suspicionDTO = new SuspicionDTO();
                    suspicionDTO.setAccuser(game.getCurrentPlayer());
                    suspicionDTO.setAcusee(suspectedPlayer);
                    LinkedList<Card>suspicion = new LinkedList<>();
                    for(Card card:suspectedPlayerHand){
                        suspicion.add(card);
                    }

                        suspicionDTO.setSuspicion(suspicion);
                        sendSuspicion();

                    finish();
                }
            }
        });

        /*suspectButton = findViewById(R.id.makeAccusationButton);
        suspectButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //Im Falle einer erfolgreichen Anklage

                if(game.getCurrentPlayer().accuse(selectedCulprit, selectedWeapon, selectedRoom, game.getInvestigationFile())) {
                    game.setGameOver(true);
                    text = "Gratuliere, du hast das Spiel gewonnen";
                    toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                    game.setGameOver(true);
                    //TODO Nachricht an andere Mitspieler verschicken
                    finish();
                }

                //Im Fall einer falschen Anklage

                else {
                    text = "Du hast eine falsche Anklage erhoben und kannst das Spiel nicht mehr gewinnen";
                    toast = Toast.makeText(getApplicationContext(), text , Toast.LENGTH_SHORT);
                    toast.show();
                    //TODO Nachricht an andere Mitspieler verschicken
                    finish();
                }
            }
        });*/
    }

    public String getCurrentRoom() {
        int playerX = game.getCurrentPlayer().getPosition().x;
        int playerY = game.getCurrentPlayer().getPosition().y;

        if(playerX == 3 && playerY == 2){
            return possibleRooms[0];
        } else if(playerX == 6 && playerY == 2){
            return possibleRooms[1];
        } else if(playerX == 9 && playerY == 2){
            return possibleRooms[2];
        } else if(playerX == 3 && playerY == 5 || playerX == 1 && playerY == 6){
            return possibleRooms[3];
        } else if(playerX == 8 && playerY == 9 || playerX == 8 && playerY == 8){
            return possibleRooms[4];
        } else if(playerX == 1 && playerY == 9 || playerX == 4 && playerY == 10){
            return possibleRooms[5];
        } else if(playerX == 3 && playerY == 13){
            return possibleRooms[6];
        } else if(playerX == 9 && playerY == 13){
            return possibleRooms[7];
        } else if(playerX == 4 && playerY == 17 || playerX == 7 && playerY == 17){
            return possibleRooms[8];
        }

        return "";
    }

    public void onStop(){
        super.onStop();
        game.changeGameState(GameState.WAITINGFORANSWER);
    }

    @Override
    //Festhaltung der Spielerauswahl
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        for (String possibleCulprit : possibleCulprits) {
            if (parent.getItemAtPosition(position).equals(possibleCulprit)) {
                selectedCulprit = (String) parent.getItemAtPosition(position).toString();
                if(selectedCulprit.equals(possibleCulprits[0])){
                    characterChoice.setImageResource(R.drawable.gatov);
                } else if(selectedCulprit.equals(possibleCulprits[1])){
                    characterChoice.setImageResource(R.drawable.bloom);
                } else if(selectedCulprit.equals(possibleCulprits[2])){
                    characterChoice.setImageResource(R.drawable.green);
                } else if(selectedCulprit.equals(possibleCulprits[3])){
                    characterChoice.setImageResource(R.drawable.porz);
                } else if(selectedCulprit.equals(possibleCulprits[4])){
                    characterChoice.setImageResource(R.drawable.gloria);
                } else if(selectedCulprit.equals(possibleCulprits[5])){
                    characterChoice.setImageResource(R.drawable.weiss);
                }
            }
        }

        for (String possibleWeapon : possibleWeapons) {
            if (parent.getItemAtPosition(position).equals(possibleWeapon)) {
                selectedWeapon = (String) parent.getItemAtPosition(position);
                if(selectedWeapon.equals(possibleWeapons[0])){
                    weaponChoice.setImageResource(R.drawable.dolch);
                } else if(selectedWeapon.equals(possibleWeapons[1])){
                    weaponChoice.setImageResource(R.drawable.leuchter);
                } else if(selectedWeapon.equals(possibleWeapons[2])){
                    weaponChoice.setImageResource(R.drawable.pistol);
                } else if(selectedWeapon.equals(possibleWeapons[3])){
                    weaponChoice.setImageResource(R.drawable.seil);
                } else if(selectedWeapon.equals(possibleWeapons[4])){
                    weaponChoice.setImageResource(R.drawable.heizungsrohr);
                } else if(selectedWeapon.equals(possibleWeapons[5])){
                    weaponChoice.setImageResource(R.drawable.rohrzange);
                }
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
        if(conType==connectionType.HOST) {
            server = NetworkServerKryo.getInstance();
        }
        else if(conType==connectionType.CLIENT || conType==connectionType.GLOBALCLIENT){
            client = NetworkClientKryo.getInstance();
        } else if (conType == connectionType.GLOBALHOST) {
            globalHost = GlobalNetworkHostKryo.getInstance();
        }
    }

    //Verdachtsverschickung
    public void sendSuspicion( ){
        //TODO add if for globalhost and global Client
        if(conType==connectionType.HOST) {
            server.broadcastMessage(suspicionDTO);
        }
        else if(conType==connectionType.CLIENT){
            client.sendMessage(suspicionDTO);
        }
        else if (conType==connectionType.GLOBALCLIENT) {
            client.broadcastToGameRoom(suspicionDTO);
        }
        else if (conType==connectionType.GLOBALHOST) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                globalHost.broadcastToClients(suspicionDTO);
            }
        }
    }
}
