package gui;

import game.PlayerShip;
import game.Ship;
import game.Space;
import game.Vector2D;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import datastructures.Grid;

public class RenderPanel extends JPanel {
	private static final long serialVersionUID = -3104652749857411284L;
	private Space space;
	private Grid grid;
	private int gridSpace = 50;
	private Color gridColor = Color.decode("#002600");
	private Color selectedColor = Color.decode("#00ffff");
	private boolean debugging = true;
	private boolean antialias = true;
	private double secondsSinceFPSRender;
	private double fps;
	private PlayerShip following;
	private ArrayList<UIField> ui;
	
	public RenderPanel(ArrayList<UIField> ui, Space space, PlayerShip sh) {
		super();
		this.space = space;
		this.ui = ui;
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
		
		drawBackground(g2);
		if(debugging)
			drawFPS(g2);
		drawLasers(g2);
		drawShips(g2);
		drawUI(g2);
	}
	
	private void drawUI(Graphics2D g2){
		Color oldColor = g2.getColor();
		g2.setColor(Color.cyan);
		g2.setFont(new Font("Consolas", Font.PLAIN, 12));
		for(UIField f : ui){
			g2.drawString(f.toString(),f.getX(this),f.getY(this));
		}
		g2.setColor(oldColor);
	}
	
	private void drawShips(Graphics2D g2) {
		Color oldColor = g2.getColor();
		g2.setColor(Color.green);
		g2.setFont(new Font("Consolas", Font.PLAIN, 12));
		Vector2D center = getScrCenter();
		float centerX = (float) center.getX();
		float centerY = (float) center.getY();
		List<Ship> ships = space.getShipList();
		for(Ship ship : ships) {
			Vector2D relpos = following.getPos().distanceVectorTo(ship.getPos());
			Ship target = following.getTarget();
			if(ship.equals(target)) {
				g2.setColor(selectedColor);
				g2.drawString(ship.toString() + " " + ship.getHealthPercentage(), 
						centerX + (float)relpos.getX(), 
						centerY + (float)relpos.getY());
				g2.setColor(Color.green);
			} else {
				g2.drawString(ship.toString() + " " + ship.getHealthPercentage(), 
						centerX + (float)relpos.getX(), 
						centerY + (float)relpos.getY());
			}
		}
		g2.setColor(oldColor);
	}
	
	private Vector2D getScrCenter() {
		double centerX = getSize().getWidth()/2;
		double centerY = getSize().getHeight()/2;
		return new Vector2D(centerX, centerY);
	}

	private void drawLasers(Graphics2D g2) {
		Color oldColor = g2.getColor();
		Ship target = following.getTarget();
		if (target != null && following.isAttacking()) {
			g2.setColor(getPulsingRed());
			Vector2D center = getScrCenter();
			Vector2D targetRelPos = following.getPos().distanceVectorTo(target.getPos());
			int x1 = (int) center.getX();
			int y1 = (int) center.getY();
			int x2 = (int) (targetRelPos.getX() + center.getX());
			int y2 = (int) (targetRelPos.getY() + center.getY());
			g2.drawLine(x1, y1, x2, y2);
		}
		g2.setColor(oldColor);
	}

	private void drawFPS(Graphics2D g2) {
		secondsSinceFPSRender += System.nanoTime() * Math.pow(10, -15);
		if(secondsSinceFPSRender > 0.5) {
			fps = 1000/RadarWindow.getTimeDelta();
			secondsSinceFPSRender = 0;
		}
		g2.setColor(Color.magenta);
		g2.setFont(new Font("Consolas", Font.PLAIN, 12));
		g2.drawString(fps + "FPS", 0, 12);
	}
	
	private void drawBackground(Graphics2D g2) {
		Color oldColor = g2.getColor();
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
		g2.setColor(oldColor);
	}
	
	public void recalculateGrid() {
		grid = new Grid(gridSpace, getSize().width + 1000, getSize().height + 1000);
	}
	
	private Color getPulsingRed() {
		long time = (long) (System.nanoTime() * Math.pow(10, -8));
		int minLevel = 128;
		int amp = 255 - minLevel;
		double zeroFlooredSine = (Math.sin(1.25 * time) + 1)/2;
		int level = (int) (amp * zeroFlooredSine + minLevel);
		Color red = new Color(level, 0, 0);
		return red;
	}
}
