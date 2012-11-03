package com.snake;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.snake.Keys;
import com.snake.InputHandler;

@SuppressWarnings("serial")
public class Main extends Canvas implements Runnable{

	private int WIDTH = 800;
	private int HEIGHT = 600;
    public Keys keys = new Keys();
    private boolean running = false;
    private int framerate = 60;
    private int fps = 0;
    private Map map = new Map(20,20);
	
    private Snake snake;
    
	public Main() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.addKeyListener(new InputHandler(keys));
	}
	
	public static void main(String[] args) {
		Main mc = new Main();

        JFrame frame = new JFrame();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(mc);
        frame.setContentPane(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        mc.start();
	}
	
	public void start() {
        running = true;
        Thread thread = new Thread(this);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }

	public void run() {
		long lastTime = System.nanoTime();
		double unprocessed = 0;
        int frames = 0;
        long lastTimer1 = System.currentTimeMillis();

    	try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        int toTick = 0;

        long lastRenderTime = System.nanoTime();
        int min = 999999999;
        int max = 0;
		while(running) {
			
            double nsPerTick = 1000000000.0 / framerate;
            boolean shouldRender = false;
            while (unprocessed >= 1) {
                toTick++;
                unprocessed -= 1;
            }

            int tickCount = toTick;
            if (toTick > 0 && toTick < 3) {
                tickCount = 1;
            }
            if (toTick > 20) {
                toTick = 20;
            }
            
            for (int i = 0; i < tickCount; i++) {
                toTick--;
                tick();
                shouldRender = true;
            }
			
			BufferStrategy bs = getBufferStrategy();
            if (bs == null) {
                createBufferStrategy(3);
                continue;
            }

            if (shouldRender) {
                frames++;
                Graphics g = bs.getDrawGraphics();
    			render(g);

                long renderTime = System.nanoTime();
                int timePassed = (int) (renderTime - lastRenderTime);
                if (timePassed < min) {
                    min = timePassed;
                }
                if (timePassed > max) {
                    max = timePassed;
                }
                lastRenderTime = renderTime;
            }

            long now = System.nanoTime();
            unprocessed += (now - lastTime) / nsPerTick;
            lastTime = now;

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (shouldRender) {
                if (bs != null) {
                    bs.show();
                }
            }
            
            if (System.currentTimeMillis() - lastTimer1 > 1000) {
                lastTimer1 += 1000;
                fps = frames;
                frames = 0;
            }
        }

	}
	
	private void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		map.render(g);
		snake.render(g);
		
		g.setColor(Color.WHITE);
		g.drawString("Score: "+snake.score, 5, 15);
		if(snake.isDead) {
			g.drawString("DEAD! Press Space to restart", 5, 35);
		}
	}

	private void tick() {
		keys.tick();
		if(!snake.isDead) {
			map.tick();
			snake.tick();
		}
		else {
			if(keys.space.isDown) {
				end();
				init();
			}
		}
	}

	private void end() {
		map = new Map(20,20);
	}

	private void init() {
        setFocusTraversalKeysEnabled(false);
        requestFocus();
        snake = new Snake(keys,10,10,map);
	}

}
