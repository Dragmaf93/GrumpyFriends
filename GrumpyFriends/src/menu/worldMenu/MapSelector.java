package menu.worldMenu;

import java.io.File;

import menu.AbstractPageComponent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MapSelector extends AbstractPageComponent{
	
	private final static double HEIGHT_BUTTON=40;
	
	private String mapSelected;
	private Pane vBox;
	
	public MapSelector(WorldPage worldPage) {
		super(worldPage);
		
		vBox= new VBox();
		
		//Load file map
		
		File dir = new File("worldXML/");
	    String[] files = dir.list();
	    for (String file : files) {
			
	    	vBox.getChildren().add(new MapSelectorButton(root.getLayoutBounds().getWidth()-5, HEIGHT_BUTTON, 
	    			file.substring(0, file.length()-4)));
		}
	    
		root.getChildren().addAll(vBox);
		vBox.relocate(0, 2.6);
	}
	
	public String getMapSelected(){
		if(MapSelectorButton.lastCliccked!=null){
			return MapSelectorButton.lastCliccked.mapSelected();
		}
		
		return null;
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

}
