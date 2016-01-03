package menu.teamMenu;

import java.util.ArrayList;

import game.GameManager;
import gui.ImageLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import menu.AbstractPageComponent;

public class PlayerName extends AbstractPageComponent {

	private static final double TEXT_HEIGHT = 25;
//	private static final double SPACING_TEXT = 70;
	private static final int NUMBER_PLAYER = 5;
	private final static Color STROKE_COLOR = new Color(32d/255d,207d/255d,208d/255d,0.9);
	
//	private VBox textBoxPari;
//	private VBox textBoxDispari;

	private ImageLoader imageLoader;
	private ImageView headRight;
	private ImageView headLeft;
	
	private ArrayList<Pane> textArea;
	private ArrayList<String> playerName;
	
	double width = this.getWidthComponent() - 70;
	double height = this.getHeightComponent()/9;
	
	public PlayerName(TeamPage teamPage, ImageLoader imageLoader) {
		super(teamPage);
		
		this.imageLoader = imageLoader;
		
//		textBoxPari = new VBox(SPACING_TEXT);
//		textBoxDispari = new VBox(SPACING_TEXT*2);
		
		textArea = new ArrayList<Pane>();
		double posY = 10;
		
		for (int i = 0; i < NUMBER_PLAYER; i++) {
			
			Pane rootPane = new StackPane();
			
			Rectangle border = new Rectangle(width,height);
			border.setFill(Color.TRANSPARENT);
			border.setStroke(STROKE_COLOR);
			border.setStrokeWidth(2);
			border.setArcHeight(height);
			border.setArcWidth(20);
		
			Rectangle rectangle = new Rectangle(width-4,height-4);
			rectangle.setFill(null);
			rectangle.setArcHeight(height-6);
			rectangle.setArcWidth(25);
			
			Pane pane = new Pane();
			TextField textField = new TextField();
			textField.setPrefWidth(width-20);
			textField.setPrefHeight(height-20);
			
			textField.setStyle("-fx-background: null; -fx-background-color: null"); 
			textField.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, TEXT_HEIGHT*0.6));
			
			checkType();
			
			rootPane.getChildren().addAll(border,rectangle,pane);
			textField.setLayoutY(10);
			
			rootPane.setLayoutX(25);
			rootPane.setLayoutY(posY);
			
			if (i % 2 == 0) {
				textField.setLayoutX(40);
				pane.getChildren().addAll(headRight,textField);
//				textBoxPari.getChildren().add(root);
			}
			else {
				textField.setLayoutX(15);
				headLeft.setLayoutX(width-20);
				pane.getChildren().addAll(textField,headLeft);
//				textBoxDispari.getChildren().add(root);
			}
			
			textArea.add(pane);
			
			posY += height+20;
			
			root.getChildren().add(rootPane);
		}
		
//		textBoxPari.relocate(30, 7);
//		textBoxDispari.relocate(10, SPACING_TEXT);
//		root.getChildren().addAll(textBoxPari);
//		root.getChildren().addAll(textBoxDispari);
	}

	@Override
	public double getHeightComponent() {
		return 350;
	}

	@Override
	public double getWidthComponent() {
		return 550;
	}

	@Override
	public String getNameComponent() {
		return "PLAYERS NAME";
	}

	public void remove() {
		
		for (int i = 0; i < textArea.size(); i++) {
			checkType();
			
			if (i % 2 == 0) {
				textArea.get(i).getChildren().remove(0);
				textArea.get(i).getChildren().add(0, headRight);
			}
			else {
				headLeft.setLayoutX(width-20);
				textArea.get(i).getChildren().remove(1);
				textArea.get(i).getChildren().add(1, headLeft);
			}
		}
	}
	
	private void checkType() {
		if (((TeamPage) getMenuPage()).getMenuManager().getTeamType() == TeamType.WHITE) {
			headRight = new ImageView(imageLoader.getHeadWhite("right"));
			headLeft = new ImageView(imageLoader.getHeadWhite("left"));
		}
		else {
			headRight = new ImageView(imageLoader.getHeadBlack("right"));
			headLeft = new ImageView(imageLoader.getHeadBlack("left"));
		}
	}
	
	
	public ArrayList<String> getPlayerName() {
		playerName = new ArrayList<String>();
		
		for (int i = 0; i < textArea.size(); i++) {
			
			if (i % 2 == 0) {
				if (!((TextField) textArea.get(i).getChildren().get(1)).getText().equals(""))
					playerName.add(((TextField) textArea.get(i).getChildren().get(1)).getText());
			}
			else {
				if (!((TextField) textArea.get(i).getChildren().get(0)).getText().equals(""))
				playerName.add(((TextField) textArea.get(i).getChildren().get(0)).getText());
			}
		}
		
		return playerName;
	}
	
	public boolean isInsert() {
		getPlayerName();
		if (playerName.size() == 0)
			return false;
		return true;
	}
	
}
