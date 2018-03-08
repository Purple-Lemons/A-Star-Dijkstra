package aicoursework;

import javax.swing.*;
import java.util.*;

public class Frame extends JFrame{

    Panel panel;

    public Frame(int width, int height, boolean[] grid, int levelWidth, int levelHeight, int cellWidth, Agent agent, AICoursework main){
        panel = new Panel(grid, levelWidth, levelHeight, cellWidth, agent, main);
        add(panel);

        setVisible(true);
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setTitle("A* Pathfinding");
    }

    public Panel getPanel(){
        return panel;
    }

    public void paint(){
        repaint();
    }
}
