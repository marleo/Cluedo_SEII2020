package com.example.cluedo_seii.sensorik;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;


public class ShakeService extends Service implements SensorEventListener{
    private float accel; //acceleration ignoring gravity
    private float accelGrav; //acceleration including gravity


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

        float accelGravLast = accelGrav; //assigning current acceleration to latest acceleration
        accelGrav = (float) Math.sqrt(x * x + y * y + z * z);
        float delta = accelGrav - accelGravLast;
        accel = accel * 0.9f + delta; // perform low-cut filter

    }
}
