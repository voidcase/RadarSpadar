package game;

import java.awt.Polygon;
import java.awt.geom.Point2D;

public abstract class Ship {
	
	protected Point2D pos;
	//protected Vector2D dir;
	//protected Vector2D vel;
	
	public void setDir(float angle){
		
	}
	public void burn(int amount){
		//vel.add(dir);
	}
	public Point2D getPos(){
		return pos;
	}
	public Polygon getSymbol(){
		Polygon p = new Polygon();
		//default romb
		p.addPoint( 1,  0);
		p.addPoint(-1,  0);
		p.addPoint( 0,  1);
		p.addPoint( 0, -1);
		return p;
	}
}
