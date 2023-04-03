package com.example.chattingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(SplashScreen.this,R.color.white));

        Thread thread = new Thread()
        {
            @Override
            public void run() {

                try {
                    sleep(3000);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally {
                    Intent i= new Intent(SplashScreen.this,SignupActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };thread.start();

    }
}