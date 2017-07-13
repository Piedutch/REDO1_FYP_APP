package com.example.stanley.redo1_fyp_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by jonat on 07/13/17.
 */

public class AboutActivity extends AppCompatActivity {

    ImageButton whatsappBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        whatsappBtn = (ImageButton)findViewById(R.id.whatsapp);
        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.whatsapp);
        Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 300, 350, true);
        whatsappBtn.setImageBitmap(bMapScaled);



        whatsappBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "I am currently having problems, please text me ASAP!");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });


    }
}
