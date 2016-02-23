package menu.teamMenu;

import gui.animation.SpriteAnimation;

import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import menu.AbstractPageComponent;
import menu.Orientation;
import menu.TriangleButton;

public class TeamTypeSelector extends AbstractPageComponent {

	private Group teamType;

	private final static String TEAM_TYPE = "image/Character/";

	private final static double BUTTON_WIDTH = 45;
	private final static double BUTTON_HEIGHT = 50;

	private final static double DIMENSION_IMAGE = 170;

	private TriangleButton buttonLeft;
	private TriangleButton buttonRight;
	private Pane pane;

	private String[] typeTeam;
	private ImageView imagesTeamType;
	private static SpriteAnimation[] spriteAnimations;

	private int indexCurrentType;

	public TeamTypeSelector(TeamPage teamPage) {
		super(teamPage);

		loadFileImage();
		imagesTeamType = new ImageView();
		imagesTeamType.setFitWidth(DIMENSION_IMAGE * 2);
		imagesTeamType.setFitHeight(DIMENSION_IMAGE * 2);

		indexCurrentType = 0;

		teamType = new Group(imagesTeamType);

		teamType.relocate(rectangleBackground.getWidth() / 2 - DIMENSION_IMAGE,
				rectangleBackground.getHeight() / 2 - DIMENSION_IMAGE + 30);

		buttonLeft = new TriangleButton(BUTTON_WIDTH, BUTTON_HEIGHT,
				Orientation.LEFT);
		buttonRight = new TriangleButton(BUTTON_WIDTH, BUTTON_HEIGHT,
				Orientation.RIGHT);

		pane = new Pane(buttonLeft, buttonRight, teamType);

		buttonLeft.relocate(rectangleBackground.getX() + 15,
				rectangleBackground.getY() + rectangleBackground.getHeight()
						/ 2 - BUTTON_HEIGHT / 2);
		buttonRight.relocate(
				rectangleBackground.getX() + rectangleBackground.getWidth()
						- BUTTON_WIDTH - 20, rectangleBackground.getY()
						+ rectangleBackground.getHeight() / 2 - BUTTON_HEIGHT
						/ 2);
		root.getChildren().add(pane);

		buttonLeft.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {

					indexCurrentType = Math.abs((indexCurrentType - 1)
							% typeTeam.length);
					teamPage.changeHeadType(typeTeam[indexCurrentType]);
				}
			}

		});

		buttonRight.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {

					indexCurrentType = (indexCurrentType + 1) % typeTeam.length;
					teamPage.changeHeadType(typeTeam[indexCurrentType]);
				}
			}
		});
	}

	private void loadFileImage() {

		File dir = new File(TEAM_TYPE);
		typeTeam = dir.list();

		spriteAnimations = new SpriteAnimation[typeTeam.length];

		for (int i = 0; i < typeTeam.length; i++) {
			String fileString = "file:" + TEAM_TYPE + typeTeam[i] + "/Idle";

			Image[] frames = { new Image(fileString + "/frame1.png"),
					new Image(fileString + "/frame2.png"),
					new Image(fileString + "/frame3.png"),
					new Image(fileString + "/frame4.png") };
			spriteAnimations[i] = new SpriteAnimation(frames, 100);
		}
	}

	public String getType() {
		return typeTeam[indexCurrentType];
	}

	@Override
	public double getHeightComponent() {
		return 400;
	}

	@Override
	public double getWidthComponent() {
		return 400;
	}

	@Override
	public String getNameComponent() {
		return "TEAM TYPE";
	}

	@Override
	public void reset() {
		indexCurrentType=0;
		((TeamPage) menuPage).changeHeadType(typeTeam[indexCurrentType]);

	}

	@Override
	public String[] getValues() {
		String[] tmp = { getType() };
		return tmp;
	}

	public void update() {
		imagesTeamType.setImage(spriteAnimations[indexCurrentType].nextFrame());
	}

}
