package com.example.cluedo_seii.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cluedo_seii.Notepad;
import com.example.cluedo_seii.R;
import java.util.LinkedHashMap;

public class NotepadScreen extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);

        final TextView textViewGatov = findViewById(R.id.notepad_gatov);
        final TextView textViewBloom = findViewById(R.id.notepad_bloom);
        final TextView textViewGreen = findViewById(R.id.notepad_green);
        final TextView textViewPorz = findViewById(R.id.notepad_porz);
        final TextView textViewGloria = findViewById(R.id.notepad_gloria);
        final TextView textViewWeiss = findViewById(R.id.notepad_weiss);
        final TextView textViewDolch = findViewById(R.id.notepad_dolch);
        final TextView textViewLeuchter = findViewById(R.id.notepad_leuchter);
        final TextView textViewPistole = findViewById(R.id.notepad_pistole);
        final TextView textViewSeil = findViewById(R.id.notepad_seil);
        final TextView textViewHeizungsrohr = findViewById(R.id.notepad_heizungsrohr);
        final TextView textViewRohrzange = findViewById(R.id.notepad_rohrzange);
        final TextView textViewHalle = findViewById(R.id.notepad_Halle);
        final TextView textViewSalon = findViewById(R.id.notepad_salon);
        final TextView textViewSpeisezimmer = findViewById(R.id.notepad_speisezimmer);
        final TextView textViewKüche = findViewById(R.id.notepad_küche);
        final TextView textViewMusikzimmer = findViewById(R.id.notepad_musikzimmer);
        final TextView textViewWinterzimmer = findViewById(R.id.notepad_winterzimmer);
        final TextView textViewBiliardzimmer = findViewById(R.id.notepad_biliardzimmer);
        final TextView textViewBibliothek = findViewById(R.id.notepad_bibliothek);
        final TextView textViewArbeitszimmer = findViewById(R.id.notepad_arbeitszimmer);
        final LinkedHashMap<String, String> notes = new LinkedHashMap<>();
        final EditText editText1 = findViewById(R.id.addMoreNotes);
        final Button btn1 = findViewById(R.id.addMoreNotesButton);
        final TextView textView = findViewById(R.id.moreNotesView);





        String[] cards = {
                "Oberst von Gatov", "Prof. Bloom", "Reverend Grün", "Baronin von Porz", "Fräulein Gloria", "Frau Weiss",

                "Dolch", "Leuchter", "Pistole", "Seil", "Heizungsrohr", "Rohrzange",

                "Halle", "Salon", "Speisezimmer", "Küche", "Musikzimmer", "Winterzimmer", "Biliardzimmer", "Bibliothek", "Arbeitszimmer"
        };


        final Notepad notepad = new Notepad(cards);

       textViewGatov.append(cards[0]);
       textViewBloom.append(cards[1]);
       textViewGreen.append(cards[2]);
       textViewPorz.append(cards[3]);
       textViewGloria.append(cards[4]);
       textViewWeiss.append(cards[5]);
       textViewDolch.append(cards[6]);
       textViewLeuchter.append(cards[7]);
       textViewPistole.append(cards[8]);
       textViewSeil.append(cards[9]);
       textViewHeizungsrohr.append(cards[10]);
       textViewRohrzange.append(cards[11]);
       textViewHalle.append(cards[12]);
       textViewSalon.append(cards[13]);
       textViewSpeisezimmer.append(cards[14]);
       textViewKüche.append(cards[15]);
       textViewMusikzimmer.append(cards[16]);
       textViewWinterzimmer.append(cards[17]);
       textViewBiliardzimmer.append(cards[18]);
       textViewBibliothek.append(cards[19]);
       textViewArbeitszimmer.append(cards[20]);



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

    public void grayOut(View view) {

        if(view.getTag() != "grayed") {
            view.setBackgroundColor(Color.argb(150,200,200,200));
            view.setTag("grayed");
        } else {
            view.setBackgroundColor(0);
            view.setTag("");
        }
    }
}
