package com.example.cluedo_seii.activities.playerGameInteraction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.R;

public class NotifyPlayerWon extends DialogFragment {
    Game game;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.player_game_interaction_layout, null);
        game = Game.getInstance();
        setCancelable(false);

        DialogInterface.OnClickListener endGame = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Game.reset();
            }
        };

        return new AlertDialog.Builder(getActivity())
                .setTitle("Player " + game.getCurrentPlayer().getId() + " won the Game!")
                .setView(view)
                .setPositiveButton("Close", endGame)
                .create();
    }

}
