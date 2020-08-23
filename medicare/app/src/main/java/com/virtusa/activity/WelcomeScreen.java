package com.virtusa.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.virtusa.recycle.R;

public class WelcomeScreen extends AppCompatActivity {
    private static int splashTimeout=4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent=new Intent(WelcomeScreen.this, LocationActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },splashTimeout);
    }
}
