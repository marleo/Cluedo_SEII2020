package com.example.cluedo_seii.activities.networkActivities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cluedo_seii.R;

public class ChooseGameTypeActivity extends AppCompatActivity {
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game_type);

        final Button startLocalGame = findViewById(R.id.start_local_game_button);
        startLocalGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseGameTypeActivity.this, StartGameScreen.class));
            }
        });

        builder = new AlertDialog.Builder(this);

        final Button startGlobalGame = findViewById(R.id.start_global_game_button);
        startGlobalGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startActivity(new Intent(ChooseGameTypeActivity.this, StartGlobalGameActivity.class));
                } else {
                    builder.setTitle("API is too old").setMessage("Min API 26 is required");

                    AlertDialog alert = builder.create();
                    alert.show();
                }

            }
        });


    }
}
