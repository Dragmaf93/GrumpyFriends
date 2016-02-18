package mapEditor;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PanelForMap extends ScrollPane {
	
	private MapEditor mapEditor;
	private Pane realPane;
	private double widthStandard;
	private double heightStandard;
	
	private SquarePolygon dragged;
	private DrawingPanel drawingObject;
	private boolean insertPushed = false;
	private boolean draggedPressed;
	private boolean movedObject;
//	private boolean vertexModify;
	
	private double valueScroll = 0;
	protected boolean up;
	protected boolean moved = false;
	
	public PanelForMap(MapEditor mapEditor, double width, double height) {
		
		this.mapEditor = mapEditor;
		this.realPane = new Pane();

		realPane.setPrefSize(width, mapEditor.getHeightScreen()-14);
		this.setPrefSize(width-2, mapEditor.getHeightScreen()-54);
		
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
	        	movedObject = false;
	        	
	        	if (event.getClickCount() == 2) {
					removePanelInsert();
					
					if (PanelForMap.this.mapEditor.getDragged() != null) {
						if (((Node) PanelForMap.this.mapEditor.getDragged()).contains(new Point2D(event.getX(), event.getY()))) {
							drawingObject = new DrawingPanel(dragged, PanelForMap.this.mapEditor);
							realPane.getChildren().add(drawingObject);
							insertPushed = true;
						}
						else {
							if (drawingObject.getIsChange())
								PanelForMap.this.mapEditor.addObjectInListImage();
							removePanelInsert();
							PanelForMap.this.mapEditor.setDragged(PanelForMap.this.mapEditor.getDragged());
						}
					}
//					vertexModify = false;
    			}
        	}
	    });
		
		realPane.setOnMouseDragged(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) { 
	        	if (!insertPushed) {
	        		PanelForMap.this.mapEditor.moveObjectInMap(event);
	        		movedObject = true;
//	        		vertexModify = false;
	        	}
	        }
	    });

		realPane.setOnMouseReleased(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) { 
	        	if (event.getButton().equals(MouseButton.SECONDARY)) {
	        		PanelForMap.this.mapEditor.getDragged().addVertex(new Point2D(event.getX(), event.getY()));
	        		PanelForMap.this.mapEditor.setDragged(PanelForMap.this.mapEditor.getDragged());
	        		dragged = PanelForMap.this.mapEditor.getDragged();
	        		removePanelInsert();
	        		
	        		drawingObject = new DrawingPanel(dragged, PanelForMap.this.mapEditor);
        			realPane.getChildren().add(drawingObject);
	        		insertPushed = true;
	        		movedObject = false;
//	        		vertexModify = true;
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
					removePanelInsert();
				}
				if (event.getCode().equals(KeyCode.ESCAPE)) {
					if (drawingObject.getIsChange())
						PanelForMap.this.mapEditor.addObjectInListImage();
					removePanelInsert();
					PanelForMap.this.mapEditor.setDragged(PanelForMap.this.mapEditor.getDragged());
				}
			}
		});
	}
	
	public boolean isUp() {
		return up;
	}
	
	public double getValueScroll() {
		return valueScroll;
	}
	
	public void removePanelInsert() {
		if (realPane.getChildren().contains(drawingObject))
			realPane.getChildren().remove(drawingObject);
		insertPushed = false;		
	}

	public void setWidhtScrollPane(double width) {
		this.setPrefWidth(width);
	}
	
	public Pane getRealPane() {
		return realPane;
	}
	
	public void addObject(SquarePolygon object) {
		if (!realPane.getChildren().contains(object))
			realPane.getChildren().add((Node) object);
	}
	
	public boolean containsObject(SquarePolygon object) {
		return realPane.getChildren().contains(object);
	}

	public void removeObject(SquarePolygon object) {
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

	public void addCurve(Curve curve) {
		if (!realPane.getChildren().contains(curve))
			realPane.getChildren().add(curve);
	}

	public boolean containsCurve(Curve draggedCurve) {
		return realPane.getChildren().contains(draggedCurve);
	}

	public void removeCurve(Curve draggedCurve) {
		realPane.getChildren().remove(draggedCurve);
	}

	public boolean containsPoints(Point2D point) {
		return (point.getX() > this.realPane.getLayoutX() && point.getY() > this.realPane.getLayoutY()
				&& point.getX() < this.realPane.getPrefWidth() && point.getY() < this.realPane.getPrefHeight());
	}
	
}
