package com.example.alrowais.cardash;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.alrowais.cardash.R.id.editYear;


public class ParkGuidActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,  OnMapReadyCallback
        ,View.OnClickListener{

    protected static final String TAG = "basic-location-sample";

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;

    protected TextView mLatitudeText;
    protected TextView mLongitudeText;
    double lat = 20.44;
    double lont = -80.44;

    private GoogleMap googleMap;
    protected Marker marker1;


    //timer stuff heeeeeeeeeeeeeer
    private Button btn_start, btn_cancel;
    private TextView tv_timer;
    String date_time;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    EditText et_hours;

    SharedPreferences mpref;
    SharedPreferences.Editor mEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_guid);

        mLatitudeText = (TextView) findViewById((R.id.latitude_text));
        mLongitudeText = (TextView) findViewById((R.id.longitude_text));
        final Button bPark = (Button) findViewById(R.id.bPark);

        Intent intent = getIntent();
        String plat = intent.getStringExtra("plat");
        String plong = intent.getStringExtra("plong");
        if(plat.equals("NULL") || plong.equals("NULL")) {
            lat = 0.0;
            lont = 0.0;
        }else{
            lat = Double.parseDouble(plat);
            lont = Double.parseDouble(plong);
        }


        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        buildGoogleApiClient();

        bPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String lat1 = String.valueOf(lat);
                final String lont2 = String.valueOf(lont);



                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {


                                Intent intent = getIntent();
                                String name = intent.getStringExtra("name");
                                String username = intent.getStringExtra("username");
                                String age = intent.getStringExtra("age");
                                String password = intent.getStringExtra("password");
                                String email = intent.getStringExtra("email");
                                String model = intent.getStringExtra("model");
                                String year = intent.getStringExtra("year");
                                String lplate = intent.getStringExtra("lplate");
                                String vin = intent.getStringExtra("vin");
                                String insurancename = intent.getStringExtra("insurancename");
                                String policynumber = intent.getStringExtra("policynumber");


                                Intent intentI = new Intent(ParkGuidActivity.this, UserAreaActivity.class);

                                intentI.putExtra("name", name);
                                intentI.putExtra("username", username);
                                intentI.putExtra("password", password);
                                intentI.putExtra("age", age);
                                intentI.putExtra("email", email);
                                intentI.putExtra("model", model);
                                intentI.putExtra("year", year);
                                intentI.putExtra("lplate", lplate);
                                intentI.putExtra("vin", vin);
                                intentI.putExtra("insurancename", insurancename);
                                intentI.putExtra("policynumber", policynumber);
                                intentI.putExtra("plat", lat1);
                                intentI.putExtra("plong", lont2);


                                ParkGuidActivity.this.startActivity(intentI);

                            } else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(ParkGuidActivity.this);
                                builder.setMessage("Faild to Update..")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                Intent intent = getIntent();
                String username = intent.getStringExtra("username");
                ParkRequest parkRequest = new ParkRequest(username, lat1,  lont2, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ParkGuidActivity.this);
                queue.add(parkRequest);
            }
        });

        //timer stuff heeeeeeeeeeeere
        init();
        listener();

    }

    private void listener() {
        btn_start.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    }

    private void init() {
        btn_start = (Button) findViewById(R.id.btn_timer);
        tv_timer = (TextView) findViewById(R.id.tv_timer);
        et_hours = (EditText) findViewById(R.id.et_hours);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);


        mpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mEditor = mpref.edit();

        try {
            String str_value = mpref.getString("data", "");
            if (str_value.matches("")) {
                et_hours.setEnabled(true);
                btn_start.setEnabled(true);
                tv_timer.setText("");

            } else {

                if (mpref.getBoolean("finish", false)) {
                    et_hours.setEnabled(true);
                    btn_start.setEnabled(true);
                    tv_timer.setText("");
                } else {

                    et_hours.setEnabled(false);
                    btn_start.setEnabled(false);
                    tv_timer.setText(str_value);
                }
            }
        } catch (Exception e) {

        }

    }
        /**
         * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
         */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            lat = mLastLocation.getLatitude();
            lont = mLastLocation.getLongitude();

        } else {
            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }



    @Override
    public void onMapReady(GoogleMap map) {
        //DO WHATEVER YOU WANT WITH GOOGLEMAP
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        if(lat == 0.0 || lont == 0.0){

        }else{
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lont), 14));
            map.addMarker(new MarkerOptions().position(new LatLng(lat,lont)).title("My Car!"));
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_timer:

                try {
                    if (et_hours.getText().toString().length() > 0) {

                        int int_hours = Integer.valueOf(et_hours.getText().toString());

                        if (int_hours <= 24) {


                            et_hours.setEnabled(false);
                            btn_start.setEnabled(false);


                            calendar = Calendar.getInstance();
                            simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                            date_time = simpleDateFormat.format(calendar.getTime());

                            mEditor.putString("data", date_time).commit();
                            mEditor.putString("hours", et_hours.getText().toString()).commit();


                            Intent intent_service = new Intent(getApplicationContext(), Timer_Service.class);
                            startService(intent_service);
                        } else {
                            Toast.makeText(getApplicationContext(), "Please select the value below 24 hours", Toast.LENGTH_SHORT).show();
                        }
/*
                    mTimer = new Timer();
                    mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 5, NOTIFY_INTERVAL);*/
                    } else {
                        Toast.makeText(getApplicationContext(), "Please select value", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e) {

                }
                break;


            case R.id.btn_cancel:


                Intent intent = new Intent(getApplicationContext(),Timer_Service.class);
                stopService(intent);

                mEditor.clear().commit();

                et_hours.setEnabled(true);
                btn_start.setEnabled(true);
                tv_timer.setText("");


                break;

        }

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String str_time = intent.getStringExtra("time");
            tv_timer.setText(str_time);

        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver,new IntentFilter(Timer_Service.str_receiver));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}




