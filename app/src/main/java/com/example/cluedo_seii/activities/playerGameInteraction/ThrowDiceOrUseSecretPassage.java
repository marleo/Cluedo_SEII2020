package com.example.cluedo_seii.activities.playerGameInteraction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.cluedo_seii.Game;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.cluedo_seii.R;
import com.example.cluedo_seii.UserInput;
import com.example.cluedo_seii.activities.GameboardScreen;

public class ThrowDiceOrUseSecretPassage extends AppCompatDialogFragment {

     Game game;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.player_game_interaction_layout, null);
        setCancelable(false);

        game = (Game) savedInstanceState.getSerializable("game");


        DialogInterface.OnClickListener listenerThrowDice = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                game.gameControl(UserInput.THROWDICE);
                ((GameboardScreen)getActivity()).updateGame(game);
            }
        };
        DialogInterface.OnClickListener  listenerUseSecretPassage = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                game.gameControl(UserInput.USESECRETPASSAGE);
                ((GameboardScreen)getActivity()).updateGame(game);
        }
        };

        return new AlertDialog.Builder(getActivity())
                .setTitle("What do You Wanna Do")
                .setView(view)
                .setPositiveButton("Throw Dice", listenerThrowDice)
                .setNegativeButton("Use Secret Passage", listenerUseSecretPassage)
                .create();
    }

}