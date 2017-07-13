package com.example.stanley.redo1_fyp_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

/**
 * Created by jonat on 07/12/17.
 */

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = SettingsActivity.class.getSimpleName();
    CheckBox cb1,cb2, cb3, cb4, cb5, cb6;
    Button opt1, opt2, opt3, opt4, opt5, opt6, submitBtn;
//    private SharedPreferences prefs;
    int intent_value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        View opt1 = findViewById(R.id.buttonOption1);
        opt1.setOnClickListener(this);
        View opt2 = findViewById(R.id.buttonOption2);
        opt2.setOnClickListener(this);
        View opt3 = findViewById(R.id.buttonOption3);
        opt3.setOnClickListener(this);
        View opt4 = findViewById(R.id.buttonOption4);
        opt4.setOnClickListener(this);
        View opt5 = findViewById(R.id.buttonOption5);
        opt5.setOnClickListener(this);
        View opt6 = findViewById(R.id.buttonOption6);
        opt6.setOnClickListener(this);

        cb1 = (CheckBox) findViewById(R.id.cbOption1);
        cb2 = (CheckBox) findViewById(R.id.cbOption2);
        cb3 = (CheckBox) findViewById(R.id.cbOption3);
        cb4 = (CheckBox) findViewById(R.id.cbOption4);
        cb5 = (CheckBox) findViewById(R.id.cbOption5);
        cb6 = (CheckBox) findViewById(R.id.cbOption6);


        submitBtn = (Button) findViewById(R.id.submit_btn_settings);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intent_value != 0){
                    Log.d(TAG, "Sending " + intent_value + " to HomeActivity");
                    Intent i = new Intent(SettingsActivity.this, HomeActivity.class);
                    i.putExtra("fetchOption", intent_value);
                    startActivity(i);
                }
            }
        });

//        intent_value = sendIntent();

//        Log.d(TAG, "Data received before Prefs: " + intent_value);

//        prefs = getPreferences(MODE_PRIVATE);
//
//        if(intent_value==0) {
//            intent_value = prefs.getInt("prefs_key", -1);
//            Log.d(TAG, "Data received after Prefs: " + intent_value);
//        }

//            Intent i = new Intent(this,HomeActivity.class);
//            i.putExtra("fetchOption", intent_value);
//            startActivity(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonOption1:
                cb1.post(new Runnable() {
                    @Override
                    public void run() {
                        cb1.setChecked(true);
                        cb2.setChecked(false);
                        cb3.setChecked(false);
                        cb4.setChecked(false);
                        cb5.setChecked(false);
                        cb6.setChecked(false);
                        intent_value = 1000*15;
                    }
                });
                break;
            case R.id.buttonOption2:
                cb2.post(new Runnable() {
                    @Override
                    public void run() {
                        cb1.setChecked(false);
                        cb2.setChecked(true);
                        cb3.setChecked(false);
                        cb4.setChecked(false);
                        cb5.setChecked(false);
                        cb6.setChecked(false);
                        intent_value = 1000*60;
                    }
                });
                break;
            case R.id.buttonOption3:
                cb3.post(new Runnable() {
                    @Override
                    public void run() {
                        cb1.setChecked(false);
                        cb2.setChecked(false);
                        cb3.setChecked(true);
                        cb4.setChecked(false);
                        cb5.setChecked(false);
                        cb6.setChecked(false);
                        intent_value = 1000*60*5;
                    }
                });
                break;
            case R.id.buttonOption4:
                cb4.post(new Runnable() {
                    @Override
                    public void run() {
                        cb1.setChecked(false);
                        cb2.setChecked(false);
                        cb3.setChecked(false);
                        cb4.setChecked(true);
                        cb5.setChecked(false);
                        cb6.setChecked(false);
                        intent_value = 1000*60*10;
                    }
                });
                break;
            case R.id.buttonOption5:
                cb5.post(new Runnable() {
                    @Override
                    public void run() {
                        cb1.setChecked(false);
                        cb2.setChecked(false);
                        cb3.setChecked(false);
                        cb4.setChecked(false);
                        cb5.setChecked(true);
                        cb6.setChecked(false);
                        intent_value = 1000*60*30;
                    }
                });
                break;
            case R.id.buttonOption6:
                cb6.post(new Runnable() {
                    @Override
                    public void run() {
                        cb1.setChecked(false);
                        cb2.setChecked(false);
                        cb3.setChecked(false);
                        cb4.setChecked(false);
                        cb5.setChecked(false);
                        cb6.setChecked(true);
                        intent_value = 1000*60*60;
                    }
                });
                break;
        }
    }


//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        prefs = getPreferences(MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putInt("prefs_key", intent_value);
//        editor.commit();
//    }



}



