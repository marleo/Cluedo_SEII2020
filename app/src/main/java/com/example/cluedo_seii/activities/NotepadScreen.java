package com.example.cluedo_seii.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cluedo_seii.Card;
import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.InvestigationFile;
import com.example.cluedo_seii.Notepad;
import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.R;
import com.example.cluedo_seii.activities.playerGameInteraction.ExposeCheater;
import com.example.cluedo_seii.network.Callback;
import com.example.cluedo_seii.network.connectionType;
import com.example.cluedo_seii.network.dto.CheatDTO;
import com.example.cluedo_seii.network.kryonet.NetworkClientKryo;
import com.example.cluedo_seii.network.kryonet.NetworkServerKryo;
import com.example.cluedo_seii.network.kryonet.SelectedConType;
import java.util.Random;

/**
 * Erzeugt den Notizblock und bietet die Funktionen Karten vom Block zu streichen, eigene Notizen
 * hinzuzufügen, eine Schummelfunktion und man gelangt durch Button Click zur
 * activity_expose_cheater.
 */

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
    private NetworkServerKryo server;
    private NetworkClientKryo client;

    private connectionType conType;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private float sensorValue;
    private Player player;


    private String test = " ";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = Game.getInstance();
        final SharedPreferences preferences = getSharedPreferences("notizblock", MODE_PRIVATE);
        final SharedPreferences.Editor editor = getSharedPreferences("notizblock", MODE_PRIVATE).edit();
        setContentView(R.layout.activity_notepad);
        conType = SelectedConType.getConnectionType();
        server = NetworkServerKryo.getInstance();
        client=NetworkClientKryo.getInstance();
        setListener();
        client.registerCheatCallback(new Callback<CheatDTO>() {
            @Override
            public void callback(CheatDTO argument) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast toast;
                        toast = Toast.makeText(getApplicationContext(),"Jemand hat geschummelt", Toast.LENGTH_LONG);
                        toast.show();

                    }
                });

            }
        });
        server.registerCheatDTOCallback(new Callback<CheatDTO>() {
            @Override
            public void callback(CheatDTO argument) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast toast;
                        toast = Toast.makeText(getApplicationContext(),"Jemand hat geschummelt", Toast.LENGTH_LONG);
                        toast.show();

                    }
                });

            }
        });
        player = game.getLocalPlayer();

        notepad=new Notepad();

        //If Bedingungen damit graue TextViews, nach Wechsel zu anderer Activity, grau bleiben
        textViewGatov = findViewById(R.id.notepad_gatov);
        if(preferences.getBoolean("gatovGray",false)){
            textViewGatov.setBackgroundColor(Color.argb(150, 200, 200, 200));
        }

        textViewBloom = findViewById(R.id.notepad_bloom);
        if(preferences.getBoolean("bloomGray",false)){
            textViewBloom.setBackgroundColor(Color.argb(150, 200, 200, 200));
        }

        textViewGreen = findViewById(R.id.notepad_green);
        if(preferences.getBoolean("greenGray",false)){
            textViewGreen.setBackgroundColor(Color.argb(150, 200, 200, 200));
        }
        textViewPorz = findViewById(R.id.notepad_porz);
        if(preferences.getBoolean("porzGray",false)){
            textViewPorz.setBackgroundColor(Color.argb(150, 200, 200, 200));
        }
        textViewGloria = findViewById(R.id.notepad_gloria);
        if(preferences.getBoolean("gloriaGray",false)){
            textViewGloria.setBackgroundColor(Color.argb(150, 200, 200, 200));
        }
        textViewWeiss = findViewById(R.id.notepad_weiss);
        if(preferences.getBoolean("weissGray",false)){
            textViewWeiss.setBackgroundColor(Color.argb(150, 200, 200, 200));
        }
        textViewDolch = findViewById(R.id.notepad_dolch);
        if(preferences.getBoolean("dolchGray",false)){
            textViewDolch.setBackgroundColor(Color.argb(150, 200, 200, 200));
        }
        textViewLeuchter = findViewById(R.id.notepad_leuchter);
        if(preferences.getBoolean("leuchterGray",false)){
            textViewLeuchter.setBackgroundColor(Color.argb(150, 200, 200, 200));
        }
        textViewPistole = findViewById(R.id.notepad_pistole);
        if(preferences.getBoolean("pistoleGray",false)){
            textViewPistole.setBackgroundColor(Color.argb(150, 200, 200, 200));
        }
        textViewSeil = findViewById(R.id.notepad_seil);
        if(preferences.getBoolean("seilGray",false)){
            textViewSeil.setBackgroundColor(Color.argb(150, 200, 200, 200));
        }
        textViewHeizungsrohr = findViewById(R.id.notepad_heizungsrohr);
        if(preferences.getBoolean("heizungsrohrGray",false)){
            textViewHeizungsrohr.setBackgroundColor(Color.argb(150, 200, 200, 200));
        }
        textViewRohrzange = findViewById(R.id.notepad_rohrzange);
        if(preferences.getBoolean("rohrzangeGray",false)){
            textViewRohrzange.setBackgroundColor(Color.argb(150, 200, 200, 200));
        }
        textViewHalle = findViewById(R.id.notepad_Halle);
        if(preferences.getBoolean("halleGray",false)){
            textViewHalle.setBackgroundColor(Color.argb(150, 200, 200, 200));
        }
        textViewSalon = findViewById(R.id.notepad_salon);
        if(preferences.getBoolean("salonGray",false)){
            textViewSalon.setBackgroundColor(Color.argb(150, 200, 200, 200));
        }
        textViewSpeisezimmer = findViewById(R.id.notepad_speisezimmer);
        if(preferences.getBoolean("speisezimmerGray",false)){
            textViewSpeisezimmer.setBackgroundColor(Color.argb(150, 200, 200, 200));
        }
        textViewKüche = findViewById(R.id.notepad_küche);
        if(preferences.getBoolean("kücheGray",false)){
            textViewKüche.setBackgroundColor(Color.argb(150, 200, 200, 200));
        }
        textViewMusikzimmer = findViewById(R.id.notepad_musikzimmer);
        if(preferences.getBoolean("musikzimmerGray",false)){
            textViewMusikzimmer.setBackgroundColor(Color.argb(150, 200, 200, 200));
        }
        textViewWinterzimmer = findViewById(R.id.notepad_winterzimmer);
        if(preferences.getBoolean("winterzimmerGray",false)){
            textViewWinterzimmer.setBackgroundColor(Color.argb(150, 200, 200, 200));
        }
        textViewBiliardzimmer = findViewById(R.id.notepad_biliardzimmer);
        if(preferences.getBoolean("biliardzimmerGray",false)){
            textViewBiliardzimmer.setBackgroundColor(Color.argb(150, 200, 200, 200));
        }
        textViewBibliothek = findViewById(R.id.notepad_bibliothek);
        if(preferences.getBoolean("bibliothekGray",false)){
            textViewBibliothek.setBackgroundColor(Color.argb(150, 200, 200, 200));
        }
        textViewArbeitszimmer = findViewById(R.id.notepad_arbeitszimmer);
        if(preferences.getBoolean("arbeitszimmerGray",false)){
            textViewArbeitszimmer.setBackgroundColor(Color.argb(150, 200, 200, 200));
        }

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
                    lightEvent(event);

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




        public void onStart () {

            super.onStart();
            SharedPreferences preferences = getSharedPreferences("notizblock", MODE_PRIVATE);
            final SharedPreferences.Editor editor = getSharedPreferences("notizblock", MODE_PRIVATE).edit();
            textView.append(preferences.getString("notes", " "));




            }
            //Damit man die grauen TextViews und Notizen löschen kann
            @Override
            public void onBackPressed() {
                super.onBackPressed();
                getSharedPreferences("notizblock", MODE_PRIVATE).edit().clear().commit();
                finish();
            }




        /**
         * Diese Funktion wählt einen zufälligen TextView vom Notizblock aus der ausgegraut werden
         * soll. Sie macht das so lange bis ein TextView (Karte) gefunden wird die nicht in der
         * Ermittlungsakte steht. Wird einer gefunden, wird die Methode grayOut() aufgerufen.
         * @param investigationFile liefert die Ermittlungsakte vom aktuellen Spiel zur Überprüfung
         *                          welche TextViews nicht ausgegraut werden dürfen.
         */
        public void cheatFunction (InvestigationFile investigationFile){
        conType=SelectedConType.getConnectionType();
            if(conType== connectionType.CLIENT){
                client.sendCheat(player);
                client.setCheated(1);
            }
            else if(conType==connectionType.HOST){
               server.sendCheat(player);
               server.setCheated(1);
                    }

            Card culprit = investigationFile.getCulprit();
            String culpritString = culprit.getDesignation();

            Card room = investigationFile.getRoom();
            String roomString = room.getDesignation();

            Card weapon = investigationFile.getWeapon();
            String weaponString = weapon.getDesignation();

            TextView randomTextView;
            String randomString;

            do{
                int random = new Random().nextInt(notepad.getTextViews().length);
                randomTextView= notepad.getTextViews()[random];
                randomString=randomTextView.getText().toString();}
            while (randomString.equals(culpritString)||randomString.equals(roomString)||randomString.equals(weaponString)||randomTextView.getTag()=="grayed");

            grayOut(randomTextView);

        }


    /**
     * Die Methode grayOut ist die onClick() Mehtode der TextViews die am Notizblock angezeigt
     * werden. Bei einem Klick auf den jeweiligen View wird dieser ausgegraut. Damit der Spieler
     * notieren kann, dass diese Karte nicht in der Ermittlungsakte ist. Es wird überprüft ob
     * der TextView einen Tag besitzt erst dann wird er ausgegraut und erhält einen Tag.
     * Außerdem wird es in SharedPreferences gespeichert damit bei einem Wechsel auf eine andere
     * Aktivität die bereits ausgegrauten Views grau bleiben.
     * @param view TextView welcher ausgegraut werden soll
     */
    public void grayOut (View view){
            SharedPreferences.Editor editor = getSharedPreferences("notizblock",MODE_PRIVATE).edit();

            if (view.getTag() != "grayed") {
                view.setBackgroundColor(Color.argb(150, 200, 200, 200));
                view.setTag("grayed");
                if(view.getId()==textViewGatov.getId()){
                    editor.putBoolean("gatovGray", true);
                    editor.apply();
                }
                else if(view.getId()==textViewBloom.getId()){
                    editor.putBoolean("bloomGray",true);
                    editor.apply();
                }
                else if(view.getId()==textViewGreen.getId()){
                    editor.putBoolean("greenGray",true);
                    editor.apply();
                }
                else if(view.getId()==textViewGloria.getId()){
                    editor.putBoolean("gloriaGray",true);
                    editor.apply();
                }
                else if(view.getId()==textViewPorz.getId()){
                    editor.putBoolean("porzGray",true);
                    editor.apply();
                }
                else if(view.getId()==textViewWeiss.getId()){
                    editor.putBoolean("weissGray",true);
                    editor.apply();
                }
                else if(view.getId()==textViewDolch.getId()){
                    editor.putBoolean("dolchGray",true);
                    editor.apply();
                }
                else if(view.getId()==textViewHeizungsrohr.getId()){
                    editor.putBoolean("heizungsrohrGray",true);
                    editor.apply();
                }
                else if(view.getId()==textViewLeuchter.getId()){
                    editor.putBoolean("leuchterGray",true);
                    editor.apply();
                }
                else if(view.getId()==textViewPistole.getId()){
                    editor.putBoolean("pistoleGray",true);
                    editor.apply();
                }
                else if(view.getId()==textViewRohrzange.getId()){
                    editor.putBoolean("rohrzangeGray",true);
                    editor.apply();
                }
                else if(view.getId()==textViewSeil.getId()){
                    editor.putBoolean("seilGray",true);
                    editor.apply();
                }
                else if(view.getId()==textViewArbeitszimmer.getId()){
                    editor.putBoolean("arbeitszimmerGray",true);
                    editor.apply();
                }
                else if(view.getId()==textViewBibliothek.getId()){
                    editor.putBoolean("bibliothekGray",true);
                    editor.apply();
                }
                else if(view.getId()==textViewBiliardzimmer.getId()){
                    editor.putBoolean("biliardzimmerGray",true);
                    editor.apply();
                }
                else if(view.getId()==textViewMusikzimmer.getId()){
                    editor.putBoolean("musikzimmerGray",true);
                    editor.apply();
                }
                else if(view.getId()==textViewSalon.getId()){
                    editor.putBoolean("salonGray",true);
                    editor.apply();
                }
                else if(view.getId()==textViewSpeisezimmer.getId()){
                    editor.putBoolean("speisezimmerGray",true);
                    editor.apply();
                }
                else if(view.getId()==textViewWinterzimmer.getId()){
                    editor.putBoolean("winterzimmerGray",true);
                    editor.apply();
                }
                else if(view.getId()==textViewHalle.getId()){
                    editor.putBoolean("halleGray",true);
                    editor.apply();
                }
                else if(view.getId()==textViewKüche.getId()){
                    editor.putBoolean("kücheGray",true);
                    editor.apply();
                }


            } else {
                view.setBackgroundColor(0);
                view.setTag("");
            }
        }

    /**
     * Methode lightEvent wird aufgerufen wenn der Licht Sensor Veränderungen wahrnimmt.
     * Es wird geprüft ob der cheated Wert kleiner eins ist und Schummelfunktion aktiviert ist.
     * Außerdem wird geprüft ob der Lichtsensor abgedeckt ist indem man einen kleinen Lichtwert
     * annimmt und erst dann wird die cheatFunction aufgerufen.
     * @param event beinhaltet den Lichtwert zur Überprüfung ob Sensor abgedeckt wird
     */
    public void lightEvent(SensorEvent event) {
            SharedPreferences preferences = getSharedPreferences("com.example.cluedo_seii", MODE_PRIVATE);
            conType= SelectedConType.getConnectionType();
            if (conType == connectionType.CLIENT) {
                client = NetworkClientKryo.getInstance();
                if (client.getCheated() < 1 && preferences.getBoolean("cheatEnabled", false)) {
                    if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                        if (event.values[0] < 3) {
                            sensorValue = event.values[0];
                        }
                        if (event.values[0] < 10) {
                            cheatFunction(game.getInvestigationFile());
                        }
                    }

                }
            }else if(conType == connectionType.HOST){
                server=NetworkServerKryo.getInstance();
                if (server.getCheated() < 1 && preferences.getBoolean("cheatEnabled", false)) {
                    if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                        if (event.values[0] < 3) {
                            sensorValue = event.values[0];
                        }
                        if (event.values[0] < 10) {
                            cheatFunction(game.getInvestigationFile());
                        }
                    }

                }
            }



        }
        //swipeListener zur Navigation zum GameboardScreen
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

    //setListener zur Netzwerkintegration
    public void setListener() {
        if(conType== connectionType.HOST){
            server.setListener(new NetworkServerKryo.ChangeListener() {
                @Override
                public void onChange() {
                    finish();
                }
            });
        }

        else if(conType==connectionType.CLIENT){
            client.setListener(new NetworkClientKryo.ChangeListener() {
                @Override
                public void onChange() {
                    finish();
                }
            });}
    }


}
