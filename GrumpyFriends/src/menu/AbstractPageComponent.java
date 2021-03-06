package menu;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public abstract class AbstractPageComponent extends Pane implements PageComponent{
	
	private static final double HEADER_HEIGHT = 45;
	private static final double PADDING_WIDTH=5;
	protected Text headerText; 
	protected Rectangle headerRectangle;
	protected Pane header;
	
	protected Rectangle rectangleBackground;
	protected Group root;
	
	protected MenuPage menuPage;
	
	public AbstractPageComponent(MenuPage menuPage) {
		this.menuPage = menuPage;
		
		headerText = new Text();
		headerText.setFill(HEADER_TEXT_COLOR);
		headerText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, HEADER_HEIGHT*0.6));
		headerText.setText(getNameComponent());
	
		headerRectangle = new Rectangle(headerText.getLayoutBounds().getWidth()+PADDING_WIDTH,HEADER_HEIGHT);
		headerRectangle.setFill(Color.TRANSPARENT);
		headerRectangle.setStrokeWidth(4);
//		headerRectangle.setStroke(STROKE_COLOR);
		
		header = new StackPane(headerRectangle,headerText);
		
		rectangleBackground = new Rectangle(getWidthComponent(), getHeightComponent()-HEADER_HEIGHT);
		rectangleBackground.setFill(Color.TRANSPARENT);
		rectangleBackground.setStrokeWidth(5);
		rectangleBackground.setStroke(STROKE_COLOR);
		rectangleBackground.setArcWidth(20);
		rectangleBackground.setArcHeight(20);
		
		root = new Group(rectangleBackground);
		root.relocate(header.getLayoutX(),header.getLayoutY()+HEADER_HEIGHT);
		
		this.getChildren().addAll(header,root);
	}
	
	
	public void setStroke(double stroke) {
		headerRectangle.setStrokeWidth(stroke);
		rectangleBackground.setStrokeWidth(stroke);
	}
	
	public MenuPage getMenuPage() {
		return menuPage;
	}
	
}
