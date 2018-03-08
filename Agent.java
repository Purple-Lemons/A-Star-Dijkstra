package aicoursework;

import java.util.ArrayList;

public class Agent {

    private int x;
    private int y;

    private ArrayList<Integer> path;
    private int pathPos;

    public Agent(int _x, int _y){
        x = _x;
        y = _y;
    }

    public void move(int levelWidth){
        pathPos--;
        if(pathPos >= 0){
            x = path.get(pathPos) % levelWidth;
            y = path.get(pathPos) / levelWidth;
        }
	else{
		path.clear();
	}
    }

    public void setPath(ArrayList<Integer> _path)
    {
        path = _path;
        pathPos = path.size() - 1;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public ArrayList<Integer> getPath(){
	return path;
    }
}
