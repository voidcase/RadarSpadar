package gui;

import java.awt.Graphics2D;
import java.awt.Graphics;
import javax.swing.JPanel;
import game.Ship;

public class RenderPanel extends JPanel {
	
	public RenderPanel() {
		super();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
	}
	
}
