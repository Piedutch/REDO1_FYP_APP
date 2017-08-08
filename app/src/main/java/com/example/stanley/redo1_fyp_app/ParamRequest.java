package com.example.stanley.redo1_fyp_app;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import static com.example.stanley.redo1_fyp_app.Constants.ALERTNOTIFICATIONS_URL;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jonat on 07/11/17.
 */

public class ParamRequest extends StringRequest {

    //    private static final String PARAM_REQUEST_URL = "http://128.199.75.229/push_notification_trial.php";
    private Map<String, String> params;

    public ParamRequest(String item_name, String asset_no, String token, Response.Listener<String> listener){
        super(Method.POST, ALERTNOTIFICATIONS_URL, listener, null);
        Log.d("DEBUG","I am in ParamRequest");
        params = new HashMap<>();
        params.put("item_name", item_name);
        params.put("asset_no", asset_no);
        params.put("token", token);
        Log.d("DEBUG","Finished ParamRequest");
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

