package com.example.pathplanninggame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        AutoCompleteTextView gridSizeDropdown = findViewById(R.id.gridSizeDropdown);
        AutoCompleteTextView robotThemeDropdown = findViewById(R.id.robotThemeDropdown);
        AutoCompleteTextView algorithmDropdown = findViewById(R.id.algorithmDropdown);
        Button saveSettingsButton = findViewById(R.id.saveSettingsButton);

        SharedPreferences preferences = getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Populate dropdowns
        setupDropdown(gridSizeDropdown, R.array.grid_sizes);
        setupDropdown(robotThemeDropdown, R.array.robot_themes);
        setupDropdown(algorithmDropdown, R.array.pathfinding_algorithms);

        // Load previously saved settings
        int savedGridSize = preferences.getInt("gridSize", 8);
        int savedRobotTheme = preferences.getInt("robotTheme", 0);
        int savedAlgorithmIndex = preferences.getInt("algorithmIndex", 0);

        // Validate and set default selections
        String[] gridSizes = getResources().getStringArray(R.array.grid_sizes);
        String[] robotThemes = getResources().getStringArray(R.array.robot_themes);
        String[] algorithms = getResources().getStringArray(R.array.pathfinding_algorithms);

        // Set values based on the saved preferences
        gridSizeDropdown.setText(gridSizes[(savedGridSize / 2) - 4], false);
        robotThemeDropdown.setText(robotThemes[savedRobotTheme], false);
        algorithmDropdown.setText(algorithms[savedAlgorithmIndex], false);

        saveSettingsButton.setOnClickListener(v -> {
            // Get the selected values
            String gridSizeText = gridSizeDropdown.getText().toString();
            int gridSize = Integer.parseInt(gridSizeText.split("x")[0]); // Default to 8

            // Get the selected robot theme index
            String selectedRobotTheme = robotThemeDropdown.getText().toString();
            int robotTheme = Arrays.asList(robotThemes).indexOf(selectedRobotTheme); // Find the index

            // Get the selected algorithm index
            String selectedAlgorithm = algorithmDropdown.getText().toString();
            int algorithmIndex = Arrays.asList(algorithms).indexOf(selectedAlgorithm); // Find the index

            // Save to SharedPreferences
            editor.putInt("gridSize", gridSize);
            editor.putInt("robotTheme", robotTheme);
            editor.putInt("algorithmIndex", algorithmIndex);
            editor.apply();

            // Log saved values for debugging
            Log.d("SettingsActivity", "Saved Grid Size: " + gridSize);
            Log.d("SettingsActivity", "Saved Robot Theme Index: " + robotTheme);
            Log.d("SettingsActivity", "Saved Algorithm Index: " + algorithmIndex);

            // Navigate back to MainActivity
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void setupDropdown(AutoCompleteTextView dropdown, int arrayResId) {
        String[] items = getResources().getStringArray(arrayResId);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, items);
        dropdown.setAdapter(adapter);
    }
}
