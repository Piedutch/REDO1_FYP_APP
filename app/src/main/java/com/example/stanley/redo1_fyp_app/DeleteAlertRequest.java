package com.example.stanley.redo1_fyp_app;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stanley on 10/7/2017.
 */


public class DeleteAlertRequest extends StringRequest{
    private static final String DELETE_REQUEST_URL = "http://128.199.75.229/deletealerts.php";
    private Map<String, String> params;

    public DeleteAlertRequest(String alert_no, Response.Listener<String> listener){
        super(Method.POST, DELETE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("alertno",alert_no);
    }
    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
