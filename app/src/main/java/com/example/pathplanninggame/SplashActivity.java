package com.example.pathplanninggame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Add a delay before starting the MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the MainActivity
                Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();  // Finish the SplashActivity so it won't be shown again
            }
        }, 3000); // 3-second delay
    }
}