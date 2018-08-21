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


public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText editName = (EditText) findViewById(R.id.editName);
        final EditText editAge = (EditText) findViewById(R.id.editAge);
        final EditText editUser = (EditText) findViewById(R.id.editUser);
        final EditText editPass = (EditText) findViewById(R.id.editPass);
        final EditText editQustion = (EditText) findViewById(R.id.editQustion);
        final EditText editEmail = (EditText) findViewById(R.id.editEmail);
        final EditText editModel = (EditText) findViewById(R.id.editModel);
        final EditText editYear = (EditText) findViewById(R.id.editYear);
        final EditText editLplate = (EditText) findViewById(R.id.editLplate);
        final EditText editVIN = (EditText) findViewById(R.id.editVIN);
        final EditText editinsuname = (EditText) findViewById(R.id.editIncename);
        final EditText editpolicyname = (EditText) findViewById(R.id.editPolicynum);
        final Button bRegister = (Button) findViewById(R.id.bRegister);



        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = editName.getText().toString();
                final String age = editAge.getText().toString();
                final String username = editUser.getText().toString();
                final String password = editPass.getText().toString();
                final String email = editEmail.getText().toString();
                final String model = editModel.getText().toString();
                final String year = editYear.getText().toString();
                final String plate = editLplate.getText().toString();
                final String vin = editVIN.getText().toString();
                final String insurancename = editinsuname.getText().toString();
                final String policynumber = editpolicyname.getText().toString();
                final String registrationnumber = editQustion.getText().toString();
                final String licencenumber = "NULL";
                final String plat = "NULL";
                final String plong = "NULL";

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            boolean checkuser = jsonResponse.getBoolean("checkuser");
                            boolean checkemail = jsonResponse.getBoolean("checkemail");

                            if(success){
                                if(checkuser){
                                    if(checkemail){
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        RegisterActivity.this.startActivity(intent);
                                    }else{
                                        editEmail.setError("The email is used");
                                    }
                                }else{
                                    editUser.setError("The username is taken");
                                }


                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed!")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                if(isEmailValid(email)) {


                    if ((name.equals("") || username.equals("") || password.equals("") || email.equals("") || age.equals("") || registrationnumber.equals(""))) {
                        if (name.equals("")) {
                            editName.setError("Please enter your name");
                        }
                        if (username.equals("")) {
                            editUser.setError("Please enter your username");
                        }
                        if (password.equals("")) {
                            editPass.setError("Please enter your password");
                        }
                        if (email.equals("")) {
                            editEmail.setError("Please enter your email");
                        }
                        if (age.equals("")) {
                            editAge.setError("Please enter your age");
                        }
                        if (registrationnumber.equals("")) {
                            editQustion.setError("Please enter your answer");
                        }
                    } else {
                        RegisterRequest registerRequest = new RegisterRequest(name, age, username, password, email, model, year, plate, vin, insurancename, policynumber, registrationnumber, licencenumber, plat, plong, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                        queue.add(registerRequest);
                    }
                }else{
                    editEmail.setError("Please enter a valid email");
                }
            }
        });
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
