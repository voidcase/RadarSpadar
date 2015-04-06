package game;

public class DrunkenShip extends Ship {

	public DrunkenShip(){
		name = "* DRUNK";
	}
	@Override
	public void act() {
		// TODO Auto-generated method stub
		vel = vel.add(new Vector2D((Math.random()*2)-1,(Math.random()*2)-1).normalize().scale(0.01));
	}
	
}
