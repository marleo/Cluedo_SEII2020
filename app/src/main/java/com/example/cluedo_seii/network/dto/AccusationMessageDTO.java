package com.example.cluedo_seii.network.dto;
import com.example.cluedo_seii.Player;

public class AccusationMessageDTO extends RequestDTO {

    private Player accuser;
    private String message;

    public Player getAccuser() {
        return accuser;
    }

    public String getMessage() {
        return message;
    }

    public void setAccuser(Player accuser) {
        this.accuser = accuser;
    }

    public void setWinMessage(){
        message = "Spieler " + accuser.getId() + "  hat eine richtige Anklage erhoben und das Spiel gewonnen.";
     }

    public void setLooseMessage(){
        message = "Spieler " + accuser.getId() + "  hat eine falsche Anklage erhoben.";
    }

}
