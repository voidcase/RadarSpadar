package game;

import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Observable;

public class Space extends Observable {
	
	ArrayList<Ship> content;
	
	public Space(){
		content = new ArrayList<Ship>();
	}
	
	void spawnShip(Ship s){
		content.add(s);
	}
	public ArrayList<Polygon> scan(Point2D origin, int range){
		ArrayList<Polygon> polys = new ArrayList<Polygon>();
		for(Ship s : content){
			if(origin.distance(s.getPos())<=range){
				polys.add(s.getSymbol());
			}
		}
		return polys;
	}
}