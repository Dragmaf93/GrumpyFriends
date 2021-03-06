package menu.worldMenu;

import java.io.File;

import menu.AbstractPageComponent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MapSelector extends AbstractPageComponent {

	private final static double HEIGHT_BUTTON = 40;

	private String mapSelected;
	private Pane vBox;

	public MapSelector(WorldPage worldPage) {
		super(worldPage);

		vBox = new VBox();

		File dir = new File("worldXML/");
		String[] files = dir.list();
		for (String file : files) {

			vBox.getChildren()
					.add(new MapSelectorButton(root.getLayoutBounds()
							.getWidth() - 5, HEIGHT_BUTTON, file.substring(0,
							file.length() - 4)));
		}

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(getWidthComponent(), getHeightComponent()*0.8);
		scrollPane.getStylesheets().add("file:styles/scrollBar.css");
		scrollPane.setContent(vBox);
		scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		root.getChildren().addAll(scrollPane);
		vBox.relocate(0, 2.6);
	}

	public String getMapSelected() {
		if (MapSelectorButton.lastCliccked != null) {
			return MapSelectorButton.lastCliccked.mapSelected();
		}

		return null;
	}

	@Override
	public void reset() {
		if (MapSelectorButton.lastCliccked != null)
			MapSelectorButton.lastCliccked.reset();

	}

	@Override
	public double getHeightComponent() {
		return 250;
	}

	@Override
	public double getWidthComponent() {
		return 450;
	}

	@Override
	public String getNameComponent() {
		return "MAP";
	}

	@Override
	public String[] getValues() {
		String[] tmp = { getMapSelected() };
		return tmp;
	}

}
