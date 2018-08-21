package com.example.alrowais.cardash;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ehabs on 3/31/2017.
 */



public class Compass extends AppCompatActivity implements SensorEventListener {

    private ImageView imageView;
    private TextView degreeView;
    private float[] mGravity = new float[3];
    private float[] mGeomagnetic = new float[3];
    private float azimuth = 0f;
    private float currectAzimuth = 0f;
    private SensorManager mSensorManager;


    protected  void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.compass_layout);

        imageView = (ImageView) findViewById(R.id.compass);
        degreeView = (TextView) findViewById(R.id.Degree);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);




    }
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this,mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this,mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_GAME);



    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final float alpha = 0.97f;
        synchronized (this){
            if(event.sensor.getType()== Sensor.TYPE_ACCELEROMETER)
            {
                mGravity[0] = alpha*mGravity[0]+ (1-alpha)*event.values[0];
                mGravity[1] = alpha*mGravity[1]+ (1-alpha)*event.values[1];
                mGravity[2] = alpha*mGravity[2]+ (1-alpha)*event.values[2];
            }
            if(event.sensor.getType()== Sensor.TYPE_MAGNETIC_FIELD)
            {
                mGeomagnetic[0] = alpha*mGeomagnetic[0]+ (1-alpha)*event.values[0];
                mGeomagnetic[1] = alpha*mGeomagnetic[1]+ (1-alpha)*event.values[1];
                mGeomagnetic[2] = alpha*mGeomagnetic[2]+ (1-alpha)*event.values[2];
            }
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R,I,mGravity,mGeomagnetic);
            if(success){

                float orientation[] = new float[3];
                SensorManager.getOrientation(R,orientation);
                azimuth = (float)Math.toDegrees(orientation[0]);
                azimuth = (azimuth+360)%360;

                Animation anim = new RotateAnimation(-currectAzimuth,-azimuth,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                currectAzimuth = azimuth;
                degreeView.setText("Degrees: " + currectAzimuth);
                anim.setDuration(500);
                anim.setRepeatCount(0);
                anim.setFillAfter(true);
                imageView.startAnimation(anim);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
