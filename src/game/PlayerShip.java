package game;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import datastructures.KeyboardStateListener;

public class PlayerShip extends Ship implements Observer{
	public static int NO_TARGET = -1;
	
	private KeyboardStateListener keyboard;
	private boolean inertia = false;
	private Space space;
	private List<Ship> hits;
	private int target;
	
	public PlayerShip(KeyboardStateListener ksl, Space s){
		name = "^";
		keyboard = ksl;
		ksl.addObserver(this);
		space = s;
		target = NO_TARGET;
	}

	@Override
	public void act() {
		Vector2D arrowDir = keyboard.generateArrowDirection().scale(0.01);
		vel = vel.add(arrowDir);
		if(inertia){
			vel = vel.add(vel.scale(-0.05));
		}
	}
	
	public Ship getTarget() {
		if(target != NO_TARGET)
			return hits.get(target);
		else return null;
	}
	
	public boolean isMoving() {
		return !vel.equals(Vector2D.ZERO);
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
			hits = space.scan(pos, 1000);
			if (hits != null && hits.size() != 0)
				target = 0;
			else target = NO_TARGET;
			System.out.println("Scan report: " + hits);
		}
		else if(e.getKeyCode() == KeyEvent.VK_R)
			target = (target + 1) % hits.size(); //like target++; but rotates over to 0 when target == hits.size()
		else if(e.getKeyCode() == KeyEvent.VK_E)
			target = (target - 1) % hits.size(); //like target--; but rotates over to 0 when target == hits.size()
		
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof PlayerShip) {
			PlayerShip ps = (PlayerShip) o;
			return inertia == ps.inertia &&
				   hits.equals(ps.hits) &&
				   target == ps.target &&
				   super.equals(o);
		}
		return false;
	}
}
