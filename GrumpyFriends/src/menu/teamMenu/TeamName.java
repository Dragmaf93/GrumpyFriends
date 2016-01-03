package menu.teamMenu;

import java.io.File;

import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import menu.AbstractPageComponent;

public class TeamName extends AbstractPageComponent {

	private static final double TEXT_HEIGHT = 45;
	
	TextField textField;
	
	public TeamName(TeamPage teamPage) {
		super(teamPage);
	
		textField = new TextField();

		textField.setPrefHeight(this.getHeightComponent()/2 - 5);
		textField.setPrefWidth(this.getWidthComponent()- 10);
		
		textField.setStyle("-fx-background: null; -fx-background-color: null"); 
		textField.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, TEXT_HEIGHT*0.6));
				
		root.getChildren().addAll(textField);
		textField.relocate(0, 5);
		
	}

	public String getName() {
		return textField.getText();
	}
	
	public boolean isInsert() {
		if (textField.getText().equals(""))
			return false;
		return true;
	}
	
	@Override
	public double getHeightComponent() {
		return 100;
	}

	@Override
	public double getWidthComponent() {
		return 400;
	}

	@Override
	public String getNameComponent() {
		return "TEAM NAME";
	}

}
