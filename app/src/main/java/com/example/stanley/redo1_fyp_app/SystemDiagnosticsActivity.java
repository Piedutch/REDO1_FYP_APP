package com.example.stanley.redo1_fyp_app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jonat on 07/14/17.
 */

public class SystemDiagnosticsActivity extends AppCompatActivity {

    private String TAG = SystemDiagnosticsActivity.class.getSimpleName();
    private static String urlParams = "http://128.199.75.229/status_app.php";
    private ImageView tempStatus_status, cameraStatus_status, armStatus_status, gpuStatus_status;
    private TextView tempStatus_tv, cameraStatus_tv, armStatus_tv, gpuStatus_tv;
    private String tempInput, cameraInput, armInput, gpuInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sysdiag);


        //Initialize
        tempStatus_status = (ImageView) findViewById(R.id.tempStatus_status);
        cameraStatus_status = (ImageView) findViewById(R.id.cameraStatus_status);
        armStatus_status = (ImageView) findViewById(R.id.armStatus_status);
        gpuStatus_status = (ImageView) findViewById(R.id.gpuStatus_status);

        tempStatus_tv = (TextView) findViewById(R.id.tempStatus_data);
        cameraStatus_tv = (TextView) findViewById(R.id.cameraStatus_data);
        armStatus_tv = (TextView) findViewById(R.id.armStatus_data);
        gpuStatus_tv = (TextView) findViewById(R.id.gpuStatus_data);

        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.mipmap.status);
        Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 75, 75, true);
        tempStatus_status.setImageBitmap(bMapScaled);
        cameraStatus_status.setImageBitmap(bMapScaled);
        armStatus_status.setImageBitmap(bMapScaled);
        gpuStatus_status.setImageBitmap(bMapScaled);

        tempStatus_status.setColorFilter(getResources().getColor(R.color.unavailable));
        cameraStatus_status.setColorFilter(getResources().getColor(R.color.unavailable));
        armStatus_status.setColorFilter(getResources().getColor(R.color.unavailable));
        gpuStatus_status.setColorFilter(getResources().getColor(R.color.unavailable));

        new GetParams().execute();
    }

    private class GetParams extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            setParams();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(urlParams);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray params = jsonObject.getJSONArray("Status");

                    for (int i = 0; i < params.length(); i++) {
                        JSONObject p = params.getJSONObject(i);

                        tempInput = p.getString("temp_status");
                        cameraInput = p.getString("camera_status");
                        armInput = p.getString("arm_usage");
                        gpuInput = p.getString("gpu_usage");
                        Log.d(TAG, "Value of Camera input: " +cameraInput);
                    }

                }catch (final JSONException e) {
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }

            return null;
        }
    }

    private void setParams() {

        if(tempInput!=null){
            tempStatus_tv.setText(tempInput);
            tempStatus_status.setColorFilter(getResources().getColor(R.color.available));
        }

        if(cameraInput!=null){
            cameraStatus_tv.setText(cameraInput);
            if(cameraInput.equals("Online")){
                cameraStatus_status.setColorFilter(getResources().getColor(R.color.available));
            }
        }

        if(armInput!=null){
            armStatus_tv.setText(armInput);
            armStatus_status.setColorFilter(getResources().getColor(R.color.available));
        }

        if(gpuInput!=null){
            gpuStatus_tv.setText(gpuInput);
            gpuStatus_status.setColorFilter(getResources().getColor(R.color.available));
        }
    }


}

