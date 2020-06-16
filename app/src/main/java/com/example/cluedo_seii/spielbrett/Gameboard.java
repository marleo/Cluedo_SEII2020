package com.example.cluedo_seii.spielbrett;

import android.graphics.Point;
import android.widget.LinearLayout;

import com.example.cluedo_seii.R;
import com.example.cluedo_seii.activities.GameboardScreen;

import java.util.ArrayList;
import java.util.List;

import static com.example.cluedo_seii.R.drawable.b12;
import static com.example.cluedo_seii.R.drawable.k1;
import static com.example.cluedo_seii.R.drawable.k10;
import static com.example.cluedo_seii.R.drawable.k11;
import static com.example.cluedo_seii.R.drawable.k12;
import static com.example.cluedo_seii.R.drawable.k13;
import static com.example.cluedo_seii.R.drawable.k2;
import static com.example.cluedo_seii.R.drawable.k3;
import static com.example.cluedo_seii.R.drawable.k4;
import static com.example.cluedo_seii.R.drawable.k5;
import static com.example.cluedo_seii.R.drawable.k6;
import static com.example.cluedo_seii.R.drawable.k7;
import static com.example.cluedo_seii.R.drawable.k8;
import static com.example.cluedo_seii.R.drawable.k9;
import static com.example.cluedo_seii.R.drawable.m1;
import static com.example.cluedo_seii.R.drawable.m10;
import static com.example.cluedo_seii.R.drawable.m12;
import static com.example.cluedo_seii.R.drawable.m2;
import static com.example.cluedo_seii.R.drawable.m4;
import static com.example.cluedo_seii.R.drawable.m5;
import static com.example.cluedo_seii.R.drawable.m6;
import static com.example.cluedo_seii.R.drawable.m7;
import static com.example.cluedo_seii.R.drawable.m8;
import static com.example.cluedo_seii.R.drawable.m9;
import static com.example.cluedo_seii.R.drawable.s1;
import static com.example.cluedo_seii.R.drawable.s2;
import static com.example.cluedo_seii.R.drawable.s3;
import static com.example.cluedo_seii.R.drawable.s4;
import static com.example.cluedo_seii.R.drawable.s5;
import static com.example.cluedo_seii.R.drawable.s6;
import static com.example.cluedo_seii.R.drawable.sp11;
import static com.example.cluedo_seii.R.drawable.sp12;
import static com.example.cluedo_seii.R.drawable.sp13;
import static com.example.cluedo_seii.R.drawable.sp14;
import static com.example.cluedo_seii.R.drawable.sp2;
import static com.example.cluedo_seii.R.drawable.sp3;
import static com.example.cluedo_seii.R.drawable.sp4;
import static com.example.cluedo_seii.R.drawable.sp5;
import static com.example.cluedo_seii.R.drawable.sp6;
import static com.example.cluedo_seii.R.drawable.sp7;
import static com.example.cluedo_seii.R.drawable.sp8;
import static com.example.cluedo_seii.R.drawable.sp9;
import static com.example.cluedo_seii.R.drawable.v1;
import static com.example.cluedo_seii.R.drawable.v2;
import static com.example.cluedo_seii.R.drawable.v3;
import static com.example.cluedo_seii.R.drawable.v4;
import static com.example.cluedo_seii.R.drawable.v5;
import static com.example.cluedo_seii.R.drawable.v6;
import static com.example.cluedo_seii.R.drawable.v7;
import static com.example.cluedo_seii.R.drawable.v8;
import static com.example.cluedo_seii.R.drawable.v9;


/* Hier wird die grafische Darstellung des Gameboards (Linear Layout) implementiert
ebenso werden die Player gespawnt
*
* */
public class Gameboard {

    private List<GameboardElement> listeGameboardElemente;
    private int laenge;
    private int breite;
    private LinearLayout layout;
    private String board;
    private int startingPointTotal;

