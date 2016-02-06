package menu;

import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

public abstract class AbstractMenuPage extends Pane implements MenuPage {
	
	protected final static double PADDING_HEIGHT = 100;
	protected final static double PADDING_WIDTH = 150;
	
	protected Pane root;
	protected Rectangle rectangleBackground;
		
	public AbstractMenuPage() {
		root = new Pane();
		root.setPrefSize(Screen.getPrimary().getBounds().getWidth()-PADDING_WIDTH*2, Screen.getPrimary().getBounds().getHeight()-PADDING_HEIGHT*2);
		
		rectangleBackground = new Rectangle(root.getPrefWidth()+PADDING_WIDTH,root.getPrefHeight()+PADDING_HEIGHT);
//		rectangleBackground.setFill(new Color(25d/255d,81d/255d,81d/255d,0.9));
		rectangleBackground.setFill(new Color(0d/255d,0d/255d,0d/255d,0.5));
		rectangleBackground.setStrokeWidth(5);
		rectangleBackground.setStroke(PageComponent.STROKE_COLOR);
		rectangleBackground.setArcWidth(20);
		rectangleBackground.setArcHeight(20);

		StackPane p = new StackPane(root);
		p.setPadding(new Insets(PADDING_HEIGHT-20,PADDING_WIDTH,PADDING_HEIGHT,PADDING_WIDTH));
		
		this.getChildren().addAll(new StackPane(rectangleBackground,p));		
	}
}
