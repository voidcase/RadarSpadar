package gui;

import game.DrunkenShip;
import game.GravityWell;
import game.PlayerShip;
import game.Ship;
import game.SoundManager;
import game.Space;
import game.Station;
import game.Vector2D;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import datastructures.KeyboardStateListener;

public class RadarWindow extends JFrame {
	/** difference of time between frames, in milliseconds*/
	protected static double timeDelta;
	private static final long serialVersionUID = -8212981428372798858L;
	
	private KeyboardStateListener keyboardStateListener;
	private RenderPanel renderPanel;
	private Space space;
	private PlayerShip p1;
	private ArrayList<UIField> uiTexts;
	private SoundManager soundManager;
	
	public RadarWindow(Space space) {
		super("Radar Window");
		keyboardStateListener = new KeyboardStateListener();
		addKeyListener(keyboardStateListener);

		this.space = space;
		uiTexts = new ArrayList<UIField>();
		generateShips();
		generateUIText();
		p1 = new PlayerShip(keyboardStateListener,space);
		p1.setPos(new Vector2D(0, 0));
		space.spawnShip(p1);

		renderPanel = new RenderPanel(uiTexts,space,p1);
		renderPanel.setLocation(0,0);
		add(renderPanel);
		
		addComponentListener(new ResizeListener());
		setSize(1100, 800);
		setLayout(null);	//ensures that absolute positioning is possible
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		soundManager = new SoundManager(p1);
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
		soundManager.manageSounds();
	}


	public static double getTimeDelta(){
		return timeDelta;
	}
	
	private void generateShips() {
		
		Ship p2 = new DrunkenShip(space);
		Ship p3 = new DrunkenShip(space);
		Ship st = new Station(space);
		Ship gw = new GravityWell(1,space);
		p2.setPos(new Vector2D(20, 250));
		p3.setPos(new Vector2D(100, 100));
		st.setPos(new Vector2D(300,100));
		gw.setPos(new Vector2D(-300,-300));
		space.spawnShip(p2);
		space.spawnShip(p3);
		space.spawnShip(st);
		space.spawnShip(gw);
	}
	
	private void generateUIText(){
		uiTexts.add(new UIField(20,20,"Hello!"));
	}
	
	// TODO add a better fucking name...
	private class ResizeListener implements ComponentListener {

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
			renderPanel.recalculateGrid();
		}

		@Override
		public void componentShown(ComponentEvent e) {
			// do nothing
		}
	}
}
