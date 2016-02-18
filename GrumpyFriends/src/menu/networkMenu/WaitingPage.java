package menu.networkMenu;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.util.Duration;
import menu.AbstractMenuPage;
import menu.GameBean;
import menu.PageComponent;

public class WaitingPage extends AbstractMenuPage {

	private Text text;
	private Circle circle;
	
	public WaitingPage() {
		super();
		
		text = new Text();
		text.setFill(PageComponent.HEADER_TEXT_COLOR);
		text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 30));
	
		circle = new Circle(5, PageComponent.STROKE_COLOR);
		StackPane mainPane = new StackPane(text);
		mainPane.setPrefSize(root.getPrefWidth(),root.getPrefHeight());
		root.getChildren().addAll(mainPane);
	}
	
	public void setText(String text){
		this.text.setText(text);
	}
	@Override
	public void nextPage() {
		
	}

	@Override
	public void backPage() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public GameBean getGameBean() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {

	}

}
