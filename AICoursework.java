package aicoursework;

import java.util.ArrayList;

public class AICoursework {

    private Frame frame;//JFrame for display

    private PathFinding pathFinding;//The A* pathinfing algorithm
    private Dijkstra dijkstra;

    //level characteristics
    private int cellWidth;
    private int levelWidth;
    private int levelHeight;

    //The blocks and free space in the level
    boolean[] grid;

    Agent agent;
    
    boolean aStar = false;

    public AICoursework(){
        cellWidth = 32;
        levelWidth = 32;
        levelHeight = 32;

        initGrid();

        agent = new Agent(8, 8);

        frame = new Frame(levelWidth * cellWidth + 96, levelHeight * cellWidth + 96, grid, levelWidth, levelHeight, cellWidth, agent, this);

        pathFinding = new PathFinding(levelWidth, levelHeight);
        dijkstra = new Dijkstra(levelWidth, levelHeight);

        setPath(15, 15);

	run();
    }

    public void run(){
	int i = 0;

	while(true)
        {
            //Slows down the movement so that it's visivle
            i++;
            if(i == 2000000){
               i = 0;
               agent.move(levelWidth);
            }

            frame.paint();
        }
    }

    public void setPath(int endX, int endY){
        //The positions in the grid array of the start and end of the path
        int endPos = endY * levelWidth + endX;
        int agentPos = agent.getY() * levelWidth + agent.getX();

        if(grid[endPos]){
            if(aStar){
                agent.setPath(pathFinding.getPath(agentPos, endPos, grid));
            }
            else{
                agent.setPath(dijkstra.getPath(agentPos, endPos, grid));
            }
        }
    }

    public void addBlock(int x, int y){
        grid[(y * levelWidth) + x] = false;
    }

    public void removeBlock(int x, int y){
        grid[(y * levelWidth) + x] = true;
    }
    
    public void changeAlgorithm(){
        aStar = !aStar;
        System.out.println(aStar);
    }

    public void initGrid(){
        grid = new boolean[levelWidth * levelHeight];

        //Sets all cells on the grid to be passable
        for(int i = 0; i < grid.length; i++){
            grid[i] = true;
        }

        //Adds some inital impassable tiles
        for(int i = 10; i < levelWidth; i++){
            int pos = (11 * levelWidth) + i;
            grid[pos] = false;
        }

        for(int i = 11; i < levelWidth - 10; i++){
            int pos = (i * levelWidth) + 10;
            grid[pos] = false;
        }
    }

    public static void main(String[] args) {
        new AICoursework();
    }

}
