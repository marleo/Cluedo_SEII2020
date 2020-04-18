package com.example.cluedo_seii;

import java.util.HashMap;

public class Notepad {
    public HashMap<String, String> notes;
    public String moreNotes;

    Notepad() {

        notes = new HashMap<>();

        String[] cards = {"Oberst von Gatov", "Prof. Bloom", "Reverend Grün", "Baronin von Porz", "Fräulein Gloria", "Frau weiss",
                "Dolch", "Leuchter", "Pistole", "Seil", "Heizungsrohr", "Rohrzange",
                "Halle", "Salon", "Speisezimmer", "Küche", "Musikzimmer", "Winterzimmer", "Biliardzimmer", "Bibliothek", "Arbeitszimmer"};

        for (String c : cards) {
            notes.put(c, " ");
        }

        this.moreNotes=" ";


    }

    public HashMap getNotes(){
        return this.notes;
    }

    public void excludeOpportunity(String bezeichnung){
        notes.put(bezeichnung, "X");
    }


    public String getMoreNotes(){
        return this.moreNotes;
    }

    public void addMoreNotes(String message){
        moreNotes += message;
    }
}
