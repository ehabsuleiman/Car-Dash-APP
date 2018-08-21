package com.example.alrowais.cardash;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;


public class UserAreaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ConnectionCallbacks, OnConnectionFailedListener {

    final Context context = this;
    private Button SetSpeed;


    String lat, lon;
    protected static final String TAG = "basic-location-sample";
    //weather stuff heeeeeere
    String finalweather;
    private TextView mTextView;
    static boolean iskm = false;
    static boolean isenabled = true;
    //layout stuff heeeeeeeeeeeeeeeeeeere
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;

    //location stuff here
    LocationService myService;
    static boolean status;
    LocationManager locationManager;
    static EditText userinput;
    static TextView dist, time, speed, MaxSpeed, averagespeed, acceleration, satilite;
    Button starttttt, pause, stoppppp, km;
    static long startTime, endTime;
    protected Location mLastLocation;
    protected GoogleApiClient mGoogleApiClient;
    static double speedAlarm;
    static String userinputtext;


    private TextView textView;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent i = new Intent(getApplicationContext(), LocationService.class);
        stopService(i);
    }

    double lat1, lon1;
    int state = 0;
    int state2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        final TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        starttttt = (Button) findViewById(R.id.btn_start);
        SetSpeed = (Button) findViewById(R.id.SetSpeed);
        speedAlarm = 10000;

        stoppppp = (Button) findViewById(R.id.btn_stop);
        km = (Button) findViewById(R.id.switchkm);
        //weather stuff hereeeeeeeeeeeeeeeeeeeee
        mTextView = (TextView) findViewById(R.id.tvWeatherD);
        buildGoogleApiClient();


        //layout heeeeeeeeeeere
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mtoggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        String message = name + "! welcome ";
        tvWelcome.setText(message);

        enable_buttons();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        dist = (TextView) findViewById(R.id.tvDistanceD);
        time = (TextView) findViewById(R.id.tvTimeD);
        speed = (TextView) findViewById(R.id.tvSpeedD);
        acceleration = (TextView) findViewById(R.id.tvAccelerationD);
        MaxSpeed = (TextView) findViewById(R.id.tvMaxSpeed);
        averagespeed = (TextView) findViewById(R.id.tvAveragespeed);
        satilite = (TextView) findViewById(R.id.sat);

    }

    private boolean ison = false;

    private void enable_buttons() {
        starttttt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                boolean gps_enabled = false;
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);


                showPermissionDialog();

                if (!isServiceRunning() && LocationService.checkPermission(UserAreaActivity.this)) {
                    Intent i = new Intent(getApplicationContext(), LocationService.class);
                    startService(i);
                }

                if (!gps_enabled) {
                    Intent i = new Intent(getApplicationContext(), LocationService.class);
                    stopService(i);
                }

            }
        });

        stoppppp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(UserAreaActivity.this);
                builder.setMessage("Are you sure you Want to Reset All Values?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                Intent i = new Intent(getApplicationContext(), LocationService.class);
                                stopService(i);
                                ison = false;
                                Toast.makeText(UserAreaActivity.this, getResources().getString(R.string.stop_updates), Toast.LENGTH_SHORT).show();
                                UserAreaActivity.isenabled = true;
                                speed.setText("0.00");
                                dist.setText("0.00");
                                time.setText("0 Minutes");
                                MaxSpeed.setText("0.00");
                                averagespeed.setText("0.00");
                                acceleration.setText("0.00");
                                satilite.setText("Satellites");

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                dialog.cancel();
                            }
                        });
                final AlertDialog alert = builder.create();
                alert.show();





            }
        });
        km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iskm == false)
                    iskm = true;
                else
                    iskm = false;
            }
        });
        SetSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view2 = (LayoutInflater.from(UserAreaActivity.this)).inflate(R.layout.user_input, null);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(UserAreaActivity.this);
                alertBuilder.setView(view2);

                final EditText userinput = (EditText) view2.findViewById(R.id.userinput);


                alertBuilder.setCancelable(true)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    userinputtext = userinput.getText().toString();
                                    speedAlarm = Double.parseDouble(userinputtext);
                                } catch (NumberFormatException e) {

                                }

                            }
                        });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_my, menu);
        return super.onCreateOptionsMenu(menu);

    }



    public boolean onOptionsItemSelected(MenuItem item) {
        if (mtoggle.onOptionsItemSelected(item))//drawer stuff
            return true;
        switch (item.getItemId()) {
            case R.id.action_setting:
                openSetting();
                return true;

            case R.id.action_logout:
                openLogout();
                return true;
            case R.id.refresh:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return true;
                }
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if(mLastLocation!=null) {
                    double lat1 = mLastLocation.getLatitude();
                    double lon1 = mLastLocation.getLongitude();
                    lat = "" + lat1;
                    lon = "" + lon1;
                }
                JsonObjectRequest request = new JsonObjectRequest("http://api.wunderground.com/api/3e56aad298ae1f7c/conditions/q/" + lat + "," + lon + ".json", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //mTextView.setText(response.toString());
                                finalweather = parseAndNotify(response.toString());
                                mTextView.setText(finalweather);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                mTextView.setText(error.toString());
                            }
                        }
                );
                // With the request created, simply add it to our Application's RequestQueue
                TempApplication.getInstance().getRequestQueue().add(request);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openSetting() {
        if (state == 1) {

        } else {
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
            String plat = intent.getStringExtra("plat");
            String plong = intent.getStringExtra("plong");


            Intent intentI = new Intent(UserAreaActivity.this, AccountUpdateActivity.class);

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
            intentI.putExtra("plat", plat);
            intentI.putExtra("plong", plong);



            UserAreaActivity.this.startActivity(intentI);
        }

    }

    public void openLogout() {
        if (state == 1) {

        } else {

            Intent intent = new Intent(UserAreaActivity.this, LoginActivity.class);
            UserAreaActivity.this.startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.action_logout) {
            openLogout();
        }

        if (id == R.id.action_settings) {
            openSetting();
        }
        if (isenabled) {
            if (id == R.id.parking_guide) {



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
                String plat = intent.getStringExtra("plat");
                String plong = intent.getStringExtra("plong");



                Intent intentI = new Intent(UserAreaActivity.this, ParkGuidActivity.class);

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
                intentI.putExtra("plat", plat);
                intentI.putExtra("plong", plong);


                UserAreaActivity.this.startActivity(intentI);

            }
            if (id == R.id.compass) {


                Intent intent = new Intent(UserAreaActivity.this, Compass.class);
                UserAreaActivity.this.startActivity(intent);
            }

            if (id == R.id.cari) {

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
                String plat = intent.getStringExtra("plat");
                String plong = intent.getStringExtra("plong");



                Intent intentI = new Intent(UserAreaActivity.this, CarInfoActivity.class);

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
                intentI.putExtra("plat", plat);
                intentI.putExtra("plong", plong);


                UserAreaActivity.this.startActivity(intentI);

            }
            if (id == R.id.music) {

                Intent intentm = getPackageManager().getLaunchIntentForPackage("com.google.android.music");
                startActivity(intentm);

            }
            if (id == R.id.maps) {

                Intent intentp = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps");
                startActivity(intentp);

            }


            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        else
            return true;

    }
    private void showPermissionDialog() {
        if (!LocationService.checkPermission(this)) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    10);
        }
    }

    private String parseAndNotify(String theResult) {
        JSONTokener theTokener = new JSONTokener(theResult);
        JSONObject theWeatherResult;
        try {
            theWeatherResult = (JSONObject) theTokener.nextValue();
            if (theWeatherResult != null) {
                JSONObject curWeather = theWeatherResult.getJSONObject("current_observation");
                JSONObject curCity = curWeather.getJSONObject("observation_location");

                String temperature = curWeather.getString("temperature_string");
                String weather = curWeather.getString("weather");
                String city = curCity.getString("full");

                return "The weather is " + weather + ", with " + temperature;
            }
        } catch (JSONException e) {
            // Log.e(INNER_TAG, e.getMessage());
        }
        return null;
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case wh
            // ere the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLastLocation!=null) {
            double lat1 = mLastLocation.getLatitude();
            double lon1 = mLastLocation.getLongitude();
            lat = "" + lat1;
            lon = "" + lon1;
        }
        // JsonObjectRequest request = new JsonObjectRequest("http://cblunt.github.io/blog-android-volley/response.json", null,
        JsonObjectRequest request = new JsonObjectRequest("http://api.wunderground.com/api/3e56aad298ae1f7c/conditions/q/" + lat + "," + lon + ".json", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //mTextView.setText(response.toString());
                        finalweather = parseAndNotify(response.toString());
                        mTextView.setText(finalweather);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTextView.setText(error.toString());
                    }
                }
        );
        // With the request created, simply add it to our Application's RequestQueue
        TempApplication.getInstance().getRequestQueue().add(request);

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
    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if("com.example.MyNeatoIntentService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onBackPressed() {

    }
}
