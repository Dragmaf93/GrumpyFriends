package menu.worldMenu;

import menu.PageComponent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MapSelectorButton extends Group {

//	private final static Color STROKE_COLOR = new Color(32d / 255d,
//			207d / 255d, 208d / 255d, 1.0);
	private final static Color STROKE_COLOR = PageComponent.STROKE_COLOR;

	public static MapSelectorButton lastCliccked;
	
	private MapSelectorButton button;
	
	private Text text;
	private Rectangle rectangle;
	private Pane root;

	public MapSelectorButton(double width, double height, String textButton) {
		root = new StackPane();

		rectangle = new Rectangle(width, height);
		rectangle.setFill(Color.TRANSPARENT);

		text = new Text();
		text.setFill(Color.WHITE);
		text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, height / 2));
		text.setText(textButton);

		root.getChildren().addAll(rectangle, text);
		this.getChildren().add(root);
		button=this;
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getEventType() == event.MOUSE_ENTERED) {
					if (button!=lastCliccked) {
						rectangle.setFill(STROKE_COLOR);
						setCursor(Cursor.HAND);
					}
				}
			}
		});

		this.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getEventType() == event.MOUSE_EXITED) {
					if (button!=lastCliccked) {
						rectangle.setFill(Color.TRANSPARENT);
					}
				}
			}
		});

		this.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					rectangle.setFill(STROKE_COLOR);
					if(lastCliccked!=null && lastCliccked!=button)
						lastCliccked.rectangle.setFill(Color.TRANSPARENT);
					lastCliccked = button;
					
				}
			}
		});
	}
	
	public String mapSelected(){
		if(lastCliccked!=null){
			return lastCliccked.text.getText();
		}
		
		return null;
	}

	public void reset() {
		rectangle.setFill(Color.TRANSPARENT);
		lastCliccked=null;
	}
}
