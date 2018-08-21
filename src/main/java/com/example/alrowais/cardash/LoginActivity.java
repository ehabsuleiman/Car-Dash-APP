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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText editLoginU = (EditText) findViewById(R.id.editLoginU);
        final EditText editLoginP = (EditText) findViewById(R.id.editLoginP);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView tvRegisterHere = (TextView) findViewById(R.id.tvRegisterHere);
        final TextView tvForgotpass = (TextView) findViewById(R.id.tvForgotpass);

        tvRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        tvForgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, ForgotActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = editLoginU.getText().toString();
                final String password = editLoginP.getText().toString();

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


                                Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("username", username);
                                intent.putExtra("password", password);
                                intent.putExtra("age", age);
                                intent.putExtra("email", email);
                                intent.putExtra("model", model);
                                intent.putExtra("year", year);
                                intent.putExtra("lplate", lplate);
                                intent.putExtra("vin", vin);
                                intent.putExtra("insurancename", insurancename);
                                intent.putExtra("policynumber", policynumber);
                                intent.putExtra("plat", plat);
                                intent.putExtra("plong", plong);


                                LoginActivity.this.startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
                if(username.equals("") || password.equals("")){
                    if(username.equals("")){
                        editLoginU.setError("Please enter your username");
                    }
                    if(password.equals("")){
                        editLoginP.setError("Please enter your password");
                    }
                }else {
                    LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
