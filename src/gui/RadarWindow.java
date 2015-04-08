package gui;

import game.DrunkenShip;
import game.GravityWell;
import game.PlayerShip;
import game.Ship;
import game.Space;
import game.Station;
import game.Vector2D;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

import datastructures.KeyboardStateListener;

public class RadarWindow extends JFrame {
	/** difference of time between frames, in milliseconds*/
	protected static double timeDelta;
	private static final long serialVersionUID = -8212981428372798858L;
	private static final String BACKGROUND_SN = "BACKGROUND";	// BACKGROUND_SOUNDNAME
	private static final String ALARM_SN = "ALARM";
	
	private KeyboardStateListener keyboardStateListener;
	private RenderPanel renderPanel;
	private Space space;
	private PlayerShip p1;
	private Map<String, Clip> sounds;
	private ArrayList<UIField> uiTexts;
	
	public RadarWindow(Space space) {
		super("Radar Window");
		keyboardStateListener = new KeyboardStateListener();
		addKeyListener(keyboardStateListener);

		this.space = space;
		setSize(1100, 800);
		setLayout(null);	//ensures that absolute positioning is possible
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		uiTexts = new ArrayList<UIField>();
		generateShips();
		generateUIText();
		p1 = new PlayerShip(keyboardStateListener,space);
		p1.setPos(new Vector2D(0, 0));
		space.spawnShip(p1);

		renderPanel = new RenderPanel(uiTexts,space,p1);
		renderPanel.setLocation(0,0);
		add(renderPanel);
		
		addComponentListener(new AComponentListener());
		setVisible(true);
		initLoop();
	}
	
	private void initLoop() {
		loadSounds();
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
		manageSounds();
	}

	private void loadSounds() {
		// TODO implement
		sounds = new HashMap<String, Clip>();
		try {
			Clip background = AudioSystem.getClip();
			Clip alarm = AudioSystem.getClip();
			background.open(AudioSystem.getAudioInputStream(new File("res/background_1.wav")));
			alarm.open(AudioSystem.getAudioInputStream(new File("res/alarm.wav")));
			
			sounds.put(BACKGROUND_SN, background);
			sounds.put(ALARM_SN, alarm);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
	
	private void manageSounds() {
		Clip background = sounds.get(BACKGROUND_SN);
		Clip alarm = sounds.get(ALARM_SN);
		if(background != null) {
			background.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	public static double getTimeDelta(){
		return timeDelta;
	}
	
	private void generateShips() {
		
		Ship p2 = new DrunkenShip();
		Ship p3 = new DrunkenShip();
		Ship st = new Station();
		Ship gw = new GravityWell(1,space);
		p2.setPos(new Vector2D(20, 250));
		p3.setPos(new Vector2D(100, 100));
		st.setPos(new Vector2D(300,100));
		gw.setPos(new Vector2D(-300,-300));
		p2.setAngle(0);
		p3.setAngle(0);
		space.spawnShip(p2);
		space.spawnShip(p3);
		space.spawnShip(st);
		space.spawnShip(gw);
	}
	
	private void generateUIText(){
		uiTexts.add(new UIField(10,90,"Rättvisemärkt snuff!"));
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
			renderPanel.redrawGrid();
		}

		@Override
		public void componentShown(ComponentEvent e) {
			// do nothing
		}
	}
}
