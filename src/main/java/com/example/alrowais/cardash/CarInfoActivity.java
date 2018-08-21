package com.example.alrowais.cardash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CarInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);
        Intent intent = getIntent();
        String model = intent.getStringExtra("model");
        String year = intent.getStringExtra("year");
        String lplate = intent.getStringExtra("lplate");
        String vin = intent.getStringExtra("vin");
        String insurancename = intent.getStringExtra("insurancename");
        String policynumber = intent.getStringExtra("policynumber");


        final TextView tvModel = (TextView) findViewById(R.id.tvmodel);
        final TextView tvYear = (TextView) findViewById(R.id.tvYear);
        final TextView tvlicene = (TextView) findViewById(R.id.tvLicene);
        final TextView tvvin = (TextView) findViewById(R.id.tvvin);
        final TextView tvinsurancename = (TextView) findViewById(R.id.tvinsurancename);
        final TextView tvpolicynumber = (TextView) findViewById(R.id.tvpolicynumber);
        final Button bupdate = (Button) findViewById(R.id.bupdate);

        String message1 = "Car Model: " + model;
        tvModel.setText(message1);

        String message2 = "Car Year: " + year;
        tvYear.setText(message2);

        String message3 = "Car Licence Plate: " + lplate;
        tvlicene.setText(message3);

        String message4 = "Car VIN: " + vin;
        tvvin.setText(message4);

        String message5 = "Insurance Company: " + insurancename;
        tvinsurancename.setText(message5);

        String message6 = "Policy Number: " + policynumber;
        tvpolicynumber.setText(message6);


        bupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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



                Intent intentI = new Intent(CarInfoActivity.this, UpdateActivity.class);

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


                CarInfoActivity.this.startActivity(intentI);
            }
        });
    }
}
