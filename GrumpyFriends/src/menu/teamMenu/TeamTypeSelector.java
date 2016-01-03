package menu.teamMenu;

import game.GameManager;

import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import menu.AbstractPageComponent;
import menu.Orientation;
import menu.TriangleButton;

public class TeamTypeSelector extends AbstractPageComponent {

	private Group teamType;

	private final static String TEAM_PATH = "image/Team/";
	
	private final static double BUTTON_WIDTH = 45;
	private final static double BUTTON_HEIGHT = 50;
	
	private final static double DIMENSION_IMAGE = 170;
	
	private TriangleButton buttonLeft;
	private TriangleButton buttonRight;
	private Pane pane;
	
	private String[] typeTeam;
	private ImageView[] imagesTeamType;
	
	private int indexCurrentType;
	
	private GameManager gameManager;
	
	public TeamTypeSelector(TeamPage teamPage) {
		super(teamPage);
		
		gameManager = GameManager.getIstance();
		
		File dir = new File(TEAM_PATH);
		typeTeam = dir.list();

		imagesTeamType = new ImageView[typeTeam.length];

		int i = 0;
		for (String team : typeTeam) {
			imagesTeamType[i] = new ImageView("file:"+TEAM_PATH+team);
			imagesTeamType[i].setFitWidth(DIMENSION_IMAGE * 2);
			imagesTeamType[i].setFitHeight(DIMENSION_IMAGE * 2);
			i++;
		}

		indexCurrentType = 0;
		
		teamType = new Group(imagesTeamType[indexCurrentType]);

		teamType.relocate(rectangleBackground.getWidth() / 2 - DIMENSION_IMAGE,
				rectangleBackground.getHeight() / 2 - DIMENSION_IMAGE +30);
		
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
		
		((TeamPage) getMenuPage()).getMenuManager().setTeamType(typeTeam[indexCurrentType]);
		
		buttonLeft.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					teamType.getChildren().remove(
							imagesTeamType[indexCurrentType]);
					indexCurrentType = Math.abs((indexCurrentType - 1)
							% typeTeam.length);
					teamType.getChildren().add(
							imagesTeamType[indexCurrentType]);
					((TeamPage) getMenuPage()).getMenuManager().setTeamTypeAfter(typeTeam[indexCurrentType]);
				}
			}

		});

		buttonRight.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {

					teamType.getChildren().remove(
							imagesTeamType[indexCurrentType]);
					indexCurrentType = (indexCurrentType + 1)
							% typeTeam.length;
					teamType.getChildren().add(
							imagesTeamType[indexCurrentType]);
					((TeamPage) getMenuPage()).getMenuManager().setTeamTypeAfter(typeTeam[indexCurrentType]);
				}
			}
		});
	}
	
	public String getType() {
		if (typeTeam[indexCurrentType].equals("teamA"))
			return "WHITE";
		return "BLACK";
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

}