    public Gameboard(GameboardScreen gameboardScreen, int laenge, int breite, String board) {
        this.laenge = laenge;
        this.breite = breite;
        this.board = board;

        listeGameboardElemente = new ArrayList<>();
        init(gameboardScreen);
    }

    private void init(GameboardScreen gameboardScreen) {
        layout = new LinearLayout(gameboardScreen);
        layout.setOrientation(LinearLayout.VERTICAL);

        char[] boardArray = this.board.toCharArray();
        char[][] arrayPerLine = new char[breite][laenge];
        int lineCounter = 0;
        int charCounter = 0;
        for(int i = 1; i < boardArray.length+1; i++) {
            //Log.i("Test", "Board index: " + (i-1) + " value: " + boardArray[i-1]);
            arrayPerLine[lineCounter][charCounter] = boardArray[i-1];
            charCounter++;
            if(i % laenge == 0) {
                lineCounter++;
                charCounter = 0;
            }
        }

        int startingPointCounter = 0;
        int doorElementCounter = 0;
        for (int y = 0; y < breite; y++) {
            LinearLayout row = new LinearLayout(gameboardScreen);
            row.setLayoutParams(new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            for (int x = 0; x < laenge; x++) {
                char position = arrayPerLine[y][x];
                GameboardElement gameboardElement = null;
                switch(position) {
                    case '0':
                        gameboardElement = new GameFieldElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        break;
                    case '1':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        break;
                    case '2':
                        gameboardElement = new StartingpointElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT), startingPointCounter++);
                        break;
                    case '3':
                        gameboardElement = new DoorElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT), doorElementCounter++);
                        break;
                    case '4':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.e1);
                        break;
                    case '5':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.e2);
                        break;
                    case '6':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.e3);
                        break;
                    case '7':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.e4);
                        break;
                    case '8':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.e5);
                        break;
                    case '9':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.e6);
                        break;
                    case 'a':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.e7);
                        break;
                    case 'b':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.e9);
                        break;
                    case 'c':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.a1);
                        break;
                    case 'd':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.a2);
                        break;
                    case 'e':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.a3);
                        break;
                    case 'f':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.a4);
                        break;
                    case 'g':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.a5);
                        break;
                    case 'h':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.a6);
                        break;
                    case 'i':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.a7);
                        break;
                    case 'j':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.a8);
                        break;
                    case 'A':
                        gameboardElement = new Geheimgang(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.geheimgang);
                        break;
                    case 'k':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.a9);
                        break;
                    case 'l':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.bib1);
                        break;
                    case 'm':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.bib2);
                        break;
                    case 'n':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.bib3);
                        break;
                    case 'o':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.bib4);
                        break;
                    case 'p':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.bib5);
                        break;
                    case 'q':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.bib6);
                        break;
                    case 'r':
                        gameboardElement = new NoneWalkableElement (gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.bib7);
                        break;
                    case 's':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.bib8);
                        break;
                    case 't':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.bib9);
                        break;
                    case 'u':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.bib10);
                        break;
                    case 'v':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b1);
                        break;
                    case 'w':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b2);
                        break;
                    case 'x':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b3);
                        break;
                    case 'y':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b4);
                        break;
                    case 'z':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b5);
                        break;
                    case 'B':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b6);
                        break;
                    case 'C':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b7);
                        break;
                    case 'D':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b8);
                        break;
                    case 'E':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b9);
                        break;
                    case 'F':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b10);
                        break;
                    case 'G':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b11);
                        break;
                    case 'H':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b13);
                        break;
                    case 'I':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(b12);
                        break;
                    case 'J':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(m1);
                        break;
                    case 'K':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(m2);
                        break;
                    case 'L':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(m4);
                        break;
                    case 'M':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(m5);
                        break;
                    case 'N':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(m6);
                        break;
                    case 'O':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(m7);
                        break;
                    case 'P':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(m8);
                        break;
                    case 'Q':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(m9);
                        break;
                    case 'R':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(m10);
                        break;
                    case 'T':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(m12);
                        break;
                    case 'S':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(s1);
                        break;
                    case 'U':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(s2);
                        break;
                    case 'V':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(s3);
                        break;
                    case 'W':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(s4);
                        break;
                    case 'X':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(s5);
                        break;
                    case 'Y':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(s6);
                        break;
                    case 'Z':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(k1);
                        break;
                    case '!':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(k2);
                        break;
                    case 'ä':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(k3);
                        break;
                    case 'ö':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(k4);
                        break;
                    case 'ü':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(k5);
                        break;
                    case '$':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(k6);
                        break;
                    case '%':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(k7);
                        break;
                    case '&':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(k8);
                        break;
                    case '/':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(k9);
                        break;
                    case '(':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(k10);
                        break;
                    case ')':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(k11);
                        break;
                    case '+':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(k12);
                        break;
                    case '-':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(k13);
                        break;
                    case '*':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(sp2);
                        break;
                    case '.':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(sp3);
                        break;
                    case ',':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(sp4);
                        break;
                    case '#':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(sp5);
                        break;
                    case ':':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(sp6);
                        break;
                    case ';':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(sp7);
                        break;
                    case '<':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(sp8);
                        break;
                    case '>':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(sp9);
                        break;
                    case '@':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(sp11);
                        break;
                    case '[':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(sp12);
                        break;
                    case ']':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(sp13);
                        break;
                    case '^':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(sp14);
                        break;
                    case '=':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(v1);
                        break;
                    case '_':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(v2);
                        break;
                    case '{':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(v3);
                        break;
                    case '}':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(v4);
                        break;
                    case '~':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(v5);
                        break;
                    case '€':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(v6);
                        break;
                    case 'Ü':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(v7);
                        break;
                    case 'Ö':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(v8);
                        break;
                    case 'Ä':
                        gameboardElement = new NoneWalkableElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(v9);
                        break;

                }

                if (gameboardElement != null) {
                    gameboardElement.getGameBoardElement().setTag(y + 1 + (x * 4));
                    gameboardElement.getGameBoardElement().setId(y + 1 + (x * 4));
                    row.addView(gameboardElement.getGameBoardElement());
                }

                listeGameboardElemente.add(gameboardElement);

            }
            layout.addView(row);
        }

        this.startingPointTotal = startingPointCounter-1;
    }
    // Spieler auf die StaringPoints setzen
    public void spawnPlayer(List<StartingPoint> startingPoints, GameboardScreen gameboardScreen) {
        for(GameboardElement gameboardElement: listeGameboardElemente) {
            if(gameboardElement instanceof StartingpointElement) {
                checkAndSetPlayerOnStartingPoints(startingPoints, gameboardElement);
            }
        }

        // Immer bei jeder Methode die Änderungen am Board vornimmt danach ausführen
        updateGameboardScreen(gameboardScreen);
    }

    private void checkAndSetPlayerOnStartingPoints(List<StartingPoint> startingPoints, GameboardElement gameboardElement) {
        for(StartingPoint startingPoint : startingPoints){
            if(startingPoint.getStartingPointId() == ((StartingpointElement) gameboardElement).getStartingPointId()) {
                ((StartingpointElement) gameboardElement).positionPlayer(true);
                startingPoint.setPlayerPosition(
                        new Point(
                            ((StartingpointElement) gameboardElement).getxKoordinate(),
                            ((StartingpointElement) gameboardElement).getyKoordinate()
                        ));
            }
        }
    }

    public void updateGameboardScreen(GameboardScreen gameboardScreen) {
        for(GameboardElement gameboardElement: listeGameboardElemente) {
            gameboardElement.setGameboardScreen(gameboardScreen);
        }
    }

    public List<GameboardElement> getListeGameboardElemente() {
        return listeGameboardElemente;
    }

    public void setListeGameboardElemente(List<GameboardElement> listeGameboardElemente) {
        this.listeGameboardElemente = listeGameboardElemente;
    }

    public LinearLayout getLayout() {
        return layout;
    }

    public void setLayout(LinearLayout layout) {
        this.layout = layout;
    }
}
