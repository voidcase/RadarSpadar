package game;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import datastructures.KeyboardStateListener;

public class PlayerShip extends Ship implements Observer{
	public static int NO_TARGET = -1;
	public static int RANGE_CLOSE = 200;
	public static int RANGE_MEDIUM = 500;
	public static int RANGE_FAR = 900;
	
	private KeyboardStateListener keyboard;
	private boolean inertia = false;
	private Space space;
	private List<Ship> hits;
	private int target;
	private boolean attacking = false;
	
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
		return vel.getX() == 0 && vel.getY() == 0;
	}

	public boolean isAttacking() {
		return attacking;
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
		else if(e.getKeyCode() == KeyEvent.VK_R){
			if(hits == null || hits.size() == 0){
				hits = space.scan(pos, 1000);
				target = 0 % hits.size();
			}
			target = Math.abs(target + 1) % hits.size();
			System.out.println("targetnr = " + target);
		}
		else if(e.getKeyCode() == KeyEvent.VK_E){
			if(hits == null || hits.size() == 0){
				hits = space.scan(pos, 1000);
				target = 1 % hits.size();
			}
			target = Math.abs(target - 1) % hits.size();
			System.out.println("dargetnr = " + target);
		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			Ship target = getTarget();
			if (target != null) {
				System.out.println("Fire laser at " + target.name);
				new LaserAttackTask(this, System.nanoTime() + 1 * (long)Math.pow(10, 9)).start();
			}
		}
	}
	
	private class LaserAttackTask extends Thread {
		private long executionTime;
		private PlayerShip parent;
		
		/**
		 * 
		 * @param executionTime the time when the task should be executed in nanoseconds
		 */
		public LaserAttackTask(PlayerShip parent, long executionTime) {
			this.executionTime = executionTime;
			this.parent = parent;
		}
		
		@Override
		public void run() {
			parent.attacking = true;
			while (System.nanoTime() - executionTime <= 0);
			//inflict damage on parent.getTarget()
			parent.attacking = false;
			System.out.println("Laser fired");
		}
	}

}
