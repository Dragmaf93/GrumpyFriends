package gui;

import sun.swing.BakedArrayList;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;

public class Popup extends Group{

	private Pane root;
	
	private Pane window;
	
	private Rectangle windowRectangle;
	private Pane windowBackGround;
	
	private Text questionText;
	
	private PopupButton rightButton;
	private PopupButton leftButton;
	
	private Color color = new Color(32d/255d,207d/255d,208d/255d,1.0);
	
	public Popup(double width, double height,String questionString,String buttonLeftText,String buttonRightText) {
		
		windowRectangle = new Rectangle(width, height);
		windowRectangle.setFill(null);
		windowRectangle.setArcHeight(20);
		windowRectangle.setArcWidth(20);
		windowRectangle.setStroke(color);
		windowRectangle.setStrokeWidth(4);
		
		questionText = new Text(questionString);

		window = new StackPane();
		
		Line vertical = new Line(windowRectangle.getLayoutX(),
				windowRectangle.getLayoutY()+windowRectangle.getHeight()*2/3,
				windowRectangle.getLayoutX()+windowRectangle.getWidth(),
				windowRectangle.getLayoutY()+windowRectangle.getHeight()*2/3);
		vertical.setStroke(color);
		vertical.setStrokeWidth(4);
		Line horizontal = new Line(windowRectangle.getLayoutX()+windowRectangle.getWidth()/2,
				windowRectangle.getLayoutY()+windowRectangle.getHeight()*2/3+0.5,
				windowRectangle.getLayoutX()+windowRectangle.getWidth()/2,
				windowRectangle.getLayoutY()+windowRectangle.getHeight());
		horizontal.setStroke(color);
		horizontal.setStrokeWidth(4);
		
		rightButton = new PopupButton(width*0.5-3, height/3-2,buttonRightText);
		leftButton = new PopupButton(width*0.5-3, height/3-2,buttonLeftText);
		
		questionText.setFill(Color.WHITE);
		questionText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,windowRectangle.getHeight()/6));
		
		windowBackGround = new Pane(rightButton,leftButton,windowRectangle,vertical,horizontal,questionText);
		
		rightButton.relocate(windowRectangle.getLayoutX()+windowRectangle.getWidth()/2+2, windowRectangle.getLayoutY()+windowRectangle.getHeight()*2/3+0.5);
		leftButton.relocate(windowRectangle.getLayoutX()+2, windowRectangle.getLayoutY()+windowRectangle.getHeight()*2/3+0.5);
		questionText.relocate(windowRectangle.getWidth()/2-questionText.getLayoutBounds().getWidth()/2,
				windowRectangle.getHeight()/3-questionText.getLayoutBounds().getHeight()/2);
		
		window.getChildren().add(windowBackGround);
		
		root = new Pane();
		root.setPrefWidth(Screen.getPrimary().getBounds()
				.getWidth());
		root.setPrefHeight(Screen.getPrimary().getBounds().getHeight());
		
//		root.setStyle("-fx-background: #ece6e6; -fx-background-color: rgba(25,81,81,0.6);");
		root.getChildren().addAll(window);
		window.relocate(root.getPrefWidth()/2-windowRectangle.getWidth()/2, root.getPrefHeight()/2-windowRectangle.getHeight()/2);
		this.getChildren().add(root);
		
	}
	
	public Node getLeftButton(){
		return leftButton;
	}
	
	public Node getRightButton(){
		return rightButton;
	}
	
}
