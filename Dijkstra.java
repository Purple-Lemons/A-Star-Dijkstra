package aicoursework;

import java.util.*;

public class Dijkstra {
    
    private boolean[] grid;
    
    private final int max = 1000000;//A number for unchecked nodes - larger than any potential distance on the grid
    
    private int levelWidth;
    private int levelHeight;
    
    public Dijkstra(int _levelWidth, int _levelHeight){
        levelWidth = _levelWidth;
        levelHeight = _levelHeight;
    }
    
    public ArrayList<Integer> getPath(int start, int goal, boolean[] _grid){
        
        grid = _grid;//Updates the grid
        
        ArrayList<Integer> dist = new ArrayList<>();//Distances from source node
        ArrayList<Integer> parent = new ArrayList<>();//Parent node of current node
        ArrayList<Integer> Q = new ArrayList<>();//List of unchecked nodes
        
        for(int i = 0; i < grid.length; i++){
            dist.add(max);//Set all of the distances to a high number
            parent.add(-1);//All nodes start with no parent
            Q.add(i);
        }
        
        dist.set(start, 0);//The source node always have 0 distance from itself
        
        int numSteps = 0;//Used for evaluation
        
        while(!Q.isEmpty()){
            int u = getMin(dist, Q);//Find the node with the shortest distance from the source
            
            //Removes u from Q
            for(int i = 0; i < Q.size(); i++){
                if(Q.get(i) == u){
                    Q.remove(i);
                    break;
                }
            }
            
            ArrayList<Integer> neighbours = getNeighbours(u, Q);//Finds u's neighbouring nodes
            
            for(int i = 0; i < neighbours.size() - 1; i++){
                //The distance can only be 3 (if vertical or horizontal) or 4 (if diagonal)
                //So the neighbours distance can be deduced from it's position in the array
                int neighbourDist = (i < neighbours.get(neighbours.size() - 1)) ? 3 : 4;
                
                int newDist = dist.get(u) + neighbourDist;
                
                if(newDist < dist.get(neighbours.get(i))){
                    dist.set(neighbours.get(i), newDist);
                    parent.set(neighbours.get(i), u);
                }
            }
            
            numSteps++;
        }
        
        ArrayList<Integer> path = new ArrayList<>();
        int u = goal;
        //Extracts the path from the evaluated nodes
        while(parent.get(u) != -1){
            path.add(u);
            u = parent.get(u);
        }
        
        //For evaluting the number of nodes checked/stored
        int numNodes = 0;
        for(int i = 0; i < parent.size(); i++){
            if(parent.get(i) != -1){
                numNodes++;
            }
        }
        System.out.println(numSteps + " -- " + numNodes);
        
        return path;
    }
    
    //Find the node with the minimum distance from the source node
    public int getMin(ArrayList<Integer> dist, ArrayList<Integer> Q){
        int min = max;
        int minPos = 0;
        
        for(int i = 0; i < dist.size(); i++){
            if(dist.get(i) <= min && Q.contains(i)){
                min = dist.get(i);
                minPos = i;
            }
        }
        
        return minPos;
    }
    
    //Finds the neighbouring nodes to the current node
    public ArrayList<Integer> getNeighbours(int current, ArrayList<Integer> Q)
    {
        ArrayList<Integer> neighbours = new ArrayList<>();
        
        //North, South... North East etc.
        int N = current - levelWidth;
        int S = current + levelWidth;
        int E = current + 1;
        int W = current - 1;
        int NE = N + 1;
        int NW = N - 1;
        int SE = S + 1;
        int SW = S - 1;

        int offset = 0;//The position at which the horizontal nodes are added and therefor the distance becomes 4
        
        //If N is on the grid, is passable and is in Q
        if(N >= 0 && grid[N] && Q.contains(N)){
            neighbours.add(N);
            offset++;
        }
        if(S < levelWidth * levelHeight - 1 && Q.contains(S) && grid[S]){
            neighbours.add(S);
            offset++;
        }
        if(E < levelWidth * levelHeight - 1 && grid[E] && Q.contains(E) && E % levelWidth != 0){
            neighbours.add(E);
            offset++;
        }
        if(W >= 0 && grid[W] && Q.contains(W) && W % levelWidth != levelWidth - 1){
            neighbours.add(W);
            offset++;
        }
        if(NE >= 0 && grid[NE] && Q.contains(NE) && NE % levelWidth != 0){
            neighbours.add(NE);
        }
        if(NW >= 0 && grid[NW] && Q.contains(NW) && NW % levelWidth != levelWidth - 1){
            neighbours.add(NW);
        }
        if(SE < levelWidth * levelHeight - 1 && grid[SE] && Q.contains(SE) && SE % levelWidth != 0){
            neighbours.add(SE);
        }
        if(SW < levelWidth * levelHeight - 1 && grid[SW] && Q.contains(SW) && SW % levelWidth != levelWidth - 1){
            neighbours.add(SW);
        }
        
        neighbours.add(offset);
        
        return neighbours;
    }
}
