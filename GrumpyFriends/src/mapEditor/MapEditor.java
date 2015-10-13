package mapEditor;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MapEditor extends Application{

	private Pane firstPane;
	private Pane panelForObject;
	private ScrollPane panelForMap;
	
	private ImageForObject dragged;
	
	private double larghezza = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private double altezza = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	private ImageForObject image;
	
	private LoaderImage loaderImage;
	private Point2D upperLeftCorner;
	
	private double deltaX;
	private double deltaY;
	
	private ArrayList<ImageForObject> objectInMap;
	private ArrayList<ImageForObject> objectToSelect;
	private ArrayList<Pair<Point2D, ImageForObject>> objectMoveInMapForUndo;
	private ArrayList<Pair<Point2D, ImageForObject>> objectMoveInMapForRedo;
	private ArrayList<Pair<Point2D, ImageForObject>> objectToCancelled;
	
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
        panelForMap = new PanelForMap(this, larghezza-panelForObject.getPrefWidth(),larghezza-panelForObject.getPrefWidth());
        ((PanelForMap) panelForMap).setWidthStandard(larghezza-panelForObject.getPrefWidth(),larghezza-panelForObject.getPrefWidth());
        
		drawAll();
		
		firstPane.getChildren().add(panelForObject);
		firstPane.getChildren().add(panelForMap);
        BorderPane root = new BorderPane();
        root.setCenter(firstPane);
        
        primaryStage.setScene(new Scene(root, larghezza, altezza));
        primaryStage.show();
	}
	
	public void setUpperAndCopyImage(MouseEvent event)
	{
		for (ImageForObject image : objectToSelect) {
			if (image.isInTheArea(event))
	        {
	        	upperLeftCorner = new Point2D(image.getX(),	image.getY());
	            
	            deltaX = event.getX() - upperLeftCorner.getX();
	            deltaY = event.getY() - upperLeftCorner.getY();
	            
	        	try {
					dragged = image.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
	        }
		}
	}
	
	public void moveObjectFromPanelObjectInMap(MouseEvent event)
	{
		if (dragged != null && checkLimitForFocusPanelObject(event))
    	{
        	dragged.setX(event.getX()-deltaX);
        	if (((PanelForObject) panelForObject).scrolledUsed())
        		dragged.setY(event.getY());
        	else
        		dragged.setY(event.getY()+((PanelForObject) panelForObject).getPanelForDimension().getPrefHeight()-deltaY);
        	
        	dragged.modifyPosition(new Point2D(dragged.getX(), dragged.getY()), dragged.getImage().getWidth(), dragged.getImage().getHeight());

        	if (!(panelForObject.getChildren().contains(dragged)))
        		panelForObject.getChildren().add(dragged);
        	
        	if (dragged.getX() > panelForObject.getWidth() && checkLimitForFocusPanelObject(event))
        	{
        		dragged.setX(event.getX()-panelForObject.getPrefWidth()-deltaX);
        		if (((PanelForObject) panelForObject).scrolledUsed())
            		dragged.setY(event.getY());
            	else
            		dragged.setY(event.getY()+((PanelForObject) panelForObject).getPanelForDimension().getPrefHeight()-deltaY);
            	
            	dragged.modifyPosition(new Point2D(dragged.getX(), dragged.getY()), dragged.getImage().getWidth(), dragged.getImage().getHeight());
            	
            	if (!((PanelForMap) panelForMap).containsObject(dragged))
            		((PanelForMap) panelForMap).addObject(dragged);
        	}
    	}
	}

	public void setUpper(MouseEvent event)
	{
		for (ImageForObject image : objectInMap) {
			if (image.isInTheArea(event))
	        {
	        	upperLeftCorner = new Point2D(image.getX(), image.getY());
	            
	            deltaX = event.getX() - upperLeftCorner.getX();
	            deltaY = event.getY() - upperLeftCorner.getY();
	           
	            dragged = image;
	        }
		}
	}
	
	public void moveObjectInMap(MouseEvent event) {
		if (dragged != null && checkLimit(event))
    	{
        	dragged.setX(event.getX()-deltaX);
        	dragged.setY(event.getY()-deltaY);
        	
        	dragged.modifyPosition(new Point2D(dragged.getX(), dragged.getY()), dragged.getImage().getWidth(), dragged.getImage().getHeight());
    	}
	}
	
	public void addObjectInListImage()
	{
		if (!objectInMap.contains(dragged) && dragged != null)
			objectInMap.add(dragged);
		
		if (dragged != null)
		{
			Point2D point = new Point2D(dragged.getX(), dragged.getY());
			objectMoveInMapForUndo.add(new Pair(point, dragged));
		}
		
		checkStatusButtonUndo();
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
					image.setY(heiPrec+10);
				}
				
				image.setStartPosition(new Point2D(image.getX(), image.getY()));
				image.setStartRightPosition(new Point2D(image.getX()+image.getImage().getWidth(), image.getY()));
				image.setEndPosition(new Point2D(image.getX(), image.getY()+image.getImage().getHeight()));
				
				objectToSelect.add(image);
				
				heiPrec += image.getImage().getHeight();
				if (heiPrec >= ((PanelForObject) panelForObject).getPanelForRealObject().getPrefHeight())
					((PanelForObject) panelForObject).setHeightPanelForRealObject(heiPrec+20);
			}
		}
	}
	
	public void saveMap() {
//		TODO conversione in xml e salvataggio in una cartella!
	}
	
	public Pane getPanelForObject() {
		return panelForObject;
	}
	
	public double getLarghezza() {
		return larghezza;
	}
	
	public double getAltezza() {
		return altezza;
	}

	public double getWidthPanelForMap() {
		return ((PanelForMap) panelForMap).getRealPane().getPrefWidth();
	}
	public double getHeightPanelForMap() {
		return ((PanelForMap) panelForMap).getRealPane().getPrefHeight();
	}
	
	public void setWidthPanelForMap(double width) {
		((PanelForMap) panelForMap).getRealPane().setPrefWidth(width);
		drawAll();
	}
	public void setHeightPanelForMap(double height) {
		((PanelForMap) panelForMap).getRealPane().setPrefHeight(height);
		drawAll();
	}
	
	public void undo()
	{
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
		objectInMap.clear();
		objectMoveInMapForRedo.clear();
		objectMoveInMapForUndo.clear();
		objectToCancelled.clear();
		
		((PanelForMap) panelForMap).removeAllObject();
		((PanelForMap) panelForMap).refreshDimension();
		
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
	private boolean checkLimit(MouseEvent event)
	{
		return (event.getX()+image.getImage().getWidth()) < ((PanelForMap) panelForMap).getRealPane().getPrefWidth()
    			&& (event.getY()+image.getImage().getHeight()) < ((PanelForMap) panelForMap).getRealPane().getPrefHeight()
    			&& (event.getY()-image.getImage().getHeight()/3) >= 0
    			&& (event.getX()-image.getImage().getWidth()/2) >= 0;
	}
	
	
	private boolean checkLimitForFocusPanelObject(MouseEvent event) {
		System.out.println(((PanelForObject) panelForObject).getPanelForSubmit().getHeight());
		return (event.getX()-panelForObject.getPrefWidth()+ image.getImage().getWidth()) < ((PanelForMap) panelForMap).getRealPane().getPrefWidth()
    			&& (event.getY()-((PanelForObject) panelForObject).getPanelForSubmit().getPrefHeight()-image.getImage().getHeight()) < ((PanelForMap) panelForMap).getRealPane().getPrefHeight()/2
    			&& (event.getY()+((PanelForObject) panelForObject).getPanelForDimension().getPrefHeight()-image.getImage().getHeight()/3) >= 0
    			&& (event.getX()-panelForObject.getPrefWidth()-image.getImage().getWidth()) >= 0;
	}
	
	private void drawAll()
	{
        panelForObject.setLayoutX(0);
        panelForMap.setLayoutX(panelForObject.getPrefWidth());
        
        loaderImage = new LoaderImage(this);
        loaderImage.load("file:image/imageMapEditor/");
        
        loadImageObject();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
