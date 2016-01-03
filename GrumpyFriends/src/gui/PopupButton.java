package gui;

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

public class PopupButton extends Group{

	private Pane root;
	private Rectangle rectangle;
	private Text text;
	
	private static Color STROKE_COLOR = new Color(32d/255d,207d/255d,208d/255d,1);

	
	public PopupButton(double width,double height,String textString) {
		root = new StackPane();
		
		rectangle = new Rectangle(width,height);
		rectangle.setFill(Color.TRANSPARENT);
//		rectangle.setArcHeight(20);
//		rectangle.setArcWidth(20);
		
		text = new Text();
		text.setFill(Color.WHITE);
		text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, height/3));
		text.setText(textString);
		
		root.getChildren().addAll(rectangle,text);
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
				}
			}
		});
	}
	
//	public void changeColor(Color color) {
//		text.setFill(color);
////		STROKE_COLOR = Color.AQUAMARINE;
//	}
	
	public void setDimension(double width) {
		rectangle.setWidth(width);
	}
	
}
