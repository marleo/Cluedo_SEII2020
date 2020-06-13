package com.example.cluedo_seii.activities.playerGameInteraction;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.GameState;
import com.example.cluedo_seii.R;

public class PlayerNotification extends AppCompatActivity {
    private Game game;
    private  Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        game = Game.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_notification);
        btn = findViewById(R.id.playerNotificationBtn);
        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();
        game.changeGameState(GameState.PLAVERMOVEMENT);
    }

}
