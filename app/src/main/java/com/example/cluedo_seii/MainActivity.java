package com.example.cluedo_seii;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button startGame = findViewById(R.id.mainScreenStartButton);
        startGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // TODO : Switch to Game Scene after button press
            }
        });

        final Button showOptions = findViewById(R.id.mainScreenOptionsButton);
        showOptions.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO: Switch to Options Scene after button press
                startActivity(new Intent(MainActivity.this, SettingScreen.class));
            }
        });

        final Button exitGame = findViewById(R.id.mainScreenExitButton);
        exitGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
                System.exit(0);
            }
        });
    }
}
