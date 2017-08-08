package com.example.stanley.redo1_fyp_app;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import static com.example.stanley.redo1_fyp_app.Constants.REGISTERFIREBASE_URL;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jonat on 07/10/17.
 */

public class TokenRequest extends StringRequest {
//    private static final String TOKEN_REQUEST_URL = "http://128.199.75.229/registerfirebase.php";
    private Map<String, String> params;

    public TokenRequest(String Token, Response.Listener<String> listener){
        super(Method.POST, REGISTERFIREBASE_URL, listener, null);
        params = new HashMap<>();
        params.put("Token",Token);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
