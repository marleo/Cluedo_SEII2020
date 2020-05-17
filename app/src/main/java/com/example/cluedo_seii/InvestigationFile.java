package com.example.cluedo_seii;

import java.io.Serializable;

public class InvestigationFile implements Serializable {

    private Card culprit;
    private Card weapon;
    private Card room;

    public Card getCulprit() {
        return culprit;
    }

    public void setCulprit(Card culprit) {
        this.culprit = culprit;
    }

    public Card getWeapon() {
        return weapon;
    }

    public void setWeapon(Card weapon) {
        this.weapon = weapon;
    }

    public Card getRoom() {
        return room;
    }

    public void setRoom(Card room) {
        this.room = room;
    }

}
