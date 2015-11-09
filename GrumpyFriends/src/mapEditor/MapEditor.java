package mapEditor;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import utils.ConverterMapToXml;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.QuadCurve;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MapEditor extends Application{

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
	private ArrayList<Pair<Point2D, PolygonObject>> objectMoveInMapForUndo;
	private ArrayList<Pair<Point2D, PolygonObject>> objectMoveInMapForRedo;
	private ArrayList<Pair<Point2D, PolygonObject>> objectToCancelled;
	private DropShadow borderGlow;
	
	private double mouseX;
	private double mouseY;

	private Curve quadCurve;
	private ConverterMapToXml converter;
	private boolean curveToMove;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Map Editor");        
		
		objectInMap = new ArrayList<PolygonObject>();
		objectToSelect = new ArrayList<PolygonObject>();
		objectMoveInMapForUndo = new ArrayList<Pair<Point2D, PolygonObject>>();
		objectMoveInMapForRedo = new ArrayList<Pair<Point2D, PolygonObject>>();
		objectToCancelled = new ArrayList<Pair<Point2D, PolygonObject>>();
		
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
	        	curveToMove = false;
	        }
		}
		if (!objectToMove && quadCurve.contains(new Point2D(event.getX(), event.getY())))
		{
			mouseX = event.getX();
            mouseY = event.getY();

            try {
				draggedCurve = quadCurve.clone();
				draggedCurve.setEffect(borderGlow);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
        	curveToMove = true;
        	objectToMove = false;
		}
	}
	
	public void moveObjectFromPanelObjectInMap(MouseEvent event)
	{
		if (dragged != null || draggedCurve != null)
    	{
			if (!isInTheMap)
			{
				if (curveToMove)
				{
					draggedCurve.modifyPositionFirst(new Point2D(event.getX(), event.getY()));
					if (!(panelForObject.getChildren().contains(draggedCurve)))
						panelForObject.getChildren().add(draggedCurve);
				}
				else
				{
					dragged.modifyAllVertex(event.getX(), event.getY(),0,0);
					if (!(panelForObject.getChildren().contains(dragged)))
		        		panelForObject.getChildren().add(dragged);
				}
			}
			if (checkLimitForFocusPanelObject(event) &&
					((PanelForMap) panelForMap).contains(new Point2D(event.getX(), event.getY())))
        	{
				if (curveToMove)
				{
					draggedCurve.modifyPositionFirst(new Point2D(event.getX()-panelForObject.getPrefWidth(), event.getY()));
					if (!((PanelForMap) panelForMap).containsCurve(draggedCurve))
	            		((PanelForMap) panelForMap).addObject(draggedCurve);
					
					System.out.println(draggedCurve);
				}
				else
				{
					dragged.modifyAllVertex(event.getX(), event.getY(),panelForObject.getPrefWidth(),0);
					if (!((PanelForMap) panelForMap).containsObject(dragged))
	            		((PanelForMap) panelForMap).addObject(dragged);
				}
            	isInTheMap = true;
        	}
        	objectToMove = true;
        	objectToMove = false;
    	}
	}

	public void setUpper(MouseEvent event)
	{
		if (dragged != null)
			for (PolygonObject image : objectInMap) {
				image.setEffect(null);
				if (image.contains(new Point2D(event.getX(), event.getY())))
		        {
		            mouseX = event.getX();
		            mouseY = event.getY();
		            
		            dragged = image;
		            dragged.computeDistanceVertex();
		            
		            dragged.setEffect(borderGlow);
		            objectToMove = true;
		            curveToMove = false;
		        }
			}
		
		if (!objectToMove && quadCurve.contains(new Point2D(event.getX(), event.getY())))
		{
			System.out.println("YO BITCH");
			mouseX = event.getX();
            mouseY = event.getY();

            try {
				draggedCurve = quadCurve.clone();
				draggedCurve.setEffect(borderGlow);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
        	curveToMove = true;
        	objectToMove = false;
		}
	}
	
	public void moveObjectInMap(MouseEvent event) {
		if (dragged != null)
    	{
			if (curveToMove)
			{
				draggedCurve.modifyPositionFirst(new Point2D(event.getX()-panelForObject.getPrefWidth(), event.getY()));
				if (!((PanelForMap) panelForMap).containsCurve(draggedCurve))
            		((PanelForMap) panelForMap).addObject(draggedCurve);
			}
			else
			{
				if (((PanelForMap) panelForMap).getDraggedPressed() &&
						((PanelForMap) panelForMap).contains(new Point2D(event.getX(), event.getY())))	
					dragged.modifyAllVertex(event.getX(), event.getY(),0,0);
    	
			}
    	}
//		objectToMove = false;
	}
	
	public double getMouseX() {
		return mouseX;
	}
	
	public double getMouseY() {
		return mouseY;
	}
	
	public void addObjectInListImage()
	{
		if (draggedCurve != null)
		{
			System.out.println("YO "+draggedCurve);
		}
		if (dragged != null) {
			Point2D point = new Point2D(dragged.getPointsVertex().get(0).getX(), dragged.getPointsVertex().get(0).getY());
			if (!objectInMap.contains(dragged) && isInTheMap)
			{
				objectInMap.add(dragged);
				dragged.setEffect(null);
			}
			if (panelForObject.contains(point) && !isInTheMap)
			{
				panelForObject.getChildren().remove(dragged);
				if (objectMoveInMapForUndo.size() > 1)
					dragged = objectMoveInMapForUndo.get(objectMoveInMapForUndo.size()-1).getValue();
			} else
				try {
					objectMoveInMapForUndo.add(new Pair(point, dragged.clone()));
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			
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
					image.setLayoutY(((PanelForObject) panelForObject).getLastItemInserted());
					i++;
				}
				else
				{
					image.setLayoutX(((PanelForObject) panelForObject).getPanelForRealObject().getPrefWidth()/3);
					image.setLayoutY(objectToSelect.get(objectToSelect.size()-1).getLayoutY()+
							objectToSelect.get(objectToSelect.size()-1).getHeight()+20);
				}
				
				image.modifyPositionFirst(new Point2D(image.getLayoutX(), image.getLayoutY()), image.getWidth(), image.getHeight());
				
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
				objectToSelect.get(objectToSelect.size()-1).getHeight()+10);
		
		quadCurve.modifyPositionFirst(new Point2D(quadCurve.getX(), quadCurve.getY()));
		
		((PanelForObject) panelForObject).getPanelForRealObject().getChildren().add(quadCurve);
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

	public void addObjectInMap(QuadCurve object) {
		((PanelForMap) panelForMap).addObject(object);
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
//				objectMoveInMapForUndo.remove(p);
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
		
		Point2D point = null;
		PolygonObject imageTmp = null;
		if (objectMoveInMapForRedo.size() > 1)
		{
			imageTmp = objectMoveInMapForRedo.get(objectMoveInMapForRedo.size()-1).getValue();
			point = objectMoveInMapForRedo.remove(objectMoveInMapForRedo.size()-1).getKey();
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
			actionForUndoRedo(point, imageTmp, objectMoveInMapForRedo, objectMoveInMapForUndo);

		checkStatusButtonUndo();
		checkStatusButtonRedo();
	}
	
	private void actionForUndoRedo(Point2D point, PolygonObject imageTmp, ArrayList<Pair<Point2D, PolygonObject>> objectMoveInMapForTake, ArrayList<Pair<Point2D, PolygonObject>> objectMoveInMapForInsert)
	{
		((PanelForMap)panelForMap).removeObject(imageTmp);
		if (objectInMap.contains(imageTmp))
			objectInMap.remove(imageTmp);
		
		point = objectMoveInMapForTake.get(objectMoveInMapForTake.size()-1).getKey();
		imageTmp = objectMoveInMapForTake.get(objectMoveInMapForTake.size()-1).getValue();
		if (!objectMoveInMapForInsert.contains(new Pair<Point2D, PolygonObject>(point, imageTmp)))
			objectMoveInMapForInsert.add(new Pair<Point2D, PolygonObject>(point, imageTmp));

		dragged = imageTmp;
		
		((PanelForMap)panelForMap).addObject(dragged);
		if (!objectInMap.contains(dragged))
			objectInMap.add(dragged );
	}
	
	public void removeObject()
	{
		Point2D point = new Point2D(dragged.getPointsVertex().get(0).getX(), dragged.getPointsVertex().get(0).getY());
		
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
	
	private void remove()
	{
		Point2D point = new Point2D(dragged.getPointsVertex().get(0).getX(), dragged.getPointsVertex().get(0).getY());
		
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
	
	private boolean checkLimitForFocusPanelObject(MouseEvent event) {
		return (event.getX()-panelForObject.getPrefWidth()+ image.getWidth()) < ((PanelForMap) panelForMap).getRealPane().getPrefWidth()
    			&& (event.getY()-((PanelForObject) panelForObject).getPanelForSubmit().getPrefHeight()-image.getHeight()/3) < ((PanelForMap) panelForMap).getRealPane().getPrefHeight()/2
    			&& (event.getY()-image.getHeight()/3) >= 0
    			&& (event.getX()-panelForObject.getPrefWidth()-image.getWidth()) >= 0;
	}
	
	private void drawAll()
	{
        panelForObject.setLayoutX(0);
        panelForMap.setLayoutX(panelForObject.getPrefWidth());
        
        loaderImage = new LoaderImage(this);
        loaderImage.load();
        
        loadImageObject();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
