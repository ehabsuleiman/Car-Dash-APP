package com.example.alrowais.cardash;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        final EditText editModel = (EditText) findViewById(R.id.editModel);
        final EditText editYear = (EditText) findViewById(R.id.editYear);
        final EditText editLplate = (EditText) findViewById(R.id.editLplate);
        final EditText editVIN = (EditText) findViewById(R.id.editVIN);
        final EditText editinsuname = (EditText) findViewById(R.id.editIncename);
        final EditText editpolicyname = (EditText) findViewById(R.id.editPolicynum);

        final Button bUpdate = (Button) findViewById(R.id.bUpdate);
        final Button bCancel = (Button) findViewById(R.id.bCancel);

        Intent intent = getIntent();
        String model = intent.getStringExtra("model");
        String year = intent.getStringExtra("year");
        String lplate = intent.getStringExtra("lplate");
        String vin = intent.getStringExtra("vin");
        String insurancename = intent.getStringExtra("insurancename");
        String policynumber = intent.getStringExtra("policynumber");


        editModel.setText(model);
        editYear.setText(year);
        editLplate.setText(lplate);
        editVIN.setText(vin);
        editinsuname.setText(insurancename);
        editpolicyname.setText(policynumber);


        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String model = editModel.getText().toString();
                final String year = editYear.getText().toString();
                final String lplate = editLplate.getText().toString();
                final String vin = editVIN.getText().toString();
                final String insurancename = editinsuname.getText().toString();
                final String policynumber = editpolicyname.getText().toString();


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
                                    String email = intent.getStringExtra("email");
                                    String password = intent.getStringExtra("password");
                                    String plat = intent.getStringExtra("plat");
                                    String plong = intent.getStringExtra("plong");


                                    Intent intentI = new Intent(UpdateActivity.this, UserAreaActivity.class);

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


                                    UpdateActivity.this.startActivity(intentI);

                            } else {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
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
                UpdateRequest updateRequest = new UpdateRequest(model,  year, lplate, vin, insurancename, policynumber, username, responseListener);
                RequestQueue queue = Volley.newRequestQueue(UpdateActivity.this);
                queue.add(updateRequest);
            }
        });


        bCancel.setOnClickListener(new View.OnClickListener() {
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

                Intent intentI = new Intent(UpdateActivity.this, UserAreaActivity.class);

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


                UpdateActivity.this.startActivity(intentI);
            }
        });


    }
}
