package datastructures;

import game.Vector2D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardStateListener implements KeyListener {
	private boolean arrowUp = false;
	private boolean arrowDown = false;
	private boolean arrowRight = false;
	private boolean arrowLeft = false;
	
	public boolean getArrowUp() {
		return arrowUp;
	}

	public boolean getArrowDown() {
		return arrowDown;
	}

	public boolean getArrowRight() {
		return arrowRight;
	}

	public boolean getArrowLeft() {
		return arrowLeft;
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
	
	public Vector2D generateArrowDirection() {
		float vert = 0;
		float hori = 0;
		if(arrowUp)
			vert--;
		if(arrowDown)
			vert++;
		if(arrowRight)
			hori++;
		if(arrowLeft)
			hori--;
		return new Vector2D(hori, vert);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// this is irrelevant
	}
}
