package gui;

import game.Vector2D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

public class RadarWindow extends JFrame implements Observer, KeyListener {
	private boolean arrowUp = false;
	private boolean arrowDown = false;
	private boolean arrowRight = false;
	private boolean arrowLeft = false;
	
	private long lastFrameTime;
	private long timeDeltaMillis;
	
	private void initLoop() {
		Thread loopUpdate = new Thread() {
			@Override
			public void run() {
				lastFrameTime = System.currentTimeMillis();
				while(!isInterrupted()) {
					try {
						long currentFrameTime = System.currentTimeMillis();
						timeDeltaMillis = currentFrameTime - lastFrameTime;
						lastFrameTime = currentFrameTime;
						update();
						sleep(1000 * (1/60));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		loopUpdate.start();
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		update();
	}
	
	/** Insert game logic here */
	protected void update() {
		
		Vector2D arrowDir = generateArrowDirection();
		//code
	}
	
	private Vector2D generateArrowDirection() {
		float vert = 0;
		float hori = 0;
		if(arrowUp)
			vert++;
		if(arrowDown)
			vert--;
		if(arrowRight)
			hori++;
		if(arrowLeft)
			hori--;
		return new Vector2D(vert, hori);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP)
			arrowUp = true;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			arrowDown = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			arrowRight = true;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			arrowLeft = true;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP)
			arrowUp = false;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			arrowDown = false;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			arrowRight = false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			arrowLeft = false;		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// this is irrelevant
	}
}
