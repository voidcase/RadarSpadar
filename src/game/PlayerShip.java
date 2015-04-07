package game;

import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import datastructures.KeyboardStateListener;

public class PlayerShip extends Ship implements Observer{
	private KeyboardStateListener keyboard;
	private boolean inertia = false;
	private Space space;
	
	public PlayerShip(KeyboardStateListener ksl, Space s){
		name = "^";
		keyboard = ksl;
		ksl.addObserver(this);
		space = s;
	}

	@Override
	public void act() {
		Vector2D arrowDir = keyboard.generateArrowDirection().scale(0.01);
		vel = vel.add(arrowDir);
		if(inertia){
			vel = vel.add(vel.scale(-0.05));
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		KeyEvent e = (KeyEvent)arg1;
		//TOGGLE INERTIA THRUSERS
		if (e.getKeyCode() == KeyEvent.VK_T){
			inertia = !inertia;
			
			System.out.print("Inertia thrusters : ");
			if(inertia)
				System.out.println("activated");
			else
				System.out.println("deactivated");
		}
		else if(e.getKeyCode() == KeyEvent.VK_S){
			System.out.println("Scan report: " + space.scan(pos, 1000));
		}
				
	}
}
