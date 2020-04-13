package com.example.cluedo_sensorik;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;



public class ShakeService extends Service implements SensorEventListener{
    private float accel; //acceleration ignoring gravity
    private float accelGravCurr; //acceleration including gravity, current


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        Sensor sensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_UI, new Handler());
        return START_STICKY;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float accelGravLast = accelGravCurr; //assigning current acceleration to latest acceleration
        accelGravCurr = (float) Math.sqrt(x * x + y * y + z * z);
        float delta = accelGravCurr - accelGravLast;
        accel = accel * 0.9f + delta; // perform low-cut filter

        if (accel > 11) {

            Toast.makeText(getApplicationContext(),"Service detects the Shake Action!!",Toast.LENGTH_LONG).show();

        }
    }
}
