package gui;

import character.Character;
import game.MatchManager;
import gui.worldDrawer.WorldDrawer;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import world.World;

public class MatchPane extends Pane {

	private World world;
	private MatchManager matchManager;

	private WorldDrawer drawer;

	public MatchPane(MatchManager matchManager) {
		this.matchManager = matchManager;
		this.world = matchManager.getWorld();

		drawer = new WorldDrawer(this);

		drawer.drawWorld();

		new AnimationTimer() {

			@Override
			public void handle(long now) {
				drawer.relocateCharacters();
				world.update();
			}
		}.start();
	}

	public World getWorld() {
		return world;
	}

	public MatchManager getMatchManager() {
		return matchManager;
	}

}
