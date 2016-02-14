package gui.hud;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import game.AbstractMatchManager;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

public class MessageNextPlayer extends Pane {

	private static final String FONT_PATH = "font/clockFont.ttf";
	
	private Font fontTurnNext;

	public MessageNextPlayer(String text, String color, boolean addOpacity) {
		
		this.setPrefSize(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
		
		if (addOpacity)
			this.setStyle("-fx-background: #ece6e6; -fx-background-color: rgba(236,230,230, 0.3); ");
		
		Pane pane = new Pane();
		pane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight()/4);
		int colorInRgb[] = getRGB(color);		
		pane.setStyle("-fx-background: #" + color + "; -fx-background-color: rgba("+colorInRgb[0]+","+colorInRgb[1]+","+colorInRgb[2]+", 0.5); ");
		
		pane.setLayoutX(0);
		pane.setLayoutY(Screen.getPrimary().getBounds().getHeight()/3);
		
		Label nameCharacterNextTurn = new Label(text);
		try {
			fontTurnNext= Font.loadFont(new FileInputStream(FONT_PATH),40);
			nameCharacterNextTurn.setFont(fontTurnNext);
			nameCharacterNextTurn.setTextFill(Color.web(color));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		nameCharacterNextTurn.setLayoutX(pane.getPrefWidth()/2 - 100);
		nameCharacterNextTurn.setLayoutY(pane.getPrefHeight()/3);
		
		pane.getChildren().add(nameCharacterNextTurn);
		
		this.getChildren().add(pane);
	}
	
	private int[] getRGB(final String color)
	{
	    final int[] ret = new int[3];
	    for (int i = 0; i < 3; i++)
	    {
	        ret[i] = Integer.parseInt(color.substring(i * 2, i * 2 + 2), 16);
	    }
	    return ret;
	}
	
}
