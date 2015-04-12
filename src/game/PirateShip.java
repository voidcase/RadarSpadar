package game;

public class PirateShip extends Ship {

	Ship target;

	public PirateShip(Space space) {
		super(space, ". PIRATE", 100);
		
	}

	@Override
	public void act() {
		if(target == null){
			target = newTarget();
		}
		if(target != null){
			//vel = vel.add(pos.distanceVectorTo(target.pos).normalize().scale(0));
		}
	}
	
	private Ship newTarget(){
		for(Ship s : space.scan(pos,900)){
			if(!s.equals(this)||s.maxHealth!=Ship.INFINITE_HEALTH)
				return s;
		}
		return null;
	}

}
