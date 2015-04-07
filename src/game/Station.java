package game;

public class Station extends Ship {
	
	public Station(){
		name = 	"* STATION";
	}

	@Override
	public void act() {
		
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Station) {
			return super.equals(o);
		}
		return false;
	}
}
