package com.example.cluedo_seii.spielbrett;

import android.graphics.Point;
import android.widget.LinearLayout;

import com.example.cluedo_seii.R;
import com.example.cluedo_seii.activities.GameboardScreen;
import com.example.cluedo_seii.spielbrett.billiard.Billard1;
import com.example.cluedo_seii.spielbrett.billiard.Billard10;
import com.example.cluedo_seii.spielbrett.billiard.Billard11;
import com.example.cluedo_seii.spielbrett.billiard.Billard12;
import com.example.cluedo_seii.spielbrett.billiard.Billard2;
import com.example.cluedo_seii.spielbrett.billiard.Billard3;
import com.example.cluedo_seii.spielbrett.billiard.Billard4;
import com.example.cluedo_seii.spielbrett.billiard.Billard5;
import com.example.cluedo_seii.spielbrett.billiard.Billard6;
import com.example.cluedo_seii.spielbrett.billiard.Billard7;
import com.example.cluedo_seii.spielbrett.billiard.Billard8;
import com.example.cluedo_seii.spielbrett.billiard.Billard9;
import com.example.cluedo_seii.spielbrett.entrace.Entrace1;
import com.example.cluedo_seii.spielbrett.entrace.Entrace2;
import com.example.cluedo_seii.spielbrett.entrace.Entrace3;
import com.example.cluedo_seii.spielbrett.entrace.Entrace4;
import com.example.cluedo_seii.spielbrett.entrace.Entrace5;
import com.example.cluedo_seii.spielbrett.entrace.Entrace6;
import com.example.cluedo_seii.spielbrett.entrace.Entrace7;
import com.example.cluedo_seii.spielbrett.entrace.Entrace8;
import com.example.cluedo_seii.spielbrett.entrace.Geheimgang;
import com.example.cluedo_seii.spielbrett.workingRoom.WorkingRoom1;
import com.example.cluedo_seii.spielbrett.workingRoom.WorkingRoom2;
import com.example.cluedo_seii.spielbrett.workingRoom.WorkingRoom3;
import com.example.cluedo_seii.spielbrett.workingRoom.WorkingRoom4;
import com.example.cluedo_seii.spielbrett.workingRoom.WorkingRoom5;
import com.example.cluedo_seii.spielbrett.workingRoom.WorkingRoom6;
import com.example.cluedo_seii.spielbrett.workingRoom.WorkingRoom7;
import com.example.cluedo_seii.spielbrett.workingRoom.WorkingRoom8;
import com.example.cluedo_seii.spielbrett.workingRoom.WorkingRoom9;
import com.example.cluedo_seii.spielbrett.bib.Bib1;
import com.example.cluedo_seii.spielbrett.bib.Bib10;
import com.example.cluedo_seii.spielbrett.bib.Bib2;
import com.example.cluedo_seii.spielbrett.bib.Bib3;
import com.example.cluedo_seii.spielbrett.bib.Bib4;
import com.example.cluedo_seii.spielbrett.bib.Bib5;
import com.example.cluedo_seii.spielbrett.bib.Bib6;
import com.example.cluedo_seii.spielbrett.bib.Bib7;
import com.example.cluedo_seii.spielbrett.bib.Bib8;
import com.example.cluedo_seii.spielbrett.bib.Bib9;

import java.util.ArrayList;
import java.util.List;

