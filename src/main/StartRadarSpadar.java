package main;

import game.Space;
import gui.RadarWindow;

public class StartRadarSpadar {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Space space = new Space();
		RadarWindow rWindow = new RadarWindow(space);
	}
}
