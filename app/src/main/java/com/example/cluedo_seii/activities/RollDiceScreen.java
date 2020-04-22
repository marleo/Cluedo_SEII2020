package com.example.cluedo_seii.activities;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import com.example.cluedo_seii.R;
import com.example.cluedo_sensorik.ShakeDetector;

public class RollDiceScreen extends Activity {

        private SensorManager sensorManager;
        private Sensor accel;
        private ShakeDetector shakeDetector;

        private TextView textView;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
             setContentView(R.layout.activity_roll_dices_screen);

            textView = findViewById(R.id.tvShake);


            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); //initialize Shake Detector
            assert sensorManager != null;
            accel = sensorManager
                    .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            shakeDetector = new ShakeDetector();
            shakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

                @Override
                public void onShake(int count) {
                    textView.setText(R.string.ShakeEventDetected);
                }
            });

        }
        @Override
        public void onResume() {
            super.onResume();
            sensorManager.registerListener(shakeDetector, accel,	SensorManager.SENSOR_DELAY_UI);
        }

        @Override
        public void onPause() {
            sensorManager.unregisterListener(shakeDetector);
            super.onPause();
            textView.setText(R.string.NoShakeAction);
        }
    }


