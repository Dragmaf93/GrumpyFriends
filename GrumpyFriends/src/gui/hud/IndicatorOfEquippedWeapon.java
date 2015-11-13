package gui.hud;


import game.MatchManager;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class IndicatorOfEquippedWeapon extends AbstractHudElement {

	private final static Color BACKGROUND_COLOR = new Color(30d / 255d, 127d / 255d, 169d / 255d, 0.8d);

	private final static float DISTANCE_SCREEN_RIGHT = 230;
	private final static float DISTANCE_SCREEN_TOP = 30;

	private Circle ammunitionContainer;
	private Circle iconContainer;
	private Rectangle nameContainer;
	
	private StackPane ammunitionPane;
	private StackPane iconPane;
	private StackPane namePane;

	private Text ammunitionText;
	private Text nameText;

	private String nameWeapon;
	private int lastAmmunition;

	private DropShadow shadow;
	private Rectangle nameContainerShadow;
	private Circle ammunitionContainerShadow;
	
	public IndicatorOfEquippedWeapon(MatchManager matchManager) {
		super(matchManager);

		ammunitionContainer = new Circle();
		iconContainer = new Circle();
		nameContainer = new Rectangle(180, 40);
		
		nameContainerShadow = new Rectangle(180, 40);
		ammunitionContainerShadow = new Circle(35);
		
		ammunitionContainer.setFill(Color.WHITE);
		ammunitionContainer.setStroke(Color.BLACK);
		ammunitionContainer.setStrokeWidth(3);
		ammunitionContainer.setRadius(35);

		nameContainer.setFill(Color.WHITE);
		nameContainer.setStrokeWidth(3);
		nameContainer.setStroke(Color.BLACK);
		nameContainer.setArcWidth(40);
		nameContainer.setArcHeight(40);

		iconContainer.setFill(BACKGROUND_COLOR);
		iconContainer.setStroke(Color.BLACK);
		iconContainer.setStrokeWidth(3);
		iconContainer.setRadius(68);

		ammunitionText = new Text();
		nameText = new Text();

		ammunitionText.setFont(Font.font(20));
		nameText.setFont(Font.font(15));
		// ammunitionText.setText("9");
		// nameText.setText("Bazooka");

		ammunitionPane = new StackPane();
		iconPane = new StackPane();
		namePane = new StackPane();

		iconPane.getChildren().add(iconContainer);

		namePane.getChildren().add(nameContainer);
		namePane.getChildren().add(nameText);

		ammunitionPane.getChildren().add(ammunitionContainer);
		ammunitionPane.getChildren().add(ammunitionText);

		root.getChildren().addAll(ammunitionContainerShadow,nameContainerShadow,iconPane, ammunitionPane, namePane);
	
		ammunitionPane.relocate(-ammunitionContainer.getRadius() / 2, -ammunitionContainer.getRadius() / 2);
		ammunitionContainerShadow.relocate(-ammunitionContainer.getRadius() / 2, -ammunitionContainer.getRadius() / 2);
		namePane.relocate(iconContainer.getLayoutBounds().getWidth() / 2 - nameContainer.getWidth() / 2 - 2.5,
				iconContainer.getLayoutBounds().getHeight() - nameContainer.getHeight() * 0.7);
		nameContainerShadow.relocate(iconContainer.getLayoutBounds().getWidth() / 2 - nameContainer.getWidth() / 2 - 2,
				iconContainer.getLayoutBounds().getHeight() - nameContainer.getHeight() * 0.7+1);
		
		root.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() != MouseButton.SECONDARY) {
//					if (iconContainer.contains(event.getX(), event.getY()) || nameContainer.contains(event.getX(), event.getY())
//							|| ammunitionContainer.contains(event.getX(), event.getY()))
						if (nameText.getText() != null && !nameText.getText().equals(""))
							matchManager.getCurrentPlayer().equipWeapon(nameText.getText());
				}
			}
		});
		
		shadow = new DropShadow(2,Color.BLACK);
		shadow.setOffsetX(3);
		shadow.setOffsetY(-1);
		iconContainer.setEffect(shadow);

		nameContainerShadow.setFill(Color.WHITE);
		nameContainerShadow.setArcWidth(40);
		nameContainerShadow.setArcHeight(40);
		nameContainerShadow.setEffect(shadow);
		
		ammunitionContainerShadow.setEffect(shadow);
		
	}

	@Override
	public void draw() {
		Scene scene = root.getScene();
		root.relocate(scene.getWidth() - DISTANCE_SCREEN_RIGHT, +DISTANCE_SCREEN_TOP);
		if (matchManager.getCurrentPlayer().getLastEquippedWeapon() != null) {
			if (!matchManager.getCurrentPlayer().getLastEquippedWeapon().equals(nameWeapon)) {

				nameText.setText(nameWeapon = matchManager.getCurrentPlayer().getLastEquippedWeapon());
			}
			if (lastAmmunition != matchManager.getCurrentPlayer().getInventoryManager()
					.getNumberOfAmmunition(nameWeapon))
				ammunitionText.setText(Integer.toString(lastAmmunition = matchManager.getCurrentPlayer()
						.getInventoryManager().getNumberOfAmmunition(nameWeapon)));
		} else {
			nameText.setText("");
			ammunitionText.setText("");
		}
	}

}
