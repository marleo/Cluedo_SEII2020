package com.example.cluedo_seii.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.Notepad;
import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.R;
import com.example.cluedo_seii.activities.playerGameInteraction.ExposeCheater;

import java.util.ArrayList;
import java.util.Random;

public class NotepadScreen extends AppCompatActivity {
    float x1, x2;
    static final int MIN_SWIPE_DISTANCE = 150;

    private TextView textViewGatov;
    private TextView textViewBloom;
    private TextView textViewGreen;
    private TextView textViewPorz;
    private TextView textViewGloria;
    private TextView textViewWeiss;
    private TextView textViewDolch;
    private TextView textViewLeuchter;
    private TextView textViewPistole;
    private TextView textViewSeil;
    private TextView textViewHeizungsrohr;
    private TextView textViewRohrzange;
    private TextView textViewHalle;
    private TextView textViewSalon;
    private TextView textViewSpeisezimmer;
    private TextView textViewKüche;
    private TextView textViewMusikzimmer;
    private TextView textViewWinterzimmer;
    private TextView textViewBiliardzimmer;
    private TextView textViewBibliothek;
    private TextView textViewArbeitszimmer;

    private EditText editText1;
    private Button btn1;
    private TextView textView;
    private Notepad notepad;
    private Game game;
    private Intent intent;
    private Player player;
    private WifiManager wifiManager;


    private SensorManager sensorManager;
    private Sensor lightSensor;
    private float sensorValue;
    ArrayList<View> grayViews;


    private String test = " ";
    private String count="";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        game = (Game) intent.getSerializableExtra("game");
        final SharedPreferences preferences = getSharedPreferences("com.example.cluedo_seii", MODE_PRIVATE);
        final SharedPreferences.Editor editor = getSharedPreferences("com.example.cluedo_seii", MODE_PRIVATE).edit();
        setContentView(R.layout.activity_notepad);


        player = game.getCurrentPlayer();
        notepad = player.getNotepad();
        grayViews = new ArrayList<>();


        textViewGatov = findViewById(R.id.notepad_gatov);
        textViewBloom = findViewById(R.id.notepad_bloom);
        textViewGreen = findViewById(R.id.notepad_green);
        textViewPorz = findViewById(R.id.notepad_porz);
        textViewGloria = findViewById(R.id.notepad_gloria);
        textViewWeiss = findViewById(R.id.notepad_weiss);
        textViewDolch = findViewById(R.id.notepad_dolch);
        textViewLeuchter = findViewById(R.id.notepad_leuchter);
        textViewPistole = findViewById(R.id.notepad_pistole);
        textViewSeil = findViewById(R.id.notepad_seil);
        textViewHeizungsrohr = findViewById(R.id.notepad_heizungsrohr);
        textViewRohrzange = findViewById(R.id.notepad_rohrzange);
        textViewHalle = findViewById(R.id.notepad_Halle);
        textViewSalon = findViewById(R.id.notepad_salon);
        textViewSpeisezimmer = findViewById(R.id.notepad_speisezimmer);
        textViewKüche = findViewById(R.id.notepad_küche);
        textViewMusikzimmer = findViewById(R.id.notepad_musikzimmer);
        textViewWinterzimmer = findViewById(R.id.notepad_winterzimmer);
        textViewBiliardzimmer = findViewById(R.id.notepad_biliardzimmer);
        textViewBibliothek = findViewById(R.id.notepad_bibliothek);
        textViewArbeitszimmer = findViewById(R.id.notepad_arbeitszimmer);
        editText1 = findViewById(R.id.addMoreNotes);
        btn1 = findViewById(R.id.addMoreNotesButton);
        textView = findViewById(R.id.moreNotesView);


        textViewGatov.append(notepad.getCards()[0].getDesignation());
        textViewBloom.append(notepad.getCards()[1].getDesignation());
        textViewGreen.append(notepad.getCards()[2].getDesignation());
        textViewPorz.append(notepad.getCards()[3].getDesignation());
        textViewGloria.append(notepad.getCards()[4].getDesignation());
        textViewWeiss.append(notepad.getCards()[5].getDesignation());
        textViewDolch.append(notepad.getCards()[6].getDesignation());
        textViewLeuchter.append(notepad.getCards()[7].getDesignation());
        textViewPistole.append(notepad.getCards()[8].getDesignation());
        textViewSeil.append(notepad.getCards()[9].getDesignation());
        textViewHeizungsrohr.append(notepad.getCards()[10].getDesignation());
        textViewRohrzange.append(notepad.getCards()[11].getDesignation());
        textViewHalle.append(notepad.getCards()[12].getDesignation());
        textViewSalon.append(notepad.getCards()[13].getDesignation());
        textViewSpeisezimmer.append(notepad.getCards()[14].getDesignation());
        textViewKüche.append(notepad.getCards()[15].getDesignation());
        textViewMusikzimmer.append(notepad.getCards()[16].getDesignation());
        textViewWinterzimmer.append(notepad.getCards()[17].getDesignation());
        textViewBiliardzimmer.append(notepad.getCards()[18].getDesignation());
        textViewBibliothek.append(notepad.getCards()[19].getDesignation());
        textViewArbeitszimmer.append(notepad.getCards()[20].getDesignation());

