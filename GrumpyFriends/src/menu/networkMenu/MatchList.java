package menu.networkMenu;

import java.util.List;

import network.InfoMatch;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import menu.AbstractPageComponent;

public class MatchList extends AbstractPageComponent {

	private List<InfoMatch> infoMatches;
	private VBox vbox; 
	
	public MatchList(NetworkPage networkPage) {
		super(networkPage);
		
		vbox = new VBox(10);
//		root.getChildren().add(new StackPane(vbox));
		vbox.relocate(5, 10);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(getWidthComponent(), getHeightComponent()*0.9);
//		scrollPane.setStyle("-fx-backgroud: transparent; -fx-background-color:transparent;");
		scrollPane.getStylesheets().add("file:styles/scrollBar.css");
		scrollPane.setContent(vbox);
		scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		root.getChildren().add(scrollPane);
	
	}

	public void setInfoMatchesList(List<InfoMatch> infoMatches){
		this.infoMatches=infoMatches;
		
		vbox.getChildren().removeAll();
		
		for (InfoMatch infoMatch : infoMatches) {
			MatchListElement m =new MatchListElement(infoMatch);
			m.setOnMouseReleased(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					((NetworkPage) menuPage).getDetailMatch().insertInfo(infoMatch);
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
		infoMatches.clear();
		vbox.getChildren().clear();
	}

	@Override
	public String[] getValues() {
		return null;
	}

}
