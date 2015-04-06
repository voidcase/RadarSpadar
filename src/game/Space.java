package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Space extends Observable {
	List<Ship> ships;
	
	public Space(){
		ships = new ArrayList<Ship>();
	}
	
	public void spawnShip(Ship s){
		ships.add(s);
	}

	public ArrayList<Ship> scan(Vector2D origin, int range){
		ArrayList<Ship> labels = new ArrayList<Ship>();
		for(Ship s : ships){
			if(origin.distance(s.getPos())<=range){
				labels.add(s);
			}
		}
		return labels;
	}
	
	public List<Ship> getShipList() {
		return ships;
	}
	
	public void moveAll(){
		for(Ship s : ships){
			s.update();
		}
	}
}