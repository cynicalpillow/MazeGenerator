import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Driver {
	static JFrame f;
	static DrawPanel p;
	static boolean run = true;
	static int score = 0;
	static Maze m;
	static ArrayList<Cell> path = new ArrayList<>();
	static int size = 5;

	public static void init(){
		f = new JFrame();
		p = new DrawPanel();
		p.addKeyListener(p);
		m = new Maze(86, 155);
		m.generateRecursive();
		f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		f.setUndecorated(true);
		p.setFocusable(true);
		f.add(p);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	static class DrawPanel extends JPanel implements KeyListener{
		public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.black);
            boolean[][] maze = m.getMaze();
            for(int i = 0; i < maze.length+2; i++){
            	for(int j = 0; j < maze[0].length+2; j++){
            		if(i == 0 || j == 0 || i == maze.length+1 || j == maze[0].length+1){
            			g.fillRect(j*size+size, i*size+size, size, size);
            		} else if(maze[i-1][j-1]){
            			g.fillRect(j*size+size, i*size+size, size, size);
            		}
            	}
            }
            g.setColor(Color.blue);
            path = m.getPath();
            for(Cell c : path){
            	g.fillRect(c.c * size + 2*size, c.r * size + 2*size, size, size);
            }
        }
      	public void keyPressed(KeyEvent e){
      		if(e.getKeyCode() == KeyEvent.VK_SPACE){
      			m.solve();
      			p.repaint();
      		} else if(e.getKeyCode() == KeyEvent.VK_R){
      			m.reset();
      			m.generateRecursive();
      			p.repaint();
      		}
      	}
	    public void keyReleased(KeyEvent e){}
	    public void keyTyped(KeyEvent e){}
	}
	public static void main(String args[]){
		init();
	}
}