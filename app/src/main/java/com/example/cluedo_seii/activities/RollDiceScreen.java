package com.example.cluedo_seii.activities;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.GameState;
import com.example.cluedo_seii.R;
import com.example.cluedo_seii.sensorik.ShakeDetector;
import java.util.Random;

public class RollDiceScreen extends Activity {

    float y1, y2;
    static final int MIN_SWIPE_DISTANCE = 150;
    private SensorManager sensorManager;
    private Sensor accel;
    private ShakeDetector shakeDetector;
    private TextView textView;
    private ImageView imageViewLeftDice, imageViewRightDice;

    private int diceValueOne, diceValueTwo;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_dices_screen);
        imageViewLeftDice = findViewById(R.id.diceView1);
        imageViewRightDice = findViewById(R.id.diceView2);
        game = Game.getInstance();

        //shakeEvent
        textView = findViewById(R.id.tvShake);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); //initialize Shake Detector
        assert sensorManager != null;
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetector = new ShakeDetector();
        shakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            //dices
            @Override
            public void onShake(int count) {


                // animation for dices
                final Animation anim1 = AnimationUtils.loadAnimation(RollDiceScreen.this, R.anim.shake);
                final Animation anim2 = AnimationUtils.loadAnimation(RollDiceScreen.this, R.anim.shake);

                final Animation.AnimationListener animationListener = new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        int value1 = randomDiceValue();
                        int value2 = randomDiceValue();
                        game.setDiceTwo(value1);
                        game.setDiceTwo(value2);

                      /*  int sum = value1+value2;
                        int res = getResources().getIdentifier("dice" + value1, "drawable", getPackageName());
                        int res2 = getResources().getIdentifier("dice" + value2, "drawable", getPackageName());


                        imageViewLeftDice.setImageResource(res);
                        imageViewRightDice.setImageResource(res2);

                        textView.setText("You rolled " + value1 + " + " + value2 + " = "+sum+"!");
                        diceValueOne = value1;
                        diceValueTwo = value2;*/

                        finish();

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                };
                anim1.setAnimationListener(animationListener);
                anim2.setAnimationListener(animationListener);

                imageViewLeftDice.startAnimation(anim1);
                imageViewRightDice.startAnimation(anim2);

            }

        });


    }
    //shakeEvent
    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeDetector, accel, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        sensorManager.unregisterListener(shakeDetector);
        super.onPause();
        textView.setText(R.string.NoShakeAction);
    }

    @Override
    public void onStop(){
        super.onStop();
        game.changeGameState(GameState.PLAYERMOVEMENT);
    }

    //dices
    public static final Random random = new Random();

    public static int randomDiceValue(){
        return random.nextInt(6)+1;
    }


    @Override
    public boolean dispatchTouchEvent (MotionEvent touchEvent){
        switch(touchEvent.getAction()){

            case MotionEvent.ACTION_DOWN:
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                y2 = touchEvent.getY();

                float swipeUp = y1-y2;

                if(swipeUp > MIN_SWIPE_DISTANCE){
                    finish();
                    overridePendingTransition(R.anim.slide_up_in, R.anim.slide_up_out);
                }
                break;
        }
        return false;
    }

    public int getDiceValueOne() {
        return diceValueOne;
    }

    public void setDiceValueOne(int diceValueOne) {
        this.diceValueOne = diceValueOne;
    }

    public int getDiceValueTwo() {
        return diceValueTwo;
    }

    public void setDiceValueTwo(int diceValueTwo) {
        this.diceValueTwo = diceValueTwo;
    }
}