/*
    Panel in the main frame with options for the visualization of the algorithm

    @author Eleftherios Kalligiannakis
*/

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class OptionHandler extends JPanel {
    JCheckBox diagonals;
    JCheckBox visualize;
    JSlider speed;

    public OptionHandler(Frame frame) {
        diagonals = new JCheckBox("Diagonals");
        visualize = new JCheckBox("Visualize");
        speed = new JSlider(SwingConstants.HORIZONTAL, 0, 99, 90);


        // Set the text color of the checkboxes
        diagonals.setForeground(Color.BLACK);
        visualize.setForeground(Color.BLACK);

        diagonals.setOpaque(false);
        visualize.setOpaque(false);
        speed.setOpaque(false);
        add(diagonals);
        add(visualize);
        add(speed);
        setOpaque(false);
        speed.addChangeListener(e -> frame.requestFocus()); // For giving focus to the frame after using the slider


        // Add mouse listener to handle transparency change
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                diagonals.setForeground(Color.BLACK);
                visualize.setForeground(Color.BLACK);
                speed.setForeground(Color.BLACK);
                setBackground(new Color(0, 0, 0, 55));
                repaint();
            }


            @Override
            public void mouseExited(MouseEvent e) {
                diagonals.setForeground(Color.WHITE);
                visualize.setForeground(Color.WHITE);
                speed.setForeground(Color.WHITE);
                setBackground(new Color(0, 0, 0, 127));
                repaint();
            }
        });


        // make panel transparent
        setBackground(new Color(0, 0, 0, 127));
        diagonals.setForeground(Color.WHITE);
        visualize.setForeground(Color.WHITE);
        speed.setForeground(Color.WHITE);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getBackground().getAlpha() > 0) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }


}
