package com.example.stanley.redo1_fyp_app;

/**
 * Created by Stanley on 5/6/2017.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashActivity extends Activity {
    // changing up some things
    ProgressBar androidProgressBar;
    int progressStatusCounter = 0;
    TextView textView10;
    Handler progressHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        androidProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView10 = (TextView) findViewById(R.id.textView11);

        //Start progressing
        new Thread(new Runnable() {
            public void run() {
                while (progressStatusCounter < 100) {
                    progressStatusCounter += 2;
                    progressHandler.post(new Runnable() {
                        public void run() {
                            androidProgressBar.setProgress(progressStatusCounter);
                            //Status update in textview
                            // textView.setText("Status: " + progressStatusCounter + "/" + androidProgressBar.getMax());
                            textView10.setText(progressStatusCounter + "%");
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                startApp();
                finish();
            }
        }).start();



    }
    private void startApp() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity1.class);//<-- This is to next activity after loading
        startActivity(intent);
    }

}
