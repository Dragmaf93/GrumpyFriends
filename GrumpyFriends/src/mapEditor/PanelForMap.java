package mapEditor;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PanelForMap extends ScrollPane {
	
	private MapEditor mapEditor;
	private Pane realPane;
	private double widthStandard;
	private double heightStandard;
	
	private ArrayList<ImageForObject> imageInseredInMap;
	
	public PanelForMap(MapEditor mapEditor, double width, double height) {
		
		this.mapEditor = mapEditor;
		this.realPane = new Pane();
		this.imageInseredInMap = new ArrayList<ImageForObject>();
		
		realPane.setPrefSize(width, mapEditor.getAltezza()-14);
		this.setPrefSize(width, mapEditor.getAltezza()-54);
		
	    this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
	    this.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
	    this.setStyle("-fx-background: #6b5d5d; -fx-background-color: null; ");
        
		this.setContent(realPane);
		
		realPane.setOnMousePressed(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) {
	        	PanelForMap.this.mapEditor.setUpper(event);
	        }
	    });
		
		realPane.setOnMouseDragged(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) { 
	        	PanelForMap.this.mapEditor.moveObjectInMap(event);
	        }
	    });
	    
	    realPane.setOnMouseReleased(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) { 
	        	PanelForMap.this.mapEditor.addObjectInListImage();
	        }
	    });
	    
	    this.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.DELETE)) {
					PanelForMap.this.mapEditor.removeObject();
				}
			}
		});
	}
	
	public void setWidhtScrollPane(double width) {
		this.setPrefWidth(width);
	}
	
	public Pane getRealPane() {
		return realPane;
	}
	
	public void addObject(ImageForObject object) {
		imageInseredInMap.add(object);
		realPane.getChildren().add(object);
	}
	
	public boolean containsObject(ImageForObject object) {
		return realPane.getChildren().contains(object);
	}

	public void removeObject(ImageForObject object) {
		realPane.getChildren().remove(object);
	}
	
	public void removeAllObject() {
		realPane.getChildren().removeAll(imageInseredInMap);
	}
	
	public void setWidthStandard(double width, double height) {
		this.widthStandard = width;
		this.heightStandard = height;
	}
	
	public void refreshDimension() {
		this.realPane.setPrefSize(widthStandard, heightStandard);
	}
}
