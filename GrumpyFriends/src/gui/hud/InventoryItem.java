package gui.hud;


import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class InventoryItem extends StackPane{
	
	private static final String PATH_ICON_ITEMS="file:image/weapons/icons/";
	private final static Color BACKGROUND_COLOR1 = new Color(15d / 255d, 62d / 255d, 82d / 255d, 0.3d);

	private static final double WIDTH = 49;
	private static final double HEIGHT = 54;
	
	private Rectangle itemContainer;
	private Rectangle borderItem;
	private String name;
	private Inventory inventory;
	private ImageView icon;
	
	
	public InventoryItem() {
		itemContainer = new Rectangle(WIDTH, HEIGHT);
		borderItem = new Rectangle(WIDTH, HEIGHT);
		
		borderItem.setFill(Color.TRANSPARENT);
		borderItem.setStroke(Color.WHITE);
		itemContainer.setFill(BACKGROUND_COLOR1);
		
		borderItem.setStrokeWidth(2);
		this.getChildren().add(itemContainer);
		this.getChildren().add(borderItem);
		
		
		
	}
	
	public void setItemName(String name){
		this.name=name;
		icon=new ImageView(new Image(PATH_ICON_ITEMS+name+".png"));	
		icon.setFitHeight(HEIGHT);
		icon.setFitWidth(WIDTH);
		
		this.getChildren().add(icon);
		itemContainer.toBack();
		borderItem.toFront();
	}
	


	public void setText(final Inventory inventory) {
		this.inventory = inventory;
		this.setOnMouseEntered(new EventHandler<MouseEvent>
		() {
			
			@Override
			public void handle(MouseEvent t) {
				inventory.setHoveredWeaponText(name);
			}
		});
		
		this.setOnMouseExited(new EventHandler<MouseEvent>
		() {
			
			@Override
			public void handle(MouseEvent t) {
				inventory.setHoveredWeaponText("");
			}
		});
		
		this.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton()== MouseButton.PRIMARY){
					inventory.itemToEquipe(name);
				}
			}
		});
	}
	
	
	
}
