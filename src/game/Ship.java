package game;

import gui.RadarWindow;

public abstract class Ship {
	public static final double INFINITE_HEALTH = Double.MAX_VALUE;
	protected String name = "o";
	protected Vector2D pos = new Vector2D(0,0);
	protected Vector2D vel = new Vector2D(0,0);
	protected double maxHealth;
	protected double health;
	protected double damageAmount = 0;
	protected Space space;
	
	public Ship(Space space, String name, double health) {
		this.space = space;
		this.name = name;
		maxHealth = health;
		this.health = health;
	}
	
	public abstract void act();
	
	public void update(){
		act();
		pos = pos.add(vel.scale(RadarWindow.getTimeDelta()));
	}
	
	public void setPos(Vector2D pos){
		this.pos = pos;
	}

	public Vector2D getPos(){
		return pos;
	}

	public Vector2D getVel() {
		return vel;
	}

	public String toString(){
		return name;
	}
	
	/** Returns a string with the percentage of remaining health, with no decimals */
	public String getHealthPercentage() {
		if (health != INFINITE_HEALTH)
			return (int)100*(health/maxHealth) + "%";
		else return "";
	}
	
	/**
	 * Applies damage to this ship, as long as it doesn't have infinite health
	 * 
	 * @param dmgAmount the amount of damage this ship will recieve
	 * */
	public void damage(double dmgAmount) {
		if (health != INFINITE_HEALTH) {
			health -= dmgAmount;
//			System.out.println(name + "'s health = " + health);
			if (health <= 0) {
				space.kill(this);
			}
		}
	}
}