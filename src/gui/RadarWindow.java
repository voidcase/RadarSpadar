package gui;

import game.DrunkenShip;
import game.PlayerShip;
import game.Ship;
import game.Space;
import game.Station;
import game.Vector2D;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

import datastructures.KeyboardStateListener;

public class RadarWindow extends JFrame {
	/** difference of time between frames, in milliseconds*/
	protected static double timeDelta;
	private static final long serialVersionUID = -8212981428372798858L;
	private KeyboardStateListener keyboardStateListener;
	private RenderPanel renderPanel;
	private Space space;
	
	public RadarWindow(Space space) {
		super("Radar Window");
		keyboardStateListener = new KeyboardStateListener();
		addKeyListener(keyboardStateListener);

		this.space = space;
		generateShips();
		Ship p1 = new PlayerShip(keyboardStateListener);
		p1.setPos(new Vector2D(50, 40));
		space.spawnShip(p1);

		renderPanel = new RenderPanel(space,p1);
		renderPanel.setBounds(0, 0, 500, 700);
		add(renderPanel);
		
		addComponentListener(new AComponentListener());
		setSize(500, 700);
		setLayout(null);	//ensures that absolute positioning is possible
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		initLoop();
	}
	
	private void initLoop() {
		Thread loopUpdate = new Thread() {
			@Override
			public void run() {
				long currentFrameTime, lastFrameTime;
				lastFrameTime = System.nanoTime();
				while(!isInterrupted()) {
					currentFrameTime = System.nanoTime();
					timeDelta = (currentFrameTime - lastFrameTime) * 0.000001;
					lastFrameTime = currentFrameTime;
					update();
					renderPanel.repaint();
					try {
						sleep(1000 * 1/60);	//this maintains a max of 60fps... aaaand stops isaks laptop from lagging. dunno why :S
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
		space.moveAll();
	}
	
	public static double getTimeDelta(){
		return timeDelta;
	}
	
	private void generateShips() {
		
		Ship p2 = new DrunkenShip();
		Ship p3 = new DrunkenShip();
		Ship st = new Station();
		p2.setPos(new Vector2D(20, 250));
		p3.setPos(new Vector2D(100, 100));
		st.setPos(new Vector2D(300,100));
		p2.setAngle(0);
		p3.setAngle(0);
		space.spawnShip(p2);
		space.spawnShip(p3);
		space.spawnShip(st);
	}
	
	// TODO add a better fucking name...
	private class AComponentListener implements ComponentListener {

		@Override
		public void componentHidden(ComponentEvent e) {
			// do nothing
		}

		@Override
		public void componentMoved(ComponentEvent e) {
			// do nothing
		}

		@Override
		public void componentResized(ComponentEvent e) {
			renderPanel.setSize(getSize());
		}

		@Override
		public void componentShown(ComponentEvent e) {
			// do nothing
		}
	}
}
