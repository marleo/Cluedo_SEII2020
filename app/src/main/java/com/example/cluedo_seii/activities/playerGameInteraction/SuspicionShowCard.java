package com.example.cluedo_seii.activities.playerGameInteraction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.cluedo_seii.Card;
import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.GameState;
import com.example.cluedo_seii.R;
import com.example.cluedo_seii.activities.GameboardScreen;
import com.example.cluedo_seii.network.dto.SuspicionAnswerDTO;

import java.util.List;

public class SuspicionShowCard extends DialogFragment {
    Game game;
    List<Card> cards;
    SuspicionAnswerDTO suspicionAnswerDTO;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.player_game_interaction_layout, null);
        game = Game.getInstance();
        cards = game.getSuspicion();
        setCancelable(false);
        suspicionAnswerDTO = new SuspicionAnswerDTO();

        for(Card card:cards){
            Log.i("suspicion card", card.getDesignation());
        }

        DialogInterface.OnClickListener listenerCardOne = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    suspicionAnswerDTO.setAnswer(cards.get(0));
                    suspicionAnswerDTO.setAcusee(game.getAcusee());
                     ((GameboardScreen)getActivity()).sendSuspicionAnswer(suspicionAnswerDTO);
                    game.changeGameState(GameState.PASSIVE);
            }
        };

        DialogInterface.OnClickListener listenerCardTwo = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    suspicionAnswerDTO.setAnswer(cards.get(1));
                    suspicionAnswerDTO.setAcusee(game.getAcusee());
                     ((GameboardScreen)getActivity()).sendSuspicionAnswer(suspicionAnswerDTO);
                     game.changeGameState(GameState.PASSIVE);
                    }
        };

        DialogInterface.OnClickListener  listenerCardThree = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    suspicionAnswerDTO.setAnswer(cards.get(2));
                    suspicionAnswerDTO.setAcusee(game.getAcusee());
                ((GameboardScreen)getActivity()).sendSuspicionAnswer(suspicionAnswerDTO);
                game.changeGameState(GameState.PASSIVE);
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
