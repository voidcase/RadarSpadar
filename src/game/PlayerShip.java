package game;

import gui.RadarWindow;
import datastructures.KeyboardStateListener;

public class PlayerShip extends Ship {
	
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
}
