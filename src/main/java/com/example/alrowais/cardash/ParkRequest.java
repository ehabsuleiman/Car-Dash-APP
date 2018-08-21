package com.example.alrowais.cardash;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by aalro on 3/29/2017.
 */


public class ParkRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://www.aalrowais.com/Park.php";
    private Map<String, String> params;

    public ParkRequest(String username, String plat, String plong, Response.Listener<String> listener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("plat", plat);
        params.put("plong", plong);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
