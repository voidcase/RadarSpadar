package gui;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

public class RadarWindow extends JFrame implements Observer {
	private long lastFrameTimeStamp;

	@Override
	public void update(Observable arg0, Object arg1) {
		long currentFrameTimeStamp = System.currentTimeMillis();
		long timeDeltaMillis = currentFrameTimeStamp - lastFrameTimeStamp;
		lastFrameTimeStamp = currentFrameTimeStamp;
		//code
	}
}
