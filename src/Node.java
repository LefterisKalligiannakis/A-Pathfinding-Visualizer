/*
    Node class for A* algorithm visualization.

    @author Eleftherios Kalligiannakis
 */

import java.util.ArrayList;
import java.awt.Color;

public class Node {
    private ArrayList<Node> adjList;
    private int x, y;
    boolean isWall = false;
    Color color;

    // for A* pathfinding
    private int gCost, hCost, fCost;
    boolean isOpen, isChecked;
    private Node parent;


    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        gCost = Integer.MAX_VALUE;
        hCost = Integer.MAX_VALUE;
        fCost = Integer.MAX_VALUE;
        isOpen = false;
        isChecked = false;
        this.adjList = new ArrayList<>();
        color = Color.white;
    }


    public boolean equals(Node n) {
        return this.x == n.getX() && this.y == n.getY();
    }


    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getgCost() {
        return this.gCost;
    }
    public void setgCost(int cost){ this.gCost = cost; }

    public int gethCost() {
        return this.hCost;
    }
    public void sethCost(int cost){ this.hCost = cost; }

    public int getfCost() {
        return this.fCost;
    }
    public void setfCost(int cost){ this.fCost = cost; }

    public Node getParent(){ return this.parent; };
    public void setParent(Node parentNode){ this.parent = parentNode; }

    public ArrayList<Node> getAdjList() {
        return adjList;
    }

    // Add connections to both nodes for an undirected graph
    public void addAdj(Node node) {
        this.adjList.add(node);
        node.adjList.add(this);
    }


}
