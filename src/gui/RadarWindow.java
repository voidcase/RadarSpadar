package gui;

import game.PlayerShip;
import game.Ship;
import game.Space;
import game.Vector2D;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;

import datastructures.KeyboardStateListener;

public class RadarWindow extends JFrame implements Observer {
	protected long timeDelta;
	
	private static final long serialVersionUID = -8212981428372798858L;
	private KeyboardStateListener keyboardStateListener;
	private long lastFrameTime;
	private RenderPanel renderPanel;
	private Space space;
	private Ship p1;
	
	private Vector2D phPos;
	
	public RadarWindow(Space space) {
		super("Radar Window");
		keyboardStateListener = new KeyboardStateListener();
		addKeyListener(keyboardStateListener);

		space = new Space();
		generateShips(space);
		renderPanel = new RenderPanel(space);
		renderPanel.setBounds(0, 0, 500, 700);
		add(renderPanel);
		
		setSize(500, 700);
		setLayout(null);	//ensures that absolute positioning is possible
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		initLoop();
		space.addObserver(this);	//fast varför observar vi egentligen space?
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
						renderPanel.repaint();
						sleep(1000 * (1/60));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		loopUpdate.start();
	}

	/** Insert game logic here */
	protected void update() {
		Vector2D arrowDir = generateArrowDirection();
		arrowDir = arrowDir.scale(timeDelta * 0.1);
		
		// Section: example of some logic that moves a label around
		phPos = phPos.add(arrowDir);
	}
	
	private void updateTimeStamps() {
		long currentFrameTime = System.currentTimeMillis();
		timeDelta = currentFrameTime - lastFrameTime;
		lastFrameTime = currentFrameTime;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		updateTimeStamps();
		update();
	}
	
	private void generateShips(Space space) {
		p1 = new PlayerShip();
		Ship p2 = new PlayerShip();
		Ship p3 = new PlayerShip();
		p1.setPos(new Vector2D(50, 40));
		p2.setPos(new Vector2D(20, 250));
		p3.setPos(new Vector2D(100, 100));
		p1.setAngle(0);
		p2.setAngle(0);
		p3.setAngle(0);
		space.spawnShip(p1);
		space.spawnShip(p2);
		space.spawnShip(p3);
	}
	
	private Vector2D generateArrowDirection() {
		float vert = 0;
		float hori = 0;
		if(keyboardStateListener.getArrowUp())
			vert--;
		if(keyboardStateListener.getArrowDown())
			vert++;
		if(keyboardStateListener.getArrowRight())
			hori++;
		if(keyboardStateListener.getArrowLeft())
			hori--;
//		if(hori != 0 && vert != 0)
//			System.out.println("hori = " + hori + " vert = " + vert);
		return new Vector2D(hori, vert);
	}
	
	private void rotateP1() {
		double angle = p1.getAngle();
		double rotationSpeed = 0.0002 * timeDelta;
		if (keyboardStateListener.getEPressed())
			angle += rotationSpeed;
		if (keyboardStateListener.getQPressed())
			angle -= rotationSpeed;
		p1.setAngle(angle);
//		System.out.println("ePressed = " + arrowKeyListener.getEPressed() + "\nqPressed = " + arrowKeyListener.getQPressed());
	}
}
