package gui.hud;

import game.MatchManager;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;

public abstract class AbstractHudElement implements HudIndicator{

	protected Group root;
	protected MatchManager matchManager;
	protected Color themeColor;
	
	public AbstractHudElement(MatchManager matchManager){
		this.root = new Group();
		this.matchManager =matchManager;
	}
	
	@Override
	public Node getNode() {
		return root;
	}
	
	@Override
	public Color getThemeColor() {
		return themeColor;
	}
	@Override
	public void setThemeColor(Color themeColor) {
		this.themeColor = themeColor;
	}
}
