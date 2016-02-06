package menu;

import gui.UpdatablePane;
import gui.animation.SpriteAnimation;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;

public class Menu extends Pane implements UpdatablePane {

	private static final String PATH_FRAME = "file:image/Menu/Cantina/";
	private static final String TITLE_PATH = "file:image/Menu/title.png";

	private final static Image[] MOVE_FRAMES = {
			new Image(PATH_FRAME + "frame1.png"),
			new Image(PATH_FRAME + "frame2.png"),
			new Image(PATH_FRAME + "frame3.png"),
			new Image(PATH_FRAME + "frame4.png"),
			new Image(PATH_FRAME + "frame5.png"),
			new Image(PATH_FRAME + "frame6.png"),
			new Image(PATH_FRAME + "frame7.png"),
			new Image(PATH_FRAME + "frame8.png"),
			new Image(PATH_FRAME + "frame9.png"),
			new Image(PATH_FRAME + "frame10.png"),
			new Image(PATH_FRAME + "frame11.png"),
			new Image(PATH_FRAME + "frame12.png"),
			new Image(PATH_FRAME + "frame13.png") };

	private final static double WIDTH_BUTTON = 320;
	private final static double HEIGHT_BUTTON = 70;

	private Pane root;
	private SpriteAnimation animationCantinaBand;

	private MenuButton localGameButton;
	private MenuButton networkGameButton;
	private MenuButton mapEditorButton;
	private MenuButton exitButton;

	private double screenHeight, screenWidth;

	private ImageView leftCantinaBand;
	private ImageView rightCantinaBand;
	private ImageView titleMenu;

	public Menu() {

		screenWidth = Screen.getPrimary().getBounds().getWidth();
		screenHeight = Screen.getPrimary().getBounds().getHeight();

		localGameButton = new MenuButton(WIDTH_BUTTON, HEIGHT_BUTTON,
				"Local Game");
		networkGameButton = new MenuButton(WIDTH_BUTTON, HEIGHT_BUTTON,
				"Network Game");
		mapEditorButton = new MenuButton(WIDTH_BUTTON, HEIGHT_BUTTON,
				"Map Editor");
		exitButton = new MenuButton(WIDTH_BUTTON, HEIGHT_BUTTON, "Exit");

		VBox buttons = new VBox(30);
		buttons.getChildren().addAll(localGameButton, networkGameButton,
				mapEditorButton, exitButton);
		buttons.relocate(screenWidth / 2 - WIDTH_BUTTON / 2, 150
				+ HEIGHT_BUTTON * 2 + buttons.getSpacing());

		titleMenu = new ImageView(TITLE_PATH);

		titleMenu.relocate(screenWidth / 2
				- titleMenu.getLayoutBounds().getWidth() / 2, titleMenu
				.getLayoutBounds().getHeight() * 0.5);

		animationCantinaBand = new SpriteAnimation(MOVE_FRAMES, 53);

		leftCantinaBand = new ImageView();
		leftCantinaBand.setFitWidth(240 * 1.65);
		leftCantinaBand.setFitHeight(200 * 1.65);

		rightCantinaBand = new ImageView();
		rightCantinaBand.setFitWidth(240 * 1.65);
		rightCantinaBand.setFitHeight(200 * 1.65);
		rightCantinaBand.setRotationAxis(Rotate.Y_AXIS);
		rightCantinaBand.setRotate(180);

		root = new Pane(titleMenu, buttons, leftCantinaBand, rightCantinaBand);
		leftCantinaBand.relocate(0,
				screenHeight - leftCantinaBand.getFitHeight() + 20);
		rightCantinaBand.relocate(screenWidth - rightCantinaBand.getFitWidth(),
				screenHeight - leftCantinaBand.getFitHeight() + 20);
		this.getChildren().add(root);

		exitButton.setOnMouseReleased(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				MenuManager.getInstance().closeGame();
			}
		});

		localGameButton.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY)
					MenuManager.getInstance().localGamePressed();
				MenuManager.getInstance().nextPage();
			}

		});
	}

	public void update() {
		leftCantinaBand.setImage(animationCantinaBand.nextFrame());
		rightCantinaBand.setImage(animationCantinaBand.nextFrame());
	}
}
