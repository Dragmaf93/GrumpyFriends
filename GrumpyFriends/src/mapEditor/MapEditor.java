package mapEditor;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import utils.ConverterMapToXml;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MapEditor {

	private Scene scene;
	private Pane firstPane;
	private Pane panelForObject;
	private ScrollPane panelForMap;
	
	private PolygonObject dragged;
	private Curve draggedCurve;
	
	private double widthScreen = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private double heightScreen = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	private PolygonObject image;
	
	private LoaderImage loaderImage;
	private boolean objectToMove;
	private boolean isInTheMap;
	
	private ArrayList<PolygonObject> objectInMap;
	private ArrayList<Curve> curveInMap;
	private ArrayList<PolygonObject> objectToSelect;
	private ArrayList<Pair<Point2D, Shape>> objectMoveInMapForUndo;
	private ArrayList<Pair<Point2D, Shape>> objectMoveInMapForRedo;
	private ArrayList<Pair<Point2D, Shape>> objectToCancelled;
	private DropShadow borderGlow;
	
	private double mouseX;
	private double mouseY;

	private Curve quadCurve;
	private ConverterMapToXml converter;
	private boolean curveToMove;
	private boolean curveOrPolygon;
	private Stage primaryStage;
	
	private ObjectMapEditor objectMapEditor;
	private ObjectForUndoRedo objectForUndoRedo;
	
//	@Override
//	public void start(Stage primaryStage) throws Exception {
	
	public MapEditor() {
//		this.primaryStage = primaryStage;
//		primaryStage.setTitle("Map Editor");        
		
		objectInMap = new ArrayList<PolygonObject>();
		objectToSelect = new ArrayList<PolygonObject>();
		objectMoveInMapForUndo = new ArrayList<Pair<Point2D, Shape>>();
		objectMoveInMapForRedo = new ArrayList<Pair<Point2D, Shape>>();
		objectToCancelled = new ArrayList<Pair<Point2D, Shape>>();
		
		curveInMap = new ArrayList<Curve>();
		
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
				objectMoveInMapForRedo, curveInMap, objectInMap, objectToCancelled);
		
		isInTheMap = false;
		firstPane.setStyle("-fx-background-color: #858484;");
		
		BorderPane root = new BorderPane();
        root.setCenter(firstPane);
        
        scene = new Scene(root, widthScreen, heightScreen);
//        primaryStage.setScene(scene);
//        primaryStage.show();
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public Stage getStage() {
		return primaryStage;
	}
	
	public void changeCursor(Cursor cursor) {
		scene.setCursor(cursor);
	}
	
	public void setUpperAndCopyImage(MouseEvent event) {
		for (PolygonObject image : objectInMap) 
			image.setEffect(null);
		for (Curve image : curveInMap) 
			image.setEffect(null);
		boolean bool = objectMapEditor.selectObject(objectToSelect, event.getX(), event.getY(),
				borderGlow, quadCurve);
		 if (bool) {
			objectToMove = true;
        	curveToMove = false;
        	curveOrPolygon = false;
		 }
		 else {
			curveToMove = true;
        	objectToMove = false;
        	curveOrPolygon = true;
		 }
		isInTheMap = false;
	}
	
	public void moveObjectFromPanelObjectInMap(MouseEvent event) {
		if (!isInTheMap)
		{
			if (curveToMove)
				objectMapEditor.moveCurveInPanelObject(null, draggedCurve, event.getX(), event.getY());
			else
				objectMapEditor.moveCurveInPanelObject(dragged, null, event.getX(), event.getY());
			isInTheMap = false;
		}
		
		if (((dragged != null && event.getX()-dragged.getWidth() > panelForObject.getPrefWidth()) || 
				(draggedCurve != null && event.getX()-draggedCurve.getWidth() > panelForObject.getPrefWidth()))  && 
				((PanelForMap) panelForMap).containsPoints(new Point2D(event.getX(), event.getY())))
    	{
			if (curveToMove)
				objectMapEditor.moveCurveInPanelMap(null, draggedCurve, event.getX(), event.getY());
			else
				objectMapEditor.moveCurveInPanelMap(dragged, null, event.getX(), event.getY());
//				double value = (2*((PanelForMap)panelForMap).getRealPane().getPrefHeight())/oldHeight;
        	isInTheMap = true;
    	}
	}

	public void setUpper(MouseEvent event) {
		curveToMove = false;
		objectToMove = false;
		boolean bool = objectMapEditor.selectObjectToMoveInMap(event.getX(), event.getY(), 
				borderGlow, curveInMap);
		if (bool) {
			objectToMove = true;
            curveToMove = false;
            curveOrPolygon = false;
		}
		else {
			curveToMove = true;
        	objectToMove = false;
        	curveOrPolygon = true;
		}
		isInTheMap = true;
	}
	
	public void moveObjectInMap(MouseEvent event) {
		if (curveToMove)
			objectMapEditor.moveObjectInMap(event.getX(), event.getY(), draggedCurve, null, (PanelForMap) panelForMap);
		else
			objectMapEditor.moveObjectInMap(event.getX(), event.getY(), null, dragged, (PanelForMap) panelForMap);
	}
	
	public double getMouseX() {
		return mouseX;
	}
	public double getMouseY() {
		return mouseY;
	}
	
	public void addObjectInListImage() {
		if (draggedCurve != null) {
			if (panelForObject.contains(draggedCurve.getX(), draggedCurve.getY()))
				panelForObject.getChildren().remove(draggedCurve);
			if (isInTheMap && !objectToMove)
				objectMapEditor.addCurveRelease(draggedCurve, curveInMap, isInTheMap, objectMoveInMapForUndo, curveToMove);
		}
		if (dragged != null && !curveOrPolygon)
			objectMapEditor.addPolygonObjectReleased(dragged, isInTheMap, objectMoveInMapForUndo, objectToMove);
		
		checkStatusButtonUndo();
		isInTheMap = false;
		objectToMove = false;
		curveToMove = false;
		dragged = null;
		draggedCurve = null;
	}
	
	public PolygonObject getDragged() {
		return dragged;
	}
	
	public Curve getDraggedCurve() {
		return draggedCurve;
	}
	
	private void loadImageObject() {

		Iterator it = loaderImage.getImages().entrySet().iterator();
		int i = 0;
		double heiPrec = 0;

		while (it.hasNext()) {
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
				((PanelForObject) panelForObject).getPanelForRealObject().getChildren().add(image);
				
				heiPrec = image.getLayoutY()+image.getHeight();
				if (heiPrec >= ((PanelForObject) panelForObject).getPanelForRealObject().getPrefHeight())
					((PanelForObject) panelForObject).setHeightPanelForRealObject(heiPrec+20);
			}
		}
		
		quadCurve = loaderImage.getQuadCurve();
		quadCurve.setX(((PanelForObject) panelForObject).getPanelForRealObject().getPrefWidth()/3);
		quadCurve.setY(objectToSelect.get(objectToSelect.size()-1).getLayoutY()+
				objectToSelect.get(objectToSelect.size()-1).getHeight()+20);
		
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
	
	public ArrayList<PolygonObject> getObjectInMap() {
		return objectInMap;
	}
	public ArrayList<Curve> getCurveInMap() {
		return curveInMap;
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
			dragged.setEffect(null);
		if (objectMoveInMapForUndo.size() > 1)
		{
			Shape imageTmp = null;
			Point2D point = objectMoveInMapForUndo.get(objectMoveInMapForUndo.size()-1).getKey();
			if (objectMoveInMapForUndo.get(objectMoveInMapForUndo.size()-1).getValue() instanceof PolygonObject)
				imageTmp = (PolygonObject) objectMoveInMapForUndo.remove(objectMoveInMapForUndo.size()-1).getValue();
			else
				imageTmp = (Curve) objectMoveInMapForUndo.remove(objectMoveInMapForUndo.size()-1).getValue();
			
			if (!objectMoveInMapForRedo.contains(new Pair<Point2D, Shape>(point, imageTmp)))
			{
				objectMoveInMapForRedo.clear();
				objectMoveInMapForRedo.add(new Pair<Point2D, Shape>(point, imageTmp));
			}
			objectForUndoRedo.deleteObjectForUndo(imageTmp, objectMoveInMapForUndo, true);
			
			objectForUndoRedo.checkBooleanForRepaintObjectCancelled(imageTmp, objectToCancelled);
			if ((objectForUndoRedo.getObjectFoundPolygon() && objectForUndoRedo.getObjectFoundRedoPolygon()) ||
					(objectForUndoRedo.getObjectFoundCurve() && objectForUndoRedo.getObjectFoundRedoCurve()))
			{
				if (imageTmp instanceof PolygonObject)
					objectForUndoRedo.undoPolygonObject(imageTmp, point);
				else
					objectForUndoRedo.undoCurveObject(imageTmp, point);
			}
			else
				objectForUndoRedo.actionForUndoRedo(point, imageTmp, objectMoveInMapForUndo, objectMoveInMapForRedo, true, true, true);
		}
		else if (objectMoveInMapForUndo.size() == 1)
		{
			objectMoveInMapForRedo.add(new Pair<Point2D, Shape>(objectMoveInMapForUndo.get(0).getKey(), objectMoveInMapForUndo.get(0).getValue()));
			objectForUndoRedo.deleteObjectForUndo(objectMoveInMapForUndo.get(0).getValue(), objectMoveInMapForUndo, false);
			objectMoveInMapForUndo.clear();
		}
		
		checkStatusButtonRedo();
		checkStatusButtonUndo();
	}
	
	public void redo() {
		if (dragged != null)
			dragged.setEffect(null);
		
		Point2D point = null;
		Shape imageTmp = null;
		
		if (objectMoveInMapForRedo.size() > 1) {
			imageTmp = objectMoveInMapForRedo.get(objectMoveInMapForRedo.size()-1).getValue();
			point = objectMoveInMapForRedo.remove(objectMoveInMapForRedo.size()-1).getKey();
			
			if (!objectMoveInMapForUndo.contains(new Pair<Point2D, Shape>(point, imageTmp)))
				objectMoveInMapForUndo.add(new Pair<Point2D, Shape>(point, imageTmp));
			
			objectForUndoRedo.checkBooleanForRepaintObjectCancelled(imageTmp, objectToCancelled);
			if ((objectForUndoRedo.getObjectFoundPolygon() && objectForUndoRedo.getObjectFoundRedoPolygon()) ||
					(objectForUndoRedo.getObjectFoundCurve() && objectForUndoRedo.getObjectFoundRedoCurve()))
			{
				if (imageTmp instanceof PolygonObject)
					objectForUndoRedo.removePolygonObjectAndRedo(objectInMap, imageTmp, dragged, point);
				else
					objectForUndoRedo.removeCurveAndRedo(draggedCurve, imageTmp, point);
			}
			else
				objectForUndoRedo.actionForUndoRedo(point, imageTmp, objectMoveInMapForRedo, objectMoveInMapForUndo, false, true, true);
		}
		else if (objectMoveInMapForRedo.size() == 1)
			objectForUndoRedo.actionForUndoRedo(point, imageTmp, objectMoveInMapForRedo, objectMoveInMapForUndo, false, true, true);

		checkStatusButtonUndo();
		checkStatusButtonRedo();
	}

	public void removeObject(Shape object) {
		if (object instanceof Curve) {
			((PanelForMap)panelForMap).removeCurve((Curve) object);
			if (curveInMap.contains(object))
				curveInMap.remove(object);
		}
		else {
			((PanelForMap)panelForMap).removeObject((PolygonObject) object);
			if (objectInMap.contains(object))
				objectInMap.remove(object);
		}
	}
	
	public void addObject(Shape object) {
		if (object instanceof Curve) {
			((PanelForMap)panelForMap).addCurve((Curve) object);
			if (!curveInMap.contains((Curve) object))
				curveInMap.add((Curve) object);
		}
		else {
			((PanelForMap)panelForMap).addObject((PolygonObject) object);
			if (!objectInMap.contains((PolygonObject) object))
				objectInMap.add((PolygonObject) object);
		}
	}
	
	public void removeObject() {
		if (!curveOrPolygon) {
			if (dragged != null)
				objectMapEditor.removePolygon((PanelForMap) panelForMap, objectToCancelled, objectMoveInMapForUndo);
		}
		else {
			if (draggedCurve != null)
				objectMapEditor.removeCurve((PanelForMap) panelForMap, objectToCancelled, objectMoveInMapForUndo, curveInMap);
		}
	}
	
	public void clearMap() {
		((PanelForMap) panelForMap).removeAllObject();
		((PanelForMap) panelForMap).refreshDimension();
		
		objectInMap.clear();
		curveInMap.clear();
		objectMoveInMapForRedo.clear();
		objectMoveInMapForUndo.clear();
		objectToCancelled.clear();
		((PanelForMap) panelForMap).removePanelInsert();
		((PanelForObject) panelForObject).cancTextInTextField();
		
		checkStatusButtonRedo();
		checkStatusButtonUndo();
	}
	
	private void checkStatusButtonRedo() {
		if (objectMoveInMapForRedo.size() == 1 || objectMoveInMapForRedo.size() == 0)
			((PanelForObject) panelForObject).changePolicyButton(0,true);
		else
			((PanelForObject) panelForObject).changePolicyButton(0,false);
	}
	
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

	public boolean isDraggedCurve() {
		return curveToMove;
	}
	
	public void setDraggedCurve(Curve draggedCurve2) {
		draggedCurve = draggedCurve2;
	}

	public void setDragged(PolygonObject dragged2) {
		dragged = dragged2;
	}

	public PanelForObject getPanelForObject() {
		return (PanelForObject) panelForObject;
	}

	public void setMouse(double x, double y) {
		mouseX = x;
		mouseY = y;
	}
	
//	public static void main(String[] args) {
//		launch(args);
//	}
}
