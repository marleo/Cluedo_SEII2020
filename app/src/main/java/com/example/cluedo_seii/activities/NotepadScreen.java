package com.example.cluedo_seii.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cluedo_seii.Notepad;
import com.example.cluedo_seii.R;


import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

public class NotepadScreen extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);

        final TextView textView1 = findViewById(R.id.notes);
        final Button btn = findViewById(R.id.exclude_button);
        final EditText editText = findViewById(R.id.excludeOpp);
        final LinkedHashMap<String, String> notes = new LinkedHashMap<>();
        final EditText editText1 = findViewById(R.id.addMoreNotes);
        final Button btn1 = findViewById(R.id.addMoreNotesButton);
        final TextView textView = findViewById(R.id.moreNotesView);




        String[] cards = {
                "Oberst von Gatov", "Prof. Bloom", "Reverend Grün", "Baronin von Porz", "Fräulein Gloria", "Frau weiss",

                "Dolch", "Leuchter", "Pistole", "Seil", "Heizungsrohr", "Rohrzange",

                "Halle", "Salon", "Speisezimmer", "Küche", "Musikzimmer", "Winterzimmer", "Biliardzimmer", "Bibliothek", "Arbeitszimmer"
        };


        final Notepad notepad = new Notepad(cards, notes);

        Set keys = notes.keySet();
        for (Iterator i = keys.iterator(); i.hasNext(); ) {
            String key = (String) i.next();
            String value = notes.get(key);
            textView1.append(key + "  " + value + "\n");
        }

        View.OnClickListener onButtonClickListener = new View.OnClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v){
                String bezeichnung = editText.getText().toString();
                notepad.excludeOpportunity(bezeichnung, notes);
                textView1.setText(" ");

                Set keys = notes.keySet();
                for (Iterator i = keys.iterator(); i.hasNext(); ) {
                    String key = (String) i.next();
                    String value = notes.get(key);
                    textView1.append(key + "  " + value + "\n");


                }
            }
        };
        btn.setOnClickListener(onButtonClickListener);

        View.OnClickListener onButtonClickListener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText1.getText().toString();
                notepad.addMoreNotes(message);
                textView.append(message);

            }
        };
        btn1.setOnClickListener(onButtonClickListener1);




    }
}
