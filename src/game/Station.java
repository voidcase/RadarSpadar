package game;

public class Station extends Ship {
	
	public Station(Space s){
		super(s, "* STATION", Ship.INFINITE_HEALTH);
	}

	@Override
	public void act() {
		
	}
}
