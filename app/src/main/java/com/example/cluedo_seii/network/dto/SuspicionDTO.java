package com.example.cluedo_seii.network.dto;

import com.example.cluedo_seii.Card;
import com.example.cluedo_seii.Player;

import java.util.LinkedList;

public class SuspicionDTO extends  RequestDTO {

    private Player accuser;
    private Player acusee;
    private LinkedList<Card> suspicion;

    public Player getAccuser() {
        return accuser;
    }

    public void setAccuser(Player accuser) {
        this.accuser = accuser;
    }

    public Player getAcusee() {
        return acusee;
    }

    public void setAcusee(Player acusee) {
        this.acusee = acusee;
    }

    public LinkedList<Card> getSuspicion() {
        return suspicion;
    }

    public void setSuspicion(LinkedList<Card> suspicion) {
        this.suspicion = suspicion;
    }
}
