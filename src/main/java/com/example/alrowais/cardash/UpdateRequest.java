package com.example.alrowais.cardash;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aalro on 3/29/2017.
 */


public class UpdateRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://www.aalrowais.com/Update.php";
    private Map<String, String> params;

    public UpdateRequest(String model, String year, String lplate, String vin, String insurancename, String policynumber, String username, Response.Listener<String> listener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("model", model);
        params.put("year", year);
        params.put("lplate", lplate);
        params.put("vin", vin);
        params.put("insurancename", insurancename);
        params.put("policynumber", policynumber);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}