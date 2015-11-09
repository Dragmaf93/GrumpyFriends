package gui;

import game.MatchManager;
import gui.drawer.DrawerManager;
import gui.hud.MatchPane;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import world.World;

public class FieldPane extends Pane {

	private World world;
	private MatchManager matchManager;

	private DrawerManager drawer;
	private GraphicsContext gc;

	private Group field;
	
	public FieldPane(MatchManager matchManager) {
		this.matchManager = matchManager;
		this.world = matchManager.getWorld();
		this.field = new Group();
		
		drawer = new DrawerManager(field,world);
		drawer.createWorld();
		
		this.getChildren().add(field);
	}
	
	public void update(){
		drawer.draw();
		world.update();
	}
	
	public World getWorld() {
		return world;
	}

	public MatchManager getMatchManager() {
		return matchManager;
	}

}
