package game;

import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import gui.RadarWindow;
import datastructures.KeyboardStateListener;

public class PlayerShip extends Ship implements Observer{
	
	KeyboardStateListener keyboard;
	
	public PlayerShip(KeyboardStateListener ksl){
		name = "♠";
		keyboard = ksl;
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		Vector2D arrowDir = keyboard.generateArrowDirection().scale(0.01);
		vel = vel.add(arrowDir);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		KeyEvent e = (KeyEvent)arg1;
	}
}
