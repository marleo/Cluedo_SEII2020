package com.example.cluedo_seii.activities;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cluedo_seii.R;
import com.example.cluedo_seii.sensorik.ShakeDetector;
import java.util.Random;

public class RollDiceScreen extends Activity {
    private SensorManager sensorManager;
    private Sensor accel;
    private ShakeDetector shakeDetector;
    private TextView textView;
    private ImageView imageViewLeftDice, imageViewRightDice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_dices_screen);
        imageViewLeftDice = findViewById(R.id.diceView1);
        imageViewRightDice = findViewById(R.id.diceView2);

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
                        int res = getResources().getIdentifier("dice"+value1, "drawable","com.example.cluedo_seii");
                        int res2 = getResources().getIdentifier("dice"+value2, "drawable","com.example.cluedo_seii");

                        if(animation ==anim1){
                            imageViewLeftDice.setImageResource(res);
                        }
                        if (animation ==anim2){
                            imageViewRightDice.setImageResource(res2);
                        }
                        textView.setText("You rolled a "+ value1+" and a "+value2+"!");
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

    //dices
    public static final Random random = new Random();

    public static int randomDiceValue(){
        return random.nextInt(6)+1;
    }

    }


