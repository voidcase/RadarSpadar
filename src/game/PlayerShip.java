package game;

import gui.RadarWindow;
import datastructures.KeyboardStateListener;

public class PlayerShip extends Ship {
	
	KeyboardStateListener keyboard;
	
	public PlayerShip(KeyboardStateListener ksl){
		name = "[player]";
		keyboard = ksl;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		Vector2D arrowDir = keyboard.generateArrowDirection().scale(RadarWindow.getTimeDelta()*0.1);
		pos = pos.add(arrowDir);
	}
}
