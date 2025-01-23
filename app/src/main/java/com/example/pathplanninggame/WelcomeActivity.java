package com.example.pathplanninggame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    private static final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        Button startGameButton = findViewById(R.id.startGameButton);
        if (startGameButton != null) {
            startGameButton.setOnClickListener(v -> {
                Log.d(TAG, "Start Game button clicked");
                try {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e(TAG, "Error starting MainActivity: " + e.getMessage());
                }
            });
        } else {
            Log.e(TAG, "startGameButton not found");
        }

        Button infoButton = findViewById(R.id.infoButton);
        infoButton.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, InfoActivity.class);
            startActivity(intent);
        });

    }
}