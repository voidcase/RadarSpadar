package gui;

import game.PlayerShip;
import game.Ship;
import game.Space;
import game.Vector2D;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import datastructures.KeyboardStateListener;

public class RadarWindow extends JFrame implements Observer {
	/** difference of time between frames, in milliseconds*/
	protected static double timeDelta;
	public static double getFPS() {
		if (timeDelta != 0)
			return 1/(timeDelta*0.001);
		else
			return -1;
	}
	
	private static final long serialVersionUID = -8212981428372798858L;
	private KeyboardStateListener keyboardStateListener;
	/** timestamp from when the last frame started rendering, in nanoseconds*/
	private long lastFrameTime;
	private RenderPanel renderPanel;
	private Space space;
	private Ship p1;
	
	public RadarWindow(Space space) {
		super("Radar Window");
		keyboardStateListener = new KeyboardStateListener();
		addKeyListener(keyboardStateListener);

		this.space = space;
		generateShips();
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
				lastFrameTime = System.nanoTime();
				while(!isInterrupted()) {
					updateTimeStamps();
					update();
					renderPanel.repaint();
				}
			}
		};
		loopUpdate.start();
	}

	/** Insert game logic here */
	protected void update() {
		Vector2D arrowDir = generateArrowDirection().scale(timeDelta *0.1);
		p1.setPos(p1.getPos().add(arrowDir));
	}
	
	private void updateTimeStamps() {
		long currentFrameTime = System.nanoTime();
		timeDelta = (currentFrameTime - lastFrameTime) * 0.000001;
		lastFrameTime = currentFrameTime;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		updateTimeStamps();
		update();
	}
	
	private void generateShips() {
		p1 = new PlayerShip();
//		Ship p2 = new PlayerShip();
//		Ship p3 = new PlayerShip();
		p1.setPos(new Vector2D(50, 40));
//		p2.setPos(new Vector2D(20, 250));
//		p3.setPos(new Vector2D(100, 100));
		p1.setAngle(0);
//		p2.setAngle(0);
//		p3.setAngle(0);
		space.spawnShip(p1);
//		space.spawnShip(p2);
//		space.spawnShip(p3);
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
		return new Vector2D(hori, vert);
	}
}
