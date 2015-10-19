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
	
	private ImageForObject dragged;
	
	private double widthScreen = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private double heightScreen = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	private ImageForObject image;
	
	private LoaderImage loaderImage;
	private Point2D upperLeftCorner;
	
	private double deltaX;
	private double deltaY;
	
	private boolean objectToMove;
	private boolean isInTheMap;
	
	private ArrayList<ImageForObject> objectInMap;
	private ArrayList<ImageForObject> objectToSelect;
	private ArrayList<Pair<Point2D, ImageForObject>> objectMoveInMapForUndo;
	private ArrayList<Pair<Point2D, ImageForObject>> objectMoveInMapForRedo;
	private ArrayList<Pair<Point2D, ImageForObject>> objectToCancelled;
	private DropShadow borderGlow;
	
	private ConverterMapToXml converter;
	private boolean inTheLimitForWidth = true;
	private boolean inTheLimitForHeight = true;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Map Editor");        
		
		objectInMap = new ArrayList<ImageForObject>();
		objectToSelect = new ArrayList<ImageForObject>();
		objectMoveInMapForUndo = new ArrayList<Pair<Point2D, ImageForObject>>();
		objectMoveInMapForRedo = new ArrayList<Pair<Point2D, ImageForObject>>();
		objectToCancelled = new ArrayList<Pair<Point2D, ImageForObject>>();
		
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
		System.out.println(panelForObject.getPrefWidth());
		firstPane.setStyle("-fx-background-color: #858484;");
