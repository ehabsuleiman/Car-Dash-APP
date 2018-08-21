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

public class UpdatePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        final EditText editOldP = (EditText) findViewById(R.id.editOldP);
        final EditText editNewP1 = (EditText) findViewById(R.id.editNewP1);
        final EditText editNewP2 = (EditText) findViewById(R.id.editNewP2);
        final Button bUpdate = (Button) findViewById(R.id.bUpdate);
        final Button bCancel = (Button) findViewById(R.id.bCancel);


        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String password = intent.getStringExtra("password");
                String username = intent.getStringExtra("username");
                final String oldp = editOldP.getText().toString();
                final String newp1 = editNewP1.getText().toString();
                final String newp2 = editNewP2.getText().toString();

                if(oldp.equals(password)) {
                    if(newp1.equals(newp2)) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
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
                                        String model = intent.getStringExtra("model");
                                        String year = intent.getStringExtra("year");
                                        String lplate = intent.getStringExtra("lplate");
                                        String vin = intent.getStringExtra("vin");
                                        String insurancename = intent.getStringExtra("insurancename");
                                        String policynumber = intent.getStringExtra("policynumber");
                                        String plat = intent.getStringExtra("plat");
                                        String plong = intent.getStringExtra("plong");


                                        Intent intentI = new Intent(UpdatePasswordActivity.this, UserAreaActivity.class);

                                        intentI.putExtra("name", name);
                                        intentI.putExtra("username", username);
                                        intentI.putExtra("password", newp1);
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


                                        UpdatePasswordActivity.this.startActivity(intentI);

                                    } else {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(UpdatePasswordActivity.this);
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
                        if (oldp.equals("") || newp1.equals("") || newp1.equals("") || newp2.equals("")){
                            if (oldp.equals("")) {
                                editOldP.setError("Please enter your old password");
                            }
                            if (newp1.equals("")) {
                                editNewP1.setError("Please enter your new password");
                            }
                            if (newp2.equals("")) {
                                editNewP2.setError("Please enter your old password");
                            }

                        }else{
                            UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest(username, newp1, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(UpdatePasswordActivity.this);
                            queue.add(updatePasswordRequest);
                        }

                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(UpdatePasswordActivity.this);
                        builder.setMessage("New Passwords do not match...")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                }else{
                    editOldP.setError("Please enter your correct password!");
                }
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

                Intent intentI = new Intent(UpdatePasswordActivity.this, UserAreaActivity.class);

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


                UpdatePasswordActivity.this.startActivity(intentI);
            }
        });
    }
}
