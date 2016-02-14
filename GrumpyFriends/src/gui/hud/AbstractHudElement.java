package gui.hud;

import game.AbstractMatchManager;
import game.MatchManager;
import gui.MatchPane;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public abstract class AbstractHudElement extends Pane implements HudIndicator{

	protected Group root;
	protected MatchManager matchManager;
	protected MatchPane matchPane;
	protected double screenWidth =  Screen.getPrimary().getBounds().getWidth();
	protected double screenHeight = Screen.getPrimary().getBounds().getHeight();
	
	protected Color themeColor;
	
	public AbstractHudElement(MatchPane matchPane,MatchManager matchManager){
		this.root = new Group();
		this.matchManager =matchManager;
		this.matchPane = matchPane;
		this.getChildren().add(root);
	}
	
	@Override
	public Node getNode() {
		return root;
	}
	
	@Override
	public void reset() {
		
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
