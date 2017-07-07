package com.example.stanley.redo1_fyp_app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Stanley on 3/7/2017.
 */


public class TestActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

     //   private final String IP = "128.199.75.229";

        String records = getUrlServlet("alertspost.php");

        String IP = TestActivity.IP;
        TextView textView =(TextView) findViewById(R.id.recordstest);

        textView.setText(gold);
    }
    public String getUrlServlet(String servlet) {
        String url = "http://" + IP + "/" + servlet;
        return url;
    }
    public static String IP = "128.199.75.229";
    public static String gold;

    public void getCategoriesSpinner(final Context context, final Spinner spinner) {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i <jsonArray.length(); i++){
                       JSONObject object = jsonArray.getJSONObject(i);
                        gold = object.optString("time");
                        System.out.print(gold);
                        //System.out.print("dasdasdsaddsdsadasdasdsad");
                    }
                  // gold = jsonArray.getJSONObject(0);
                }catch (Exception e){

                }
            }
        };
    }

}




/*public String WebUtil {
    String IP = "http://128.199.75.229/"; //10.0.2.2:8080

    private String loginurl = getUrlServlet("Login");

    public String getUrlServlet(String servlet) {
        String url = "http://" + IP + "/Shoplet/" + servlet;
        return url;
    }*/
