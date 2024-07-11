/*
    Contains the A* algorithm and is connected to the @Frame class 
    for visualizing the algorithm

    @author Eleftherios Kalligiannakis
 */

import java.awt.Color;
import java.util.*;


public class Pathfinder implements Runnable{
    Graph graph;
    Frame frame;
    Node start, end;
    int delay;

    // For A* pathfinding
    Node current;
    ArrayList<Node> openList = new ArrayList<>();
    ArrayList<Node> checkedList = new ArrayList<>();
    boolean goalReached = false;

    public Pathfinder(Frame frame) {
        this.frame = frame;
        this.graph = new Graph(100, 100, frame);
        this.delay = 0;
    }

    @Override
    public void run() {
        frame.isRan = true;
        current = start;
        openNode(current); // open start node
        AstarPathfinder(delay - frame.optionhandler.speed.getValue());
    }



    public void AstarPathfinder(int delay) {

        while (!goalReached) {

            int col = current.getX();
            int row = current.getY();

            setChecked(current); // Make node checked
            openList.remove(current); // Close node
            current.isOpen = false;

            // Open surrounding nodes
            if (row - 1 >= 0) {
                openNode(graph.getNode(col, row - 1));
            }
            if (col - 1 >= 0)
                openNode(graph.getNode(col - 1, row));{

            }
            if (graph.getSizeY() > row + 1) {
                openNode(graph.getNode(col, row + 1));
            }
            if (graph.getSizeX() > col + 1){
                openNode(graph.getNode(col + 1, row));
            }

            // Open diagonal nodes
            if(frame.diagonals) {
                if (row - 1 >= 0 && col - 1 >= 0) {
                    openNode(graph.getNode(col - 1, row - 1));
                }
                if (row - 1 >= 0 && graph.getSizeX() > col + 1) {
                    openNode(graph.getNode(col + 1, row - 1));
                }
                if (graph.getSizeY() > row + 1 && col - 1 >= 0) {
                    openNode(graph.getNode(col - 1, row + 1));
                }
                if (graph.getSizeY() > row + 1 && graph.getSizeX() > col + 1) {
                    openNode(graph.getNode(col + 1, row + 1));
                }
            }


            // Get the smallest node in open list and open surrounding nodes.
            int minIndex = 0;
            int minFcost = Integer.MAX_VALUE;
            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).getfCost() < minFcost) {
                    minFcost = openList.get(i).getfCost();
                    minIndex = i;
                } else if (openList.get(i).getfCost() == minFcost) {
                    if (openList.get(i).gethCost() < openList.get(minIndex).gethCost()) {
                        minIndex = i;
                    }
                }
            }


            if (openList.isEmpty()) {
                System.out.println("No path found");
                return;
            }

            current = openList.get(minIndex);
            frame.repaint();

            if (current.equals(end)) {
                goalReached = true;
                trackPath(delay);
            }


            if(delay > 0){
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        openList.clear();
        checkedList.clear();
        current = start;
        goalReached = false;
    }


    // Track the shortest path using node.parent
    public void trackPath(int delay){
        current = end;
        while(current != start){
            if(current != end)
                current.color = Color.YELLOW;
            current = current.getParent();

            if(delay > 0){
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            frame.repaint();
        }
    }


    // Get costs for all nodes.
    public void getCostAll(){
        for (int i = 0; i < graph.getSizeY(); i++) {
            for (int j = 0; j < graph.getSizeX(); j++) {
                getCost(graph.getNode(i,j));
            }
        }
    }
    // Get costs for a given node
    private void getCost(Node node){
        // calculate Gcost
        if(start != null && end != null) {
            int xDistance = Math.abs(node.getX() - start.getX());
            int yDistance = Math.abs(node.getY() - start.getY());
            node.setgCost(xDistance + yDistance);

            // calculate Hcost
            xDistance = Math.abs(node.getX() - end.getX());
            yDistance = Math.abs(node.getY() - end.getY());
            node.sethCost(xDistance + yDistance);

            node.setgCost(node.gethCost() + node.getgCost());
        }
    }

    private void setChecked(Node node){
        node.isChecked = true;
        checkedList.add(node);
        if(!node.equals(start) && !node.equals(end))
            node.color = Color.orange;
    }
    private void openNode(Node node){
        if(!node.isOpen && !node.isChecked && !node.isWall){
            node.isOpen = true;
            node.setParent(current);
            openList.add(node);
            if(!node.equals(start) && !node.equals(end))
                node.color = Color.CYAN;
        }

    }



}