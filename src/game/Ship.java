package game;

import java.awt.Polygon;

public abstract class Ship {
	
	protected String name;
	protected Vector2D pos;
	protected double angle;
	//protected Vector2D vel;
	
	public void setPos(Vector2D pos){
		this.pos = pos;
	}

	public void setAngle(double angle){
		this.angle = angle;
	}

	public Vector2D getPos(){
		return pos;
	}

	public double getAngle(){
		return angle;
	}
	
	public void burn(int amount){
		//vel.add(dir);
	}

	public String toString(){
		return "[ship]";
	}
}
