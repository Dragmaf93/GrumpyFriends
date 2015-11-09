package gui.hud;

import element.weaponsManager.WeaponsManager;
import game.MatchManager;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class Inventory extends AbstractHudElement {

	private final static double INVENTORY_WIDTH = 310;
	private final static double INVENTORY_HEIGTH = 460;

	private final static double DISTANCE_HEADER = 7;

	private final static double POS_X = 120;

	private final static Color BACKGROUND_COLOR = new Color(30d / 255d, 127d / 255d, 169d / 255d, 0.9d);
	private final static Color BACKGROUND_COLOR1 = new Color(30d / 255d, 127d / 255d, 169d / 255d, 1.0d);

	private Rectangle background;

	private Rectangle inventoryTextContainer;
	private Rectangle shadowTextContainer;

	private StackPane header;

	private Group weaponsGroup;
	private Group utilitiesGroup;

	private Text inventoryText;
	private Text weaponText;
	private Text utilitiesText;

	public WeaponsManager weaponManager;
	
	public Inventory(MatchManager matchManager) {
		super(matchManager);

		
		background = new Rectangle(INVENTORY_WIDTH, INVENTORY_HEIGTH, BACKGROUND_COLOR);

		DropShadow ds = new DropShadow();
		ds.setOffsetX(7.0);
		ds.setOffsetY(0.0);
		ds.setColor(Color.BLACK);

		background.setEffect(ds);
		background.setStroke(Color.BLACK);

		inventoryText = new Text("Inventory");
		inventoryText.setFill(Color.WHITE);
		// inventoryText.setStroke(Color.BLACK);
		weaponText = new Text("Weapons");
		utilitiesText = new Text("Utilities");

		header = new StackPane();
		weaponsGroup = new Group(weaponText);
		utilitiesGroup = new Group(utilitiesText);

		inventoryTextContainer = new Rectangle(213, 45, BACKGROUND_COLOR1);
		shadowTextContainer = new Rectangle(inventoryTextContainer.getWidth(), inventoryTextContainer.getHeight() / 2,
				null);
		ds.setOffsetY(0);
		shadowTextContainer.setEffect(ds);
		inventoryTextContainer.setStroke(Color.BLACK);

		inventoryText.setFont(Font.font(32));
		weaponText.setFont(Font.font(28));
		utilitiesText.setFont(Font.font(28));

		root.getChildren().add(background);
		root.getChildren().add(shadowTextContainer);
		root.getChildren().add(header);
		root.getChildren().add(weaponsGroup);
		root.getChildren().add(utilitiesGroup);
		// root.getChildren().add(inventoryText);
		// ,weaponText,utilitiesText);

		header.getChildren().add(inventoryTextContainer);
		header.getChildren().add(inventoryText);
		header.relocate(background.getWidth() / 2 - inventoryTextContainer.getWidth() / 2,
				-inventoryTextContainer.getHeight() / 2);
		shadowTextContainer.relocate(background.getWidth() / 2 - inventoryTextContainer.getWidth() / 2,
				-inventoryTextContainer.getHeight() / 2);
		VBox weaponsBox = createInvetoryItem(3, 5);
		weaponsGroup.getChildren().add(weaponsBox);
		double distanceBorder = background.getWidth() / 2 - weaponsGroup.getLayoutBounds().getWidth() / 2;
		weaponsGroup.relocate(distanceBorder, distanceBorder + DISTANCE_HEADER);
		weaponsBox.relocate(0, DISTANCE_HEADER);

		VBox utilitiesBox = createInvetoryItem(2, 5);
		utilitiesGroup.getChildren().add(utilitiesBox);
		utilitiesGroup.relocate(distanceBorder, weaponsGroup.getLayoutY() + weaponsGroup.getLayoutBounds().getHeight());
		utilitiesBox.relocate(0, DISTANCE_HEADER);
		
		Rotate rotate = new Rotate();
		rotate.setAngle(7);
		root.getTransforms().add(rotate);
		
		
	}

	private VBox createInvetoryItem(int x, int y) {
		VBox vBox = new VBox(1.5);
		for (int i = 0; i < x; i++) {
			HBox box = new HBox(1.5);
			vBox.getChildren().add(box);
			for (int j = 0; j < y; j++) {
				box.getChildren().add(new InventoryItem());
			}
		}
		return vBox;
	}

	@Override
	public void draw() {

		Scene scene = root.getScene();
		if(scene!=null)
			root.relocate(scene.getWidth()-root.getLayoutBounds().getWidth(), -10);
	}

}
