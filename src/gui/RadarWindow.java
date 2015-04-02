package gui;

import game.Space;
import game.Vector2D;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class RadarWindow extends JFrame implements Observer, KeyListener {
	protected long timeDeltaMillis;

	private boolean arrowUp = false;
	private boolean arrowDown = false;
	private boolean arrowRight = false;
	private boolean arrowLeft = false;
	
	private JLabel placeholder;
	private long lastFrameTime;
	
	public RadarWindow(Space space) {
		super("Radar Window");
		placeholder = new JLabel("asd");
		placeholder.setBounds(100, 100, 25, 45);
		add(placeholder);
		
		setSize(500, 700);
		setLayout(null);	//ensures that absolute positioning is possible
		setVisible(true);
		initLoop();
	}
	
	
	private void initLoop() {
		Thread loopUpdate = new Thread() {
			@Override
			public void run() {
				lastFrameTime = System.currentTimeMillis();
				while(!isInterrupted()) {
					try {
						updateTimeStamps();
						update();
						sleep(1000 * (1/60));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		loopUpdate.start();
	}
	
	private void updateTimeStamps() {
		long currentFrameTime = System.currentTimeMillis();
		timeDeltaMillis = currentFrameTime - lastFrameTime;
		lastFrameTime = currentFrameTime;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		updateTimeStamps();
		update();
	}
	
	/** Insert game logic here */
	protected void update() {
//		System.out.println("RadarWindow.update()");
		Vector2D arrowDir = generateArrowDirection();
		
		// Section: example of some logic that moves 
		Rectangle bounds = placeholder.getBounds();
		int x = bounds.x + (int) arrowDir.getX();
		int y = bounds.y + (int) arrowDir.getY();
		placeholder.setBounds(x, y, bounds.width, bounds.height);
	}
	
	private Vector2D generateArrowDirection() {
		float vert = 0;
		float hori = 0;
		if(arrowUp)
			vert++;
		if(arrowDown)
			vert--;
		if(arrowRight)
			hori++;
		if(arrowLeft)
			hori--;
		if(hori != 0 && vert != 0)
			System.out.println("hori = " + hori + " vert = " + vert);
		return new Vector2D(vert, hori);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP)
			arrowUp = true;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			arrowDown = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			arrowRight = true;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			arrowLeft = true;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP)
			arrowUp = false;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			arrowDown = false;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			arrowRight = false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			arrowLeft = false;		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// this is irrelevant
	}
}
