package com.example.cluedo_seii.activities.playerGameInteraction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.cluedo_seii.Game;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.cluedo_seii.GameState;
import com.example.cluedo_seii.R;
import com.example.cluedo_seii.activities.GameboardScreen;

public class ThrowDiceOrUseSecretPassage extends AppCompatDialogFragment {

     Game game;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.player_game_interaction_layout, null);
        setCancelable(false);
        game = Game.getInstance();


        DialogInterface.OnClickListener listenerThrowDice = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((GameboardScreen)getActivity()).rollDice();

            }
        };
        DialogInterface.OnClickListener  listenerUseSecretPassage = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //((GameboardScreen)getActivity()).useSecretPassage();
                game.changeGameState(GameState.PLAYERACCUSATION);
        }
        };

        return new AlertDialog.Builder(getActivity())
                .setTitle("Was willst du tun?")
                .setView(view)
                .setPositiveButton("Würfeln", listenerThrowDice)
                .setNegativeButton("Geheimgang verwenden", listenerUseSecretPassage)
                .create();
    }

}
