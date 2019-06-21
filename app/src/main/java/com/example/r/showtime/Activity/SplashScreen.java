package com.example.r.showtime.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.r.showtime.R;

public class SplashScreen extends AppCompatActivity {
    int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_splash_screen );

        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent signInIntent=new Intent(SplashScreen.this,MainActivity.class);
                startActivity(signInIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
