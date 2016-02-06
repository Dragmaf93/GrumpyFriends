package gui.animation;

import org.apache.commons.math3.stat.regression.MillerUpdatingRegression;

import javafx.scene.image.Image;

public class SpriteAnimation {

	private Image[] frames;
	private int currentIndex;
	private int numberFrames;

	private long deltaMillis;

	private long lastMillis;

	public SpriteAnimation(Image[] frames, long deltaMillis) {
		this.frames = frames;
		this.currentIndex = 0;
		this.deltaMillis = deltaMillis;
		this.numberFrames = frames.length;
	}

	public void restartAnimation() {
		currentIndex = 0;
	}

	public boolean isFinished() {
		return currentIndex >= numberFrames;
	}

	public Image nextFrame() {
		Image frame = frames[currentIndex % numberFrames];

		if (System.currentTimeMillis() >= lastMillis + deltaMillis) {
			lastMillis = System.currentTimeMillis();
			currentIndex += 1;
		}
		return frame;
	}
}
