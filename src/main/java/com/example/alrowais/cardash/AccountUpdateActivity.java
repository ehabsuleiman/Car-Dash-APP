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

public class AccountUpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_update);

        final EditText editAge = (EditText) findViewById(R.id.editAge);
        final Button bUpdate = (Button) findViewById(R.id.bUpdate);
        final Button bCancel = (Button) findViewById(R.id.bCancel);
        final Button bUpPassowrd = (Button) findViewById(R.id.bUpPassowrd);

        Intent intent = getIntent();
        String age = intent.getStringExtra("age");

        editAge.setText(age);

        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String age = editAge.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                             if(success){

                                    Intent intent = getIntent();
                                    String name = intent.getStringExtra("name");
                                    String email = intent.getStringExtra("email");
                                    String username = intent.getStringExtra("username");
                                    String password = intent.getStringExtra("password");
                                    String model = intent.getStringExtra("model");
                                    String year = intent.getStringExtra("year");
                                    String lplate = intent.getStringExtra("lplate");
                                    String vin = intent.getStringExtra("vin");
                                    String insurancename = intent.getStringExtra("insurancename");
                                    String policynumber = intent.getStringExtra("policynumber");
                                    String plat = intent.getStringExtra("plat");
                                    String plong = intent.getStringExtra("plong");

                                    Intent intentI = new Intent(AccountUpdateActivity.this, UserAreaActivity.class);

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



                                    AccountUpdateActivity.this.startActivity(intentI);

                             }else{
    ;
                                    AlertDialog.Builder builder = new AlertDialog.Builder(AccountUpdateActivity.this);
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
                UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest(username, age, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AccountUpdateActivity.this);
                queue.add(updateAccountRequest);
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

                Intent intentI = new Intent(AccountUpdateActivity.this, UserAreaActivity.class);

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


                AccountUpdateActivity.this.startActivity(intentI);
            }
        });

        bUpPassowrd.setOnClickListener(new View.OnClickListener() {
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

                Intent intentI = new Intent(AccountUpdateActivity.this, UpdatePasswordActivity.class);

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


                AccountUpdateActivity.this.startActivity(intentI);
            }
        });
    }
}
