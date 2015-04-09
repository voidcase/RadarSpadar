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
	private static final String TARGET_CLOSE_SN = "TARGET_CLOSE";
	private static final String TARGET_MEDIUM_SN = "TARGET_MEDIUM";
	private static final String TARGET_FAR_SN = "TARGET_FAR";
	
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
		loadSounds();
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
		manageSounds();
	}

	private void loadSounds() {
		// TODO implement
		sounds = new HashMap<String, Clip>();
		try {
			Clip background = AudioSystem.getClip();
			Clip alarm = AudioSystem.getClip();
			Clip targetClose = AudioSystem.getClip();
			Clip targetMedium = AudioSystem.getClip();
			Clip targetFar = AudioSystem.getClip();

			background.open(AudioSystem.getAudioInputStream(new File("res/background_1.wav")));
			alarm.open(AudioSystem.getAudioInputStream(new File("res/alarm.wav")));
			targetClose.open(AudioSystem.getAudioInputStream(new File("res/target_close.wav")));
			targetMedium.open(AudioSystem.getAudioInputStream(new File("res/target_medium.wav")));
			targetFar.open(AudioSystem.getAudioInputStream(new File("res/target_far.wav")));
			
			sounds.put(BACKGROUND_SN, background);
			sounds.put(ALARM_SN, alarm);
			sounds.put(TARGET_CLOSE_SN, targetClose);
			sounds.put(TARGET_MEDIUM_SN, targetMedium);
			sounds.put(TARGET_FAR_SN, targetFar);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
	
	private void manageSounds() {
		Clip background = sounds.get(BACKGROUND_SN);
		if(background != null) {
			background.loop(Clip.LOOP_CONTINUOUSLY);
		}
		
		Clip targetClose = sounds.get(TARGET_CLOSE_SN);
		Clip targetMedium = sounds.get(TARGET_MEDIUM_SN);
		Clip targetFar = sounds.get(TARGET_FAR_SN);
		Ship target = p1.getTarget();
		if ((target != null) && (targetClose != null) && (targetMedium != null) && (targetFar != null)) {
			double targetDistance = p1.getPos().distance(target.getPos());
			if (targetDistance < PlayerShip.RANGE_CLOSE) {
				targetMedium.stop();
				targetFar.stop();
				if(!targetClose.isRunning()) {
					targetClose.setMicrosecondPosition(0);
					targetClose.loop(Clip.LOOP_CONTINUOUSLY);
				}
			} else if (targetDistance < PlayerShip.RANGE_MEDIUM) {
				targetClose.stop();
				targetFar.stop();
				if(!targetMedium.isRunning()) {
					targetMedium.setMicrosecondPosition(0);
					targetMedium.loop(Clip.LOOP_CONTINUOUSLY);
				}
			} else if (targetDistance < PlayerShip.RANGE_FAR) {
				targetClose.stop();
				targetMedium.stop();
				if(!targetFar.isRunning()) {
					targetFar.setMicrosecondPosition(0);
					targetFar.loop(Clip.LOOP_CONTINUOUSLY);
				}
			} else {
				targetClose.stop();
				targetMedium.stop();
				targetFar.stop();
			}
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
