package com.example.cluedo_seii;


import java.util.LinkedHashMap;


public class Notepad  {
    public LinkedHashMap<String, String> notes;
    private String[] cards;
    private String moreNotes;



    public Notepad(String[] cards){
        this.cards=cards;
        this.moreNotes=" ";
        }





        public String getMoreNotes () {
            return this.moreNotes;
        }

        public void addMoreNotes (String message){
            moreNotes += message;
        }
    }

