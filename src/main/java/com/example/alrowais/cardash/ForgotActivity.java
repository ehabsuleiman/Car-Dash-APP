package com.example.alrowais.cardash;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        final EditText editusername = (EditText) findViewById(R.id.etuser);
        final EditText editAnswer = (EditText) findViewById(R.id.etAnswer);
        final Button bgetpass = (Button) findViewById(R.id.bGetPass);
        final Button bcancel = (Button) findViewById(R.id.bCancel);

        bcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(ForgotActivity.this, LoginActivity.class);
                ForgotActivity.this.startActivity(registerIntent);
            }
        });


        bgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = editusername.getText().toString();
                final String registrationnumber = editAnswer.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                String name = jsonResponse.getString("name");
                                String age = jsonResponse.getString("age");
                                String password = jsonResponse.getString("password");
                                String email = jsonResponse.getString("email");
                                String model = jsonResponse.getString("model");
                                String year = jsonResponse.getString("year");
                                String lplate = jsonResponse.getString("lplate");
                                String vin = jsonResponse.getString("vin");
                                String insurancename = jsonResponse.getString("insurancename");
                                String policynumber = jsonResponse.getString("policynumber");
                                String plat = jsonResponse.getString("plat");
                                String plong = jsonResponse.getString("plong");


                                AlertDialog.Builder builder = new AlertDialog.Builder(ForgotActivity.this);
                                builder.setMessage("your password is " + password)
                                        .setNegativeButton("Done", null)
                                        .create()
                                        .show();

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(ForgotActivity.this);
                                builder.setMessage("Username or Password is not correct!")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                if(username.equals("") || registrationnumber.equals("")){
                    if(username.equals("")){
                        editusername.setError("Please enter your username");
                    }
                    if(registrationnumber.equals("")){
                        editAnswer.setError("Please enter your answer");
                    }
                }else {
                    ForgotRequest forgotRequest = new ForgotRequest(username, registrationnumber, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ForgotActivity.this);
                    queue.add(forgotRequest);
                }
            }
        });
    }

}
