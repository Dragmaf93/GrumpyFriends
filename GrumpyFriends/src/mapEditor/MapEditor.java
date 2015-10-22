package mapEditor;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import utils.ConverterMapToXml;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light.Point;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MapEditor extends Application{

	private Scene scene;
	private Pane firstPane;
	private Pane panelForObject;
	private ScrollPane panelForMap;
	
	private PolygonObject dragged;
	
	private double widthScreen = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private double heightScreen = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	private PolygonObject image;
	
	private LoaderImage loaderImage;
	private Point2D upperLeftCorner;
	
	private double deltaX;
	private double deltaY;
	
	private boolean objectToMove;
	private boolean isInTheMap;
	
	private ArrayList<PolygonObject> objectInMap;
	private ArrayList<PolygonObject> objectToSelect;
	private ArrayList<Pair<Point2D, PolygonObject>> objectMoveInMapForUndo;
	private ArrayList<Pair<Point2D, PolygonObject>> objectMoveInMapForRedo;
	private ArrayList<Pair<Point2D, PolygonObject>> objectToCancelled;
	private DropShadow borderGlow;
	
	private ConverterMapToXml converter;
	private double mouseX;
	private double mouseY;
	
	private Point2D distanceUpperLeft;
	private Point2D distanceUpperRight;
	private Point2D distanceBottomLeft;
	private Point2D distanceBottomRight;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Map Editor");        
		
		objectInMap = new ArrayList<PolygonObject>();
		objectToSelect = new ArrayList<PolygonObject>();
		objectMoveInMapForUndo = new ArrayList<Pair<Point2D, PolygonObject>>();
		objectMoveInMapForRedo = new ArrayList<Pair<Point2D, PolygonObject>>();
		objectToCancelled = new ArrayList<Pair<Point2D, PolygonObject>>();
		
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
		
		isInTheMap = false;
		firstPane.setStyle("-fx-background-color: #858484;");
//		TODO Provare a vedere com'è con un immagine di strisce
		
		BorderPane root = new BorderPane();
        root.setCenter(firstPane);
        
        scene = new Scene(root, widthScreen, heightScreen);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	public void changeCursor(Cursor cursor) {
		scene.setCursor(cursor);
	}
	
	public void setUpperAndCopyImage(MouseEvent event)
	{
		for (PolygonObject image : objectInMap) 
			image.setEffect(null);
		for (PolygonObject image : objectToSelect) {
			if (image.containsPoint(new Point2D(event.getX(),event.getY())))
	        {
	        	upperLeftCorner = new Point2D(image.getLayoutX(),	image.getLayoutY());
	            
	            deltaX = event.getX() - upperLeftCorner.getX();
	            deltaY = event.getY() - upperLeftCorner.getY();
	            
	            mouseX = event.getX();
	            mouseY = event.getY();
	            
	        	try {
					dragged = image.clone();
					dragged.setEffect(borderGlow);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
	        	dragged.computeDistanceVertex();
	        	objectToMove = true;
	        }
		}
	}
	
	public void moveObjectFromPanelObjectInMap(MouseEvent event)
	{
		if (dragged != null)
    	{
			if (!isInTheMap)
			{
				dragged.modifyAllVertex(event.getX(), event.getY()+((PanelForObject) panelForObject).getPanelForDimension().getPrefHeight());
	        	if (!(panelForObject.getChildren().contains(dragged)))
	        		panelForObject.getChildren().add(dragged);
			}
			if (checkLimitForFocusPanelObject(event))
        	{
				dragged.modifyAllVertex(event.getX()-panelForObject.getPrefWidth(), event.getY()+((PanelForObject) panelForObject).getPanelForDimension().getPrefHeight());
            	if (!((PanelForMap) panelForMap).containsObject(dragged))
            		((PanelForMap) panelForMap).addObject(dragged);
            	
            	isInTheMap = true;
        	}
        	objectToMove = true;
    	}
	}

	public void setUpper(MouseEvent event)
	{
		if (dragged != null)
			for (PolygonObject image : objectInMap) {
				image.setEffect(null);
				if (image.contains(new Point2D(event.getX(), event.getY())))
		        {
		        	upperLeftCorner = new Point2D(image.getX(), image.getY());
		            
		            deltaX = event.getX() - upperLeftCorner.getX();
		            deltaY = event.getY() - upperLeftCorner.getY();
		           
		            mouseX = event.getX();
		            mouseY = event.getY();
		            
		            dragged = image;
		            dragged.computeDistanceVertex();
		            
		            dragged.setEffect(borderGlow);
		            objectToMove = true;
		        }
			}
	}
	
	public void moveObjectInMap(MouseEvent event) {
		if (dragged != null)
    	{
			if (((PanelForMap) panelForMap).getDraggedPressed())	
				dragged.modifyAllVertex(event.getX(), event.getY());
    	}
		objectToMove = false;
	}
	
	public double getMouseX() {
		return mouseX;
	}
	
	public double getMouseY() {
		return mouseY;
	}
	
	public void addObjectInListImage()
	{
		if (dragged != null) {
			if (!objectInMap.contains(dragged) && dragged != null)
			{
				objectInMap.add(dragged);
				dragged.setEffect(null);
			}
			
			Point2D point = new Point2D(dragged.getLayoutX(), dragged.getLayoutY());
			if (panelForObject.contains(point) && !isInTheMap)
			{
				panelForObject.getChildren().remove(dragged);
				if (objectMoveInMapForUndo.size() > 1)
					dragged = objectMoveInMapForUndo.get(objectMoveInMapForUndo.size()-1).getValue();
			}
			else
				objectMoveInMapForUndo.add(new Pair(point, dragged));
			
			checkStatusButtonUndo();
			if (!objectToMove)
				dragged.setEffect(null);
			
			isInTheMap = false;
		}
	}
	
	public PolygonObject getDragged() {
		return dragged;
	}
	
	private void loadImageObject() {

		Iterator it = loaderImage.getImages().entrySet().iterator();
		int i = 0;
		double heiPrec = 0;

		while (it.hasNext())
		{
			Map.Entry pairs = (Map.Entry)it.next();
			if (it != null)
			{
				image = loaderImage.getImages().get(pairs.getKey());
				if (i == 0)
				{
					image.setLayoutX(((PanelForObject) panelForObject).getPanelForRealObject().getPrefWidth()/3);
					image.setLayoutY(0);
					i++;
				}
				else
				{
					image.setLayoutX(((PanelForObject) panelForObject).getPanelForRealObject().getPrefWidth()/3);
					image.setLayoutY(heiPrec);
				}
				
				image.modifyPositionFirst(new Point2D(image.getLayoutX(), image.getLayoutY()), image.getWidth(), image.getHeight());

				objectToSelect.add(image);
				((PanelForObject) panelForObject).getPanelForRealObject().getChildren().add(image);
				
				heiPrec += image.getHeight()+20;
				if (heiPrec >= ((PanelForObject) panelForObject).getPanelForRealObject().getPrefHeight())
					((PanelForObject) panelForObject).setHeightPanelForRealObject(heiPrec+20);
			}
		}
	}
	
	public void saveMap() throws ParserConfigurationException, TransformerException {
		converter = new ConverterMapToXml();
		converter.convertToXml(this);
	}
	
	public Pane getPanelForObject() {
		return panelForObject;
	}
	
	public double getLarghezza() {
		return widthScreen;
	}
	
	public double getAltezza() {
		return heightScreen;
	}
	
	public ArrayList<PolygonObject> getObjectInMap() {
		return objectInMap;
	}

	public double getWidthPanelForObject() {
		return panelForObject.getPrefWidth();
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
	
	public void undo()
	{
		if (dragged != null)
			dragged.setEffect(null);
		if (objectMoveInMapForUndo.size() > 1)
		{
			PolygonObject imageTmp = objectMoveInMapForUndo.get(objectMoveInMapForUndo.size()-1).getValue();
			Point2D point = objectMoveInMapForUndo.remove(objectMoveInMapForUndo.size()-1).getKey();
			if (!objectMoveInMapForRedo.contains(new Pair<Point2D, PolygonObject>(point, imageTmp)))
			{
				objectMoveInMapForRedo.clear();
				objectMoveInMapForRedo.add(new Pair<Point2D, PolygonObject>(point, imageTmp));
			}
			
			((PanelForMap)panelForMap).removeObject(dragged);
			if (objectInMap.contains(imageTmp))
				objectInMap.remove(imageTmp);
			
			Pair<Point2D,PolygonObject> p = new Pair<Point2D, PolygonObject>(point,imageTmp);

			if (objectToCancelled.contains(p) && objectMoveInMapForUndo.contains(p))
			{
				objectToCancelled.add(new Pair<Point2D, PolygonObject>(point, dragged));
				objectMoveInMapForRedo.add(new Pair<Point2D, PolygonObject>(point, dragged));
				objectMoveInMapForUndo.remove(p);
				actionForUndoRedo(point, dragged, objectMoveInMapForUndo, objectMoveInMapForRedo);
			}
			else
				actionForUndoRedo(point, imageTmp, objectMoveInMapForUndo, objectMoveInMapForRedo);
		}
		else if (objectMoveInMapForUndo.size() == 1)
		{
			remove();
			objectMoveInMapForUndo.clear();
		}
		
		checkStatusButtonRedo();
		checkStatusButtonUndo();
	}
	
	public void redo()
	{
		if (dragged != null)
			dragged.setEffect(null);
		if (objectMoveInMapForRedo.size() > 1)
		{
			PolygonObject imageTmp = objectMoveInMapForRedo.get(objectMoveInMapForRedo.size()-1).getValue();
			Point2D point = objectMoveInMapForRedo.remove(objectMoveInMapForRedo.size()-1).getKey();
			if (!objectMoveInMapForUndo.contains(new Pair<Point2D, PolygonObject>(point, imageTmp)))
				objectMoveInMapForUndo.add(new Pair<Point2D, PolygonObject>(point, imageTmp));
			
			if (imageTmp != dragged)
			{
				((PanelForMap)panelForMap).removeObject(dragged);
				if (objectInMap.contains(imageTmp))
					objectInMap.remove(imageTmp);
			}
			Pair<Point2D,PolygonObject> p = new Pair<Point2D, PolygonObject>(point,imageTmp);

			if (objectToCancelled.contains(p) && objectMoveInMapForRedo.contains(p))
			{
				((PanelForMap)panelForMap).removeObject(dragged);
				objectInMap.remove(dragged);
				objectToCancelled.add(new Pair<Point2D, PolygonObject>(point, dragged));
				objectMoveInMapForUndo.add(new Pair<Point2D, PolygonObject>(point, dragged));
			}
			else
				actionForUndoRedo(point, imageTmp, objectMoveInMapForRedo, objectMoveInMapForUndo);
			
		}
		else if (objectMoveInMapForRedo.size() == 1)
		{
			Point2D point = null;
			PolygonObject imageTmp = null;
			actionForUndoRedo(point, imageTmp, objectMoveInMapForRedo, objectMoveInMapForUndo);
		}
			
		checkStatusButtonUndo();
		checkStatusButtonRedo();
	}
	
	private void actionForUndoRedo(Point2D point, PolygonObject imageTmp, ArrayList<Pair<Point2D, PolygonObject>> objectMoveInMapForTake, ArrayList<Pair<Point2D, PolygonObject>> objectMoveInMapForInsert)
	{
		point = objectMoveInMapForTake.get(objectMoveInMapForTake.size()-1).getKey();
		imageTmp = objectMoveInMapForTake.get(objectMoveInMapForTake.size()-1).getValue();
		if (!objectMoveInMapForInsert.contains(new Pair<Point2D, PolygonObject>(point, imageTmp)))
			objectMoveInMapForInsert.add(new Pair<Point2D, PolygonObject>(point, imageTmp));

		((PanelForMap)panelForMap).removeObject(imageTmp);
		if (objectInMap.contains(imageTmp))
			objectInMap.remove(imageTmp);

		dragged = imageTmp;
		
		dragged.setLayoutX(point.getX());
		dragged.setLayoutY(point.getY());

		((PanelForMap)panelForMap).addObject(dragged);
		if (!objectInMap.contains(dragged))
			objectInMap.add(dragged );
	}
	
	public void removeObject()
	{
		Point2D point = new Point2D(dragged.getLayoutX(), dragged.getLayoutY());
		if (!objectMoveInMapForRedo.contains(point))
		{
			objectMoveInMapForRedo.add(new Pair<Point2D, PolygonObject>(point, dragged));
			objectMoveInMapForRedo.add(new Pair<Point2D, PolygonObject>(point, dragged));
		}
		objectToCancelled.add(new Pair<Point2D, PolygonObject>(point, dragged));
		objectMoveInMapForUndo.add(new Pair<Point2D, PolygonObject>(point, dragged));
		
		((PanelForMap)panelForMap).removeObject(dragged);
		objectInMap.remove(dragged);
	}
	
	public void remove()
	{
		Point2D point = new Point2D(dragged.getLayoutX(), dragged.getLayoutY());
		if (!objectMoveInMapForRedo.contains(point))
			objectMoveInMapForRedo.add(new Pair<Point2D, PolygonObject>(point, dragged));
		
		((PanelForMap)panelForMap).removeObject(dragged);
		objectInMap.remove(dragged);
	}
	
	public void clearMap()
	{
		((PanelForMap) panelForMap).removeAllObject();
		((PanelForMap) panelForMap).refreshDimension();
		
		objectInMap.clear();
		objectMoveInMapForRedo.clear();
		objectMoveInMapForUndo.clear();
		objectToCancelled.clear();
		
		((PanelForObject) panelForObject).cancTextInTextField();
		
		checkStatusButtonRedo();
		checkStatusButtonUndo();
	}
	
	public boolean isInMap() {
		return isInTheMap;
	}
	
	private void checkStatusButtonRedo()
	{
		if (objectMoveInMapForRedo.size() == 1 || objectMoveInMapForRedo.size() == 0)
			((PanelForObject) panelForObject).changePolicyButton(0,true);
		else
			((PanelForObject) panelForObject).changePolicyButton(0,false);
	}
	
	private void checkStatusButtonUndo()
	{
		if (objectMoveInMapForUndo.size() == 0)
			((PanelForObject) panelForObject).changePolicyButton(1,true);
		else
			((PanelForObject) panelForObject).changePolicyButton(1,false);
	}
	
	private boolean checkLimit(MouseEvent event) {
		System.out.println(dragged.getWidth());
		return (event.getX()+((dragged.getLayoutX()+dragged.getWidth())-event.getX())) < ((PanelForMap) panelForMap).getRealPane().getPrefWidth()
    			&& (event.getY()+dragged.getHeight()) < ((PanelForMap) panelForMap).getRealPane().getPrefHeight()
    			&& (event.getY()-dragged.getHeight()) >= 0
    			&& (event.getX()-(event.getX()-dragged.getLayoutX())) >= 0;
	}
	
	private boolean checkLimitForFocusPanelObject(MouseEvent event) {
		return (event.getX()-panelForObject.getPrefWidth()+ image.getWidth()) < ((PanelForMap) panelForMap).getRealPane().getPrefWidth()
    			&& (event.getY()-((PanelForObject) panelForObject).getPanelForSubmit().getPrefHeight()-image.getHeight()/3) < ((PanelForMap) panelForMap).getRealPane().getPrefHeight()/2
    			&& (event.getY()+((PanelForObject) panelForObject).getPanelForDimension().getPrefHeight()-image.getHeight()/3) >= 0
    			&& (event.getX()-panelForObject.getPrefWidth()-image.getWidth()) >= 0;
	}
	
	private void drawAll()
	{
        panelForObject.setLayoutX(0);
        panelForMap.setLayoutX(panelForObject.getPrefWidth()-Double.MIN_VALUE);
        
        loaderImage = new LoaderImage(this);
        loaderImage.load();
        
        loadImageObject();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
