package menu;


import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MenuButton extends Group{

	private final static Color FILL_COLOR = new Color(1,1,1,0.0);
//	private final static Color STROKE_COLOR = new Color(215d/255d,225d/255d,228d/255d,0.9);
//	private final static Color STROKE_COLOR = new Color(43d/255d,111d/255d,137d/255d,0.8);
	private final static Color STROKE_COLOR = new Color(32d/255d,207d/255d,208d/255d,0.9);
	
	private Pane root;
	private Rectangle border;
	private Rectangle rectangle;
	private Text text;
	
	public MenuButton(double width, double height, String textButton) {
		root = new StackPane();
		
		border = new Rectangle(width,height);
		border.setFill(Color.TRANSPARENT);
		border.setStroke(STROKE_COLOR);
		border.setStrokeWidth(4);
		border.setArcHeight(height);
		border.setArcWidth(50);
	
		rectangle = new Rectangle(width-4,height-4);
		rectangle.setFill(null);
		rectangle.setArcHeight(height-6);
		rectangle.setArcWidth(50-6);
		
		text = new Text();
		text.setFill(Color.WHITE);
		text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, height/2));
		text.setText(textButton);
		
		root.getChildren().addAll(border,rectangle,text);
		this.getChildren().add(root);
		
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getEventType()==event.MOUSE_ENTERED){
					rectangle.setFill(STROKE_COLOR);
					setCursor(Cursor.HAND);
				}
			}
		});
		
		this.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getEventType()==event.MOUSE_EXITED){
					rectangle.setFill(Color.TRANSPARENT);
//					rectangle.setStrokeWidth(4);
//					text.setFill(STROKE_COLOR);
				}
			}
		});
		
	}
	
}
