package gui;

import game.Ship;
import game.Space;
import game.Vector2D;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.JPanel;

import datastructures.Grid;

public class RenderPanel extends JPanel {
	private Space space;
	private Grid grid;
	private int gridSpace = 50;
	private Color gridColor = Color.decode("#002600");
	private boolean debugging = true;
	private boolean antialias = true;
	private double secondsSinceFPSRender;
	private double fps;
	private Ship following;
	
	public RenderPanel(Space space, Ship sh) {
		super();
		this.space = space;
		following = sh;
		secondsSinceFPSRender = System.nanoTime() * Math.pow(10, -15);
		grid = new Grid(gridSpace, getSize().width, getSize().height);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		if(antialias){
			RenderingHints rh = new RenderingHints(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2.setRenderingHints(rh);
		}
		
		List<Ship> ships = space.getShipList();
//		g2.setColor(Color.black);
//		g2.fill(getBounds());
		drawBackground(g2);
		
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
		float centerX = (float) getSize().getWidth()/2;
		float centerY = (float) getSize().getHeight()/2;
		for(Ship ship : ships) {
			Vector2D relpos = ship.getPos().add(following.getPos().scale(-1));
			g2.drawString(ship.toString(), 
					centerX + (float)relpos.getX(), 
					centerY + (float)relpos.getY());
		}
	}
	
	private void drawBackground(Graphics2D g2) {
		g2.setColor(Color.black);
		g2.fill(getBounds());
		
		g2.setColor(gridColor);
		Vector2D shift = following.getVel().scale(-10);
		grid.translate((float)shift.getX(), (float)shift.getY());
		
		Vector2D[] horizontalStartVectors = grid.getHorizontalStartVectors();
		Vector2D[] horizontalEndVectors = grid.getHorizontalEndVectors();
		Vector2D[] verticalStartVectors = grid.getVerticalStartVectors();
		Vector2D[] verticalEndVectors = grid.getVerticalEndVectors();
		
		for (int i = 0; i < horizontalStartVectors.length; i++) {
			int x1 = (int) horizontalStartVectors[i].getX();
			int x2 = (int) horizontalEndVectors[i].getX();
			int y = (int) horizontalStartVectors[i].getY();
			g2.drawLine(x1, y, x2, y);
		}
		
		for (int i = 0; i < verticalStartVectors.length; i++) {
			int x = (int) verticalEndVectors[i].getX();
			int y1 = (int) verticalStartVectors[i].getY();
			int y2 = (int) verticalEndVectors[i].getY();
			g2.drawLine(x, y1, x, y2);
		}
//		System.out.println("horizontalStartVectors[0].getX() = " + horizontalStartVectors[0].getX());
	}
	
	public void redrawGrid() {
		grid = new Grid(gridSpace, getSize().width + 1000, getSize().height + 1000);
	}
}
