package com.example.cluedo_seii.activities.playerGameInteraction;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.R;
import com.example.cluedo_seii.network.ClientData;

import java.util.Collections;

public class ExposeCheater extends AppCompatActivity /*implements AdapterView.OnItemSelectedListener*/ {

   /* private Spinner cheaterSpinner;
    private Button accuseCheater;
    private Toast toast;
    private ArrayAdapter<String> adapterCheater;
    private ClientData clientData;
    private String selectedUsername;
    private Game game;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expose_cheater);

        cheaterSpinner=findViewById(R.id.possibleCheater);
        cheaterSpinner.setOnItemSelectedListener(this);
        adapterCheater= ArrayAdapter.createFromResource(this, R.array.culprits, android.R.layout.simple_spinner_item);
        adapterCheater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cheaterSpinner.setAdapter(adapterCheater);




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
        if(selectedUsername.){}
    }*/

}
