package menu.networkMenu;

import java.util.List;

import network.InfoMatch;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import menu.AbstractPageComponent;

public class MatchList extends AbstractPageComponent {

	private final static int position = 40;

	private List<InfoMatch> infoMatches;
	private VBox vbox;
	private Button buttonRefresh;

	public MatchList(NetworkPage networkPage) {
		super(networkPage);

		vbox = new VBox(10);
		// root.getChildren().add(new StackPane(vbox));
		vbox.relocate(5, 10);

		buttonRefresh = new Button();
		buttonRefresh.setStyle("-fx-background-color:transparent");
		buttonRefresh.setGraphic(new ImageView(new Image(
				"file:image/Network/refresh.png", 25, 25, false, false)));
		buttonRefresh.relocate(this.getWidthComponent() - position, -position);

		buttonRefresh.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getEventType() == event.MOUSE_ENTERED) {
					setCursor(Cursor.HAND);
				}
			}
		});
		
		buttonRefresh.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				if (event.getButton() == MouseButton.PRIMARY) {
					reset();
					networkPage.updateListMatch();
				}
			}
		});

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(getWidthComponent(), getHeightComponent() * 0.9);
		// scrollPane.setStyle("-fx-backgroud: transparent; -fx-background-color:transparent;");
		scrollPane.getStylesheets().add("file:styles/scrollBar.css");
		scrollPane.setContent(vbox);
		scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		root.getChildren().addAll(buttonRefresh, scrollPane);

	}

	public void setInfoMatchesList(List<InfoMatch> infoMatches) {
		this.infoMatches = infoMatches;

		vbox.getChildren().removeAll();

		for (InfoMatch infoMatch : infoMatches) {
			MatchListElement m = new MatchListElement(infoMatch);
			m.setOnMouseReleased(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					((NetworkPage) menuPage).getDetailMatch().insertInfo(
							infoMatch);
					((NetworkPage) menuPage).getDetailMatch().setVisible(true);
				}
			});
			vbox.getChildren().add(m);
		}
	}

	@Override
	public double getHeightComponent() {
		return 440;
	}

	@Override
	public double getWidthComponent() {
		return 400;
	}

	@Override
	public String getNameComponent() {
		return "MATCHES LIST";
	}

	@Override
	public void reset() {
		if (infoMatches != null)
			infoMatches.clear();
		vbox.getChildren().clear();
	}

	@Override
	public String[] getValues() {
		return null;
	}

}
