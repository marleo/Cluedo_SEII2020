package com.example.cluedo_seii.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.example.cluedo_seii.R;
import static android.hardware.Sensor.TYPE_LIGHT;

public class CheatingScreen extends Activity {
    private SensorManager sensorManager;
    private Sensor light;
    private SensorEventListener sensorEventListener;
    private TextView v;
    private TextView lightTV;
    private float value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheating);
        v = findViewById(R.id.lightTV);
        lightTV = findViewById(R.id.lightSensorTV);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        assert sensorManager != null;
        light = sensorManager.getDefaultSensor(TYPE_LIGHT);
        if(light == null){
        lightTV.setText(R.string.NoLightSensor);
        }
        value = light.getMaximumRange();

        sensorEventListener = new SensorEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSensorChanged(SensorEvent event) {
                double val = event.values[0];
                lightTV.setText("Luminosity: "+val+" lx");

                int newVal = (int) (255f*val/value);
                v.setBackgroundColor(Color.rgb(newVal, newVal, newVal));

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, light, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }
}
