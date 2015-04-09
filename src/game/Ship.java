package game;

import gui.RadarWindow;

public abstract class Ship {
	protected String name = "o";
	protected Vector2D pos = new Vector2D(0,0);
	protected Vector2D vel = new Vector2D(0,0);
	
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
}