package menu;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TextFieldMenu extends Pane {

	public TextFieldMenu(double width, double height) {
		Pane rootPane = new StackPane();

		Rectangle border = new Rectangle(width, height);
		border.setFill(Color.TRANSPARENT);
		border.setStroke(PageComponent.STROKE_COLOR);
		border.setStrokeWidth(2);
		border.setArcHeight(20);
		border.setArcWidth(20);

		Rectangle rectangle = new Rectangle(width - 4, height - 4);
		rectangle.setFill(null);
		rectangle.setArcHeight(20);
		rectangle.setArcWidth(20);

		TextField textField = new TextField();
		textField.setPrefWidth(width - 20);
		textField.setPrefHeight(height - 20);

		textField.setStyle("-fx-background: null; -fx-background-color: null; -fx-text-fill:white");
		textField.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, height * 0.4));
		
		rootPane.getChildren().addAll(border, rectangle, textField);
		
		this.getChildren().add(rootPane);
		
	}
	
}
