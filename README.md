# Path Planning Game

An interactive Android application designed to teach and visualize pathfinding algorithms, focusing on robotic navigation. The app allows users to simulate grid-based path planning by placing start/end points, obstacles, and weights, and then calculating the optimal path using various algorithms.

---

## 🏗️ Features

- **Grid-Based Navigation**:
  - Customizable grid sizes (e.g., 8x8, 10x10).
  - Dynamic cell assignment for start, end, obstacles, and weights.
- **Pathfinding Algorithms**:
  - Implements popular algorithms such as:
    - A*
    - Dijkstra
    - Breadth-First Search (BFS)
    - Depth-First Search (DFS)
- **Robot Themes**:
  - Choose from three robot designs: Classic Robot, Futuristic Robot, and Car Robot.
- **Customizations**:
  - Set weights for cells to represent traversal difficulty.
  - Add random obstacles and compare algorithms.
- **Real-Time Feedback**:
  - Displays the steps and total cost of the computed path.
  - Animates the robot as it traverses the grid.
- **Algorithm Comparison**:
  - Compare performance metrics (steps and cost) of all algorithms side-by-side.
- **Interactive Sound Effects**:
  - Success, failure, and click sounds enhance the user experience.

---

## 🚀 How It Works

1. **Setup**:
   - Choose a grid size and robot theme in the settings.
2. **Interactive Grid**:
   - Place start and end points.
   - Add obstacles or assign custom weights to specific cells.
3. **Calculate Path**:
   - Select an algorithm and click **Calculate Path** to see the robot traverse the grid.
4. **Compare Algorithms**:
   - Use the **Compare Algorithms** button to analyze the efficiency of all available algorithms.

---

## 🧠 Pathfinding Algorithms

1. **A***:
   - Uses both path cost and a heuristic to find the shortest path efficiently.
   - Suitable for weighted grids.

2. **Dijkstra**:
   - Guarantees the shortest path by prioritizing nodes with the lowest cost.
   - Works well with weighted grids but may explore unnecessary nodes.

3. **BFS**:
   - Explores all nodes at the current depth before moving to the next.
   - Guarantees the shortest path but only for unweighted grids.

4. **DFS**:
   - Explores as far as possible along each branch before backtracking.
   - Does not guarantee the shortest path and is less efficient for large grids.

---

## 🛠️ Project Structure

### **Key Files**

- `MainActivity.java`:
  - Handles the main screen, user interactions, and grid actions.
- `GridAdapter.java`:
  - Manages the grid logic, including obstacle placement, pathfinding, and animation.
- `WelcomeActivity.java`:
  - Allows users to configure grid size, robot theme, and algorithms.
- `res/`:
  - Contains XML files for layouts, string resources, and drawable assets.

---

## 📂 Folder Structure

```
PathPlanningGame/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/pathplanninggame/
│   │   │   │   ├── MainActivity.java
│   │   │   │   ├── GridAdapter.java
│   │   │   │   ├── WelcomeActivity.java
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   ├── activity_welcome.xml
│   │   │   │   ├── drawable/
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   ├── colors.xml
```

---

## 💡 Features to Explore

1. **Algorithm Comparison**:
   - Run the same grid setup using different algorithms to compare results.
2. **Random Obstacle Placement**:
   - Simulate real-world challenges with randomized obstacles.
3. **Weighted Grids**:
   - Experiment with weighted paths to understand cost-based navigation.

---

## 🔧 Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/MohamedAymanOuchker/PathPlanningGame.git
   ```
2. Open the project in **Android Studio**.
3. Sync the Gradle files.
4. Build and run the app on an emulator or Android device.

---

## 🚀 Future Enhancements

1. **More Algorithms**:
   - Adding algorithms like Greedy Best-First Search.
2. **Dynamic Grid Resizing**:
   - Allow users to resize grids dynamically during runtime.
3. **Obstacle Patterns**:
   - Provide pre-designed patterns (e.g., mazes) for users to test.
4. **Export Results**:
   - Enable exporting of paths or grid setups as a JSON or image file.

---

## 🙌 Contribution

Contributions are welcome!  
1. Fork the repository.  
2. Create a feature branch:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add new feature"
   ```
4. Push to the branch:
   ```bash
   git push origin feature-name
   ```
5. Open a Pull Request.

---

## 📝 License

This project is licensed under the MIT License. See the `LICENSE` file for details.

---

## 💬 Feedback

For questions or feedback, feel free to reach out via [LinkedIn](https://www.linkedin.com/in/mohamed-ayman-ouchker) or open an issue in the repository.

---

### 📧 Contact

- **Author**: [Mohamed Ayman Ouchker](https://github.com/MohamedAymanOuchker)  
- **Email**: ayman.ouchker@outlook.com  
- **LinkedIn**: [LinkedIn Profile](https://www.linkedin.com/in/mohamed-ayman-ouchker)

---

Happy coding and exploring! 🚀