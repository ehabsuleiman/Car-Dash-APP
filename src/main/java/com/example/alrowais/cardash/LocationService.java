package com.example.alrowais.cardash;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;


public class LocationService extends Service implements GpsStatus.Listener {
    protected Location mCurrentLocation;
    private LocationListener listener;
    private LocationManager locationManager;
    Location lStart, lEnd;
    double distance;
    double cspeed = 0;
    double oldspeed = 0;
    double accelertaion = 0;
    double MaxSpeed = 0;
    private GpsStatus mStatus;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        UserAreaActivity.startTime = System.currentTimeMillis();


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


                if (location.getAccuracy() < 50) {

                    if (location == null)
                        UserAreaActivity.speed.setText("-.- M/H");
                    else {
                        cspeed = location.getSpeed();
                        if (cspeed > UserAreaActivity.speedAlarm)
                            UserAreaActivity.isenabled = false;
                        else
                            UserAreaActivity.isenabled = true;


                        if (UserAreaActivity.iskm == false) {
                            UserAreaActivity.speed.setText(new DecimalFormat("#.##").format(cspeed * 2.23694) + " M/H");

                            if (cspeed * 2.23694 > UserAreaActivity.speedAlarm) {
                                UserAreaActivity.isenabled = false;
                                UserAreaActivity.speed.setTextColor(Color.RED);
                                Animation anim = new AlphaAnimation(0.0f, 1.0f);
                                anim.setDuration(50); //You can manage the time of the blink with this parameter
                                anim.setStartOffset(20);
                                anim.setRepeatMode(Animation.REVERSE);
                                anim.setRepeatCount(Animation.INFINITE);
                                UserAreaActivity.speed.startAnimation(anim);
                            } else {
                                UserAreaActivity.isenabled = true;
                                UserAreaActivity.speed.setTextColor(Color.GREEN);
                                UserAreaActivity.speed.clearAnimation();
                            }
                        } else {
                            if (cspeed * 3.6 > UserAreaActivity.speedAlarm)
                                UserAreaActivity.isenabled = false;
                            else
                                UserAreaActivity.isenabled = true;
                            UserAreaActivity.speed.setText(new DecimalFormat("#.##").format(cspeed * 3.6) + " K/H");
                        }
                    }

                    mCurrentLocation = location;
                    if (lStart == null) {
                        lStart = mCurrentLocation;
                        lEnd = mCurrentLocation;
                    } else
                        lEnd = mCurrentLocation;


                    if (location.getSpeed() != 0)
                        distance = distance + (lStart.distanceTo(lEnd));
                    if (UserAreaActivity.iskm == false) {
                        UserAreaActivity.dist.setText(new DecimalFormat("#.##").format(distance / 1000.00 * 0.621371) + " Miles.");

                    } else
                        UserAreaActivity.dist.setText(new DecimalFormat("#.##").format(distance * .001) + " Kilos.");

                    lStart = lEnd;


                    UserAreaActivity.endTime = System.currentTimeMillis();
                    long diff = UserAreaActivity.endTime - UserAreaActivity.startTime;
                    diff = TimeUnit.MILLISECONDS.toSeconds(diff);
                    if (diff % 60 < 10)
                        UserAreaActivity.time.setText(TimeUnit.SECONDS.toMinutes(diff) + ":" + "0" + diff % 60);
                    else
                        UserAreaActivity.time.setText(TimeUnit.SECONDS.toMinutes(diff) + ":" + diff % 60);

                    if (MaxSpeed < cspeed)
                        MaxSpeed = cspeed;
                    if (UserAreaActivity.iskm == false) {
                        UserAreaActivity.MaxSpeed.setText("" + new DecimalFormat("#.##").format(MaxSpeed * 2.23694) + " M/H");
                    } else
                        UserAreaActivity.MaxSpeed.setText("" + new DecimalFormat("#.##").format(MaxSpeed * 3.6) + " K/H");

                    if (UserAreaActivity.iskm == false)
                        UserAreaActivity.averagespeed.setText("" + new DecimalFormat("#.##").format((distance / diff) * 2.23694));
                    else
                        UserAreaActivity.averagespeed.setText("" + new DecimalFormat("#.##").format((distance / diff) * 3.6));

                    accelertaion = cspeed - oldspeed;
                    UserAreaActivity.acceleration.setText("" + new DecimalFormat("#.##").format(accelertaion) + " M/S^2");

                    oldspeed = location.getSpeed();
                }


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            public double getspeed() {
                return cspeed;

            }


            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                UserAreaActivity.isenabled = true;

                startActivity(i);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.addGpsStatusListener(this);

        //noinspection MissingPermission
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);

    }

    public static boolean checkPermission(final Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            //noinspection MissingPermission
            locationManager.removeUpdates(listener);
        }
    }

    @Override

        public void onGpsStatusChanged (int event){
            int satellites = 0;
            int satellitesInFix = 0;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            int timetofix = locationManager.getGpsStatus(null).getTimeToFirstFix();

            for (GpsSatellite sat : locationManager.getGpsStatus(null).getSatellites()) {
                if(sat.usedInFix()) {
                    satellitesInFix++;
                }
                satellites++;
            }
            if(satellitesInFix<7)
                 UserAreaActivity.satilite.setTextColor(Color.YELLOW);
            else
                UserAreaActivity.satilite.setTextColor(Color.GREEN);
            UserAreaActivity.satilite.setText("" + satellitesInFix);



    }
}