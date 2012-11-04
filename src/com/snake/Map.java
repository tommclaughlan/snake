package com.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;

public class Map {

	public int width;
	public int height;

	public int tilewidth = 20;
	public int tileheight = 20;
	
	private int[][] squares;
	public Node bit = null;
	
	private Random rand = new Random();
	
	public Map(int x, int y) {
		this.width = x;
		this.height = y;
		squares = new int[x][y];
		for(int i=0; i<x; ++i){
			for(int j=0; j<y; ++j){
				squares[i][j] = 0;
			}
		}
	}
	
	public void render(Graphics g) {
		for(int i=0; i<width; ++i){
			for(int j=0; j<height; ++j){
				if(squares[i][j] == 0) {
					g.setColor(new Color(210,230,190));
					g.fillRect(i*tilewidth, j*tileheight, tilewidth, tileheight);
					g.setColor(new Color(220,240,200));
					g.fillRect(i*tilewidth+2, j*tileheight+2, tilewidth-4, tileheight-4);
				}
				if(bit != null && i == bit.x && j == bit.y) {
					g.setColor(new Color(100,120,80));
					g.fillRect(i*tilewidth, j*tileheight, tilewidth, tileheight);
					g.setColor(new Color(140,180,120));
					g.fillRect(i*tilewidth+2, j*tileheight+2, tilewidth-4, tileheight-4);
				}

				
			}
		}
	}
	
	public void tick() {
		if(bit==null)
			bit = new Node(rand.nextInt(width-1), rand.nextInt(height-1));
	}

	public boolean newBit(List<Node> mysquares) {

		int newx = rand.nextInt(width-1);
		int newy = rand.nextInt(height-1);
		
		for(Node s : mysquares) {
			if(newx == s.x && newy == s.y)
				return false;
		}
		
		bit = new Node(newx,newy);
		
		return true;
		
	}
}
