package mapEditor;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
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
	
	private PolygonObject dragged;
	private boolean isInTheLimit;
	protected DrawingPanel drw;
	protected boolean insertPushed = false;
	protected boolean draggedPressed;
	
	public PanelForMap(MapEditor mapEditor, double width, double height) {
		
		this.mapEditor = mapEditor;
		this.realPane = new Pane();

		realPane.setPrefSize(width, mapEditor.getAltezza()-14);
		this.setPrefSize(width-2, mapEditor.getAltezza()-54);
		
	    this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
	    this.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
//	    this.setStyle("-fx-background: #6b5d5d; -fx-background-color: null; ");
	    this.getStylesheets().add("file:styles/customScrollBarForPanelMap.css");
	    
		this.setContent(realPane);
		
		realPane.setOnMousePressed(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) {
	        	PanelForMap.this.mapEditor.setUpper(event);
	        	
	        	dragged = PanelForMap.this.mapEditor.getDragged();
	        	
	        	draggedPressed = true;
	        	
	        	if (event.getClickCount() == 2) {
					if (realPane.getChildren().contains(drw))
						realPane.getChildren().remove(drw);
					
					drw = new DrawingPanel(dragged, PanelForMap.this.mapEditor);
        			realPane.getChildren().add(drw);
        			insertPushed = true;
    			}
        	}
	    });
		
		realPane.setOnMouseMoved(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
			}
		});
		
		realPane.setOnMouseDragged(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) { 
	        	if (!insertPushed)
        			PanelForMap.this.mapEditor.moveObjectInMap(event);
	        }
	    });

		realPane.setOnMouseReleased(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) { 
	        	if (!isInTheLimit)
	        		PanelForMap.this.mapEditor.addObjectInListImage();
	        	else
	        	{
	        		isInTheLimit = false;
	        		PanelForMap.this.mapEditor.changeCursor(Cursor.DEFAULT);
	        	}
	        }
	    });
	    
	    this.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.DELETE)) {
					PanelForMap.this.mapEditor.removeObject();
				}
				if (event.getCode().equals(KeyCode.ESCAPE)) {
					if (realPane.getChildren().contains(drw))
						realPane.getChildren().remove(drw);
					insertPushed = false;
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
	
	public void addObject(PolygonObject object) {
		realPane.getChildren().add(object);
	}
	
	public boolean containsObject(PolygonObject object) {
		return realPane.getChildren().contains(object);
	}

	public void removeObject(PolygonObject object) {
		realPane.getChildren().remove(object);
	}
	
	public void removeAllObject() {
		realPane.getChildren().removeAll(mapEditor.getObjectInMap());
	}
	
	public void setDimensionStandard(double width, double height) {
		this.widthStandard = width;
		this.heightStandard = height;
	}
	
	public void refreshDimension() {
		this.realPane.setPrefSize(widthStandard, heightStandard);
	}
	
	public void setWidthStandard() {
		this.realPane.setPrefWidth(widthStandard);
	}
	
	public void setHeightStandard() {
		this.realPane.setPrefHeight(heightStandard);
	}
	
	public boolean getDraggedPressed() {
		return draggedPressed;
	}

	public boolean getInsertPressed() {
		return insertPushed;
	}
	
	
}
