package com.example.cluedo_seii.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cluedo_seii.Card;
import com.example.cluedo_seii.DeckOfCards;
import com.example.cluedo_seii.InvestigationFile;
import com.example.cluedo_seii.Notepad;
import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.R;
import java.util.LinkedHashMap;

public class NotepadScreen extends AppCompatActivity {
    private TextView [] textViews;

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


        DeckOfCards deckOfCards= new DeckOfCards();

        Card[] cards = {deckOfCards.oberstVonGatow, deckOfCards.profBloom, deckOfCards.reverendGruen, deckOfCards.baroninVonPorz, deckOfCards.fraeuleinGloria, deckOfCards.frauWeiss,
                        deckOfCards.dolch, deckOfCards.leuchter, deckOfCards.pistole, deckOfCards.seil, deckOfCards.heizungsrohr, deckOfCards.rohrzange,
                        deckOfCards.halle, deckOfCards.salon, deckOfCards.speisezimmer, deckOfCards.kueche, deckOfCards.musikzimmer, deckOfCards.winterzimmer, deckOfCards.biliardzimmer, deckOfCards.bibliothek, deckOfCards.arbeitszimmer
        };

        final Notepad notepad = new Notepad(cards);

       textViewGatov.append(cards[0].getDesignation());
       textViewBloom.append(cards[1].getDesignation());
       textViewGreen.append(cards[2].getDesignation());
       textViewPorz.append(cards[3].getDesignation());
       textViewGloria.append(cards[4].getDesignation());
       textViewWeiss.append(cards[5].getDesignation());
       textViewDolch.append(cards[6].getDesignation());
       textViewLeuchter.append(cards[7].getDesignation());
       textViewPistole.append(cards[8].getDesignation());
       textViewSeil.append(cards[9].getDesignation());
       textViewHeizungsrohr.append(cards[10].getDesignation());
       textViewRohrzange.append(cards[11].getDesignation());
       textViewHalle.append(cards[12].getDesignation());
       textViewSalon.append(cards[13].getDesignation());
       textViewSpeisezimmer.append(cards[14].getDesignation());
       textViewKüche.append(cards[15].getDesignation());
       textViewMusikzimmer.append(cards[16].getDesignation());
       textViewWinterzimmer.append(cards[17].getDesignation());
       textViewBiliardzimmer.append(cards[18].getDesignation());
       textViewBibliothek.append(cards[19].getDesignation());
       textViewArbeitszimmer.append(cards[20].getDesignation());

        textViews = new TextView[21];
        textViews[0]= textViewGatov;
        textViews[1]=textViewBloom;
        textViews[2]=textViewGreen;
        textViews[3]=textViewPorz;
        textViews[4]=textViewGloria;
        textViews[5]=textViewWeiss;
        textViews[6]=textViewDolch;
        textViews[7]=textViewLeuchter;
        textViews[8]=textViewPistole;
        textViews[9]=textViewSeil;
        textViews[10]= textViewHeizungsrohr;
        textViews[11]=textViewRohrzange;
        textViews[12]= textViewHalle;
        textViews[13]= textViewSalon;
        textViews[14]= textViewSpeisezimmer;
        textViews[15]=textViewKüche;
        textViews[16]=textViewMusikzimmer;
        textViews[17]=textViewWinterzimmer;
        textViews[18]=textViewBiliardzimmer;
        textViews[19]=textViewBibliothek;
        textViews[20]=textViewArbeitszimmer;



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
    public void cheatFunction(){
       // Notepad notepad = new Notepad(notepad.getCards());
       // Player player;
       // player = new Player (player.getId(), player.getIP(), player.getPlayerCharacter(), notepad);


        InvestigationFile investigationFile = new InvestigationFile();
        Card culprite = investigationFile.getCulprit();
        Card room = investigationFile.getRoom();
        Card weapon = investigationFile.getWeapon();
        TextView randomTextView;

        do {randomTextView = textViews[(int) Math.floor(Math.random() * textViews.length)];}

        while(randomTextView.equals(culprite)||randomTextView.equals(room)||randomTextView.equals(weapon)||randomTextView.getTag()=="grayed");


        randomTextView.setBackgroundColor(Color.argb(150, 200, 200, 200));
       // player.setCheated();


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
