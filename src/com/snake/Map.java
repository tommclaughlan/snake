package com.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Map {

	public int width;
	public int height;

	public int tilewidth = 20;
	public int tileheight = 20;
	
	private int[][] squares;
	
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
				if(squares[i][j] == 0)
					g.setColor(Color.BLACK);
				else if(squares[i][j] == 1)
					g.setColor(Color.RED);

				g.fillRect(i*tilewidth, j*tileheight, tilewidth, tileheight);
			}
		}
	}
	
}
