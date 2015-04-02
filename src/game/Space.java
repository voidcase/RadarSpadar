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

	public ArrayList<String> scan(Vector2D origin, int range){
		ArrayList<String> labels = new ArrayList<String>();
		for(Ship s : content){
			if(origin.distance(s.getPos())<=range){
				labels.add(s.toString());
			}
		}
		return labels;
	}
}