package game;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {
	private static final String BACKGROUND_SN = "BACKGROUND";	// BACKGROUND_SOUNDNAME
	private static final String ALARM_SN = "ALARM";
	private static final String TARGET_CLOSE_SN = "TARGET_CLOSE";
	private static final String TARGET_MEDIUM_SN = "TARGET_MEDIUM";
	private static final String TARGET_FAR_SN = "TARGET_FAR";
	private Map<String, Clip> sounds;
	private PlayerShip player;
	
	public SoundManager(PlayerShip player) {
		this.player = player;
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
	
	public void manageSounds() {
		Clip background = sounds.get(BACKGROUND_SN);
		if(background != null) {
			background.loop(Clip.LOOP_CONTINUOUSLY);
		}
		
		Clip targetClose = sounds.get(TARGET_CLOSE_SN);
		Clip targetMedium = sounds.get(TARGET_MEDIUM_SN);
		Clip targetFar = sounds.get(TARGET_FAR_SN);
		Ship target = player.getTarget();
		if ((target != null) && (targetClose != null) && (targetMedium != null) && (targetFar != null)) {
			double targetDistance = player.getPos().distance(target.getPos());
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
}