        notepad.setTextViews(textViewGatov, 0);
        notepad.setTextViews(textViewBloom, 1);
        notepad.setTextViews(textViewGreen, 2);
        notepad.setTextViews(textViewPorz, 3);
        notepad.setTextViews(textViewGloria, 4);
        notepad.setTextViews(textViewWeiss, 5);
        notepad.setTextViews(textViewDolch, 6);
        notepad.setTextViews(textViewLeuchter, 7);
        notepad.setTextViews(textViewPistole, 8);
        notepad.setTextViews(textViewSeil, 9);
        notepad.setTextViews(textViewHeizungsrohr, 10);
        notepad.setTextViews(textViewRohrzange, 11);
        notepad.setTextViews(textViewHalle, 12);
        notepad.setTextViews(textViewSalon, 13);
        notepad.setTextViews(textViewSpeisezimmer, 14);
        notepad.setTextViews(textViewKüche, 15);
        notepad.setTextViews(textViewMusikzimmer, 16);
        notepad.setTextViews(textViewWinterzimmer, 17);
        notepad.setTextViews(textViewBiliardzimmer, 18);
        notepad.setTextViews(textViewBibliothek, 19);
        notepad.setTextViews(textViewArbeitszimmer, 20);



            View.OnClickListener onButtonClickListener1 = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = editText1.getText().toString();
                    notepad.setNotes(message);
                    textView.append(notepad.getNotes());
                    test = textView.getText().toString();
                    editor.putString("notes", test);
                    editor.apply();


                }
            };
            btn1.setOnClickListener(onButtonClickListener1);


            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            if (lightSensor == null) {
                finish();
            }

            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if (game.getCurrentPlayer().getCheated() < 1 && preferences.getBoolean("cheatEnabled", false)) {
                        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                            if (event.values[0] < 3) {
                                sensorValue = event.values[0];
                            }
                            if (event.values[0] < 10) {
                                notepad.cheatFunction(game.getInvestigationFile());
                                int value = 1;
                                game.getCurrentPlayer().setCheated(value);

                            }
                        }

                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            }, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

            final Button exposeButton = findViewById(R.id.exposeButton);
            exposeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(NotepadScreen.this, ExposeCheater.class));
                }
            });


        }

        /*@Override
        public void onSaveInstanceState(Bundle savedInstanceState) {
            super.onSaveInstanceState(savedInstanceState);
            savedInstanceState.putString("My String","test");

        }*/



        public void onStart () {

            super.onStart();
            SharedPreferences preferences = getSharedPreferences("com.example.cluedo_seii", MODE_PRIVATE);
            final SharedPreferences.Editor editor = getSharedPreferences("com.example.cluedo_seii", MODE_PRIVATE).edit();
            textView.append(preferences.getString("notes", " "));

            for(View v : grayViews){
                v.setBackgroundColor(Color.argb(150, 200, 200, 200));
            }




            /*for (int i = 0; i < count.length(); i++) {
                int j= count.charAt(i);
                notepad.getTextViews()[j].setBackgroundColor(Color.argb(150, 200, 200, 200));
            }



                //notepad.getTextViews()[Integer.parseInt(preferences.getString("gray", "0"))].setBackgroundColor(Color.argb(150, 200, 200, 200));


            /*for (int i = 0; i < preferences.getString("gray", " ").length(); i++) {
                for (int j = 0; j < textViews.length; j++) {
                    if (preferences.getString("gray", " ").charAt(i) == j) {

                        textViews[preferences.getString("gray", " ").charAt(i)].setBackgroundColor(Color.argb(150, 200, 200, 200));
                    }
                }
            }*/
            }





        public TextView getRandom (TextView[]array){
            int random = new Random().nextInt(array.length);
            return array[random];
        }




        public void grayOut (View view){

            if (view.getTag() != "grayed") {
                view.setBackgroundColor(Color.argb(150, 200, 200, 200));
                view.setTag("grayed");
                grayViews.add(view);
            } else {
                view.setBackgroundColor(0);
                view.setTag("");
            }
        }

        @Override
        public boolean onTouchEvent (MotionEvent touchEvent){
            switch (touchEvent.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    x1 = touchEvent.getX();
                    break;
                case MotionEvent.ACTION_UP:
                    x2 = touchEvent.getX();

                    float swipeLeft = x1 - x2;

                    if (swipeLeft > MIN_SWIPE_DISTANCE) {
                        finish();
                        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                    }
                    break;
            }
            return false;
        }


}
