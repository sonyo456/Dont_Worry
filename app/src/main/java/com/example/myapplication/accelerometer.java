package com.example.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class accelerometer {
    private SensorManager sensorManager;
    public  accelerometer(){
       // sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener((SensorEventListener) this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    public void onSensorChanged(SensorEvent event) {
        double ax, ay, az;
        double svm;
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            ax=event.values[0];
            ay=event.values[1];
            az=event.values[2];
            svm = Math.sqrt(Math.pow(ax,2)+Math.pow(ay,2)+Math.pow(az,2));
            System.out.println("x:" + ax + "y:" + ay+"z: "+ az);
        }
    }

}
