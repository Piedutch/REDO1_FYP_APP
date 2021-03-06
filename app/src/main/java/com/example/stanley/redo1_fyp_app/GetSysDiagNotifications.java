package com.example.stanley.redo1_fyp_app;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import static com.example.stanley.redo1_fyp_app.Constants.RETRIEVEFORSYSDIAGNOTIF_URL;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jonat on 07/20/17.
 */

public class GetSysDiagNotifications extends Service{
    private String TAG = GetSysDiagNotifications.class.getSimpleName();
    private boolean isRunning = false;
    private JSONArray params;
    // random value for initialization
    private int present_bit = 1;
//    private static String urlParams = "http://128.199.75.229/get_pi_status.php";
    private int counter = 0;
    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        new GetValues().execute();
//        handler = new Handler();
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, 60000);

//        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

//// This schedule a runnable task every 2 minutes
//        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
//            public void run() {
//                //To-Do
//            }
//        }, 0, 2, TimeUnit.MINUTES);
        return Service.START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class GetValues extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(RETRIEVEFORSYSDIAGNOTIF_URL);
            Log.e(TAG, "Response from url: " + jsonStr);
            try{
                JSONObject jsonObject = new JSONObject(jsonStr);
                params = jsonObject.getJSONArray("Available");
            }catch (JSONException e){
                Log.e(TAG, "JSON parsing error: " + e.getMessage());
            }

            //&& jsonStr.length() < 4

            if (params == null) {
                present_bit = 0;
                Log.d(TAG, "Did it come here?");
            } else if (params != null) {
//            if(jsonStr!=null){
                try {

//                if (params == null){
//                    present_bit = 0;
//                    Log.d(TAG, "Did it come here?");
//                } else {
                    for (int i = 0; i < params.length(); i++) {
                        JSONObject p = params.getJSONObject(i);

                        present_bit = p.getInt("camera_status");
                    }
//                }
                } catch (final JSONException e) {
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }

            Log.d(TAG, "Present bit is: " + present_bit);
            if ((present_bit == 0)) {
                Log.d(TAG, "I enter the condition");
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "At onResponse, before the tryCatch");
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Log.i("tagconvertstr", "[" + response + "]");
                            boolean success = jsonResponse.getBoolean("Push Notification Successful");
                            Log.d(TAG, "I enter the tryCatch");
                            if (success) {
                                Log.d(TAG, "Notification Successful");
                            } else {
                                Log.d(TAG, "Notification Unsuccessful");
                            }

                        } catch (JSONException e) {
                            Log.d(TAG, "Why I never enter TryCatch: " + e.getMessage());
                        }
                    }
                };
                ValuesRequest valuesRequest = new ValuesRequest(FirebaseInstanceId.getInstance().getToken(), responseListener);
//                        ParamRequest paramRequest = new ParamRequest(aa, ff, responseListener);
                RequestQueue queue = Volley.newRequestQueue(GetSysDiagNotifications.this);
                queue.add(valuesRequest);
                present_bit = 1;

            }
            return null;
        }
    }
}

