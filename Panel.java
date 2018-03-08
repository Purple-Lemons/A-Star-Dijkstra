package aicoursework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class Panel extends JPanel{

    boolean[] grid;
    int cellWidth;

    Agent agent;
    AICoursework main;

    int levelWidth;
    int levelHeight;

    int xOffset;
    int yOffset;

    Color emptyColor = new Color(64, 64, 64);
    Color fullColor = new Color(200, 32, 32);
    Color agentColor = new Color(0, 128, 200);
    Color pathColor = new Color(0, 200, 64);

    //For determining what the user is doing with the mouse
    boolean dragged = false;
    boolean playerClicked = false;

    public Panel(boolean[] _grid, int _levelWidth, int _levelHeight, int _cellWidth, Agent _agent, AICoursework _main){
        grid = _grid;
        agent = _agent;

        levelWidth = _levelWidth;
        levelHeight = _levelHeight;
        cellWidth = _cellWidth;

        xOffset = 32;
        yOffset = 32;

        main = _main;

        setFocusable(true);

        addMouseListener(new Mouse());
        addMouseMotionListener(new MouseMotion());

        setBackground(new Color(0, 0, 0));
    }

    public void paint(Graphics g){
        super.paint(g);

	//draw grid
        for(int i = 0; i < grid.length; i++)
        {
            //If a cell is impassable it is one color, if it's passable, another
            if(grid[i]){
                g.setColor(emptyColor);
            }
            else{
                g.setColor(fullColor);
            }

            //Draws a cell
            g.fillRect((i % levelWidth) * cellWidth + xOffset, (i / levelWidth) * cellWidth + yOffset, cellWidth, cellWidth);

            //Draws an outline of the cell
            g.setColor(Color.white);
            g.drawRect((i % levelWidth) * cellWidth + xOffset, (i / levelWidth) * cellWidth + yOffset, cellWidth, cellWidth);
        }

	//draw path
	ArrayList<Integer> path = agent.getPath();
	for(int i = 0; i < path.size(); i++){
		int col = path.get(i) % levelWidth;
		int row = path.get(i) / levelWidth;

		int x = col * cellWidth + xOffset;
		int y = row * cellWidth + yOffset;

		g.setColor(pathColor);
		g.fillRect(x + cellWidth / 4, y + cellWidth / 4, cellWidth / 2, cellWidth / 2);
	}

        //Draws the agent
        g.setColor(agentColor);
        g.fillRect(agent.getX() * cellWidth + xOffset, agent.getY() * cellWidth + yOffset, cellWidth, cellWidth);

        g.dispose();
    }

    class MouseMotion extends MouseAdapter{
        public void mouseDragged(MouseEvent e){
            dragged = true;
        }
    }

    class Mouse extends MouseAdapter{
        public void mouseReleased(MouseEvent e){
            if(dragged && playerClicked){
                main.setPath((e.getX() - xOffset) / cellWidth, (e.getY() - yOffset) / cellWidth);

                dragged = false;
                playerClicked = false;
            }
        }

        public void mousePressed(MouseEvent e){
            //The position on the grid that has been clicked
            int col = (e.getX() - xOffset) / cellWidth;
            int row = (e.getY() - yOffset) / cellWidth;

            //If the mouse is clicked on the agent, the user is interacting with the agent
            //if not the user is interacting with the level
            if(agent.getX() == col && agent.getY() == row){
                playerClicked = true;
            }
            else if(e.getButton() == e.BUTTON1){
                main.addBlock(col, row);
            }
            else if(e.getButton() == e.BUTTON3){
                main.removeBlock(col, row);
            }
            else if(e.getButton() == e.BUTTON2){
                main.changeAlgorithm();
            }
        }
    }

}
