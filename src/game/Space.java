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
	
}