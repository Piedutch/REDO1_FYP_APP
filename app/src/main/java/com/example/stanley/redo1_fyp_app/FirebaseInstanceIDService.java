package com.example.stanley.redo1_fyp_app;

import android.app.Service;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jonat on 07/10/17.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i("INFO", "This is the token: " +token);
        //For debug
        //        String token = "ABDDQSDWCSCSC";
        //        Toast.makeText(this, token, Toast.LENGTH_LONG).show();
        registerToken(token);
    }

    private void registerToken(final String token) {
        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("Insert Successful");

                    if(success){
                        Log.d("DEBUG", "Token added successfully: " + token);
                    } else {
                        Log.d("DEBUG", "Token not added");
                    }

                } catch(JSONException e){
                    e.printStackTrace();
                }
            }
        };
        TokenRequest tokenRequest = new TokenRequest(token, responseListener);
        RequestQueue queue = Volley.newRequestQueue(FirebaseInstanceIDService.this);
        queue.add(tokenRequest);
    }
}
