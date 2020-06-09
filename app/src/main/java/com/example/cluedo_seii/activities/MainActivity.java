package com.example.cluedo_seii.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cluedo_seii.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = getSharedPreferences("com.example.cluedo_seii", MODE_PRIVATE); // Load in sharedPreference

        // Button Handling (Redirecting to other Activities)
        final Button startGame = findViewById(R.id.mainScreenStartButton);
        startGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, StartGameScreen.class));

            }
        });

        final Button showOptions = findViewById(R.id.mainScreenOptionsButton);
        showOptions.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
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

        // Handle Cheat-Toggle State in Options Activity
        final TextView cheatText = (TextView) findViewById(R.id.mainScreenCheatInformation);
        String cheatsOn = "Schummeln aktiviert";
        String cheatsOff = "Schummeln deaktiviert";

        if(preferences.getBoolean("cheatEnabled", false)){ // Get Boolean Value of cheatEnabled (this is set in SettingScreen.java)
            cheatText.setText(cheatsOn);
        } else {
            cheatText.setText(cheatsOff);
        }
    }

    @Override
    protected void onResume() {
        // Refresh Main Activity after Preferences have changed
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("com.example.cluedo_seii", MODE_PRIVATE); // Get the preferences

        if (preferences.getBoolean("configChanged", true)) {
            SharedPreferences.Editor editor = getSharedPreferences("com.example.cluedo_seii", MODE_PRIVATE).edit();
            editor.putBoolean("configChanged", false);
            editor.apply();
            this.recreate();
        }
    }
}
