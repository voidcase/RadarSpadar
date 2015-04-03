package datastructures;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardStateListener implements KeyListener {
	private boolean arrowUp = false;
	private boolean arrowDown = false;
	private boolean arrowRight = false;
	private boolean arrowLeft = false;
	private boolean qPressed = false;
	private boolean ePressed = false;
	
	
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

	public boolean getQPressed() {
		return qPressed;
	}

	public boolean getEPressed() {
		return ePressed;
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
		if(e.getKeyCode() == KeyEvent.VK_Q)
			qPressed = true;
		if(e.getKeyCode() == KeyEvent.VK_E)
			ePressed = true;
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
		if(e.getKeyCode() == KeyEvent.VK_Q)
			qPressed = false;
		if(e.getKeyCode() == KeyEvent.VK_E)
			ePressed = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// this is irrelevant
	}
}
