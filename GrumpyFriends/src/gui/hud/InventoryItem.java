package gui.hud;


import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class InventoryItem extends Group{
	
	private static final double WIDTH = 49;
	private static final double HEIGHT = 54;
	
	private Rectangle itemContainer;
	
	public InventoryItem() {
		itemContainer = new Rectangle(WIDTH, HEIGHT);
		itemContainer.setFill(null);
		itemContainer.setStroke(Color.BLACK);
		this.getChildren().add(itemContainer);
	}
	
}
