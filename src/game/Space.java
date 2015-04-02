package game;

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

	public ArrayList<Ship> scan(Vector2D origin, int range){
		ArrayList<Ship> labels = new ArrayList<Ship>();
		for(Ship s : content){
			if(origin.distance(s.getPos())<=range){
				labels.add(s);
			}
		}
		return labels;
	}
}