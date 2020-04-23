package com.example.cluedo_seii;


import android.os.Build;


import androidx.annotation.RequiresApi;

import java.util.HashMap;



public class Notepad  {
    public HashMap<String, String> notes;
    String[] cards;
    public String moreNotes;






    public Notepad(String[] cards, HashMap<String,String> notes){
        this.cards=cards;
        this.moreNotes=" ";

        for (String c : cards) {
                notes.put(c, " ");
            }
        }



        @RequiresApi(api = Build.VERSION_CODES.N)
        public void excludeOpportunity (String bezeichnung, HashMap<String,String> notes){
           if (notes.containsKey(bezeichnung)){
                    notes.replace(bezeichnung," ", "X");
                }
            }





        public String getMoreNotes () {
            return this.moreNotes;
        }

        public void addMoreNotes (String message){
            moreNotes += message;
        }
    }