//		TODO Provare a vedere com'è con un immagine di strisce
		
		BorderPane root = new BorderPane();
        root.setCenter(firstPane);
        scene = new Scene(root, widthScreen, heightScreen);
        primaryStage.setScene(scene);
        
        firstPane.setOnMouseMoved(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (dragged != null)
	        	{
	        		if ((int)(event.getX()-((PanelForObject)panelForObject).getPrefWidth())-1 == (int)dragged.getUpperRightPosition().getX())
	        			scene.setCursor(Cursor.H_RESIZE);
	        		else if ((int)(event.getX()-((PanelForObject)panelForObject).getPrefWidth())-1 == (int)dragged.getUpperLeftPosition().getX())
	        			scene.setCursor(Cursor.H_RESIZE);
	        		else if ((int)event.getY()-1 == (int)dragged.getBottomLeftPosition().getY())
	        			scene.setCursor(Cursor.V_RESIZE);
	        		else if ((int)event.getY()-1 == (int)dragged.getUpperLeftPosition().getY())
	        			scene.setCursor(Cursor.V_RESIZE);
	        		else
	        			scene.setCursor(Cursor.DEFAULT);
	        	}
			}
		});
        
        primaryStage.show();
	}
	
	public void changeCursor(Cursor cursor) {
		scene.setCursor(cursor);
	}
	
	public void setUpperAndCopyImage(MouseEvent event)
	{
		for (ImageForObject image : objectInMap) 
			image.setEffect(null);
		for (ImageForObject image : objectToSelect) {
			if (image.isInTheArea(event))
	        {
	        	upperLeftCorner = new Point2D(image.getX(),	image.getY());
	            
	            deltaX = event.getX() - upperLeftCorner.getX();
	            deltaY = event.getY() - upperLeftCorner.getY();
	            
	        	try {
					dragged = image.clone();
					dragged.setEffect(borderGlow);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
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
	        	dragged.setX(event.getX()-deltaX);
	        	modifyPositionObject(event);
	        	if (!(panelForObject.getChildren().contains(dragged)))
	        		panelForObject.getChildren().add(dragged);
			}
			if (checkLimitForFocusPanelObject(event))
        	{
        		dragged.setX(event.getX()-panelForObject.getPrefWidth()-deltaX);
        		modifyPositionObject(event);
            	if (!((PanelForMap) panelForMap).containsObject(dragged))
            		((PanelForMap) panelForMap).addObject(dragged);
            	
            	isInTheMap = true;
        	}
        	objectToMove = true;
    	}
	}

	private void modifyPositionObject(MouseEvent event)
	{
		if (dragged != null)
		{
			if (((PanelForObject) panelForObject).scrolledUsed())
	    		dragged.setY(event.getY());
	    	else
	    		dragged.setY(event.getY()+((PanelForObject) panelForObject).getPanelForDimension().getPrefHeight()-deltaY);
	    	
	    	dragged.modifyPosition(new Point2D(dragged.getX(), dragged.getY()), dragged.getImage().getWidth(), dragged.getImage().getHeight());
		}
	}
	
	public void setUpper(MouseEvent event)
	{
		if (dragged != null)
			for (ImageForObject image : objectInMap) {
				image.setEffect(null);
				if (image.isInTheArea(event))
		        {
		        	upperLeftCorner = new Point2D(image.getX(), image.getY());
		            
		            deltaX = event.getX() - upperLeftCorner.getX();
		            deltaY = event.getY() - upperLeftCorner.getY();
		           
		            dragged = image;
		            dragged.setEffect(borderGlow);
		            objectToMove = true;
		        }
			}
	}
	
	public void moveObjectInMap(MouseEvent event) {
		if (dragged != null)
    	{
			if (event.getX() > dragged.getUpperRightPosition().getX() && event.getY() < dragged.getBottomLeftPosition().getY() || event.getX() < dragged.getUpperLeftPosition().getX() && !inTheLimitForWidth)
					setPositionObject(event,0);
			else if (event.getX() > dragged.getUpperLeftPosition().getX()  ||
					event.getX() < dragged.getUpperRightPosition().getX() && inTheLimitForWidth )
			{
				if (event.getX()+(dragged.getUpperRightPosition().getX()-event.getX()) < ((PanelForMap) panelForMap).getRealPane().getPrefWidth() &&
						event.getX()-(event.getX()-dragged.getUpperLeftPosition().getX()) > 0 && inTheLimitForHeight)
					setPositionObject(event,0);
				else
					inTheLimitForWidth = false;
				if (event.getY()+(dragged.getBottomLeftPosition().getY()-event.getY()) < ((PanelForMap) panelForMap).getRealPane().getPrefHeight() &&
						event.getY()-(event.getY()-dragged.getBottomLeftPosition().getY()) > 0 && inTheLimitForWidth)
				{
					System.out.println("sono qui dentro YOOOO");
					setPositionObject(event,1);
				}
				else
					inTheLimitForHeight = false;
			}
//			else if (event.getY() > dragged.getUpperLeftPosition().getY()||
//					event.getY() < dragged.getBottomLeftPosition().getY() && !inTheLimitForHeight)
//				setPositionObject(event,1);
//			else if (event.getY() > dragged.getBottomLeftPosition().getY() && event.getY() > dragged.getUpperRightPosition().getX()  || event.getY() < dragged.getUpperLeftPosition().getY() && inTheLimitForHeight)
			else
			{
				inTheLimitForWidth = false;
				inTheLimitForHeight = false;
			}
    	}
		objectToMove = false;
	}
	
	private void setPositionObject(MouseEvent event,int ind) {
		dragged.setX(event.getX()-deltaX);
    	dragged.setY(event.getY()-deltaY);
    	
    	dragged.modifyPosition(new Point2D(dragged.getX(), dragged.getY()), dragged.getImage().getWidth(), dragged.getImage().getHeight());
    	objectToMove = true;
    	if (ind == 0)
    		inTheLimitForWidth = true;
    	else
    		inTheLimitForHeight = true;
	}
	
	public void addObjectInListImage()
	{
		if (dragged != null) {
			if (!objectInMap.contains(dragged) && dragged != null)
			{
				objectInMap.add(dragged);
	//			dragged.setEffect();
				dragged.setEffect(null);
			}
			
			Point2D point = new Point2D(dragged.getX(), dragged.getY());
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
	//			dragged.setEffect();
			
			isInTheMap = false;
		}
	}
	
	public ImageForObject getDragged() {
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
				((PanelForObject) panelForObject).getPanelForRealObject().getChildren().add(image);
				
				if (i == 0)
				{
					image.setX(((PanelForObject) panelForObject).getPanelForRealObject().getPrefWidth()/3);
					image.setY(0);
					i++;
				}
				else
				{
					image.setX(((PanelForObject) panelForObject).getPanelForRealObject().getPrefWidth()/3);
					image.setY(heiPrec);
				}
				
				image.setUpperLeftPosition(new Point2D(image.getX(), image.getY()));
				image.setUpperRightPosition(new Point2D(image.getX()+image.getImage().getWidth(), image.getY()));
				image.setBottomLeftPosition(new Point2D(image.getX(), image.getY()+image.getImage().getHeight()));
				
				objectToSelect.add(image);
				
				heiPrec += image.getImage().getHeight()+20;
				if (heiPrec >= ((PanelForObject) panelForObject).getPanelForRealObject().getPrefHeight())
					((PanelForObject) panelForObject).setHeightPanelForRealObject(heiPrec+20);
			}
		}
	}
	
	public void saveMap() throws ParserConfigurationException, TransformerException {
//		TODO Formattare xml!
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
	
	public ArrayList<ImageForObject> getObjectInMap() {
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
			ImageForObject imageTmp = objectMoveInMapForUndo.get(objectMoveInMapForUndo.size()-1).getValue();
			Point2D point = objectMoveInMapForUndo.remove(objectMoveInMapForUndo.size()-1).getKey();
			if (!objectMoveInMapForRedo.contains(new Pair<Point2D, ImageForObject>(point, imageTmp)))
			{
				objectMoveInMapForRedo.clear();
				objectMoveInMapForRedo.add(new Pair<Point2D, ImageForObject>(point, imageTmp));
			}
			
			((PanelForMap)panelForMap).removeObject(dragged);
			if (objectInMap.contains(imageTmp))
				objectInMap.remove(imageTmp);
			
			Pair<Point2D,ImageForObject> p = new Pair<Point2D, ImageForObject>(point,imageTmp);

			if (objectToCancelled.contains(p) && objectMoveInMapForUndo.contains(p))
			{
				objectToCancelled.add(new Pair<Point2D, ImageForObject>(point, dragged));
				objectMoveInMapForRedo.add(new Pair<Point2D, ImageForObject>(point, dragged));
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
			ImageForObject imageTmp = objectMoveInMapForRedo.get(objectMoveInMapForRedo.size()-1).getValue();
			Point2D point = objectMoveInMapForRedo.remove(objectMoveInMapForRedo.size()-1).getKey();
			if (!objectMoveInMapForUndo.contains(new Pair<Point2D, ImageForObject>(point, imageTmp)))
				objectMoveInMapForUndo.add(new Pair<Point2D, ImageForObject>(point, imageTmp));
			
			if (imageTmp != dragged)
			{
				((PanelForMap)panelForMap).removeObject(dragged);
				if (objectInMap.contains(imageTmp))
					objectInMap.remove(imageTmp);
			}
			Pair<Point2D,ImageForObject> p = new Pair<Point2D, ImageForObject>(point,imageTmp);

			if (objectToCancelled.contains(p) && objectMoveInMapForRedo.contains(p))
			{
				((PanelForMap)panelForMap).removeObject(dragged);
				objectInMap.remove(dragged);
				objectToCancelled.add(new Pair<Point2D, ImageForObject>(point, dragged));
				objectMoveInMapForUndo.add(new Pair<Point2D, ImageForObject>(point, dragged));
			}
			else
				actionForUndoRedo(point, imageTmp, objectMoveInMapForRedo, objectMoveInMapForUndo);
			
		}
		else if (objectMoveInMapForRedo.size() == 1)
		{
			Point2D point = null;
			ImageForObject imageTmp = null;
			actionForUndoRedo(point, imageTmp, objectMoveInMapForRedo, objectMoveInMapForUndo);
		}
			
		checkStatusButtonUndo();
		checkStatusButtonRedo();
	}
	
	private void actionForUndoRedo(Point2D point, ImageForObject imageTmp, ArrayList<Pair<Point2D, ImageForObject>> objectMoveInMapForTake, ArrayList<Pair<Point2D, ImageForObject>> objectMoveInMapForInsert)
	{
		point = objectMoveInMapForTake.get(objectMoveInMapForTake.size()-1).getKey();
		imageTmp = objectMoveInMapForTake.get(objectMoveInMapForTake.size()-1).getValue();
		if (!objectMoveInMapForInsert.contains(new Pair<Point2D, ImageForObject>(point, imageTmp)))
			objectMoveInMapForInsert.add(new Pair<Point2D, ImageForObject>(point, imageTmp));

		((PanelForMap)panelForMap).removeObject(imageTmp);
		if (objectInMap.contains(imageTmp))
			objectInMap.remove(imageTmp);

		dragged = imageTmp;
		
		dragged.setX(point.getX());
		dragged.setY(point.getY());

		((PanelForMap)panelForMap).addObject(dragged);
		if (!objectInMap.contains(dragged))
			objectInMap.add(dragged );
	}
	
	public void removeObject()
	{
		Point2D point = new Point2D(dragged.getX(), dragged.getY());
		if (!objectMoveInMapForRedo.contains(point))
		{
			objectMoveInMapForRedo.add(new Pair<Point2D, ImageForObject>(point, dragged));
			objectMoveInMapForRedo.add(new Pair<Point2D, ImageForObject>(point, dragged));
		}
		objectToCancelled.add(new Pair<Point2D, ImageForObject>(point, dragged));
		objectMoveInMapForUndo.add(new Pair<Point2D, ImageForObject>(point, dragged));
		
		((PanelForMap)panelForMap).removeObject(dragged);
		objectInMap.remove(dragged);
	}
	
	public void remove()
	{
		Point2D point = new Point2D(dragged.getX(), dragged.getY());
		if (!objectMoveInMapForRedo.contains(point))
			objectMoveInMapForRedo.add(new Pair<Point2D, ImageForObject>(point, dragged));
		
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
		System.out.println(dragged.getImage().getWidth());
		return (event.getX()+((dragged.getX()+dragged.getImage().getWidth())-event.getX())) < ((PanelForMap) panelForMap).getRealPane().getPrefWidth()
    			&& (event.getY()+dragged.getImage().getHeight()) < ((PanelForMap) panelForMap).getRealPane().getPrefHeight()
    			&& (event.getY()-dragged.getImage().getHeight()) >= 0
    			&& (event.getX()-(event.getX()-dragged.getX())) >= 0;
	}
	
	private boolean checkLimitForFocusPanelObject(MouseEvent event) {
		return (event.getX()-panelForObject.getPrefWidth()+ image.getImage().getWidth()) < ((PanelForMap) panelForMap).getRealPane().getPrefWidth()
    			&& (event.getY()-((PanelForObject) panelForObject).getPanelForSubmit().getPrefHeight()-image.getImage().getHeight()/3) < ((PanelForMap) panelForMap).getRealPane().getPrefHeight()/2
    			&& (event.getY()+((PanelForObject) panelForObject).getPanelForDimension().getPrefHeight()-image.getImage().getHeight()/3) >= 0
    			&& (event.getX()-panelForObject.getPrefWidth()-image.getImage().getWidth()) >= 0;
	}
	
	private void drawAll()
	{
        panelForObject.setLayoutX(0);
        panelForMap.setLayoutX(panelForObject.getPrefWidth()-Double.MIN_VALUE);
        
        loaderImage = new LoaderImage(this);
        loaderImage.load("file:image/imageMapEditor/");
        
        loadImageObject();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
