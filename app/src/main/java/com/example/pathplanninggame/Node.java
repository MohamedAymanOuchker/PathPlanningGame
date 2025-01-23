package com.example.pathplanninggame;

public class Node {
    public int position; // Position of the node in the grid
    public int g;        // Cost from the start to this node
    public int f;        // Estimated total cost (g + heuristic)

    public Node(int position, int g, int f) {
        this.position = position;
        this.g = g;
        this.f = f;
    }
}