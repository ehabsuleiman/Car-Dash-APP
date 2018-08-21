package com.example.alrowais.cardash;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aalro on 3/4/2017.
 */

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://www.aalrowais.com/Register4.php";
    private Map<String, String> params;

    public RegisterRequest(String name, String age, String username, String password, String email, String model, String year, String plate, String vin, String insurancename, String policynumber, String registrationnumber, String licencenumber, String plat,String plong, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("age", age +"");
        params.put("username", username);
        params.put("password", password);
        params.put("email", email);
        params.put("model", model);
        params.put("year", year);
        params.put("lplate", plate);
        params.put("vin", vin);
        params.put("insurancename", insurancename);
        params.put("policynumber", policynumber);
        params.put("registrationnumber", registrationnumber);
        params.put("licencenumber", licencenumber);
        params.put("plat", plat);
        params.put("plong", plong);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
