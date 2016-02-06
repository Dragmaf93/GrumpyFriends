package mapEditor;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import utils.ConverterMapToXml;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class MapEditor extends Pane {

	private Pane firstPane;
	private Pane panelForObject;
	private ScrollPane panelForMap;
	
	private SquarePolygon dragged;
	
	private double widthScreen = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private double heightScreen = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	private SquarePolygon image;
	
	private LoaderImage loaderImage;
	private boolean objectToMove;
	private boolean isInTheMap;
	
	private ArrayList<SquarePolygon> objectInMap;
	private ArrayList<SquarePolygon> objectToSelect;
	private ArrayList<Pair<Point2D, SquarePolygon>> objectMoveInMapForUndo;
//	private ArrayList<Pair<Point2D, Shape>> objectMoveInMapForRedo;
	private ArrayList<Pair<Point2D, SquarePolygon>> objectToCancelled;
	private DropShadow borderGlow;
	
	private double mouseX;
	private double mouseY;

	private ConverterMapToXml converter;
	
	private ObjectMapEditor objectMapEditor;
	private ObjectForUndoRedo objectForUndoRedo;
	
	private Curve quadCurve;
	
	public MapEditor() {
		objectInMap = new ArrayList<SquarePolygon>();
		objectToSelect = new ArrayList<SquarePolygon>();
		objectMoveInMapForUndo = new ArrayList<Pair<Point2D, SquarePolygon>>();
//		objectMoveInMapForRedo = new ArrayList<Pair<Point2D, Shape>>();
		objectToCancelled = new ArrayList<Pair<Point2D, SquarePolygon>>();
		
		firstPane = new Pane();

		panelForObject = new PanelForObject(this);
        panelForMap = new PanelForMap(this, widthScreen-panelForObject.getPrefWidth(),panelForObject.getPrefWidth());
        ((PanelForMap) panelForMap).setDimensionStandard(widthScreen-panelForObject.getPrefWidth(),panelForObject.getPrefHeight());
        
		drawAll();
		
		firstPane.getChildren().add(panelForMap);
		firstPane.getChildren().add(panelForObject);
		
		borderGlow = new DropShadow();
		borderGlow.setOffsetX(0f);
		borderGlow.setOffsetY(0f);
		borderGlow.setColor(Color.rgb(15, 71, 207));
		borderGlow.setWidth(30f);
		borderGlow.setHeight(30f);
		
		objectMapEditor = new ObjectMapEditor(this, panelForObject, panelForMap, objectInMap);
		objectForUndoRedo = new ObjectForUndoRedo(this, objectMoveInMapForUndo,
					objectInMap, objectToCancelled);
		
		isInTheMap = false;
		firstPane.setStyle("-fx-background-color: #858484;");
		
		BorderPane root = new BorderPane();
        root.setCenter(firstPane);
        
        
        this.getChildren().add(root);
	}
	
	public void setUpperAndCopyImage(MouseEvent event) {
		for (SquarePolygon image : objectInMap) 
			((Node) image).setEffect(null);
		objectMapEditor.selectObject(objectToSelect, event.getX(), event.getY(),
				borderGlow);
		isInTheMap = false;
	}
	
	public void moveObjectFromPanelObjectInMap(MouseEvent event) {
		if (!isInTheMap) {
			objectMapEditor.moveObjectInPanelObject(dragged, event.getX(), event.getY());
			isInTheMap = false;
		}
		
		if (((dragged != null && event.getX()-dragged.getWidth() > panelForObject.getPrefWidth()))  && 
				((PanelForMap) panelForMap).containsPoints(new Point2D(event.getX(), event.getY())))
    	{
			objectMapEditor.moveObjectInPanelMap(dragged, event.getX(), event.getY());
        	isInTheMap = true;
    	}
	}

	public void setUpper(MouseEvent event) {
//		curveToMove = false;
		objectToMove = false;
		if (dragged != null)
			((Node) this.dragged).setEffect(null);
		objectMapEditor.selectObjectToMoveInMap(event.getX(), event.getY(), 
				borderGlow);
		isInTheMap = true;
	}
	
	public void moveObjectInMap(MouseEvent event) {
		objectMapEditor.moveObjectInMap(event.getX(), event.getY(), dragged, 
				(PanelForMap) panelForMap);
	}
	
	public double getMouseX() {
		return mouseX;
	}
	public double getMouseY() {
		return mouseY;
	}
	
	public void addObjectInListImage() {
		if (dragged != null)
			objectMapEditor.addPolygonObjectReleased(dragged, isInTheMap, objectToMove, objectMoveInMapForUndo);
		
		isInTheMap = false;

		checkStatusButtonUndo();
	}
	
	public SquarePolygon getDragged() {
		return dragged;
	}
	
	private void loadImageObject() {

		Iterator<Entry<String, SquarePolygon>> it = loaderImage.getImages().entrySet().iterator();
		int i = 0;
		double heiPrec = 0;

		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry pairs = (Map.Entry)it.next();
			if (it != null) {
				image = loaderImage.getImages().get(pairs.getKey());
				
				if (i == 0) {
					image.setLayoutX(((PanelForObject) panelForObject).getPanelForRealObject().getPrefWidth()/3);
					image.setLayoutY(((PanelForObject) panelForObject).getLastItemInserted());
					i++;
				}
				else {
					image.setLayoutX(((PanelForObject) panelForObject).getPanelForRealObject().getPrefWidth()/3);
					image.setLayoutY(objectToSelect.get(objectToSelect.size()-1).getLayoutY()+
							objectToSelect.get(objectToSelect.size()-1).getHeight()+20);
				}
				
				image.modifyPositionFirst(new Point2D(image.getLayoutX(), image.getLayoutY()), 
					image.getWidth(), image.getHeight());
				
				objectToSelect.add(image);
				((PanelForObject) panelForObject).getPanelForRealObject().getChildren().add((Node) image);
				
				heiPrec = image.getLayoutY()+image.getHeight();
				if (heiPrec >= ((PanelForObject) panelForObject).getPanelForRealObject().getPrefHeight())
					((PanelForObject) panelForObject).setHeightPanelForRealObject(heiPrec+20);
			}
		}
		
		quadCurve = loaderImage.getQuadCurve();
		quadCurve.setX(((PanelForObject) panelForObject).getPanelForRealObject().getPrefWidth()/3);
		quadCurve.setY(objectToSelect.get(objectToSelect.size()-1).getLayoutY()+
				objectToSelect.get(objectToSelect.size()-1).getHeight()+20);
		
		objectToSelect.add(quadCurve);
		quadCurve.modifyPositionFirst(new Point2D(quadCurve.getX(), quadCurve.getY()));
		
		((PanelForObject) panelForObject).getPanelForRealObject().getChildren().add(quadCurve);
	}
	
	public void saveMap(String nameFile) throws ParserConfigurationException, TransformerException {
		converter = new ConverterMapToXml();
		converter.convertToXml(this, nameFile);
	}
	
	public double getWidthScreen() {
		return widthScreen;
	}
	public double getHeightScreen() {
		return heightScreen;
	}
	
	public ArrayList<SquarePolygon> getObjectInMap() {
		return objectInMap;
	}
	
	public double getWidthPanelForMap() {
		return ((PanelForMap) panelForMap).getRealPane().getPrefWidth();
	}
	public double getHeightPanelForMap() {
		return ((PanelForMap) panelForMap).getRealPane().getPrefHeight();
	}
	
	public void setWidthPanelForMap(double width) {
		if (width < getWidthPanelForMap())
			((PanelForMap) panelForMap).setPrefWidth(width);
		((PanelForMap) panelForMap).getRealPane().setPrefWidth(width);
		drawAll();
	}
	public void setHeightPanelForMap(double height) {
		if (height < getHeightPanelForMap())
			((PanelForMap) panelForMap).setPrefHeight(height);
		((PanelForMap) panelForMap).getRealPane().setPrefHeight(height);
		drawAll();
	}
	
	public void setDimensionStandardPanelMap(boolean widthOrHeight) {
		if (widthOrHeight)
			((PanelForMap) panelForMap).setWidthStandard();
		else
			((PanelForMap) panelForMap).setHeightStandard();
	}
	
	public void undo() {
		if (dragged != null)
			((Node) dragged).setEffect(null);
		if (objectMoveInMapForUndo.size() > 1) {
			
			Point2D point = objectMoveInMapForUndo.get(objectMoveInMapForUndo.size()-1).getKey();
			SquarePolygon imageTmp = objectMoveInMapForUndo.remove(objectMoveInMapForUndo.size()-1).getValue();
			objectForUndoRedo.deleteObjectForUndo(imageTmp, objectMoveInMapForUndo, true);
			
			objectForUndoRedo.checkBooleanForRepaintObjectCancelled(imageTmp, objectToCancelled);
			if ((objectForUndoRedo.getObjectFoundPolygon()))
				objectForUndoRedo.undoPolygonObject(imageTmp, point);
			else
				objectForUndoRedo.actionForUndoRedo(point, imageTmp, objectMoveInMapForUndo, true, true, true);
		}
		else if (objectMoveInMapForUndo.size() == 1) {
			this.removeObject(dragged);
			objectMoveInMapForUndo.clear();
		}
		checkStatusButtonUndo();
	}

	public void removeObject(SquarePolygon object) {
		((PanelForMap)panelForMap).removeObject(object);
		if (objectInMap.contains(object))
			objectInMap.remove(object);
		
	}
	
	public void addObject(SquarePolygon object) {
		((PanelForMap)panelForMap).addObject(object);
		if (!objectInMap.contains(object))
			objectInMap.add(object);
	}
	
	public void removeObject() {
		((PanelForMap)panelForMap).removeObject(dragged);
		if (objectInMap.contains(dragged))
			objectInMap.remove(dragged);
		
//		objectMapEditor.removePolygon((PanelForMap) panelForMap, objectToCancelled, objectMoveInMapForUndo);
//		dragged = null;
	}
	
	public void clearMap() {
		((PanelForMap) panelForMap).removeAllObject();
		((PanelForMap) panelForMap).refreshDimension();
		
		objectInMap.clear();
//		curveInMap.clear();
//		objectMoveInMapForRedo.clear();
		objectMoveInMapForUndo.clear();
		objectToCancelled.clear();
		((PanelForMap) panelForMap).removePanelInsert();
		((PanelForObject) panelForObject).cancTextInTextField();
		
//		checkStatusButtonRedo();
		checkStatusButtonUndo();
	}
	
//	private void checkStatusButtonRedo() {
//		if (objectMoveInMapForRedo.size() == 1 || objectMoveInMapForRedo.size() == 0)
//			((PanelForObject) panelForObject).changePolicyButton(0,true);
//		else
//			((PanelForObject) panelForObject).changePolicyButton(0,false);
//	}

	private void checkStatusButtonUndo() {
		if (objectMoveInMapForUndo.size() == 0)
			((PanelForObject) panelForObject).changePolicyButton(1,true);
		else
			((PanelForObject) panelForObject).changePolicyButton(1,false);
	}

	private void drawAll() {
        panelForObject.setLayoutX(0);
        panelForMap.setLayoutX(panelForObject.getPrefWidth());
        
        loaderImage = new LoaderImage(this);
        loaderImage.load();
        
        loadImageObject();
	}
	
	public boolean isDragged() {
		return objectToMove;
	}

	public void setDragged(SquarePolygon dragged) {
		this.dragged = dragged;
		
//		((Node) this.dragged).setEffect(null);
	}

	public PanelForObject getPanelForObject() {
		return (PanelForObject) panelForObject;
	}

	public void setMouse(double x, double y) {
		mouseX = x;
		mouseY = y;
	}
}
