package menu.worldMenu;

import java.io.File;

import menu.AbstractPageComponent;
import menu.Orientation;
import menu.TriangleButton;
import gui.ImageLoader;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class WorldTypeSelector extends AbstractPageComponent {

	private Group worldType;

	private final static String WORLD_PATH = "image/World/";
	private final static double BUTTON_WIDTH = 50;
	private final static double BUTTON_HEIGHT = 50;

	private final static double RADIUS_WORLD = 80;

	private TriangleButton buttonLeft;
	private TriangleButton buttonRight;

	private String[] typeWorlds;
	private ImageView[] imagesWorldType;

	private Pane pane;
	private int indexCurrentType;

	public WorldTypeSelector(WorldPage worldPage, ImageLoader imageLoader) {
		super(worldPage);

		File dir = new File(WORLD_PATH);
		typeWorlds = dir.list();

		imagesWorldType = new ImageView[typeWorlds.length];

		int i = 0;
		for (String typeWorld : typeWorlds) {
			imagesWorldType[i] = new ImageView(
					imageLoader.getPreview(typeWorld));
			imagesWorldType[i].setFitWidth(RADIUS_WORLD * 2);
			imagesWorldType[i].setFitHeight(RADIUS_WORLD * 2);
			i++;
		}

		indexCurrentType = 0;

		buttonLeft = new TriangleButton(BUTTON_WIDTH, BUTTON_HEIGHT,
				Orientation.LEFT);
		buttonRight = new TriangleButton(BUTTON_WIDTH, BUTTON_HEIGHT,
				Orientation.RIGHT);

		worldType = new Group(imagesWorldType[indexCurrentType]);

		pane = new Pane(buttonLeft, buttonRight, worldType);

		worldType.relocate(rectangleBackground.getWidth() / 2 - RADIUS_WORLD,
				rectangleBackground.getHeight() / 2 - RADIUS_WORLD);
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
					worldType.getChildren().remove(
							imagesWorldType[indexCurrentType]);
					indexCurrentType = Math.abs((indexCurrentType - 1)
							% typeWorlds.length);
					worldType.getChildren().add(
							imagesWorldType[indexCurrentType]);
				}
			}

		});

		buttonRight.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {

					worldType.getChildren().remove(
							imagesWorldType[indexCurrentType]);
					indexCurrentType = (indexCurrentType + 1)
							% typeWorlds.length;
					worldType.getChildren().add(
							imagesWorldType[indexCurrentType]);
				}
			}
		});
	}

	public String getTypeWorld() {
		return typeWorlds[indexCurrentType];
	}

	@Override
	public double getHeightComponent() {
		return 250;
	}
	
	@Override
	public void reset() {
		
	}
	
	@Override
	public double getWidthComponent() {
		return 450;
	}

	@Override
	public String getNameComponent() {
		return "WORLD TYPE";
	}

	@Override
	public String[] getValues() {
		String[] tmp = {getTypeWorld()};
		return tmp;
	}

}
