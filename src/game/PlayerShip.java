package game;

import globals.Globals;

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
	private List<Ship> hits;
	private int target;
	private boolean attacking = false;
	
	public PlayerShip(KeyboardStateListener ksl, Space s){
		super(s, ". player", 200);
		keyboard = ksl;
		ksl.addObserver(this);
		target = NO_TARGET;
		damageAmount = 20;
	}

	@Override
	public void act() {
		Vector2D arrowDir = keyboard.generateArrowDirection().scale(0.01);
		vel = vel.add(arrowDir);
		if(inertia){
			vel = vel.add(vel.scale(-0.05));
		}
		if(attacking){
			getTarget().damage(1);
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
	
	public String status(){
		StringBuilder sb = new StringBuilder();
		sb.append("HULL: ");
		sb.append(getHealthPercentage() + " | ");
		sb.append("INERTIA: ");
		sb.append(inertia?"ON  | ":"OFF | ");
		sb.append("TARGET-DISTANCE: ");
			if(getTarget() == null) sb.append("-");
			else sb.append(pos.distance(getTarget().pos));
		
		return sb.toString();
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
		else if(e.getKeyCode() == Globals.KeyCodes.SCAN){
			hits = space.scan(pos, 1000);
			if (hits != null && hits.size() != 0)
				target = 0;
			else target = NO_TARGET;
			System.out.println("Scan report: " + hits);
		}
		else if(e.getKeyCode() == Globals.KeyCodes.NEXT_TARGET){
			if(hits == null || hits.size() == 0){
				hits = space.scan(pos, 1000);
				target = 0 % hits.size();
			}
			target = Math.abs(target + 1) % hits.size();
			System.out.println("targetnr = " + target);
		}
		else if(e.getKeyCode() == Globals.KeyCodes.PREV_TARGET){
			if(hits == null || hits.size() == 0){
				hits = space.scan(pos, 1000);
				target = 1 % hits.size();
			}
			target = Math.abs(target - 1) % hits.size();
			System.out.println("dargetnr = " + target);
		}
		else if(e.getKeyCode() == Globals.KeyCodes.FIRE_LASER) {
			Ship target = getTarget();
			if (target != null) {
				new LaserAttackTask(this, target, System.nanoTime() + 1 * (long)Math.pow(10, 9)).start();
			}
		}
	}
	
	private class LaserAttackTask extends Thread {
		private long executionTime;
		private PlayerShip parent;
		private Ship target;
		
		/**
		 * 
		 * @param executionTime the time when the task should be executed in nanoseconds
		 */
		public LaserAttackTask(PlayerShip parent, Ship target, long executionTime) {
			this.executionTime = executionTime;
			this.parent = parent;
			this.target = target;
		}
		
		@Override
		public void run() {
			parent.attacking = true;
			while (System.nanoTime() - executionTime <= 0 && !target.isKill());
			//target.damage(damageAmount);
			parent.attacking = false;
		}
	}

}
