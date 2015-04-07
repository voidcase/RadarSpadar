package game;

public class GravityWell extends Ship {
	private int mass;
	private Space environment;
	
	public GravityWell(int m, Space e){
		mass = m;
		environment = e;
		name = "* Gravity_well";
	}
	
	@Override
	public void act() {
		for(Ship s: environment.scan(pos,1000)){
			if(!s.equals(this)){
				s.vel = s.vel.add(s.pos.distanceVectorTo(pos).normalize().scale(mass/pos.distance(s.pos)));
			}
		}
	}
	
	@Override
	public boolean equals(Object o ) {
		if(o instanceof GravityWell) {
			GravityWell gw = (GravityWell) o;
			return mass == gw.mass &&
				   super.equals(o);
		}
		return false;
	}
}
