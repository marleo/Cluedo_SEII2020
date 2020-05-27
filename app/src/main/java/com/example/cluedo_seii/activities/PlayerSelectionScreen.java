package com.example.cluedo_seii.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.example.cluedo_seii.R;

public class PlayerSelectionScreen extends Activity {


    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button startBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_player);
        radioGroup = findViewById(R.id.radioGroup);
        startBtn = findViewById(R.id.playerSelected);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlayerSelectionScreen.this, GameboardScreen.class));
            }
        });


    }

    public void checkRadioButton(View v){
        int radioBtnID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioBtnID);
        Toast.makeText(this,"You chose to play as: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }
}