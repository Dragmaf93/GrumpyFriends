package menu;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class TriangleButton extends Group{

	
//	private final static Color STROKE_COLOR = new Color(32d / 255d,
//			207d / 255d, 208d / 255d, 1.0);
	private final static Color STROKE_COLOR = PageComponent.STROKE_COLOR;

	private Polygon polygon;
	private Orientation orientation;

	private double width,height;
	
	public TriangleButton(double width, double height ,Orientation orientation) {
		this.orientation=orientation;
		this.width=width;
		this.height=height;
		polygon = new Polygon();
		polygon.setStroke(STROKE_COLOR);
		polygon.setStrokeWidth(4);
		polygon.setFill(Color.TRANSPARENT);
		
		switch (orientation) {
		case DOWN:
			polygon.getPoints().addAll(0d,0d,width,0d,width/2,height);
			break;
		case LEFT:
			polygon.getPoints().addAll(0d,0d,-width,height/2,0d,height);
			break;
		case RIGHT:
			polygon.getPoints().addAll(0d,0d,width,height/2,0d,height);
			break;
		case UP:
			polygon.getPoints().addAll(width/2,-height,width,0d,0d,0d);
			break;
		}
		this.getChildren().add(polygon);
		
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getEventType() == event.MOUSE_ENTERED) {
						polygon.setFill(STROKE_COLOR);
						setCursor(Cursor.HAND);
				}
			}
		});

		this.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getEventType() == event.MOUSE_EXITED) 
						polygon.setFill(Color.TRANSPARENT);	
			}
		});
	}
	public double getHeight() {
		return height;
	}
	public double getWidth() {
		return width;
	}
	
}
