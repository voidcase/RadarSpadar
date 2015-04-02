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

import datastructures.ArrowKeyListener;

public class RadarWindow extends JFrame implements Observer {
	protected long timeDelta;
	
	private static final long serialVersionUID = -8212981428372798858L;
	private ArrowKeyListener arrowKeyListener;
	private long lastFrameTime;
	private JLabel placeholder;
	private Vector2D phPos;
	
	public RadarWindow(Space space) {
		super("Radar Window");
		placeholder = new JLabel("asd");
		placeholder.setBounds(100, 100, 25, 45);
		phPos = new Vector2D(placeholder.getBounds().getX(), placeholder.getBounds().getY());
		add(placeholder);
		
		arrowKeyListener = new ArrowKeyListener();
		addKeyListener(arrowKeyListener);
		
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
		timeDelta = currentFrameTime - lastFrameTime;
		lastFrameTime = currentFrameTime;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		updateTimeStamps();
		update();
	}
	
	/** Insert game logic here */
	protected void update() {
		Vector2D arrowDir = generateArrowDirection();
		arrowDir = arrowDir.scale(timeDelta * 0.1);
		
		// Section: example of some logic that moves a label around
		phPos = phPos.add(arrowDir);
		placeholder.setBounds((int) phPos.getX(), (int) phPos.getY(),
				placeholder.getBounds().width, placeholder.getBounds().height);
	}
	
	private Vector2D generateArrowDirection() {
		float vert = 0;
		float hori = 0;
		if(arrowKeyListener.getArrowUp())
			vert--;
		if(arrowKeyListener.getArrowDown())
			vert++;
		if(arrowKeyListener.getArrowRight())
			hori++;
		if(arrowKeyListener.getArrowLeft())
			hori--;
		if(hori != 0 && vert != 0)
			System.out.println("hori = " + hori + " vert = " + vert);
		return new Vector2D(hori, vert);
	}
}
