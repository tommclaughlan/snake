package com.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;

public class Snake {

	public int xpos;
	public int ypos;
	private Keys keys;
	public int direction = 0; // N,E,S,W = 0,1,2,3
	private Map map;
	public boolean isDead = false;
	
	public int score = 0;
	private int length = 3;
	
	private List<Node> mysquares= new ArrayList<Node>();
	
	private int changetimer = 10;
	private int count = 0;
	
	public Snake(Keys _keys, int x, int y, Map _map) {
		xpos = x;
		ypos = y;
		keys = _keys;
		map = _map;
	}
	
	public void tick() {
		
		if(keys.up.isDown && direction != 2)
			direction = 0;
		if(keys.right.isDown && direction != 3)
			direction = 1;
		if(keys.down.isDown && direction != 0)
			direction = 2;
		if(keys.left.isDown && direction != 1)
			direction = 3;
		
		count++;
		if(count == changetimer) {
			if(direction == 0) {
				if(ypos == 0)
					ypos = map.height-1;
				else
					ypos--;
			}
			else if(direction == 1) {
				if(xpos == map.width-1)
					xpos = 0;
				else
					xpos++;
			}
			else if(direction == 2) {
				if(ypos == map.height-1)
					ypos = 0;
				else
					ypos++;
			}
			else if(direction == 3) {
				if(xpos == 0)
					xpos =  map.width-1;
				else
					xpos--;
			}
			count = 0;
			
			if(mysquares.size() >= length)
				mysquares.remove(0);
			
			for(Node s : mysquares){
				if(s.x == xpos && s.y == ypos){
					isDead = true;
				}
			}
			
			if(xpos == map.bit.x && ypos == map.bit.y) {
				while(!map.newBit(mysquares));
				addScore(1);
			}
			
			mysquares.add(new Node(xpos,ypos));
		}
		
	}
	
	public void addScore(int value) {
		score+=value;
		length++;
	}
	
	public void render(Graphics g) {
		for(Node s : mysquares){
			g.setColor(new Color(220,0,0));
			g.fillRect(s.x*map.tilewidth, s.y*map.tileheight, map.tilewidth, map.tileheight);
			g.setColor(Color.RED);
			g.fillRect(s.x*map.tilewidth+2, s.y*map.tileheight+2, map.tilewidth-4, map.tileheight-4);
		}
	}
	
}
