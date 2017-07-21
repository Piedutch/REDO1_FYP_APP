package com.example.stanley.redo1_fyp_app;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jonat on 07/20/17.
 */

public class ValuesRequest extends StringRequest {

    private static final String PARAM_REQUEST_URL = "http://128.199.75.229/push_notification_sysdiag.php";
    private Map<String, String> values;

    public ValuesRequest(String token, Response.Listener<String> listener){
        super(Method.POST, PARAM_REQUEST_URL, listener, null);
        Log.d("DEBUG","I am in ValuesRequest");
        values = new HashMap<>();
        values.put("token", token);
        Log.d("DEBUG","Finished ValuesRequest");
    }

    @Override
    public Map<String, String> getParams() {return values;}
}
