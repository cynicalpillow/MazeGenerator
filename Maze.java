import java.util.*;
public class Maze {
	private boolean[][] maze; //Maze array
	private boolean[][] visited; //Visited array for generation
	private int w; //Width
	private int h; //Height
	private int visitedCount; //How many empty spaces have been visited in the maze
	private ArrayList<Cell> path = new ArrayList<>(); //Shortest path
	public Maze(int h, int w){
		this.w = w;
		this.h = h;
		visitedCount = w*h;
		maze = new boolean[h*2+1][w*2+1];
		visited = new boolean[h*2+1][w*2+1];
		//Generate the walls
		for(int j = 1; j < w*2; j+=2){
			for(int i = 0; i < h*2+1; i++){
				maze[i][j] = true;
			}
		}
		for(int i = 1; i < h*2; i+=2){
			for(int j = 0; j < w*2+1; j++){
				maze[i][j] = true;
			}
		}
	}
	//Recursive backtracking algorithm for generating maze
	public void generateRecursive(){
		ArrayDeque<Cell> q = new ArrayDeque<>();
		Cell c = new Cell(0, 0);
		q.push(c);
		visited[0][0] = true;
		int visits = 0;
		while(visits < visitedCount || !q.isEmpty()){
			while((c.r + 2 < 2*h+1 && !visited[c.r+2][c.c]) 
				|| (c.r - 2 >= 0 && !visited[c.r-2][c.c]) 
				|| (c.c + 2 < 2*w+1 && !visited[c.r][c.c+2]) 
				|| (c.c - 2 >= 0 && !visited[c.r][c.c-2])){
				int rand = (int)(Math.random() * 4 + 1);
				//Check up
				if(rand == 1 && c.r - 2 >= 0 && !visited[c.r-2][c.c]){
					maze[c.r-1][c.c] = false;
					Cell x = new Cell(c.r-2, c.c);
					q.add(x);
					visited[c.r-2][c.c] = true;
					visits++;
					c = x;
				}
				//Check down
				if(rand == 2 && c.r + 2 < 2*h+1 && !visited[c.r+2][c.c]){
					maze[c.r+1][c.c] = false;
					Cell x = new Cell(c.r+2, c.c);
					q.add(x);
					visited[c.r+2][c.c] = true;
					visits++;
					c = x;
				}
				//Check left
				if(rand == 3 && c.c - 2 >= 0 && !visited[c.r][c.c-2]){
					maze[c.r][c.c-1] = false;
					Cell x = new Cell(c.r, c.c-2);
					q.add(x);
					visited[c.r][c.c-2] = true;
					visits++;
					c = x;
				}
				//Check right
				if(rand == 4 && c.c + 2 < 2*w+1 && !visited[c.r][c.c+2]){
					maze[c.r][c.c+1] = false;
					Cell x = new Cell(c.r, c.c+2);
					q.add(x);
					visited[c.r][c.c+2] = true;
					visits++;
					c = x;
				}
			}
			if(!q.isEmpty()){
				c = q.pop();
			}
		}
	}
	//Solve the maze with bfs :D
	public void solve(){
		int[] yMove = {-2, 2};
		int[] xMove = {-2, 2};
		ArrayDeque<Cell> q = new ArrayDeque<>();
		visited = new boolean[h*2+1][w*2+1];
		visited[0][0] = true;
		Cell start = new Cell(0,0);
		start.path.add(start);
		q.add(start);
		while(!q.isEmpty()){
			Cell c = q.poll();
			if(c.r == 2*h && c.c == 2*w){
				path = c.path;
				break;
			}
			for(int i = 0; i < 2; i++){
				if(yMove[i] + c.r >= 0 && yMove[i] + c.r < 2*h+1 && !maze[c.r+(yMove[i]/2)][c.c] && !visited[c.r+yMove[i]][c.c]){
					visited[c.r+yMove[i]][c.c] = true;
					Cell x = new Cell(yMove[i] + c.r, c.c);
					x.path = new ArrayList<Cell>(c.path);
					x.path.add(new Cell(c.r+(yMove[i]/2), c.c));
					x.path.add(x);
					q.add(x);
				}
			}
			for(int i = 0; i < 2; i++){
				if(xMove[i] + c.c >= 0 && xMove[i] + c.c < 2*w+1 && !maze[c.r][c.c+(xMove[i]/2)] && !visited[c.r][c.c+xMove[i]]){
					visited[c.r][c.c+xMove[i]] = true;
					Cell x = new Cell(c.r, xMove[i] + c.c);
					x.path = new ArrayList<Cell>(c.path);
					x.path.add(new Cell(c.r, c.c+(xMove[i]/2)));
					x.path.add(x);
					q.add(x);
				}
			}
		}
	}
	//Reset method for future generation
	public void reset(){
		maze = new boolean[h*2+1][w*2+1];
		visited = new boolean[h*2+1][w*2+1];
		path = new ArrayList<>();
		for(int j = 1; j < w*2; j+=2){
			for(int i = 0; i < h*2+1; i++){
				maze[i][j] = true;
			}
		}
		for(int i = 1; i < h*2; i+=2){
			for(int j = 0; j < w*2+1; j++){
				maze[i][j] = true;
			}
		}
	}
	public boolean[][] getMaze(){
		return maze;
	}
	public ArrayList<Cell> getPath(){
		return path;
	}
	public int getHeight(){
		return h;
	}
	public int getWidth(){
		return w;
	}
}