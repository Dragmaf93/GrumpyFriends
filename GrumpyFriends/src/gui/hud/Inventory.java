package gui.hud;

import java.util.ArrayList;
import java.util.List;

import element.weaponsManager.WeaponsManager;
import game.MatchManager;
import gui.MatchPane;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class Inventory extends AbstractHudElement {

	private final static String GUI_WEAPON_PATH = "gui.weapon.";

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
	private Group hoveredItemGroup;

	private Text inventoryText;
	private Text weaponText;
	private Text utilitiesText;

	private Text hoveredItemText;
	private Text ammunitionHoveredItem;

	private VBox weaponsBox;
	private VBox utilitiesBox;

	private List<InventoryItem> weapons;
	private List<InventoryItem> utilities;

	private int lastTurn;
	private double distanceBorder;

	private boolean hide;

	public Inventory(MatchPane matchPane, MatchManager matchManager) {
		super(matchPane, matchManager);

		background = new Rectangle(INVENTORY_WIDTH, INVENTORY_HEIGTH, BACKGROUND_COLOR);

		lastTurn = matchManager.getTurn();

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
		hoveredItemText = new Text();
		ammunitionHoveredItem = new Text();

		header = new StackPane();
		weaponsGroup = new Group(weaponText);
		utilitiesGroup = new Group(utilitiesText);
		hoveredItemGroup = new Group(hoveredItemText, ammunitionHoveredItem);

		inventoryTextContainer = new Rectangle(213, 45, BACKGROUND_COLOR1);
		shadowTextContainer = new Rectangle(inventoryTextContainer.getWidth(), inventoryTextContainer.getHeight() / 2,
				null);
		ds.setOffsetY(0);
		shadowTextContainer.setEffect(ds);
		inventoryTextContainer.setStroke(Color.BLACK);

		inventoryText.setFont(Font.font(32));
		weaponText.setFont(Font.font(28));
		utilitiesText.setFont(Font.font(28));
		hoveredItemText.setFont(Font.font(28));
		ammunitionHoveredItem.setFont(Font.font(28));

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

		weapons = new ArrayList<>();
		weaponsBox = createInvetoryItem(3, 5, weapons);
		weaponsGroup.getChildren().add(weaponsBox);
		distanceBorder = background.getWidth() / 2 - weaponsGroup.getLayoutBounds().getWidth() / 2;
		weaponsGroup.relocate(distanceBorder, distanceBorder + DISTANCE_HEADER);
		weaponsBox.relocate(0, DISTANCE_HEADER);

		utilities = new ArrayList<>();
		utilitiesBox = createInvetoryItem(2, 5, utilities);
		utilitiesGroup.getChildren().add(utilitiesBox);
		utilitiesGroup.relocate(distanceBorder, weaponsGroup.getLayoutY() + weaponsGroup.getLayoutBounds().getHeight());
		utilitiesBox.relocate(0, DISTANCE_HEADER);

		root.getChildren().add(hoveredItemGroup);
		hoveredItemGroup.relocate(distanceBorder,
				INVENTORY_HEIGTH - DISTANCE_HEADER - hoveredItemText.getLayoutBounds().getHeight());

		Rotate rotate = new Rotate();
		rotate.setAngle(7);
		root.getTransforms().add(rotate);

		setWeaponsOfInventoryItem();
		
		root.relocate(screenWidth - root.getLayoutBounds().getWidth(), -10);

	}

	private VBox createInvetoryItem(int x, int y, List<InventoryItem> items) {
		VBox vBox = new VBox(3);
		for (int i = 0; i < x; i++) {
			HBox box = new HBox(3);
			vBox.getChildren().add(box);
			for (int j = 0; j < y; j++) {
				InventoryItem item = new InventoryItem();
				items.add(item);
				box.getChildren().add(item);
			}
		}
		return vBox;
	}

	public void setWeaponsOfInventoryItem() {
		String[] weaponsName = WeaponsManager.getWeaponList();

		for (int i = 0; i < weaponsName.length; i++) {
			weapons.get(i).setItemName(weaponsName[i].substring(0, weaponsName[i].length() - 5));
			weapons.get(i).setText(this);
		}
	}

	public void itemToEquipe(String name) {
		matchManager.getCurrentPlayer().equipWeapon(name);
		hide=true;
	}

	@Override
	public void draw() {
		if(matchManager.isTheCurrentTurnEnded() && !hide)
			hide();

	}

	public void setAmmunitionOfHoveredWeapon(String name) {
	}

	public void setHoveredWeaponText(String name) {

		if (!name.equals("")) {
			ammunitionHoveredItem.setText(Integer
					.toString(matchManager.getCurrentPlayer().getInventoryManager().getNumberOfAmmunition(name)));
			ammunitionHoveredItem.relocate(
					INVENTORY_WIDTH - distanceBorder * 2 - ammunitionHoveredItem.getLayoutBounds().getWidth(),
					hoveredItemText.getLayoutBounds().getMinY());
		} else
			ammunitionHoveredItem.setText("");
		hoveredItemText.setText(name);
	}

	public boolean isHidden() {
		return hide;
	}
	
	public void hide(){
		hide=true;
	}
	public void show(){
		hide=false;
	}

}
