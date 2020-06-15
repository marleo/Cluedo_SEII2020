package com.example.cluedo_seii.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import com.example.cluedo_seii.R;


public class SettingScreen extends AppCompatActivity {

    private static Boolean cheatEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_screen);

        final Switch cheatToggle = findViewById(R.id.cheatToggle);

        SharedPreferences preferences = getSharedPreferences("com.example.cluedo_seii", MODE_PRIVATE);
        cheatToggle.setChecked(preferences.getBoolean("cheatEnabled", false));

        cheatToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SharedPreferences.Editor editor = getSharedPreferences("com.example.cluedo_seii", MODE_PRIVATE).edit();
                    editor.putBoolean("cheatEnabled", true);
                    editor.putBoolean("configChanged", true);
                    editor.apply();

                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.example.cluedo_seii", MODE_PRIVATE).edit();
                    editor.putBoolean("cheatEnabled", false);
                    editor.putBoolean("configChanged", true);
                    editor.apply();

                }
            }

        });


        final Button helpButton = findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(SettingScreen.this, helpSettings.class));
            }
        });

        final ImageButton backButton = findViewById(R.id.backButtonOptions);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(SettingScreen.this, MainActivity.class));
            }
        });

    }

    public static Boolean getCheatEnabled() {
        return cheatEnabled;
    }
}
