package gui.hud;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import game.MatchManager;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

public class PanelForNextTurn extends Pane {

	private static final String FONT_PATH = "font/clockFont.ttf";
	private static final String colorFont = "#0a5fbf";
	
	
	private Font fontTurnNext;

	public PanelForNextTurn(MatchManager matchManager) {
		
		this.setPrefSize(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight()/4);
		this.setStyle("-fx-background: #bcb5b5; -fx-background-color: rgba(0, 100, 100, 0.5); ");
		
		this.setLayoutX(0);
		this.setLayoutY(Screen.getPrimary().getBounds().getHeight()/3);
		
		Label nameCharacterNextTurn = new Label(matchManager.getCurrentPlayer().getName());
		try {
			fontTurnNext= Font.loadFont(new FileInputStream(FONT_PATH),40);
			nameCharacterNextTurn.setFont(fontTurnNext);
			nameCharacterNextTurn.setTextFill(Color.web(colorFont));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		nameCharacterNextTurn.setLayoutX(this.getPrefWidth()/2 - 100);
		nameCharacterNextTurn.setLayoutY(this.getPrefHeight()/3);
		
		this.getChildren().add(nameCharacterNextTurn);
	}
	
}
