package menu.networkMenu;

import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import menu.AbstractPageComponent;

public class UserName extends AbstractPageComponent {

	private static final double TEXT_HEIGHT = 45;
	
	private TextField textField;
	
	public UserName(NetworkPage networkPage) {
		super(networkPage);
				
		textField = new TextField();

		textField.setPrefHeight(this.getHeightComponent()/2 - 5);
		textField.setPrefWidth(this.getWidthComponent()- 10);
		
		textField.setStyle("-fx-background: null; -fx-background-color: null; -fx-text-fill:white"); 
		textField.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, TEXT_HEIGHT*0.6));
				
		root.getChildren().addAll(new StackPane(textField));
//		textField.relocate(0, 5);
		
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
		return "USER NAME";
	}

	@Override
	public String[] getValues() {
		String[] tmp = {textField.getText()} ;
		return tmp;
	}

	@Override
	public void reset() {
		textField.setText("");
	}

}
