package gui.hud;

import javafx.scene.Node;

public interface HudIndicator {
	
	public abstract Node getNode();
	public abstract void draw();
}
