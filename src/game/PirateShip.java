package game;

public class PirateShip extends Ship {

	Ship target;
	double r;

	public PirateShip(Space space) {
		super(space, ". PIRATE", 100);
		r = 300;
		
	}

	@Override
	public void act() {
		if(target == null){
			target = newTarget();
		}
		if(target != null){
			double dist = pos.distance(target.pos);
			
			double scalar = dist<r?-1:1;
			vel = vel.add(pos.distanceVectorTo(target.pos).normalize().scale(scalar*0.005));
		}
	}
	
	private Ship newTarget(){
		for(Ship s : space.scan(pos,900)){
			if(!equals(s) && s.maxHealth!=Ship.INFINITE_HEALTH)
				return s;
		}
		return null;
	}

}
