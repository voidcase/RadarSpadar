package game;

import java.awt.Polygon;

public abstract class Ship {
	
	protected Vector2D pos;
	//protected Vector2D dir;
	//protected Vector2D vel;
	
	public void setDir(float angle){
		
	}
	public void burn(int amount){
		//vel.add(dir);
	}
	public Vector2D getPos(){
		return pos;
	}
	public String toString(){
		return "[ship]";
	}
}
