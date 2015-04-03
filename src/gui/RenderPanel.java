package gui;

import game.Ship;
import game.Space;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

public class RenderPanel extends JPanel {
	private Space space;
	private boolean debugging = true;
	private double secondsSinceFPSRender;
	private double fps;
	private Ship following;
	
	public RenderPanel(Space space, Ship sh) {
		super();
		this.space = space;
		following = sh;
		secondsSinceFPSRender = System.nanoTime() * Math.pow(10, -15);;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		List<Ship> ships = space.getShipList();
		g2.setBackground(Color.black);
		g2.fill(getBounds());
		
		if(debugging) {
			secondsSinceFPSRender += System.nanoTime() * Math.pow(10, -15);
			if(secondsSinceFPSRender > 0.5) {
				fps = 1000/RadarWindow.getTimeDelta();
				secondsSinceFPSRender = 0;
			}
			g2.setColor(Color.magenta);
			g2.setFont(new Font("Consolas", Font.PLAIN, 12));
			g2.drawString(fps + "FPS", 0, 12);
		}
		
		g2.setColor(Color.green);
		g2.setFont(new Font("Consolas", Font.PLAIN, 12));
		for(Ship ship : ships) {
			g2.drawString(ship.toString(), (float)ship.getPos().getX(), (float)ship.getPos().getY());
		}
	}
	
	private Dimension getScreenCenter() {
		int centerX = (int) getPreferredSize().getWidth()/2;
		int centerY = (int) getPreferredSize().getHeight()/2;
		return new Dimension(centerX, centerY);
	}
}
