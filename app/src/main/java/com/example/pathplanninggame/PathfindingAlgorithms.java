package com.example.pathplanninggame;

import java.util.*;

public class PathfindingAlgorithms {

    // A* Algorithm
    public static List<Integer> aStar(int start, int end, int size, Set<Integer> obstacles, Map<Integer, Integer> weights) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.f));
        Set<Integer> closedSet = new HashSet<>();
        Map<Integer, Integer> cameFrom = new HashMap<>();

        openSet.add(new Node(start, 0, heuristic(start, end, size)));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            if (current.position == end) {
                return reconstructPath(cameFrom, end);
            }

            closedSet.add(current.position);

            for (int neighbor : getNeighbors(current.position, size)) {
                if (closedSet.contains(neighbor) || obstacles.contains(neighbor)) continue;

                // int tentativeG = current.g + 1;
                int tentativeG = current.g + weights.getOrDefault(neighbor, 1); // Default weight is 1
                Node neighborNode = new Node(neighbor, tentativeG, tentativeG + heuristic(neighbor, end, size));

                openSet.add(neighborNode);
                cameFrom.put(neighbor, current.position);
            }
        }
        return Collections.emptyList();
    }

    // Dijkstra's Algorithm
    public static List<Integer> dijkstra(int start, int end, int size, Set<Integer> obstacles, Map<Integer, Integer> weights) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.g));
        Set<Integer> closedSet = new HashSet<>();
        Map<Integer, Integer> cameFrom = new HashMap<>();

        openSet.add(new Node(start, 0, 0));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            if (current.position == end) {
                return reconstructPath(cameFrom, end);
            }

            closedSet.add(current.position);

            for (int neighbor : getNeighbors(current.position, size)) {
                if (closedSet.contains(neighbor) || obstacles.contains(neighbor)) continue;

                // int  = current.g + 1;
                int tentativeG = current.g + weights.getOrDefault(neighbor, 1); // Default weight is 1
                openSet.add(new Node(neighbor, tentativeG, 0));
                cameFrom.put(neighbor, current.position);
            }
        }
        return Collections.emptyList();
    }

    // Breadth-First Search (BFS)
    public static List<Integer> bfs(int start, int end, int size, Set<Integer> obstacles, Map<Integer, Integer> weights) {
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> cameFrom = new HashMap<>();
        Set<Integer> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == end) {
                return reconstructPath(cameFrom, end);
            }

            for (int neighbor : getNeighbors(current, size)) {
                if (!visited.contains(neighbor) && !obstacles.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    cameFrom.put(neighbor, current);
                }
            }
        }

        return Collections.emptyList();
    }

    // Depth-First Search (DFS)
    public static List<Integer> dfs(int start, int end, int size, Set<Integer> obstacles, Map<Integer, Integer> weights) {
        Stack<Integer> stack = new Stack<>();
        Map<Integer, Integer> cameFrom = new HashMap<>();
        Set<Integer> visited = new HashSet<>();

        stack.push(start);

        while (!stack.isEmpty()) {
            int current = stack.pop();
            if (visited.contains(current)) continue;

            visited.add(current);

            if (current == end) {
                return reconstructPath(cameFrom, end);
            }

            for (int neighbor : getNeighbors(current, size)) {
                if (!visited.contains(neighbor) && !obstacles.contains(neighbor)) {
                    stack.push(neighbor);
                    cameFrom.put(neighbor, current);
                }
            }
        }

        return Collections.emptyList();
    }

    // Heuristic function for A*
    private static int heuristic(int a, int b, int size) {
        int x1 = a % size, y1 = a / size;
        int x2 = b % size, y2 = b / size;
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    // Get neighbors for a cell
    private static List<Integer> getNeighbors(int position, int size) {
        List<Integer> neighbors = new ArrayList<>();
        int x = position % size;
        int y = position / size;

        if (x > 0) neighbors.add(position - 1);         // Left
        if (x < size - 1) neighbors.add(position + 1);  // Right
        if (y > 0) neighbors.add(position - size);      // Up
        if (y < size - 1) neighbors.add(position + size); // Down

        return neighbors;
    }

    // Reconstruct the path
    private static List<Integer> reconstructPath(Map<Integer, Integer> cameFrom, int current) {
        List<Integer> path = new ArrayList<>();
        while (cameFrom.containsKey(current)) {
            path.add(0, current);
            current = cameFrom.get(current);
        }
        return path;
    }
}