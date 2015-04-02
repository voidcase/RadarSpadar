package gui;

import java.awt.Graphics2D;
import java.awt.Graphics;
import javax.swing.JPanel;
import game.Ship;
import game.Space;

public class RenderPanel extends JPanel {
	private Space space;
	
	public RenderPanel(Space space) {
		super();
		this.space = space;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
	}	
}
