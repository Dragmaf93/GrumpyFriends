package gui;

import network.client.ClientMatchManager;
import character.Team;
import game.AbstractMatchManager;
import game.MatchManager;
import gui.hud.AbstractHudElement;
import menu.MenuButton;
import menu.MenuManager;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;

public class WinnerPane extends AbstractHudElement {

	private final static double BUTTON_WIDTH = 318;
	private final static double BUTTON_HEIGHT = 65;

	private final static String PATH_IMAGES = "file:image/Character/";
	private final static String PATH_WINNER = "/WinnerLoser/winner.png";
	private final static String PATH_LOSER = "/WinnerLoser/loser.png";

	private Text textGameWinner;

	private VBox winnerButtons;

	private MenuButton restartButton;
	private MenuButton exitButton;

	private Popup restartPopup;
	private Popup exitPopup;

	private ImageView teamWinner;
	private ImageView teamLoser;

	private double width;
	private double height;

	public WinnerPane(MatchPane pane, MatchManager matchManager) {
		super(pane, matchManager);
		this.width = Screen.getPrimary().getBounds().getWidth();
		this.height = Screen.getPrimary().getBounds().getHeight();
		this.setPrefSize(width, height);
		this.setStyle("-fx-background: #ece6e6; -fx-background-color: rgba(25,81,81,0.8);");

		winnerButtons = new VBox(40);

		textGameWinner = new Text();
		textGameWinner.setFill(Color.WHITE);
		textGameWinner.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 70));
		textGameWinner.relocate(this.getPrefWidth() / 2
				- textGameWinner.getLayoutBounds().getWidth(), 150);

		restartButton = new MenuButton(BUTTON_WIDTH, BUTTON_HEIGHT, "RESTART");
		exitButton = new MenuButton(BUTTON_WIDTH, BUTTON_HEIGHT, "EXIT GAME");

		winnerButtons.getChildren().addAll(restartButton, exitButton);
		winnerButtons.relocate(width / 2 - BUTTON_WIDTH / 2, 150
				+ BUTTON_HEIGHT * 2 + winnerButtons.getSpacing());

		// teamWinner = new ImageView("file:image/winner/"+teamWin+".png");
		teamWinner = new ImageView();
		teamWinner.relocate(40, height / 2);

		// teamLoser = new ImageView("file:image/winner/"+teamLose+".png");
		teamLoser = new ImageView();
		teamLoser.relocate(width - 450, height / 2);

		root.getChildren().addAll(textGameWinner, winnerButtons, teamWinner,
				teamLoser);

		restartPopup = new Popup(300, 200, "Are you sure?", "Cancel", "Accept");
		exitPopup = new Popup(300, 200, "Are you sure?", "Cancel", "Accept");

		restartButton.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					root.getChildren().add(restartPopup);
					winnerButtons.setVisible(false);
				}
			}
		});

		exitButton.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					root.getChildren().add(exitPopup);
					winnerButtons.setVisible(false);
				}
			}
		});

		restartPopup.getRightButton().setOnMouseClicked(
				new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						if (event.getButton() == MouseButton.PRIMARY)
							matchPane.restartMatch();
					}
				});
		restartPopup.getLeftButton().setOnMouseClicked(
				new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						if (event.getButton() == MouseButton.PRIMARY) {
							root.getChildren().remove(restartPopup);
							winnerButtons.setVisible(true);
						}
					}
				});

		exitPopup.getRightButton().setOnMouseClicked(
				new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						if (event.getButton() == MouseButton.PRIMARY)
							MenuManager.getInstance().goToMainMenu();
					}
				});
		exitPopup.getLeftButton().setOnMouseClicked(
				new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						if (event.getButton() == MouseButton.PRIMARY) {
							root.getChildren().remove(exitPopup);
							winnerButtons.setVisible(true);
						}
					}
				});
		
		
		if (matchManager instanceof ClientMatchManager)
			restartButton.setVisible(false);

	}

	@Override
	public void reset() {
		root.getChildren().remove(exitPopup);
		root.getChildren().remove(restartPopup);
		teamLoser.setImage(null);
		teamWinner.setImage(null);
		winnerButtons.setVisible(true);

	}

	@Override
	public void draw() {

		if (matchManager.hasWinnerTeam()) {
			textGameWinner.setText("Il vincitore è : "
					+ matchManager.getWinnerTeam().getName());
			teamWinner.setImage(new Image(PATH_IMAGES
					+ matchManager.getWinnerTeam().getType() + PATH_LOSER));
			teamLoser.setImage(new Image(PATH_IMAGES
					+ matchManager.getLoserTeam().getType() + PATH_WINNER));
		} else {
			textGameWinner.setText("Pareggio");
			teamWinner.setImage(new Image(PATH_IMAGES
					+ matchManager.getTeamA().getType() + PATH_LOSER));
			teamLoser.setImage(new Image(PATH_IMAGES
					+ matchManager.getTeamB().getType() + PATH_LOSER));
		}
		textGameWinner.relocate(this.getPrefWidth() / 2
				- textGameWinner.getLayoutBounds().getWidth() / 2, 150);
	}

}
