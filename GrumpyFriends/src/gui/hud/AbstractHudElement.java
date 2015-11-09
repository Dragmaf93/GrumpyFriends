package gui.hud;

import game.MatchManager;
import javafx.scene.Group;
import javafx.scene.Node;

public abstract class AbstractHudElement implements HudIndicator{

	protected Group root;
	protected MatchManager matchManager;
	
	public AbstractHudElement(MatchManager matchManager){
		this.root = new Group();
		this.matchManager =matchManager;
	}
	
	@Override
	public Node getNode() {
		return root;
	}
	
	
	
}
