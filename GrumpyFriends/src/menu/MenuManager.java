package menu;

import java.io.IOException;

import network.NetworkGame;
import mapEditor.MapEditor;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import game.Game;
import game.GrumpyFriends;
import game.LocalGame;
import game.AbstractMatchManager;
import game.MatchManager;
import game.MediaPlayerManager;
import gui.ImageLoader;
import gui.MatchPane;
import gui.UpdatablePane;

public class MenuManager {

	private GrumpyFriends grumpyFriends;

	private Pane root;
	private Pane lastAddedPane;
	private UpdatablePane currentUpdatablePane;

	private Menu menu;
	private MenuBackground menuBackground;

	private ImageLoader imageLoader;

	// private SequencePage currentSequence;

	private static MenuManager instance;

	private Game currentGame;

	private Game networkGame;
	private Game localGame;

	private MediaPlayerManager mediaPlayer;

	public Pane getRoot() {
		return root;
	}

	public static MenuManager getInstance() {
		if (instance == null)
			instance = new MenuManager();
		return instance;
	}

	private MenuManager() {
	}

	public void initialize(GrumpyFriends grumpyFriends) {
		this.grumpyFriends = grumpyFriends;

		mediaPlayer = new MediaPlayerManager();
		mediaPlayer.play();
		imageLoader = new ImageLoader();
		menu = new Menu();
		menuBackground = new MenuBackground();
		localGame = new LocalGame();
		networkGame = new NetworkGame();
		lastAddedPane = menu;
		currentUpdatablePane = menu;

		root = new Pane(menuBackground, menu);
	}

	public void closeGame() {

		grumpyFriends.closeStage();
	}

	public ImageLoader getImageLoader() {
		return imageLoader;
	}

	public void networkGamePressed() {
		currentGame = networkGame;
		currentGame.getSequencePages().restartSequence();
		try {
			((NetworkGame) networkGame).connectToServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void localGamePressed() {

		currentGame = localGame;
		currentGame.getSequencePages().restartSequence();
	}

	public void goToMainMenu() {
		root.getChildren().remove(lastAddedPane);
		root.getChildren().addAll(menuBackground, menu);
		mediaPlayer.play();
		lastAddedPane = menu;
		currentUpdatablePane = menu;
		grumpyFriends.getScene().setEventHandler(lastAddedPane);
	}

	public void setClientType(boolean chooser) {
		((NetworkGame) networkGame).setClientType(chooser);
	}

	public void nextPage() {

		MenuPage page = currentGame.nextPage();

		if (page != null) {
			root.getChildren().remove(lastAddedPane);
			root.getChildren().add((Node) page);
			lastAddedPane = (Pane) page;
			currentUpdatablePane = page;
		} else {
			currentGame.setUpGame();
			currentGame.startGame();
			MatchManager matchManager = currentGame.getMatchManager();
			MatchPane matchPane = new MatchPane(matchManager);
			// MainScene scene = new
			// MainScene(matchPane,Screen.getPrimary().getBounds().getWidth(),Screen.getPrimary().getBounds().getHeight());
			root.getChildren().removeAll(menuBackground, lastAddedPane);
			root.getChildren().add(matchPane);
			currentUpdatablePane = matchPane;
			lastAddedPane = matchPane;
			mediaPlayer.stop();
			// grumpyFriends.setScene(scene);
		}
		grumpyFriends.getScene().setEventHandler(lastAddedPane);

	}

	public void goToMapEditor() {

		root.getChildren().removeAll(menuBackground, lastAddedPane);

		MapEditor mapEditor = new MapEditor();
		root.getChildren().add(mapEditor);
		lastAddedPane = mapEditor;
		mediaPlayer.stop();

	}

	public void previousPage() {
		MenuPage page = currentGame.prevPage();

		if (page != null) {
			root.getChildren().remove(lastAddedPane);
			root.getChildren().add((Node) page);
			lastAddedPane = (Pane) page;
			currentUpdatablePane = page;
		} else {
			root.getChildren().remove(lastAddedPane);
			root.getChildren().add(menu);
			lastAddedPane = menu;
			currentUpdatablePane = menu;
		}
		grumpyFriends.getScene().setEventHandler(lastAddedPane);
	}

	public void update() {
		currentUpdatablePane.update();
	}
}
