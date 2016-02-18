package menu.networkMenu;

import network.InfoMatch;
import menu.PageComponent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MatchListElement extends Pane {

	private final static int dimImage = 25;

	private final static Image LOCK_ICON = new Image(
			"file:image/Network/lock.png", dimImage, dimImage, false, false);
	private final static Image UNLOCK_ICON = new Image(
			"file:image/Network/unlock.png", dimImage, dimImage, false, false);

	private final static double WIDTH = 200;
	private Text userNameHeaderText;
	private Text matchNameHeaderText;

	private Text userNameText;
	private Text matchNameText;

	private InfoMatch infoMatch;
	private Pane root;
	private ImageView imagePadlock;

	private double padding;

	public MatchListElement(InfoMatch infoMatch) {

		userNameText = new Text(infoMatch.getUserName());
		userNameText.setFill(PageComponent.HEADER_TEXT_COLOR);
		userNameText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 17));

		matchNameText = new Text(infoMatch.getMatchName());
		matchNameText.setFill(PageComponent.HEADER_TEXT_COLOR);
		matchNameText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 17));

		userNameHeaderText = new Text("Administrator");
		userNameHeaderText.setFill(PageComponent.HEADER_TEXT_COLOR);
		userNameHeaderText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,
				18));

		matchNameHeaderText = new Text("Match Name");
		matchNameHeaderText.setFill(PageComponent.HEADER_TEXT_COLOR);
		matchNameHeaderText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,
				18));

		HBox admin = new HBox(30, userNameHeaderText, userNameText);
		HBox matchName = new HBox(30, matchNameHeaderText, matchNameText);

		if (infoMatch.isPrivateMatch()) {
			imagePadlock = new ImageView(LOCK_ICON);
			padding = 59.9;
		} else {
			imagePadlock = new ImageView(UNLOCK_ICON);
			padding = 55.3;
		}
		Line line = new Line(-15, 0, WIDTH * 1.35, 0);
		line.setStroke(PageComponent.STROKE_COLOR);
		// line.relocate(line.getLayoutBounds().getWidth()/2, 0);

		VBox allObject = new VBox(3, new HBox(padding, new VBox(3, admin,
				matchName), new StackPane(imagePadlock)), new Pane(line));

		root = new Pane(allObject);
		root.relocate(70, 0);
		this.getChildren().add(root);

		this.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getEventType() == event.MOUSE_ENTERED) {
					setCursor(Cursor.HAND);
				}
			}
		});

	}

	public InfoMatch getInfoMatch() {
		return infoMatch;
	}
}
