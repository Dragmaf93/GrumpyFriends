package gui;

import game.MatchManager;
import gui.drawer.DrawerManager;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import world.World;

public class MatchPane extends Pane {

	private World world;
	private MatchManager matchManager;

	
	private DrawerManager drawer;
	private GraphicsContext gc;
	
	public MatchPane(MatchManager matchManager) {
		this.matchManager = matchManager;
		this.world = matchManager.getWorld();
		
		drawer = new DrawerManager(this,world);
		
		drawer.createWorld();

		new AnimationTimer() {

			@Override
			public void handle(long now) {
				drawer.draw();
				world.update();
				
			}
		}.start();
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
