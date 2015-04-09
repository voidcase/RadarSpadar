package datastructures;

import game.Vector2D;
import globals.Globals;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;

public class KeyboardStateListener extends Observable implements KeyListener {
	private boolean arrowUp = false;
	private boolean arrowDown = false;
	private boolean arrowRight = false;
	private boolean arrowLeft = false;

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == Globals.KeyCodes.UP)
			arrowUp = true;
		if(e.getKeyCode() == Globals.KeyCodes.DOWN)
			arrowDown = true;
		if(e.getKeyCode() == Globals.KeyCodes.RIGHT)
			arrowRight = true;
		if(e.getKeyCode() == Globals.KeyCodes.LEFT)
			arrowLeft = true;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == Globals.KeyCodes.UP)
			arrowUp = false;
		else if(e.getKeyCode() == Globals.KeyCodes.DOWN)
			arrowDown = false;
		else if(e.getKeyCode() == Globals.KeyCodes.RIGHT)
			arrowRight = false;
		else if(e.getKeyCode() == Globals.KeyCodes.LEFT)
			arrowLeft = false;
		else{
			setChanged();
			notifyObservers(e);
		}
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
