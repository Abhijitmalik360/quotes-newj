package com.attitud.ssc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;

import android.os.Handler;


public class FlashScreen extends AppCompatActivity {
    private static final long SPLASH_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FlashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();  // Close the splash activity so the user can't go back to it
            }
        }, SPLASH_DELAY);



    }
}