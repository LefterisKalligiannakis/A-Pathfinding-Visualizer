/*
    Frame class for pathfinding visualization.
    Each box represents a node in the graph, by clicking a node you can
    remove all verticies looking to it, essentially making it a wall in the graph.
    s+click, adds the start node
    e+click, adds the end node

    @author Eleftherios Kalligiannakis
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Frame extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener, MouseWheelListener {

    private final int WIDTH = 930;
    private final int HEIGHT = 950;
    private int size = 15; // square size
    Pathfinder pathfinder;
    final OptionHandler optionhandler;
    private JFrame window;
    boolean isRan = false;
    boolean diagonals = false;
    boolean visualize = false;
    private boolean isMousePressed = false;
    private int rolloverx = Integer.MIN_VALUE;
    private int rollovery = Integer.MIN_VALUE;
    private int key = 0; // pressed key


    public Frame() {


        pathfinder = new Pathfinder(this);


        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);
        addMouseWheelListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocusInWindow(true);
        requestFocus();


        window = new JFrame();
        window.setSize(WIDTH, HEIGHT);
        window.setTitle("A* Pathfinding Visualization");
        window.setVisible(true);
        window.setContentPane(this);
        window.setResizable(false);
        window.addKeyListener(this);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        optionhandler = new OptionHandler(this);
        add(optionhandler);
        optionhandler.setBounds(this.getWidth() - 900, this.getHeight()-40,  400, 35);

        revalidate();
        repaint();

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int row = 0, col = 0;
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, WIDTH, HEIGHT);


        for (int i = 0; i < this.getHeight(); i += size) {
            for (int j = 0; j < this.getWidth(); j += size) {
                g.setColor(pathfinder.graph.getNode(row, col).color);
                g.fillRect(i, j, size - 1, size - 1);
                col++;
            }
            col = 0;
            row++;
        }

    }


    void reset() {
        pathfinder.graph = new Graph(pathfinder.graph.getSizeX(), pathfinder.graph.getSizeX(), this);
        pathfinder.start = null;
        pathfinder.end = null;
        isRan = false;
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        requestFocus();
        key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            if (!isRan) {
                if(visualize)
                    pathfinder.delay = 100;
                else
                    pathfinder.delay = 0;

                Thread thread = new Thread(pathfinder);
                thread.start();
            }
        }

        if (key == KeyEvent.VK_BACK_SPACE) {
            isRan = false;
            reset();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        key = 0;
    }

    /*
    Detects mouse movement and calculates the square the mouse is over using the
    mouse's coordinates.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        // mouse coordinates x
        int mx = e.getX();
        // mouse coordinates y
        int my = e.getY();
        rolloverx = mx / size;
        rollovery = my / size;
        if (diagonals != optionhandler.diagonals.isSelected()) {
            diagonals = optionhandler.diagonals.isSelected();
            reset();
            requestFocus();
        }
        if (visualize != optionhandler.visualize.isSelected()) {
            visualize = optionhandler.visualize.isSelected();
            requestFocus();
        }
    }


    /*
        Click to add wall
     */
    @Override
    public void mousePressed(MouseEvent e) {
        isMousePressed = true; // to check if mouse is pressed for click and drag

        if (key == KeyEvent.VK_S) {
            if(pathfinder.start != null)
                pathfinder.start.color = Color.WHITE;
            pathfinder.start = pathfinder.graph.getNode(rolloverx, rollovery);
            pathfinder.current = pathfinder.start;
            pathfinder.start.color = Color.BLUE;
            pathfinder.getCostAll();
        } else if (key == KeyEvent.VK_E) {
            if(pathfinder.end != null)
                pathfinder.end.color = Color.WHITE;
            pathfinder.end = pathfinder.graph.getNode(rolloverx, rollovery);
            pathfinder.end.color = Color.RED;
            pathfinder.getCostAll();
        } else {
            if (e.getButton() == MouseEvent.BUTTON1) {
                pathfinder.graph.makeWall(rolloverx, rollovery);
            } else if (e.getButton() == MouseEvent.BUTTON3)
                pathfinder.graph.removeWall(rolloverx, rollovery);
        }

        repaint();
    }

    /*
        Click and drag functionality for deleting and adding walls.
        When mouse is clicked and dragged it continuously calculates
        the square the mouse is over.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (isMousePressed) {
            rolloverx = e.getX() / size;
            rollovery = e.getY() / size;

            int modifiers = e.getModifiersEx(); //e.getButton() doesn't work for some reason...

            if ((modifiers & InputEvent.BUTTON1_DOWN_MASK) != 0 && key != KeyEvent.VK_S && key != KeyEvent.VK_E) {
                pathfinder.graph.makeWall(rolloverx, rollovery);
            } else if ((modifiers & InputEvent.BUTTON3_DOWN_MASK) != 0 && key != KeyEvent.VK_S && key != KeyEvent.VK_E) {
                pathfinder.graph.removeWall(rolloverx, rollovery);
            }

            repaint();
        }
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        isMousePressed = false;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int rotation = e.getWheelRotation();

        if (rotation == 1 && size > 15) {
            size -= 5;
            repaint();
        }
        if(rotation == -1){
            size += 5;
            repaint();
        }

    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }



}
