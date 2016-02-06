package menu.teamMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import menu.AbstractPageComponent;
import menu.PageComponent;

public class PlayerName extends AbstractPageComponent {

	private static final double TEXT_HEIGHT = 25;
	private static final int NUMBER_PLAYER = 5;
	private final static Color STROKE_COLOR = PageComponent.STROKE_COLOR;

	private static HashMap<String, Image> heads;

	private final static String PATH_IMAGE_HEAD = "file:image/Character/";

	private List<ImageView> headsList;

	private List<Pane> textArea;
	private List<TextField> textFields;
	private List<String> playerName;

	double width = this.getWidthComponent() - 70;
	double height = this.getHeightComponent() / 9;

	public PlayerName(TeamPage teamPage) {
		super(teamPage);

		if (heads == null)
			heads = new HashMap<String, Image>();

		headsList = new ArrayList<ImageView>();
		textFields = new ArrayList<TextField>();
		playerName = new ArrayList<String>();

		textArea = new ArrayList<Pane>();
		double posY = 10;

		for (int i = 0; i < NUMBER_PLAYER; i++) {

			Pane rootPane = new StackPane();

			Rectangle border = new Rectangle(width, height);
			border.setFill(Color.TRANSPARENT);
			border.setStroke(STROKE_COLOR);
			border.setStrokeWidth(2);
			border.setArcHeight(height);
			border.setArcWidth(20);

			Rectangle rectangle = new Rectangle(width - 4, height - 4);
			rectangle.setFill(null);
			rectangle.setArcHeight(height - 6);
			rectangle.setArcWidth(25);

			Pane pane = new Pane();
			TextField textField = new TextField();
			textFields.add(textField);
			textField.setPrefWidth(width - 20);
			textField.setPrefHeight(height - 20);

			textField
					.setStyle("-fx-background: null; -fx-background-color: null; -fx-text-fill:white");
			textField.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,
					TEXT_HEIGHT * 0.6));
			rootPane.getChildren().addAll(border, rectangle, pane);
			textField.setLayoutY(10);

			rootPane.setLayoutX(25);
			rootPane.setLayoutY(posY);

			if (i % 2 == 0) {
				ImageView tmp = new ImageView();
				headsList.add(tmp);
				textField.setLayoutX(40);
				pane.getChildren().addAll(tmp, textField);
			} else {
				ImageView tmp = new ImageView();
				headsList.add(tmp);
				tmp.setRotationAxis(Rotate.Y_AXIS);
				tmp.setRotate(180);
				textField.setLayoutX(15);
				tmp.setLayoutX(width - 20);
				pane.getChildren().addAll(textField, tmp);
			}

			textArea.add(pane);

			posY += height + 20;

			root.getChildren().add(rootPane);
		}
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

	public void checkType(String typeCharacter) {

		if (!heads.containsKey(typeCharacter)) {
			heads.put(typeCharacter, new Image(PATH_IMAGE_HEAD + typeCharacter
					+ "/Head/head.png"));
		}

		for (ImageView imageView : headsList) {
			imageView.setImage(heads.get(typeCharacter));
		}
	}

	private List<String> getPlayerName() {

		for (int i = 0; i < textArea.size(); i++) {

			if (i % 2 == 0) {
				if (!((TextField) textArea.get(i).getChildren().get(1))
						.getText().equals(""))
					playerName.add(((TextField) textArea.get(i).getChildren()
							.get(1)).getText());
			} else {
				if (!((TextField) textArea.get(i).getChildren().get(0))
						.getText().equals(""))
					playerName.add(((TextField) textArea.get(i).getChildren()
							.get(0)).getText());
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

	@Override
	public String[] getValues() {
		List<String> strings = getPlayerName();
		String[] tmp = new String[strings.size()];
		for (int i = 0; i < strings.size(); i++) {
			tmp[i] = strings.get(i);
		}
		return tmp;
	}

	@Override
	public void reset() {
		for (TextField textField : textFields) {
			textField.setText("");
		}
			playerName.clear();
	}

}
