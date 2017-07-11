package com.example.stanley.redo1_fyp_app;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jonat on 07/11/17.
 */

public class GetNotifications extends Service {
    private String TAG = GetNotifications.class.getSimpleName();
    private boolean isRunning = false;
    // random value for initialization
    private int old_alert_no = 0;
    private int new_alert_no = 0;
    private String item_name;
    private String asset_no;
    private Handler handler;
    private static String urlParams = "http://128.199.75.229/getlastrow.php";

    public void onCreate() {
        isRunning = true;
        Log.d(TAG, "It comes into GetNotifications.java");
        Log.d(TAG, "In onCreate(), Old alert number is " + old_alert_no);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        new GetParams().execute();
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

    private class GetParams extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(urlParams);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray params = jsonObject.getJSONArray("GetLastRow");

                    for (int i = 0; i < params.length(); i++) {
                        JSONObject p = params.getJSONObject(i);

                        item_name = p.getString("item_name");
                        asset_no = p.getString("asset_no");
                        new_alert_no = p.getInt("alert_no");
                        item_name = item_name + "";
                        if (item_name.length() > 20) {
                            item_name = item_name.substring(0, 20);
                        }
                    }
                    Log.d(TAG, "Item name is: " + item_name);
                    Log.d(TAG, "Asset No is: " + asset_no);
                    Log.d(TAG, "New Alert No is: " + new_alert_no);
                    Log.d(TAG, "Old Alert No is: " + old_alert_no);

                    if ((new_alert_no != old_alert_no)) {
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

                        ParamRequest paramRequest = new ParamRequest(item_name, asset_no, responseListener);
//                        ParamRequest paramRequest = new ParamRequest(aa, ff, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(GetNotifications.this);
                        queue.add(paramRequest);
                        old_alert_no = new_alert_no;
                        Log.d(TAG, "After Change, old alert number(" + old_alert_no + ") should be same as new alert number (" + new_alert_no + ")!");
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }

            return null;
        }
    }

}
