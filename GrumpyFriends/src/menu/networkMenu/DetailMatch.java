package menu.networkMenu;

import network.InfoMatch;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import menu.MenuButton;
import menu.PageComponent;
import menu.TextFieldMenu;

public class DetailMatch extends Pane {

	protected final static double PADDING_HEIGHT = 100;
	protected final static double PADDING_WIDTH = 150;
	
	private static final double TEXT_HEIGHT = 45;
	private static Font font = Font.font("Comic Sans MS", FontWeight.BOLD,
			TEXT_HEIGHT * 0.7);
	private static Font fontValue = Font.font("Comic Sans MS", FontWeight.NORMAL,
			TEXT_HEIGHT * 0.7);
	
	private InfoMatch infoMatch;
	
	protected Pane root;
	protected Rectangle rectangleBackground;
	private VBox realValue;
	private VBox label;
	
	public DetailMatch(InfoMatch infoMatch) {
		
		this.infoMatch = infoMatch;
			
		root = new Pane();
		root.setPrefSize(Screen.getPrimary().getBounds().getWidth()-PADDING_WIDTH*5, Screen.getPrimary().getBounds().getHeight()-PADDING_HEIGHT*4.5);
		
		rectangleBackground = new Rectangle(root.getPrefWidth()+PADDING_WIDTH,root.getPrefHeight()+PADDING_HEIGHT);
		rectangleBackground.setFill(new Color(0d/255d,0d/255d,0d/255d,0.5));
		rectangleBackground.setStrokeWidth(5);
		rectangleBackground.setStroke(PageComponent.STROKE_COLOR);
		rectangleBackground.setArcWidth(20);
		rectangleBackground.setArcHeight(20);

		StackPane p = new StackPane(root);
		p.setPadding(new Insets(PADDING_HEIGHT-20,PADDING_WIDTH,PADDING_HEIGHT,PADDING_WIDTH));
		
		this.getChildren().addAll(new StackPane(rectangleBackground,p));
		
		
		insertInfo();
	}
	
	
	public void insertInfo() {
		
		Text nameMatch = new Text("Match Name:");
		setFontAndColor(nameMatch);
		Text name = new Text(infoMatch.getMatchName());
		setFontAndColorValue(name);
		
		Text nameUserText = new Text("Username:");
		setFontAndColor(nameUserText);
		Text nameUser = new Text(infoMatch.getUserName());
		setFontAndColorValue(nameUser);
		
		Text numberPlayerText = new Text("Number Character:");
		setFontAndColor(numberPlayerText);
		Text numberPlayer = new Text(Integer.toString(infoMatch.getCharacterTeamNumber()));
		setFontAndColorValue(numberPlayer);
		
		Text worldTypeText = new Text("World Type:");
		setFontAndColor(worldTypeText);
		Text worldType = new Text(infoMatch.getWorldType());
		setFontAndColorValue(worldType);

		Text statusText = new Text("Status:");
		setFontAndColor(statusText);
		Text status = new Text(infoMatch.getStatusMatch().toString());
		setFontAndColorValue(status);
		
		Text securityTypeText = new Text("Security:");
		setFontAndColor(securityTypeText);
		Text securityType = new Text("Public");
		setFontAndColorValue(securityType);

		label = new VBox(4, nameMatch, nameUserText, numberPlayerText, worldTypeText, statusText, 
				securityTypeText);
		realValue = new VBox(4, name, nameUser, numberPlayer, worldType, status, securityType);
		
		boolean isPrivate = infoMatch.isPrivateMatch();
		if (isPrivate) {
			securityType.setText("Private");
			Text pass = new Text("Password");
			setFontAndColor(pass);
			
			root.setPrefHeight(Screen.getPrimary().getBounds().getHeight()-PADDING_HEIGHT*3.6);
			rectangleBackground.setHeight(root.getPrefHeight()+PADDING_HEIGHT);
			TextFieldMenu password = new TextFieldMenu(root.getPrefWidth()*0.4, root.getPrefHeight()*0.1);
			label.getChildren().addAll(pass,password);
		}

		
		MenuButton backButton = new MenuButton(130, 40, "Back");
		MenuButton connectButton = new MenuButton(130, 40, "Connect");
		
		HBox valueBox = new HBox(130, label, realValue);
		HBox buttonBox = new HBox(20, backButton, connectButton);
		buttonBox.relocate(root.getPrefWidth()/2-buttonBox.getLayoutBounds().getWidth()/2, 
				root.getPrefHeight()-buttonBox.getLayoutBounds().getHeight());
		
		root.getChildren().addAll(valueBox,buttonBox);
		
	}
	
	
	private void setFontAndColor(Text text) {
		text.setFont(font);
		text.setFill(PageComponent.HEADER_TEXT_COLOR);
	}
	private void setFontAndColorValue(Text text) {
		text.setFont(fontValue);
		text.setFill(PageComponent.HEADER_TEXT_COLOR);
	}
	
}
