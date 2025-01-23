package com.example.pathplanninggame;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridAdapter adapter;
    private GridView gridView;
    private SharedPreferences preferences;
    private TextView selectedAlgorithmText;
    private TextView pathStepsText;
    private TextView pathCostText;
    private MediaPlayer setPointSound, successSound, failureSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load settings
        preferences = getSharedPreferences("Settings", MODE_PRIVATE);

        setContentView(R.layout.activity_main);

        // Initialize toolbar
        Toolbar toolbar = findViewById(R.id.topAppBar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            });
        } else {
            Log.e("MainActivity", "Toolbar is null");
        }

        // Initialize views
        gridView = findViewById(R.id.gridView);
        selectedAlgorithmText = findViewById(R.id.selectedAlgorithmText);
        pathStepsText = findViewById(R.id.pathStepsText);
        pathCostText = findViewById(R.id.pathCostText);
        RadioButton setStartButton = findViewById(R.id.setStartButton);
        RadioButton setEndButton = findViewById(R.id.setEndButton);
        RadioButton setObstacleButton = findViewById(R.id.setObstacleButton);
        Button calculatePathButton = findViewById(R.id.calculatePathButton);
        Button resetGridButton = findViewById(R.id.resetGridButton);
        Button randomObstaclesButton = findViewById(R.id.randomObstaclesButton);
        Button assignWeightsButton = findViewById(R.id.assignWeightsButton);
        Button compareAlgorithmsButton = findViewById(R.id.compareAlgorithmsButton);
        Button clearWeightsButton = findViewById(R.id.clearWeightsButton);

        // Load sound effects
        setPointSound = MediaPlayer.create(this, R.raw.click);
        successSound = MediaPlayer.create(this, R.raw.yay);
        failureSound = MediaPlayer.create(this, R.raw.fail);

        // Initialize adapter with current settings
        initializeAdapter();

        // Handle grid item clicks
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            if (setStartButton.isChecked()) {
                adapter.setStart(position);
                setStartButton.setChecked(false); // Uncheck after setting start point
                Toast.makeText(this, "Start point set!", Toast.LENGTH_SHORT).show();
                playSound(setPointSound);
            } else if (setEndButton.isChecked()) {
                adapter.setEnd(position);
                setEndButton.setChecked(false); // Uncheck after setting end point
                Toast.makeText(this, "End point set!", Toast.LENGTH_SHORT).show();
                playSound(setPointSound);
            } else if (setObstacleButton.isChecked()) {
                adapter.setObstacle(position);
                playSound(setPointSound);
            } else { // Handle weight setting
                showWeightDialog(position);
            }
        });

        // Button logic
        calculatePathButton.setOnClickListener(v -> calculateAndAnimatePath());
        resetGridButton.setOnClickListener(v -> adapter.resetGrid());
        randomObstaclesButton.setOnClickListener(v -> showObstacleDialog());
        assignWeightsButton.setOnClickListener(v -> assignRandomWeights());
        compareAlgorithmsButton.setOnClickListener(v -> compareAlgorithms());
        clearWeightsButton.setOnClickListener(v -> clearAllWeights());
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeAdapter(); // Refresh adapter with updated settings
    }

    // Initialize adapter with settings
    private void initializeAdapter() {
        // Retrieve settings
        int gridSize = preferences.getInt("gridSize", 8); // Default grid size
        int robotThemeIndex = preferences.getInt("robotTheme", 0); // Default theme
        int algorithmIndex = preferences.getInt("algorithmIndex", 0); // Default algorithm

        // Validate indices
        String[] robotThemes = getResources().getStringArray(R.array.robot_themes);
        String[] algorithms = getResources().getStringArray(R.array.pathfinding_algorithms);
        robotThemeIndex = validateIndex(robotThemeIndex, robotThemes.length);
        algorithmIndex = validateIndex(algorithmIndex, algorithms.length);

        // Map robot theme to drawable
        int robotThemeDrawable = getRobotThemeDrawable(robotThemeIndex);
        String selectedAlgorithm = algorithms[algorithmIndex];

        // Update UI
        selectedAlgorithmText.setText("Selected Algorithm: " + selectedAlgorithm);

        // Initialize or update the adapter
        adapter = new GridAdapter(this, gridSize, robotThemeDrawable);
        gridView.setAdapter(adapter);

        // Debug logs to confirm settings are applied
        Log.d("MainActivity", "Applied Grid Size: " + gridSize);
        Log.d("MainActivity", "Applied Robot Theme Index: " + robotThemeIndex);
        Log.d("MainActivity", "Applied Algorithm Index: " + algorithmIndex);
    }

    private int validateIndex(int index, int arrayLength) {
        return (index >= 0 && index < arrayLength) ? index : 0; // Fallback to valid index
    }

    private int getRobotThemeDrawable(int robotThemeIndex) {
        switch (robotThemeIndex) {
            case 1:
                return R.drawable.fut_robot;
            case 2:
                return R.drawable.car_robot;
            default:
                return R.drawable.robot; // Default theme
        }
    }

    private void calculateAndAnimatePath() {
        int algorithmIndex = preferences.getInt("algorithmIndex", 0);
        String[] algorithms = getResources().getStringArray(R.array.pathfinding_algorithms);
        algorithmIndex = validateIndex(algorithmIndex, algorithms.length);

        String selectedAlgorithm = algorithms[algorithmIndex];
        List<Integer> path = adapter.calculatePath(selectedAlgorithm);

        if (path.isEmpty()) {
            Toast.makeText(this, "No valid path found!", Toast.LENGTH_SHORT).show();
            pathStepsText.setText("Steps: N/A");
            pathCostText.setText("Cost: N/A");
            playSound(failureSound);
        } else {
            int steps = path.size();
            int cost = adapter.calculatePathCost(path);
            pathStepsText.setText("Steps: " + steps);
            pathCostText.setText("Cost: " + cost);
            animateRobot(path);
            playSound(successSound);
        }
    }

    private void animateRobot(List<Integer> path) {
        int totalCost = adapter.calculatePathCost(path);
        new Thread(() -> {
            int currentStep = 0;
            for (int position : path) {
                final int step = ++currentStep;
                runOnUiThread(() -> {
                    adapter.setRobotPosition(position);
                    pathStepsText.setText("Steps: " + step);
                    pathCostText.setText("Cost: " + totalCost);
                });
                try {
                    Thread.sleep(500); // Adjust animation speed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            runOnUiThread(() -> adapter.setPath(path)); // Highlight the full path
        }).start();
    }

    private void assignRandomWeights() {
        int weightCount = 10; // Example count
        int maxWeight = 5;    // Maximum weight value
        adapter.setRandomWeights(weightCount, maxWeight);
        Toast.makeText(this, weightCount + " weighted cells assigned!", Toast.LENGTH_SHORT).show();
    }

    private void compareAlgorithms() {
        String[] algorithms = getResources().getStringArray(R.array.pathfinding_algorithms);
        StringBuilder comparisonResult = new StringBuilder("Algorithm Comparison:\n");

        for (String algorithm : algorithms) {
            List<Integer> path = adapter.calculatePath(algorithm);
            int steps = path.size();
            int cost = adapter.calculatePathCost(path);

            comparisonResult.append(algorithm)
                    .append(" -> Steps: ").append(steps)
                    .append(", Cost: ").append(cost)
                    .append("\n");
        }

        new AlertDialog.Builder(this)
                .setTitle("Algorithm Comparison")
                .setMessage(comparisonResult.toString())
                .setPositiveButton("OK", null)
                .show();
    }

    private void clearAllWeights() {
        adapter.clearWeights();
        Toast.makeText(this, "All weights cleared!", Toast.LENGTH_SHORT).show();
    }

    private void showObstacleDialog() {
        final EditText input = new EditText(this);
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        input.setHint("Enter number of obstacles");

        new AlertDialog.Builder(this)
                .setTitle("Random Obstacles")
                .setMessage("How many obstacles do you want?")
                .setView(input)
                .setPositiveButton("OK", (dialog, which) -> {
                    String value = input.getText().toString();
                    if (!value.isEmpty()) {
                        int obstacleCount = Integer.parseInt(value);
                        adapter.setRandomObstacles(obstacleCount);
                        Toast.makeText(this, obstacleCount + " obstacles placed!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please enter a valid number.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showWeightDialog(int position) {
        final EditText input = new EditText(this);
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        input.setHint("Enter weight (or leave empty to clear)");

        new AlertDialog.Builder(this)
                .setTitle("Set Weight")
                .setMessage("Enter the weight for this cell:")
                .setView(input)
                .setPositiveButton("OK", (dialog, which) -> {
                    String value = input.getText().toString();
                    if (value.isEmpty()) {
                        adapter.toggleWeight(position, 1); // Clear weight
                        Toast.makeText(this, "Weight cleared!", Toast.LENGTH_SHORT).show();
                    } else {
                        int weight = Integer.parseInt(value);
                        if (weight < 1) {
                            Toast.makeText(this, "Weight must be at least 1.", Toast.LENGTH_SHORT).show();
                        } else {
                            adapter.toggleWeight(position, weight); // Set weight
                            Toast.makeText(this, "Weight set to " + weight, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish(); // Close the current activity
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    private void playSound(MediaPlayer sound) {
        if (sound != null) {
            sound.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (setPointSound != null) setPointSound.release();
        if (successSound != null) successSound.release();
        if (failureSound != null) failureSound.release();
    }
}
