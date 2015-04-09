package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Space{
	private List<Ship> ships;
	
	public Space(){
		ships = new ArrayList<Ship>();
	}
	
	public void spawnShip(Ship s){
		ships.add(s);
	}

	public ArrayList<Ship> scan(Vector2D origin, int range){
		
		ArrayList<Ship> hits = new ArrayList<Ship>();
		for(Ship s : ships){
			if(origin.distance(s.getPos())<=range){
				hits.add(s);
				
			}
		}
		Collections.sort(hits,new ProximityComparator(origin));
		return hits;
			
		}
	
	
	public List<Ship> getShipList() {
		return ships;
	}
	
	public void moveAll(){
		for(Ship s : ships){
			s.update();
		}
	}
	
	private class ProximityComparator implements Comparator<Ship>{
		Vector2D ori;
		public ProximityComparator(Vector2D origin){
			ori = origin;
		}
		@Override
		public int compare(Ship arg0, Ship arg1) {
			// TODO Auto-generated method stub
			return (int) (ori.distance(arg0.pos)-ori.distance(arg1.pos));
		}
		
	}
}