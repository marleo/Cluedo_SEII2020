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

import com.example.cluedo_seii.Card;
import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.R;
import com.example.cluedo_seii.activities.GameboardScreen;

import java.util.List;

public class SuspicionShowCard extends DialogFragment {
    Game game;
    List<Card> cards;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.player_game_interaction_layout, null);
        game = Game.getInstance();
        cards =  ((GameboardScreen)getActivity()).getSuspicionCards();
        setCancelable(false);

        DialogInterface.OnClickListener listenerCardOne = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO send Accuser back Card
            }
        };

        DialogInterface.OnClickListener listenerCardTwo = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO send Accuser back Card
                    }
        };

        DialogInterface.OnClickListener  listenerCardThree = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO send Accuser back Card
            }
        };

        return new AlertDialog.Builder(getActivity())
                .setTitle("Welche Karte willst du Zeigen")
                .setView(view)
                .setPositiveButton(cards.get(0).getDesignation(), listenerCardOne)
                .setNeutralButton(cards.get(1).getDesignation(), listenerCardTwo)
                .setNegativeButton(cards.get(2).getDesignation(), listenerCardThree)
                .create();
    }

}
