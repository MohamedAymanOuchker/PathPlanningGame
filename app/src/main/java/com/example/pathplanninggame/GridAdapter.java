package com.example.pathplanninggame;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.*;

public class GridAdapter extends BaseAdapter {

    private final Context context;
    private final int size;
    private final int robotTheme; // Selected robot theme resource ID
    private final int[] grid;
    private int start = -1;
    private int end = -1;
    private int robotPosition = -1; // Track the robot's current position
    private final List<Integer> obstacles = new ArrayList<>();
    private List<Integer> path = new ArrayList<>();
    private final Map<Integer, Integer> weights = new HashMap<>();

    public GridAdapter(Context context, int size, int robotTheme) {
        this.context = context;
        this.size = size;
        this.robotTheme = robotTheme;
        this.grid = new int[size * size];
    }

    public void setStart(int position) {
        start = position;
        robotPosition = position; // Robot starts at the start position
        notifyDataSetChanged();
    }

    public void setEnd(int position) {
        end = position;
        notifyDataSetChanged();
    }

    public void setObstacle(int position) {
        if (!obstacles.contains(position)) {
            obstacles.add(position);
        }
        notifyDataSetChanged();
    }

    public void resetGrid() {
        Arrays.fill(grid, 0);
        start = -1;
        end = -1;
        obstacles.clear();
        path.clear();
        notifyDataSetChanged();
    }

    public void setPath(List<Integer> path) {
        this.path = path;
        notifyDataSetChanged();
    }

    public List<Integer> calculatePath(String algorithm) {
        if (start == -1 || end == -1) return Collections.emptyList();

        Set<Integer> obstacleSet = new HashSet<>(obstacles);

        switch (algorithm) {
            case "Dijkstra":
                return PathfindingAlgorithms.dijkstra(start, end, size, obstacleSet, weights);
            case "Breadth-First Search (BFS)":
                return PathfindingAlgorithms.bfs(start, end, size, obstacleSet, weights);
            case "Depth-First Search (DFS)":
                return PathfindingAlgorithms.dfs(start, end, size, obstacleSet, weights);
            default: // Default to A*
                return PathfindingAlgorithms.aStar(start, end, size, obstacleSet, weights);
        }
    }

    @Override
    public int getCount() {
        return grid.length;
    }

    @Override
    public Object getItem(int position) {
        return grid[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = new View(context);
            view.setLayoutParams(new GridView.LayoutParams(100, 100));
        }

        if (weights.containsKey(position)) {
            int weight = weights.get(position);
            if (weight == 2) {
                view.setBackgroundColor(Color.BLUE); // Example: Weight 2 (Mud)
            } else if (weight == 3) {
                view.setBackgroundColor(Color.CYAN); // Example: Weight 3 (Water)
            } else {
                view.setBackgroundColor(Color.MAGENTA); // Other weights
            }
        } else if (position == robotPosition) {
            view.setBackgroundResource(robotTheme); // Display the robot icon
        } else if (position == start) {
            view.setBackgroundColor(Color.GREEN); // Start
        } else if (position == end) {
            view.setBackgroundColor(Color.RED); // End
        } else if (obstacles.contains(position)) {
            view.setBackgroundColor(Color.BLACK); // Obstacle
        } else if (path.contains(position)) {
            view.setBackgroundColor(Color.YELLOW); // Path
        } else {
            view.setBackgroundColor(Color.LTGRAY); // Default
        }
        return view;
    }

    public void setRobotPosition(int position) {
        this.robotPosition = position;
        notifyDataSetChanged();
    }

    public void setRandomObstacles(int count) {
        Random random = new Random();
        obstacles.clear(); // Clear existing obstacles

        while (obstacles.size() < count) {
            int position = random.nextInt(size * size); // Random position in the grid

            // Ensure it's not start, end, or already an obstacle
            if (position != start && position != end && !obstacles.contains(position)) {
                obstacles.add(position);
            }
        }

        notifyDataSetChanged(); // Refresh the grid view
    }

    // Method to assign random weights to cells
    public void setRandomWeights(int count, int maxWeight) {
        Random random = new Random();
        weights.clear(); // Clear existing weights

        while (weights.size() < count) {
            int position = random.nextInt(size * size); // Random position in the grid
            // Ensure it's not the start, end, or an obstacle
            if (position != start && position != end && !obstacles.contains(position)) {
                int weight = random.nextInt(maxWeight - 1) + 2; // Generate weights (2 to maxWeight)
                weights.put(position, weight);
            }
        }

        notifyDataSetChanged(); // Refresh the grid view
    }

    // Method to set weights for specific cells
    public void setWeightedCell(int position, int weight) {
        weights.put(position, weight);
        notifyDataSetChanged(); // Refresh the grid view
    }

    // Method to reset all weights
    public void clearWeights() {
        weights.clear();
        notifyDataSetChanged();
    }

    // Toggle weight for a cell
    public void toggleWeight(int position, int weight) {
        if (weights.containsKey(position)) {
            weights.remove(position); // Clear weight if already set
        } else {
            weights.put(position, weight); // Set weight
        }
        notifyDataSetChanged(); // Refresh the grid view
    }

    public int calculatePathCost(List<Integer> path) {
        int cost = 0;
        for (int position : path) {
            cost += weights.getOrDefault(position, 1); // Default weight is 1
        }
        return cost;
    }
}
