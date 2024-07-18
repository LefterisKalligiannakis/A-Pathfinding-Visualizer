/*
    Graph class used for pathfinding visualization.

    @author Eleftherios Kalligiannakis
 */

import java.awt.Color;
import java.util.ArrayList;


public class Graph {

    Frame frame;
    private final int sizeX, sizeY;
    private ArrayList<Node> nodeList; // Array containing all Nodes
    private Node[][] grid;


    public Graph(int sizeX, int sizeY, Frame frame) {
        this.frame = frame;
        this.nodeList = new ArrayList<>();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        createGraph();
    }

    public Node getNode(int x, int y) {
        for (Node node : nodeList) {
            if (node.getX() == x && node.getY() == y)
                return node;
        }
        return null;
    }

    public void makeWall(int x, int y) {
        Node node = grid[x][y];
        node.color = Color.BLACK;
        node.isWall = true;
    }

    public void removeWall(int x, int y) {
        Node node = grid[x][y];
        if (node.color == Color.BLACK) {
            node.isWall = false;
            node.color = Color.WHITE;
        }
    }

    private void makeConnections(int x, int y) {
        Node currentNode = grid[x][y];

        // above
        if (x > 0) {
            currentNode.addAdj(grid[x - 1][y]);
        }

        // up-left
        if (x > 0 && y > 0 && frame.diagonals) {
            currentNode.addAdj(grid[x - 1][y - 1]);
        }

        // up-right
        if (x > 0 && y < grid[x].length - 1 && frame.diagonals) {
            currentNode.addAdj(grid[x - 1][y + 1]);
        }

        // left
        if (y > 0) {
            currentNode.addAdj(grid[x][y - 1]);
        }

        // right
        if (y < grid[x].length - 1) {
            currentNode.addAdj(grid[x][y + 1]);
        }

        // below
        if (x < grid.length - 1) {
            currentNode.addAdj(grid[x + 1][y]);
        }

        // down-left
        if (x < grid.length - 1 && y > 0 && frame.diagonals) {
            currentNode.addAdj(grid[x + 1][y - 1]);
        }

        // down-right
        if (x < grid.length - 1 && y < grid[x].length - 1 && frame.diagonals) {
            currentNode.addAdj(grid[x + 1][y + 1]);
        }
    }

    public void createGraph() {

        grid = new Node[sizeX][sizeY];

        for (int i = 0; i< sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                grid[i][j] = new Node(i, j); // Create new Node
                nodeList.add(grid[i][j]); // add node to nodeList
            }
        }

        // Add neighbours for each node
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                makeConnections(i, j);
            }
        }

    }

    // Getters
    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public ArrayList<Node> getNodeList() { return nodeList; }




}
