package gui.hud;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public interface HudIndicator {
	
	public abstract Node getNode();
	public abstract Color getThemeColor();
	public abstract void setThemeColor(Color themeColor);
	public abstract void draw();
	public abstract void reset();
}
