package gui;

import game.Ship;
import game.Space;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

public class RenderPanel extends JPanel {
	private Space space;
	
	public RenderPanel(Space space) {
		super();
		this.space = space;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		List<Ship> ships = space.getShipList();
		g2.setBackground(Color.black);
		
		g2.setColor(Color.green);
		g2.setFont(new Font("Consolas", Font.PLAIN, 20));
		for(Ship ship : ships) {
			g2.rotate(ship.getAngle());
			g2.drawString(ship.toString(), (float)ship.getPos().getX(), (float)ship.getPos().getY());
			g2.rotate(-ship.getAngle());
		}
	}	
}
