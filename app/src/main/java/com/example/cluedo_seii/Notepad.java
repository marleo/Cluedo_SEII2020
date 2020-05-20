package com.example.cluedo_seii;



public class Notepad  {
    private Card[] cards;
    private String moreNotes;


    public Notepad(Card[] cards){
        this.cards=cards;
        this.moreNotes=" ";
        }





        public String getMoreNotes () {
            return this.moreNotes;
        }
        public Card[] getCards(){return cards;}

        public void addMoreNotes (String message){
            moreNotes += message;
        }
    }