import static com.example.cluedo_seii.R.drawable.b12;

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
                        gameboardElement = new RoomElement(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        break;
                    case '4':
                        gameboardElement = new Entrace1(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.e1);
                        break;
                    case '5':
                        gameboardElement = new Entrace2(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.e2);
                        break;
                    case '6':
                        gameboardElement = new Entrace3(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.e3);
                        break;
                    case '7':
                        gameboardElement = new Entrace4(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.e4);
                        break;
                    case '8':
                        gameboardElement = new Entrace5(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.e5);
                        break;
                    case '9':
                        gameboardElement = new Entrace6(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.e6);
                        break;
                    case 'a':
                        gameboardElement = new Entrace7(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.e7);
                        break;
                    case 'b':
                        gameboardElement = new Entrace8(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.e9);
                        break;
                    case 'c':
                        gameboardElement = new WorkingRoom1(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.a1);
                        break;
                    case 'd':
                        gameboardElement = new WorkingRoom2(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.a2);
                        break;
                    case 'e':
                        gameboardElement = new WorkingRoom3(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.a3);
                        break;
                    case 'f':
                        gameboardElement = new WorkingRoom4(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.a4);
                        break;
                    case 'g':
                        gameboardElement = new WorkingRoom5(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.a5);
                        break;
                    case 'h':
                        gameboardElement = new WorkingRoom6(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.a6);
                        break;
                    case 'i':
                        gameboardElement = new WorkingRoom7(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.a7);
                        break;
                    case 'j':
                        gameboardElement = new WorkingRoom8(gameboardScreen, x, y, new LinearLayout.LayoutParams
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
                        gameboardElement = new WorkingRoom9(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.a9);
                        break;
                    case 'l':
                        gameboardElement = new Bib1(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.bib1);
                        break;
                    case 'm':
                        gameboardElement = new Bib2(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.bib2);
                        break;
                    case 'n':
                        gameboardElement = new Bib3(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.bib3);
                        break;
                    case 'o':
                        gameboardElement = new Bib4(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.bib4);
                        break;
                    case 'p':
                        gameboardElement = new Bib5(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.bib5);
                        break;
                    case 'q':
                        gameboardElement = new Bib6(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.bib6);
                        break;
                    case 'r':
                        gameboardElement = new Bib7 (gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.bib7);
                        break;
                    case 's':
                        gameboardElement = new Bib8(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.bib8);
                        break;
                    case 't':
                        gameboardElement = new Bib9(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.bib9);
                        break;
                    case 'u':
                        gameboardElement = new Bib10(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.bib10);
                        break;
                    case 'v':
                        gameboardElement = new Billard1(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b1);
                        break;
                    case 'w':
                        gameboardElement = new Billard2(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b2);
                        break;
                    case 'x':
                        gameboardElement = new Billard3(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b3);
                        break;
                    case 'y':
                        gameboardElement = new Billard4(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b4);
                        break;
                    case 'z':
                        gameboardElement = new Billard5(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b5);
                        break;
                    case 'B':
                        gameboardElement = new Billard6(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b6);
                        break;
                    case 'C':
                        gameboardElement = new Billard7(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b7);
                        break;
                    case 'D':
                        gameboardElement = new Billard8(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b8);
                        break;
                    case 'E':
                        gameboardElement = new Billard9(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b9);
                        break;
                    case 'F':
                        gameboardElement = new Billard10(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b10);
                        break;
                    case 'G':
                        gameboardElement = new Billard11(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b11);
                        break;
                    case 'H':
                        gameboardElement = new Billard12(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(R.drawable.b13);
                        break;
                    case 'I':
                        gameboardElement = new Billard12(gameboardScreen, x, y, new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT));
                        gameboardElement.getGameBoardElement().setImageResource(b12);
                        break;
                }

                if (gameboardElement != null) {
                    gameboardElement.getGameBoardElement().setTag(y + 1 + (x * 4));
                    gameboardElement.getGameBoardElement().setId(y + 1 + (x * 4));
                    row.addView(gameboardElement.getGameBoardElement());
                }

                listeGameboardElemente.add(gameboardElement);

                // welche Felder an welcher Position eingespeichert werden sollen
                // Beispiel: if(x == 1 && y == 2) { listeSpielbrettElemente.add(new Raum(x,y,32,32)); }
                // else => Spielfeld
            }
            layout.addView(row);
        }

        this.startingPointTotal = startingPointCounter-1;
    }

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

    private void berechneWeg(/* Spieler möchte diese Koordinaten begehen */) {
        /*
            Beispiel: Spieler würfelt 7
            Spieler geht 3 Schritte => Anzeige mit Information (Spieler ist 3 Schritte gegangen und muss noch 4 weitere gehen)
            => erst wenn Spieler die gewürfelte Zahl gegangen ist kann der nächste Spieler dran sein oder ein Ereignis (Raum) passiert
         */
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
