package game;

public class GravityWell extends Ship {
	private int mass;
	
	public GravityWell(int m, Space s){
		super(s, ". Gravity_well", Ship.INFINITE_HEALTH);
		mass = m;
	}
	
	@Override
	public void act() {
		for(Ship s: space.scan(pos,1000)){
			if(!s.equals(this)){
				s.vel = s.vel.add(s.pos.distanceVectorTo(pos).normalize().scale(mass/pos.distance(s.pos)));
			}
		}
	}
}
