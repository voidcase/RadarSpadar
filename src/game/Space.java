package game;

import java.util.ArrayList;

public class Space {
	
	ArrayList<Ship> content;
	
	public Space(){
		content = new ArrayList<Ship>();
	}
	
	void spawnShip(Ship s){
		content.add(s);
	}
	
}