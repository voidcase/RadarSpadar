package game;

public class DrunkenShip extends Ship {

	public DrunkenShip(Space space){
		super(space, ". Drunk", 100);
	}
	
	@Override
	public void act() {
		vel = vel.add(new Vector2D((Math.random()*2)-1,(Math.random()*2)-1).normalize().scale(0.01));
	}
}
