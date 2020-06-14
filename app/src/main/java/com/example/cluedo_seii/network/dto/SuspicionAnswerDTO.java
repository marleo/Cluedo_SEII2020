package com.example.cluedo_seii.network.dto;

import com.example.cluedo_seii.Card;
import com.example.cluedo_seii.Player;

import java.util.LinkedList;

public class SuspicionAnswerDTO extends  RequestDTO {
    private Card answer;
    private Player acusee;

    public Card getAnswer() {
        return answer;
    }

    public void setAnswer(Card answer) {
        this.answer = answer;
    }

    public Player getAcusee() {
        return acusee;
    }

    public void setAcusee(Player acusee) {
        this.acusee = acusee;
    }
}
