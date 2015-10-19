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
	
	private ImageForObject dragged;
	private boolean isInTheLimit;
	private boolean moveUpperLeftPositionX;
	private boolean moveUpperRightPosition;
	private boolean moveUpperLeftPositionY;
	private boolean moveBottomLeftPosition;
	
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
	        	if (dragged != null)
	        	{
	        		if (dragged.isInTheLimit(event))
	        		{
	        			isInTheLimit = true;
	        			
	        			if ((int)event.getX() == (int)dragged.getUpperLeftPosition().getX())
	        				moveUpperLeftPositionX = true;
	        			else if ((int)event.getX() == (int)dragged.getUpperRightPosition().getX())
	        				moveUpperRightPosition = true;
	        			else if ((int)event.getY() == (int)dragged.getUpperLeftPosition().getY())
	        				moveUpperLeftPositionY = true;
	        			else if ((int)event.getY() == (int)dragged.getBottomLeftPosition().getY())
	        				moveBottomLeftPosition = true;
	        		}
	        	
//		        	System.out.println(event.getX()+" "+event.getY());
//		        	System.out.println(dragged);
	        	}
        	}
	    });
		
		realPane.setOnMouseDragged(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) { 
        		if (moveUpperLeftPositionX)
        		{
        			dragged.setWidth(dragged.getWidth()+(dragged.getUpperLeftPosition().getX()-event.getX()));
        			dragged.setX(event.getX());
        			dragged.modifyPosition(new Point2D(dragged.getX(), dragged.getY()), dragged.getWidth(), dragged.getHeight());
        			dragged.relocate(dragged.getX(), dragged.getY());
        			PanelForMap.this.mapEditor.changeCursor(Cursor.H_RESIZE);
        		}
        		else if (moveUpperRightPosition)
				{
        			dragged.setWidth(dragged.getWidth()+(event.getX()-dragged.getUpperRightPosition().getX()));
        			dragged.modifyPosition(new Point2D(dragged.getX(), dragged.getY()), dragged.getWidth(), dragged.getHeight());
        			PanelForMap.this.mapEditor.changeCursor(Cursor.H_RESIZE);
				}
        		else if (moveBottomLeftPosition)
        		{
        			dragged.setHeight(dragged.getHeight()+(event.getY()-dragged.getBottomLeftPosition().getY()));
        			dragged.modifyPosition(new Point2D(dragged.getX(), dragged.getY()), dragged.getWidth(), dragged.getHeight());
        			PanelForMap.this.mapEditor.changeCursor(Cursor.V_RESIZE);
        		}
        		else if (moveUpperLeftPositionY)
        		{
        			dragged.setHeight(dragged.getHeight()+(dragged.getUpperLeftPosition().getY()-event.getY()));
        			dragged.setY(event.getY());
        			dragged.modifyPosition(new Point2D(dragged.getX(),dragged.getY()), dragged.getWidth(), dragged.getHeight());
        			dragged.relocate(dragged.getX(), dragged.getY());
        			PanelForMap.this.mapEditor.changeCursor(Cursor.V_RESIZE);
    			}
        		else
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
	        		moveUpperLeftPositionX = false;
	        		moveUpperRightPosition = false;
	        		moveUpperLeftPositionY = false;
	        		moveBottomLeftPosition = false;
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
		realPane.getChildren().add(object);
	}
	
	public boolean containsObject(ImageForObject object) {
		return realPane.getChildren().contains(object);
	}

	public void removeObject(ImageForObject object) {
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
}
