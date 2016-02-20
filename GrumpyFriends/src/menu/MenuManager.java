package menu;

import java.io.IOException;

import network.client.ClientGame;
import mapEditor.MapEditor;
import menu.networkMenu.WaitingPage;
import menu.worldMenu.WorldPage;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import game.Game;
import game.GameTask;
import game.GrumpyFriends;
import game.LocalGame;
import game.AbstractMatchManager;
import game.MatchManager;
import game.MediaPlayerManager;
import gui.ImageLoader;
import gui.MatchPane;
import gui.Popup;
import gui.UpdatablePane;

public class MenuManager {

	private final class CreateMatchPaneTask extends GameTask {
		@Override
		protected void work() throws IOException {
			currentGame.startGame();
			MatchManager matchManager = currentGame.getMatchManager();
			matchPane = new MatchPane(matchManager);

		}

		@Override
		protected void afterWork() {
			root.getChildren().removeAll(menuBackground, lastAddedPane);
			root.getChildren().add(matchPane);
			currentUpdatablePane = matchPane;
			lastAddedPane = matchPane;
			grumpyFriends.getScene().setEventHandler(lastAddedPane);

//				mediaPlayer.stop();
		}

		@Override
		protected void handleException() {
			MenuManager.getInstance().addExceptionPopup(exceptionPopup);
			exceptionPopup.getRightButton().setOnMouseReleased(
				new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						if (event.getButton() == MouseButton.PRIMARY) {
							MenuManager.getInstance().previousPage();
							MenuManager.getInstance().previousPage();
							MenuManager.getInstance().previousPage();
							MenuManager.getInstance()
									.removeExceptionPopup(
											exceptionPopup);
						}
					}
				});
		}
	}

	private GrumpyFriends grumpyFriends;

	private Pane root;
	private Pane lastAddedPane;
	private UpdatablePane currentUpdatablePane;

	private Menu menu;
	private MenuBackground menuBackground;

	private Popup exceptionPopup;

	private WaitingPage waitingPage;

	private ImageLoader imageLoader;
	private MatchPane matchPane;
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

		// mediaPlayer = new MediaPlayerManager();
		// mediaPlayer.play();
		imageLoader = new ImageLoader();
		menu = new Menu();
		menuBackground = new MenuBackground();
		localGame = new LocalGame();
		networkGame = new ClientGame();
		lastAddedPane = menu;
		waitingPage = new WaitingPage();
		currentUpdatablePane = menu;
		exceptionPopup = new Popup(400, 180, "Error", null, "Ok");
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

	}

	public void addExceptionPopup(Popup node) {
		if (!root.getChildren().contains(node))
			root.getChildren().add(node);
	}

	public void removeExceptionPopup(Popup node) {
		root.getChildren().remove(node);
	}

	public void viewLoadingPane(String string) {
		waitingPage.setText(string);
		root.getChildren().remove(lastAddedPane);
		root.getChildren().add(waitingPage);

	}

	public void removeWaintingPane() {

	}

	public void hideLoadingPane() {
		root.getChildren().remove(waitingPage);
		// lastAddedPane = (Pane) currentGame.getSequencePages().currentPage();
		lastAddedPane = (Pane) currentGame.nextPage();
		if (lastAddedPane == null) {
			createMatchPane();
		} else
			root.getChildren().add(lastAddedPane);
	}

	public WaitingPage getWaitingPage() {
		return waitingPage;
	}

	public void localGamePressed() {

		currentGame = localGame;
		currentGame.getSequencePages().restartSequence();
	}

	public void goToMainMenu() {
		root.getChildren().remove(lastAddedPane);
		root.getChildren().addAll(menuBackground, menu);
//		mediaPlayer.play();
		lastAddedPane = menu;
		currentUpdatablePane = menu;
		matchPane = null;
		grumpyFriends.getScene().setEventHandler(lastAddedPane);
	}

	public void nextPage() {

		MenuPage page = currentGame.nextPage();

		if (page != null) {
			root.getChildren().remove(lastAddedPane);
			root.getChildren().add((Node) page);
			lastAddedPane = (Pane) page;
			currentUpdatablePane = page;
		} else {
			createMatchPane();
		}
		grumpyFriends.getScene().setEventHandler(lastAddedPane);

	}

	private void createMatchPane() {
		// if (matchPane!=null) {

		root.getChildren().remove(lastAddedPane);
		lastAddedPane = waitingPage;
		waitingPage.setText("Creazione partita...");
		root.getChildren().add(waitingPage);
		GameTask task = new CreateMatchPaneTask();
		task.startToWork();
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

	public Game getCurrentGame() {
		return currentGame;
	}

}
