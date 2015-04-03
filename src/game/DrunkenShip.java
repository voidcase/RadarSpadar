package game;

public class DrunkenShip extends Ship {

	public DrunkenShip(){
		name = "[drunk]";
	}
	@Override
	public void move() {
		// TODO Auto-generated method stub
		pos = pos.add(new Vector2D((Math.random()*2)-1,(Math.random()*2)-1).normalize());
	}
	
}
