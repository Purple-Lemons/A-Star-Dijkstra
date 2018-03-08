package aicoursework;

import java.util.ArrayList;

public class PathFinding {

    private ArrayList<Integer> openSet;
    private ArrayList<Integer> closedSet;
    private ArrayList<Integer> openSetG;
    private ArrayList<Integer> closedSetG;
    
    boolean[] grid;
    
    private int levelWidth;
    private int levelHeight;
    
    public PathFinding(int _levelWidth, int _levelHeight){
        levelWidth = _levelWidth;
        levelHeight = _levelHeight;
        
        openSet = new ArrayList<Integer>();
        closedSet = new ArrayList<Integer>();
        openSetG = new ArrayList<Integer>();
        closedSetG = new ArrayList<Integer>();
    }
    
    public ArrayList<Integer> getPath(int start, int goal, boolean[] _grid){
        ArrayList<Integer> path = new ArrayList<Integer>();
        grid = _grid;
        
        //Clears the lists from the previous run
        openSet.clear();
        closedSet.clear();
        openSetG.clear();
        closedSetG.clear();
        
        closedSet.add(start);
        closedSetG.add(0);
        
        boolean found = false;
        
        int numCalcs = 0;//Tracks the number of loops
        
        while(!found){
            numCalcs++;
            for(int i = 0; i < closedSet.size(); i++){
                getSurrounding(closedSet.get(i), i);
            }
            
            //Finds the next cell with the lowest travel weight
            int currentLowest = levelWidth * levelHeight * 6;
            int currentLowestPos = 0;
            
            for(int i = 0; i < openSet.size(); i++){
                if(getH(openSet.get(i), goal) + openSetG.get(i) < currentLowest){
                    currentLowest = getH(openSet.get(i), goal) + openSetG.get(i);
                    currentLowestPos = i;
                }
            }
            
            //Moves the best cell from the open list to the closed list
            closedSet.add(openSet.get(currentLowestPos));
            closedSetG.add(openSetG.get(currentLowestPos));
            openSet.remove(currentLowestPos);
            openSetG.remove(currentLowestPos);
            
            if(closedSet.get(closedSet.size() - 1) == goal){
                found = true;
            }
            
            if(numCalcs > levelWidth * levelHeight){
                return null;
            }
        }
        System.out.println(numCalcs + " -- " + (closedSet.size() + openSet.size()));
        
        return getPath();
    }
    
    //Finds a path from the closed set
    public ArrayList<Integer> getPath(){
        ArrayList<Integer> path = new ArrayList<>();
        ArrayList<Integer> pathG = new ArrayList<>();
        
        path.add(closedSet.get(closedSet.size() - 1));
        pathG.add(closedSetG.get(closedSetG.size() - 1));
        
        for(int i = closedSet.size() - 2; i >= 0; i--){
            if((closedSetG.get(i) + 3 == pathG.get(pathG.size() - 1) && getDistance(closedSet.get(i), path.get(pathG.size() - 1)) == 3)|| 
                    (closedSetG.get(i) + 4 == pathG.get(pathG.size() - 1)  && getDistance(closedSet.get(i), path.get(pathG.size() - 1)) == 4)){
                path.add(closedSet.get(i));
                pathG.add(closedSetG.get(i));
                
            }
        }
        
        return path;
    }
    
    //Finds the valids cells surrounding the current cell
    public void getSurrounding(int current, int closedPos)
    {
        //North, South... North East etc.
        int N = current - levelWidth;
        int S = current + levelWidth;
        int E = current + 1;
        int W = current - 1;
        int NE = N + 1;
        int NW = N - 1;
        int SE = S + 1;
        int SW = S - 1;

        //If N is on the grid, is passable and hasn't already been checked
        if(N >= 0 && !closedSet.contains(N) && grid[N] && !openSet.contains(N)){
            openSet.add(N);
            openSetG.add(closedSetG.get(closedPos) + 3);
        }
        if(S < levelWidth * levelHeight - 1 && !closedSet.contains(S) && grid[S] && !openSet.contains(S)){
            openSet.add(S);
            openSetG.add(closedSetG.get(closedPos) + 3);
        }
        if(E < levelWidth * levelHeight - 1 && grid[E] && !openSet.contains(E) && !closedSet.contains(E) && E % levelWidth != 0){
            openSet.add(E);
            openSetG.add(closedSetG.get(closedPos) + 3);
        }
        if(W >= 0 && grid[W] && !openSet.contains(W) && !closedSet.contains(W) && W % levelWidth != levelWidth - 1){
            openSet.add(W);
            openSetG.add(closedSetG.get(closedPos) + 3);
        }
        if(NE >= 0 && grid[NE] && !openSet.contains(NE) && !closedSet.contains(NE) && NE % levelWidth != 0){
            openSet.add(NE);
            openSetG.add(closedSetG.get(closedPos) + 4);
        }
        if(NW >= 0 && grid[NW] && !openSet.contains(NW) && !closedSet.contains(NW) && NW % levelWidth != levelWidth - 1){
            openSet.add(NW);
            openSetG.add(closedSetG.get(closedPos) + 4);
        }
        if(SE < levelWidth * levelHeight - 1 && grid[SE] && !openSet.contains(SE) && !closedSet.contains(SE) && SE % levelWidth != 0){
            openSet.add(SE);
            openSetG.add(closedSetG.get(closedPos) + 4);
        }
        if(SW < levelWidth * levelHeight - 1 && grid[SW] && !openSet.contains(SW) && !closedSet.contains(SW) && SW % levelWidth != levelWidth - 1){
            openSet.add(SW);
            openSetG.add(closedSetG.get(closedPos) + 4);
        }
    }

    //Finds a hueristic distance from the current cell to the goal
    public int getH(int start, int goal){
        int startCol = start % levelWidth;
        int startRow = start / levelWidth;

        int goalCol = goal % levelWidth;
        int goalRow = goal / levelWidth;

        return Math.abs((goalRow - startRow)) + Math.abs((goalCol - startCol));
    }

    //Finds the direct distance between cells
    public int getDistance(int start, int goal){
        int startCol = (start % levelWidth) * 3;
        int startRow = (start / levelWidth) * 3;

        int goalCol = (goal % levelWidth) * 3;
        int goalRow = (goal / levelWidth) * 3;

        return (int) Math.sqrt( ((startCol - goalCol) * (startCol - goalCol)) + ((startRow - goalRow) * (startRow - goalRow)));
    }
}
