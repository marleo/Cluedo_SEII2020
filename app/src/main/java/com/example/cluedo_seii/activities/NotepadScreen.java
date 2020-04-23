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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class NotepadScreen extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);

        TextView textView1 = findViewById(R.id.notes);
        //TextView textView2 = findViewById(R.id.notepadMoreNotes);
        Button btn = findViewById(R.id.exclude_button);
        EditText editText = findViewById(R.id.excludeOpp);

        HashMap<String, String> notes = new HashMap<>();


        String[] cards = {"Oberst von Gatov", "Prof. Bloom", "Reverend Grün", "Baronin von Porz", "Fräulein Gloria", "Frau weiss",
                "Dolch", "Leuchter", "Pistole", "Seil", "Heizungsrohr", "Rohrzange",
                "Halle", "Salon", "Speisezimmer", "Küche", "Musikzimmer", "Winterzimmer", "Biliardzimmer", "Bibliothek", "Arbeitszimmer"};


        Notepad notepad = new Notepad(cards, notes);

        Set keys = notes.keySet();
        for (Iterator i = keys.iterator(); i.hasNext(); ) {
            String key = (String) i.next();
            String value = (String) notes.get(key);
            textView1.append(key + " = " + value + "\n");
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
                    String value = (String) notes.get(key);
                    textView1.append(key + " = " + value + "\n");


                }
            }
        };
        btn.setOnClickListener(onButtonClickListener);




    }
}
