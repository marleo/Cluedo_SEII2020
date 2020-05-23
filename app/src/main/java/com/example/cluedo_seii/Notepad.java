package com.example.cluedo_seii;


import android.graphics.Color;
import android.widget.TextView;

import com.example.cluedo_seii.activities.NotepadScreen;

public class Notepad {
    private Card[] cards;
    private String moreNotes;
    private TextView[] textViews;


    public Notepad() {
        DeckOfCards deckOfCards = new DeckOfCards();

        this.cards =new Card[]{deckOfCards.oberstVonGatow, deckOfCards.profBloom, deckOfCards.reverendGruen, deckOfCards.baroninVonPorz, deckOfCards.fraeuleinGloria, deckOfCards.frauWeiss,
                deckOfCards.dolch, deckOfCards.leuchter, deckOfCards.pistole, deckOfCards.seil, deckOfCards.heizungsrohr, deckOfCards.rohrzange,
                deckOfCards.halle, deckOfCards.salon, deckOfCards.speisezimmer, deckOfCards.kueche, deckOfCards.musikzimmer, deckOfCards.winterzimmer, deckOfCards.biliardzimmer, deckOfCards.bibliothek, deckOfCards.arbeitszimmer};
        this.moreNotes = " ";

        this.textViews=new TextView[21];


    }


    public String getMoreNotes() {
        return this.moreNotes;
    }

    public Card[] getCards() {
        return cards;
    }

    public void addMoreNotes(String message) {
        moreNotes += message;
    }

    public TextView[] getTextViews(){
        return textViews;
    }

    public void setTextViews(TextView textView, int number){
        this.textViews[number]=textView;

    }

    public void cheatFunction() {
        InvestigationFile investigationFile = new InvestigationFile();
        /*DeckOfCards deckOfCards = new DeckOfCards();
        Card card = deckOfCards.arbeitszimmer;
        investigationFile.setCulprit(card);*/

        Card culprit = investigationFile.getCulprit();
        String culpritString = culprit.getDesignation();

        Card room = investigationFile.getRoom();
        String roomString = room.getDesignation();

        Card weapon = investigationFile.getWeapon();
        String weaponString = weapon.getDesignation();

        TextView randomTextView;
        String randomString;

        NotepadScreen notepadScreen = new NotepadScreen();

        do {
            randomTextView = notepadScreen.getRandom(textViews);
            randomString = randomTextView.getText().toString();
        }
        while(randomString.equals(culpritString)||randomString.equals(roomString)||randomString.equals(weaponString)||randomTextView.getTag()=="grayed");

        randomTextView.setBackgroundColor(Color.argb(150, 200, 200, 200));
    }
}



