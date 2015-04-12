package gui;

import game.PlayerShip;

public class StatusBar extends UIField{
	
	private PlayerShip subject;
	
	
	public StatusBar(PlayerShip s) {
		super(10,90,"---");
		subject = s;
	}
	
	public String toString(){
		
		return subject.status();
	}



}
