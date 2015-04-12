package gui;

import game.PlayerShip;

public class StatusBar extends UIField{
	private PlayerShip subject;
	
	public StatusBar(PlayerShip s) {
		super("---");
		subject = s;
	}
	
	public String toString(){
		
		return subject.status();
	}


	@Override
	public int getX(RenderPanel parent){
		return 50;
	}

	@Override
	public int getY(RenderPanel parent){
		return parent.getHeight() - 80;
	}
}
