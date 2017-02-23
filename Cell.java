import java.util.*;
public class Cell{
	int r;
	int c;
	ArrayList<Cell> path = new ArrayList<>();
	public Cell(int r, int c){
		this.r = r;
		this.c = c;
	}
}