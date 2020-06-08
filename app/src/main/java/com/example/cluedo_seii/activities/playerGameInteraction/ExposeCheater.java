package com.example.cluedo_seii.activities.playerGameInteraction;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.esotericsoftware.kryonet.Client;
import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.R;
import com.example.cluedo_seii.network.Callback;
import com.example.cluedo_seii.network.ClientData;
import com.example.cluedo_seii.network.NetworkClient;
import com.example.cluedo_seii.network.dto.UserNameRequestDTO;
import com.example.cluedo_seii.network.kryonet.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ExposeCheater extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner cheaterSpinner;
    private Button accuseCheaterButton;
    private Toast toast;
    private ClientData clientData;
    private String selectedUsername;
    private Game game;
    private Callback<LinkedHashMap<Integer, ClientData>> newClientCallback;
    private LinkedHashMap<Integer, ClientData> clientList;
    private ArrayList<String> userNames = new ArrayList<>();
    private String text;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expose_cheater);

        accuseCheaterButton=findViewById(R.id.accuseCheaterButton);

        newClientCallback.callback(clientList);
        for(ClientData clientData:clientList.values()){
            userNames.add(clientData.getUsername());
        }

        cheaterSpinner=findViewById(R.id.possibleCheater);
        cheaterSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, userNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cheaterSpinner.setAdapter(adapter);

        View.OnClickListener onButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accuseCheating();


            }
        };
        accuseCheaterButton.setOnClickListener(onButtonClickListener);




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       if (parent.getItemAtPosition(position).equals(clientData.getUsername())) {
            selectedUsername = (String) parent.getItemAtPosition(position);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void accuseCheating(){
        for(ClientData clientData : clientList.values()){
            if (clientData.getUsername()==selectedUsername){
                if(clientData.getCheated()>0){
                    text = "Deine Anschuldigung ist richtig";
                    toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    text = "Deine Anschuldigung ist falsch";
                    toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();

                }
            }


        }
    }

}
