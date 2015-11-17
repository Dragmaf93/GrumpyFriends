package mapEditor;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.QuadCurve;

public class PanelForMap extends ScrollPane {
	
	private MapEditor mapEditor;
	private Pane realPane;
	private double widthStandard;
	private double heightStandard;
	
	private PolygonObject dragged;
	private DrawingPanel drawingObject;
	private DrawingPanelForCurve drawingCurve;
	private boolean insertPushed = false;
	private boolean draggedPressed;
	private boolean movedObject;
	private Curve draggedCurve;
	protected boolean vertexModify;
	
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
	        	
	        	if (PanelForMap.this.mapEditor.isDragged())
	        		dragged = PanelForMap.this.mapEditor.getDragged();
	        	else
	        		draggedCurve = PanelForMap.this.mapEditor.getDraggedCurve();
	        	
	        	draggedPressed = true;
	        	movedObject = false;
	        	
	        	if (event.getClickCount() == 2) {
					if (realPane.getChildren().contains(drawingObject))
						realPane.getChildren().remove(drawingObject);
					
					if (PanelForMap.this.mapEditor.isDragged() && PanelForMap.this.mapEditor.getDragged() != null)
					{
						drawingObject = new DrawingPanel(dragged, PanelForMap.this.mapEditor);
						realPane.getChildren().add(drawingObject);
					}
					else
					{
						drawingCurve = new DrawingPanelForCurve(draggedCurve, PanelForMap.this.mapEditor);
						realPane.getChildren().add(drawingCurve);
					}
					insertPushed = true;
					vertexModify = false;
    			}
        	}
	    });
		
		realPane.setOnMouseDragged(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) { 
	        	if (!insertPushed)
	        	{
	        		PanelForMap.this.mapEditor.moveObjectInMap(event);
	        		movedObject = true;
	        		vertexModify = false;
	        	}
	        }
	    });

		realPane.setOnMouseReleased(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) { 
	        	if (event.getButton().equals(MouseButton.SECONDARY))
	        	{
	        		PanelForMap.this.mapEditor.getDragged().addVertex(new Point2D(event.getX(), event.getY()));
	        		dragged = PanelForMap.this.mapEditor.getDragged();
	        		removePanelInsert();
	        		
	        		drawingObject = new DrawingPanel(dragged, PanelForMap.this.mapEditor);
        			realPane.getChildren().add(drawingObject);
	        		insertPushed = true;
	        		movedObject = false;
	        		vertexModify = true;
	        	}
	        	if (movedObject)
	        		PanelForMap.this.mapEditor.addObjectInListImage();
	        }
	    });
	    
	    this.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.DELETE)) {
					PanelForMap.this.mapEditor.removeObject();
				}
				if (event.getCode().equals(KeyCode.ESCAPE)) {
					removePanelInsert();
					if (!vertexModify)
						PanelForMap.this.mapEditor.setDraggedCurve(PanelForMap.this.mapEditor.getDraggedCurve());
					else
						PanelForMap.this.mapEditor.setDragged(PanelForMap.this.mapEditor.getDragged());
					PanelForMap.this.mapEditor.addObjectInListImage();
				}
			}
		});
	}
	
	public void removePanelInsert() {
		if (realPane.getChildren().contains(drawingObject))
			realPane.getChildren().remove(drawingObject);
		if (realPane.getChildren().contains(drawingCurve))
			realPane.getChildren().remove(drawingCurve);
		insertPushed = false;		
	}

	public void setWidhtScrollPane(double width) {
		this.setPrefWidth(width);
	}
	
	public Pane getRealPane() {
		return realPane;
	}
	
	public void addObject(PolygonObject object) {
		if (!realPane.getChildren().contains(object))
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
		realPane.getChildren().removeAll(mapEditor.getCurveInMap());
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

	public void addObject(Curve curve) {
		if (!realPane.getChildren().contains(curve))
			realPane.getChildren().add(curve);
	}

	public boolean containsCurve(Curve draggedCurve) {
		return realPane.getChildren().contains(draggedCurve);
	}

	public void removeCurve(Curve draggedCurve) {
		realPane.getChildren().remove(draggedCurve);
	}
	
	
}
