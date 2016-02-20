package menu.networkMenu;

import game.Game;
import network.InfoMatch;
import network.client.ClientGame;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import menu.MenuButton;
import menu.MenuManager;
import menu.PageComponent;
import menu.TextFieldMenu;

public class DetailMatch extends Pane {

	protected final static double PADDING_HEIGHT = 100;
	protected final static double PADDING_WIDTH = 150;

	private static final double TEXT_HEIGHT = 45;
	private static Font font = Font.font("Comic Sans MS", FontWeight.BOLD,
			TEXT_HEIGHT * 0.7);
	private static Font fontValue = Font.font("Comic Sans MS",
			FontWeight.NORMAL, TEXT_HEIGHT * 0.7);

	protected Pane root;
	protected Rectangle rectangleBackground;
	private VBox realValue;
	private VBox label;
	private MenuButton backButton;
	private MenuButton connectButton;
	private InfoMatch infoMatch;

	private NetworkPage networkPage;
	private Text name;
	private Text nameMatch;
	private Text nameUserText;
	private Text nameUser;
	private Text numberPlayerText;
	private Text numberPlayer;
	private Text worldTypeText;
	private Text worldType;
	private Text statusText;
	private Text status;
	private Text securityTypeText;
	private Text securityType;
	private TextFieldMenu password;
	private Text pass;

	public DetailMatch(NetworkPage networkPage) {

		this.networkPage = networkPage;

		root = new Pane();
		root.setPrefSize(Screen.getPrimary().getBounds().getWidth()
				- PADDING_WIDTH * 5, Screen.getPrimary().getBounds()
				.getHeight()
				- PADDING_HEIGHT * 4.5);

		rectangleBackground = new Rectangle(
				root.getPrefWidth() + PADDING_WIDTH, root.getPrefHeight()
						+ PADDING_HEIGHT);
		rectangleBackground.setFill(new Color(25d / 255d, 25d / 255d,
				34d / 255d, 0.9));
		rectangleBackground.setStrokeWidth(5);
		rectangleBackground.setStroke(PageComponent.STROKE_COLOR);
		rectangleBackground.setArcWidth(20);
		rectangleBackground.setArcHeight(20);

		StackPane p = new StackPane(root);
		p.setPadding(new Insets(PADDING_HEIGHT - 20, PADDING_WIDTH,
				PADDING_HEIGHT, PADDING_WIDTH));

		StackPane pa = new StackPane(rectangleBackground, p);
		pa.setPadding(new Insets(10, PADDING_WIDTH - 20, PADDING_HEIGHT, 75));

		this.getChildren().add(pa);
		backButton = new MenuButton(130, 40, "Back");
		connectButton = new MenuButton(130, 40, "Connect");

		backButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					setVisible(false);
					label.getChildren().removeAll(pass,password);
				}
			}
		});
		connectButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					if (networkPage.isUsernameInsert()) {
						if (infoMatch.isPrivateMatch()) {
							if (password.getText().equals(infoMatch.getPassword())) {
								Game game = MenuManager.getInstance().getCurrentGame();
								((ClientGame) game).setClientType(true);
							}
							else
								networkPage.insertPopupWrongPassword();
						}
						else {
							Game game = MenuManager.getInstance().getCurrentGame();
							((ClientGame) game).setClientType(true);
						}
						
					} else {
						networkPage.insertPopupUsernameMissing();
					}
				}

			}
		});

		inizializeText();
	}

	private void inizializeText() {
		nameMatch = new Text("Match Name:");
		setFontAndColor(nameMatch);
		name = new Text();
		setFontAndColorValue(name);

		nameUserText = new Text("Username:");
		setFontAndColor(nameUserText);
		nameUser = new Text();
		setFontAndColorValue(nameUser);

		numberPlayerText = new Text("Number Character:");
		setFontAndColor(numberPlayerText);
		numberPlayer = new Text();
		setFontAndColorValue(numberPlayer);

		worldTypeText = new Text("World Type:");
		setFontAndColor(worldTypeText);
		worldType = new Text();
		setFontAndColorValue(worldType);

		statusText = new Text("Status:");
		setFontAndColor(statusText);
		status = new Text();
		setFontAndColorValue(status);

		securityTypeText = new Text("Security:");
		setFontAndColor(securityTypeText);
		securityType = new Text();
		setFontAndColorValue(securityType);
		
		password = new TextFieldMenu(
				root.getPrefWidth() * 0.4, root.getPrefHeight() * 0.1);
	}

	public void insertInfo(InfoMatch infoMatch) {

		this.infoMatch = infoMatch;
		password.resetTextField();

		name.setText(infoMatch.getMatchName());
		nameUser.setText(infoMatch.getUserName());
		numberPlayer.setText(Integer.toString(infoMatch.getCharacterTeamNumber()));
		worldType.setText(infoMatch.getWorldType());
		status.setText(infoMatch.getStatusMatch().toString());
		securityType.setText("Public");
		
		label = new VBox(4, nameMatch, nameUserText, numberPlayerText,
				worldTypeText, statusText, securityTypeText);
		realValue = new VBox(4, name, nameUser, numberPlayer, worldType,
				status, securityType);

		boolean isPrivate = infoMatch.isPrivateMatch();
		if (isPrivate) {
			securityType.setText("Private");
			pass = new Text("Password");
			setFontAndColor(pass);

			root.setPrefHeight(Screen.getPrimary().getBounds().getHeight()
					- PADDING_HEIGHT * 3.6);
			rectangleBackground
					.setHeight(root.getPrefHeight() + PADDING_HEIGHT);
			label.getChildren().addAll(pass, password);
		}

		HBox valueBox = new HBox(130, label, realValue);
		HBox buttonBox = new HBox(20, backButton, connectButton);
		buttonBox.relocate(root.getPrefWidth() / 2
				- buttonBox.getLayoutBounds().getWidth() / 2,
				root.getPrefHeight() - buttonBox.getLayoutBounds().getHeight());

		root.getChildren().addAll(valueBox, buttonBox);

	}

	private void setFontAndColor(Text text) {
		text.setFont(font);
		text.setFill(PageComponent.HEADER_TEXT_COLOR);
	}

	private void setFontAndColorValue(Text text) {
		text.setFont(fontValue);
		text.setFill(PageComponent.HEADER_TEXT_COLOR);
	}

	public InfoMatch getInfoMatch() {
		return infoMatch;
	}

}
