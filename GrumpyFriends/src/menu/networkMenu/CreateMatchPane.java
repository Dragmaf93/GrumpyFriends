package menu.networkMenu;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import menu.AbstractPageComponent;
import menu.MenuButton;
import menu.MenuManager;
import menu.MenuPage;
import menu.PageComponent;
import menu.TextFieldMenu;

public class CreateMatchPane extends AbstractPageComponent {

	private final static double PADDING_WIDHT = 5;
	private final static double PADDING_HEIGHT = 15;
	private static final double TEXT_HEIGHT = 45;

	private final static String initValueComboBox = "1";

	private Font font = Font.font("Comic Sans MS", FontWeight.BOLD,
			TEXT_HEIGHT * 0.6);

	private double width = this.getWidthComponent() - 80;
	private double height = this.getHeightComponent() / 9;

	private TextFieldMenu matchNameTextField;
	private ComboBox<String> choiseNumberPlayer;
	private CheckBox check;
	private TextFieldMenu password;

	public CreateMatchPane(NetworkPage networkPage) {
		super(networkPage);

		Text matchName = new Text("Match Name");
		matchName.setFont(font);
		matchName.setFill(PageComponent.HEADER_TEXT_COLOR);

		matchNameTextField = new TextFieldMenu(width, height);
		VBox matchNamePane = new VBox(1, matchName, matchNameTextField);

		Text numberPlayer = new Text("Number Player");
		numberPlayer.setFont(font);
		numberPlayer.setFill(PageComponent.HEADER_TEXT_COLOR);

		choiseNumberPlayer = new ComboBox<String>(
				FXCollections.observableArrayList("1", "2", "3", "4", "5"));
		choiseNumberPlayer.setPrefWidth(width * 0.3);
		choiseNumberPlayer.setValue(initValueComboBox);
		// choiseNumberPlayer.setStyle("-fx-background-color: null");
		choiseNumberPlayer.setPrefHeight(numberPlayer.getLayoutBounds()
				.getHeight());
		choiseNumberPlayer.backgroundProperty().set(null);
		choiseNumberPlayer.borderProperty().set(
				new Border(new BorderStroke(PageComponent.STROKE_COLOR,
						BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
						new BorderWidths(2))));

		choiseNumberPlayer.getStylesheets().add("file:styles/comboBox.css");

		HBox numberPlayerPane = new HBox(138, numberPlayer, choiseNumberPlayer);

		Text privateCheck = new Text("Private");
		privateCheck.setFont(font);
		privateCheck.setFill(PageComponent.HEADER_TEXT_COLOR);

		
		check = new CheckBox();
		
		HBox checkPrivate = new HBox(360, privateCheck, check);
		
		VBox rootBox = new VBox(25, matchNamePane, numberPlayerPane,
				checkPrivate);
		
		Text passwordText = new Text("Password");
		passwordText.setFont(font);
		passwordText.setFill(PageComponent.HEADER_TEXT_COLOR);
		password = new TextFieldMenu(width, height);
		VBox pass = new VBox(1, passwordText, password);
		
		
		check.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> ov,
					Boolean old_val, Boolean new_val) {
				
				if (new_val == true)
					rootBox.getChildren().add(pass);
				else 
					rootBox.getChildren().remove(pass);
			}
		});



		// MenuButton createMatch = new MenuButton(this.getWidthComponent() / 3,
		// 50, "Create Match");
		// createMatch.relocate(this.getWidthComponent()
		// - createMatch.getLayoutBounds().getWidth(), 0);

		
		rootBox.relocate(this.getWidthComponent() / 2 - width / 2,
				PADDING_HEIGHT);

		root.getChildren().addAll(rootBox);

		// createMatch.setOnMouseReleased(new EventHandler<MouseEvent>() {
		//
		// @Override
		// public void handle(MouseEvent event) {
		// if (event.getButton() == MouseButton.PRIMARY) {
		// MenuManager.getInstance().setClientType(false);
		// networkPage.nextPage();
		// }
		// }
		//
		// });
	}

	@Override
	public double getHeightComponent() {
		return 450;
	}

	@Override
	public double getWidthComponent() {
		return 550;
	}

	@Override
	public String getNameComponent() {
		return "CREATE MATCH";
	}

	@Override
	public void reset() {
		matchNameTextField.resetTextField();
		choiseNumberPlayer.setValue(initValueComboBox);
		check.setSelected(false);
		if (password != null)
			password.resetTextField();
	}

	@Override
	public String[] getValues() {

		String[] values = new String[4];
		values[0] = matchNameTextField.getText();
		values[1] = choiseNumberPlayer.getValue();
		values[2] = Boolean.toString(check.isSelected());
		values[3] = password.getText();
		return values;
	}

	public boolean isAllInsert() {

		String[] value = getValues();

		if (!value[0].equals("") || value[0] == null) {
			if (value[2].equals("false"))
				return true;
			else if (value[2].equals("true"))
				if (!value[3].equals(""))
					return true;
			return false;
		}
		return false;
	}

}
