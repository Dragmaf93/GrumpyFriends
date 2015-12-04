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
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MapEditor extends Application{

	private Scene scene;
	private Pane firstPane;
	private Pane panelForObject;
	private ScrollPane panelForMap;
	
	private PolygonObject dragged;
	private Curve draggedCurve;
//	private PolygonObject draggedTmp;
//	private Curve draggedCurveTmp;
	
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
	
	private double oldHeight;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Map Editor");        
		
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
        oldHeight = ((PanelForMap)panelForMap).getRealPane().getPrefHeight();
        
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
		
		BorderPane root = new BorderPane();
        root.setCenter(firstPane);
        
        scene = new Scene(root, widthScreen, heightScreen);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	public Stage getStage() {
		return primaryStage;
	}
	
	public void changeCursor(Cursor cursor) {
		scene.setCursor(cursor);
	}
	
	public void setUpperAndCopyImage(MouseEvent event)
	{
		for (PolygonObject image : objectInMap) 
			image.setEffect(null);
		for (Curve image : curveInMap) 
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
	        	curveOrPolygon = false;
	        }
		}
		if (quadCurve.contains(new Point2D(event.getX(), event.getY())))
		{
			mouseX = event.getX();
            mouseY = event.getY();

            try {
				draggedCurve = quadCurve.clone();
				
				draggedCurve.computeDistanceVertex();
				draggedCurve.setEffect(borderGlow);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
        	curveToMove = true;
        	objectToMove = false;
        	curveOrPolygon = true;
		}
		isInTheMap = false;
	}
	
	public void moveObjectFromPanelObjectInMap(MouseEvent event)
	{
		if (!isInTheMap)
		{
			if (curveToMove)
			{
				if (draggedCurve != null)
				{
					draggedCurve.modifyPosition(new Point2D(event.getX(), event.getY()),0,0);
					if (!(panelForObject.getChildren().contains(draggedCurve)))
						panelForObject.getChildren().add(draggedCurve);
				}
			}
			else
			{
				if (dragged != null)
				{
					dragged.modifyAllVertex(event.getX(), event.getY(),0,0);
					if (!(panelForObject.getChildren().contains(dragged)))
		        		panelForObject.getChildren().add(dragged);
				}
			}
			isInTheMap = false;
		}
		
		if (((dragged != null && event.getX()-dragged.getWidth() > panelForObject.getPrefWidth()) || 
				(draggedCurve != null && event.getX()-draggedCurve.getWidth() > panelForObject.getPrefWidth()))  && 
				((PanelForMap) panelForMap).containsPoints(new Point2D(event.getX(), event.getY())))
    	{
			if (curveToMove)
			{
//				if (((PanelForMap) panelForMap).isUp())
//					draggedCurve.modifyPosition(new Point2D(event.getX()-panelForObject.getPrefWidth(), event.getY()-((PanelForMap) panelForMap).getValueScroll()),panelForObject.getPrefWidth(),0);
//				else
					draggedCurve.modifyPosition(new Point2D(event.getX()-panelForObject.getPrefWidth(), event.getY()-((PanelForMap) panelForMap).getValueScroll()),panelForObject.getPrefWidth(),0);
					
				if (!((PanelForMap) panelForMap).containsCurve(draggedCurve))
            		((PanelForMap) panelForMap).addCurve(draggedCurve);
			}
			else
			{
//				if (((PanelForMap) panelForMap).isUp())
//					dragged.modifyAllVertex(event.getX(), event.getY()+((PanelForMap) panelForMap).getValueScroll(),panelForObject.getPrefWidth(),0);
//				else
//				System.out.println("OOOOOH: "+((PanelForMap) panelForMap).getValueScroll()+"       "+ event.getY() + "      "+(event.getY()+((PanelForMap) panelForMap).getValueScroll()));
				double value = (2*((PanelForMap)panelForMap).getRealPane().getPrefHeight())/oldHeight;
				System.out.println("EHI: "+((PanelForMap) panelForMap).getValueScroll()*value);
				dragged.modifyAllVertex(event.getX(), event.getY()-((PanelForMap) panelForMap).getValueScroll()*value,panelForObject.getPrefWidth(),((PanelForMap) panelForMap).getValueScroll()*value);
				
				if (!((PanelForMap) panelForMap).containsObject(dragged))
            		((PanelForMap) panelForMap).addObject(dragged);
			}
        	isInTheMap = true;
    	}
	}

	public void setUpper(MouseEvent event)
	{
		curveToMove = false;
		objectToMove = false;
		for (PolygonObject image : objectInMap) {
			image.setEffect(null);
			if (image.contains(new Point2D(event.getX(), event.getY())))
	        {
	            mouseX = event.getX();
	            mouseY = event.getY();
	            
	            dragged = (PolygonObject) image;
	            dragged.computeDistanceVertex();
	            
	            dragged.setEffect(borderGlow);
	            objectToMove = true;
	            curveToMove = false;
	            curveOrPolygon = false;
	        }
		}
		
		if (!objectToMove)
		{
			for (Curve curve : curveInMap) {
				curve.setEffect(null);
				if (curve.contains(new Point2D(event.getX(), event.getY())))
				{
					mouseX = event.getX();
		            mouseY = event.getY();
		
					draggedCurve = curve;
					draggedCurve.computeDistanceVertex();
					draggedCurve.setEffect(borderGlow);
					
		        	curveToMove = true;
		        	objectToMove = false;
		        	curveOrPolygon = true;
				}
			}
		}
		isInTheMap = true;
	}
	
	public void moveObjectInMap(MouseEvent event) {
		if (curveToMove)
		{
			if (draggedCurve != null &&
					((PanelForMap) panelForMap).containsPoints(new Point2D(event.getX(), event.getY()))) 
				draggedCurve.modifyPosition(new Point2D(event.getX(), event.getY()),0,0);
		}
		else
		{
			if (dragged != null)
			{
				if (((PanelForMap) panelForMap).getDraggedPressed() &&
						((PanelForMap) panelForMap).containsPoints(new Point2D(event.getX(), event.getY())))
					dragged.modifyAllVertex(event.getX(), event.getY(),0,0);
			}
		}
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
			if (panelForObject.contains(draggedCurve.getX(), draggedCurve.getY()))
				panelForObject.getChildren().remove(draggedCurve);
			if (isInTheMap && !objectToMove)
			{
				Point2D point = new Point2D(draggedCurve.getRealPoints().get(0).getX(), draggedCurve.getRealPoints().get(0).getY());
	
				if (!curveInMap.contains(draggedCurve) && isInTheMap)
				{
					curveInMap.add(draggedCurve);
					draggedCurve.setEffect(null);
				}
				if (panelForObject.contains(point) && !isInTheMap)
				{
					panelForObject.getChildren().remove(draggedCurve);
					if (objectMoveInMapForUndo.size() > 1)
						draggedCurve = (Curve) objectMoveInMapForUndo.get(objectMoveInMapForUndo.size()-1).getValue();
				}
				else
				{
					((PanelForMap) panelForMap).addCurve(draggedCurve);
					try {
						objectMoveInMapForUndo.add(new Pair(point, draggedCurve.clone()));
						((Curve) objectMoveInMapForUndo.get(objectMoveInMapForUndo.size()-1).getValue()).setPointWithExistingObject(draggedCurve);
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				}
				if (!curveToMove)
					draggedCurve.setEffect(null);
			}
		}
		if (dragged != null && !curveOrPolygon) {
			int id = dragged.getIdObject();
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
					dragged = (PolygonObject) objectMoveInMapForUndo.get(objectMoveInMapForUndo.size()-1).getValue();
			}
			else
			{
				((PanelForMap) panelForMap).addObject(dragged);
				try {
					objectMoveInMapForUndo.add(new Pair(point, dragged.clone()));
					((PolygonObject) objectMoveInMapForUndo.get(objectMoveInMapForUndo.size()-1).getValue()).setIdObject(id);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
			if (!objectToMove)
				dragged.setEffect(null);
		}
		
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
				objectToSelect.get(objectToSelect.size()-1).getHeight()+20);
		
		quadCurve.modifyPositionFirst(new Point2D(quadCurve.getX(), quadCurve.getY()));
		
		((PanelForObject) panelForObject).getPanelForRealObject().getChildren().add(quadCurve);
	}
	
	public void saveMap(String nameFile) throws ParserConfigurationException, TransformerException {
		converter = new ConverterMapToXml();
		converter.convertToXml(this, nameFile);
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
	
	public void undo()
	{
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
			deleteObjectForUndo(imageTmp, objectMoveInMapForUndo, true);
			
			boolean objectFoundPolygon = false, objectFoundRedoPolygon = false;
			boolean objectFoundCurve = false, objectFoundRedoCurve = false;
			
			checkBooleanForRepaintObjectCancelled(imageTmp, objectFoundRedoCurve, objectFoundRedoCurve, objectFoundRedoCurve, objectFoundRedoCurve);
			if ((objectFoundPolygon && objectFoundRedoPolygon) || (objectFoundCurve && objectFoundRedoCurve))
			{
				if (imageTmp instanceof PolygonObject)
				{
					for (int i = 0; i < objectInMap.size(); i++)
						if (objectInMap.get(i).vertexEquals((PolygonObject) imageTmp))
							dragged = (PolygonObject) objectInMap.get(i);
//					objectToCancelled.add(new Pair<Point2D, Shape>(point, imageTmp));
//					objectMoveInMapForRedo.add(new Pair<Point2D, Shape>(point, imageTmp));
					actionForUndoRedo(point, imageTmp, objectMoveInMapForUndo, objectMoveInMapForRedo, true, true, true);
				}
				else
				{
					for (Curve curve : curveInMap)
						if (((Curve) imageTmp).vertexEquals(curve))
							draggedCurve = curve;
//					objectToCancelled.add(new Pair<Point2D, Shape>(point, draggedCurve));
//					objectMoveInMapForRedo.add(new Pair<Point2D, Shape>(point, draggedCurve));
					actionForUndoRedo(point, imageTmp, objectMoveInMapForUndo, objectMoveInMapForRedo, true, true, true);
				}
			}
			else
				actionForUndoRedo(point, imageTmp, objectMoveInMapForUndo, objectMoveInMapForRedo, true, true, true);
		}
		else if (objectMoveInMapForUndo.size() == 1)
		{
			objectMoveInMapForRedo.add(new Pair<Point2D, Shape>(objectMoveInMapForUndo.get(0).getKey(), objectMoveInMapForUndo.get(0).getValue()));
			deleteObjectForUndo(objectMoveInMapForUndo.get(0).getValue(), objectMoveInMapForUndo, false);
			objectMoveInMapForUndo.clear();
		}
		
		checkStatusButtonRedo();
		checkStatusButtonUndo();
	}
	
	private void checkBooleanForRepaintObjectCancelled(Shape imageTmp, boolean objectFoundPolygon, boolean objectFoundCurve, boolean objectFoundRedoPolygon, boolean objectFoundRedoCurve) {
		for (Pair<Point2D, Shape> object : objectToCancelled)
		{
			if (imageTmp instanceof PolygonObject && object.getValue() instanceof PolygonObject)
			{
				if (((PolygonObject) object.getValue()).vertexEquals((PolygonObject) imageTmp))
					objectFoundPolygon = true;
			}
			else if (imageTmp instanceof Curve && object.getValue() instanceof Curve)
			{
				if (checkCurvePresent((Curve) imageTmp))
					objectFoundCurve = true;
			}
			
		}
		for (Pair<Point2D, Shape> object : objectMoveInMapForRedo)
		{
			if (imageTmp instanceof PolygonObject && object.getValue() instanceof PolygonObject)
			{
				if (((PolygonObject) object.getValue()).vertexEquals((PolygonObject) imageTmp))
					objectFoundRedoPolygon = true;
			}
			else if (imageTmp instanceof Curve && object.getValue() instanceof Curve)
			{
				if (checkCurvePresent((Curve) imageTmp))
					objectFoundRedoCurve = true;
			}
		}
	}

	public void redo()
	{
		if (dragged != null)
			dragged.setEffect(null);
		
		Point2D point = null;
		Shape imageTmp = null;
		
		if (objectMoveInMapForRedo.size() > 1)
		{
			imageTmp = objectMoveInMapForRedo.get(objectMoveInMapForRedo.size()-1).getValue();
			point = objectMoveInMapForRedo.remove(objectMoveInMapForRedo.size()-1).getKey();
			
			if (!objectMoveInMapForUndo.contains(new Pair<Point2D, Shape>(point, imageTmp)))
				objectMoveInMapForUndo.add(new Pair<Point2D, Shape>(point, imageTmp));
			
			boolean objectFoundPolygon = false, objectFoundRedoPolygon = false;
			boolean objectFoundCurve = false, objectFoundRedoCurve = false;
			
			for (Pair<Point2D, Shape> object : objectToCancelled)
			{
				if (imageTmp instanceof PolygonObject && object.getValue() instanceof PolygonObject)
				{
					if (((PolygonObject) object.getValue()).vertexEquals((PolygonObject) imageTmp))
						objectFoundPolygon = true;
				}
				else if (imageTmp instanceof Curve && object.getValue() instanceof Curve)
				{
					if (checkCurvePresent((Curve) imageTmp))
						objectFoundCurve = true;
				}
				
			}
			for (Pair<Point2D, Shape> object : objectMoveInMapForRedo)
			{
				if (imageTmp instanceof PolygonObject && object.getValue() instanceof PolygonObject)
				{
					if (((PolygonObject) object.getValue()).vertexEquals((PolygonObject) imageTmp))
						objectFoundRedoPolygon = true;
				}
				else if (imageTmp instanceof Curve && object.getValue() instanceof Curve)
				{
					if (checkCurvePresent((Curve) imageTmp))
						objectFoundRedoCurve = true;
				}
			}
			
			if ((objectFoundPolygon && objectFoundRedoPolygon) || (objectFoundCurve && objectFoundRedoCurve))
			{
				if (imageTmp instanceof PolygonObject)
				{
					for (int i = 0; i < objectInMap.size(); i++)
						if (objectInMap.get(i).vertexEquals((PolygonObject) imageTmp))
							dragged = (PolygonObject) objectInMap.get(i);
					removeObject(dragged);
//					objectToCancelled.add(new Pair<Point2D, Shape>(point, imageTmp));
					int i = 0;
					for (Pair<Point2D, Shape> object : objectMoveInMapForRedo) {
						if (object.getValue() instanceof PolygonObject && ((PolygonObject) object.getValue()).vertexEquals(dragged))
							i++;
					}
					if (i > 1)
						actionForUndoRedo(point, imageTmp, objectMoveInMapForRedo, objectMoveInMapForUndo, false, true, true);
					else
						actionForUndoRedo(point, imageTmp, objectMoveInMapForRedo, objectMoveInMapForUndo, false, true, false);
//					objectMoveInMapForUndo.add(new Pair<Point2D, Shape>(point, imageTmp));
				}
				else
				{
					draggedCurve = (Curve) imageTmp;
					removeObject(draggedCurve);
					
					int i = 0;
					for (Pair<Point2D, Shape> object : objectMoveInMapForRedo) {
						if (object.getValue() instanceof Curve && ((Curve) object.getValue()).vertexEquals(draggedCurve))
							i++;
					}
					if (i > 1)
						actionForUndoRedo(point, imageTmp, objectMoveInMapForRedo, objectMoveInMapForUndo, false, true, true);
					else
						actionForUndoRedo(point, imageTmp, objectMoveInMapForRedo, objectMoveInMapForUndo, false, true, false);
//					objectMoveInMapForUndo.add(new Pair<Point2D, Shape>(point, draggedCurve));
				}
			}
			else
				actionForUndoRedo(point, imageTmp, objectMoveInMapForRedo, objectMoveInMapForUndo, false, true, true);
		}
		else if (objectMoveInMapForRedo.size() == 1)
			actionForUndoRedo(point, imageTmp, objectMoveInMapForRedo, objectMoveInMapForUndo, false, true, true);

		checkStatusButtonUndo();
		checkStatusButtonRedo();
	}
	
	private void actionForUndoRedo(Point2D point, Shape imageTmp, ArrayList<Pair<Point2D, Shape>> listForTake, ArrayList<Pair<Point2D, Shape>> listForInsert, boolean undo, boolean insert, boolean redo)
	{
		boolean isAdd = false;
		if (undo)
		{
			deleteObjectForUndo(imageTmp, listForTake, undo);
			point = listForTake.get(listForTake.size()-1).getKey();
			imageTmp = listForTake.get(listForTake.size()-1).getValue();
		}
		else
		{
			deleteObjectForRedo(point, imageTmp, listForTake, undo, redo);
			point = listForTake.get(listForTake.size()-1).getKey();
			imageTmp = listForTake.get(listForTake.size()-1).getValue();
		}
		if (!isAdd && insert)
		{
			if (!listForInsert.contains(new Pair<Point2D, Shape>(point, imageTmp)))
				listForInsert.add(new Pair<Point2D, Shape>(point, imageTmp));
			
			boolean isPresent = false;
			if (imageTmp instanceof PolygonObject)
			{
				dragged = (PolygonObject) imageTmp;
				
				for (int i = 0; i < objectInMap.size(); i++)
					if (objectInMap.get(i).vertexEquals((PolygonObject) imageTmp))
						isPresent = true;
				if (!isPresent)
					addObject(dragged);
				for (Pair<Point2D, Shape> object : objectToCancelled) {
					if (object.getValue() instanceof PolygonObject && ((PolygonObject)object.getValue()).vertexEquals((PolygonObject) imageTmp))
					{
						point = object.getKey();
						dragged = (PolygonObject)object.getValue();
					}
				}
				if (objectToCancelled.contains(new Pair<Point2D, Shape>(point, dragged)) && !redo )
					removeObject(dragged);
			}
			else
			{
				draggedCurve = (Curve) imageTmp;
				
				for (Curve curve : curveInMap)
					if (draggedCurve.vertexEquals(curve))
						isPresent = true;
				if (!isPresent)
					addObject(draggedCurve);
				for (Pair<Point2D, Shape> object : objectToCancelled) {
					if (object.getValue() instanceof Curve && ((Curve)object.getValue()).vertexEquals((Curve) imageTmp))
					{
						point = object.getKey();
						draggedCurve = (Curve)object.getValue();
					}
				}
				if (objectToCancelled.contains(new Pair<Point2D, Shape>(point, draggedCurve)) && !redo )
					removeObject(draggedCurve);
			}
		}
	}

	public boolean checkCurvePresent(Curve draggCurve) {
		for (Curve curve : curveInMap)
			if (draggedCurve.vertexEquals(curve))
				return true;
		return false;
	}
	
	private boolean deleteObjectForRedo(Point2D point, Shape imageTmp,ArrayList<Pair<Point2D, Shape>> listForTake, boolean undo, boolean redo) {
		boolean isAdd = false;
		if (imageTmp instanceof PolygonObject)
		{
			for (int j = 0; j < objectInMap.size(); j++) {
				if (((PolygonObject) imageTmp).getIdObject() == objectInMap.get(j).getIdObject())
				{
					removeObject(objectInMap.get(j));
					addObject((PolygonObject) imageTmp);
					objectMoveInMapForUndo.add(new Pair<Point2D, Shape>(point, imageTmp));
					isAdd = true;
				}
			}
		}
		else
		{
			for (int j = 0; j < curveInMap.size(); j++) {
				if (((Curve) imageTmp).getIdObject() == curveInMap.get(j).getIdObject())
				{
					removeObject(curveInMap.get(j));
//					isAdd = true;
					for (Pair<Point2D, Shape> object : objectToCancelled) {
						if (object.getValue() instanceof Curve && ((Curve)object.getValue()).vertexEquals((Curve) imageTmp))
						{
							point = object.getKey();
							draggedCurve = (Curve)object.getValue();
						}
					}
					if (objectToCancelled.contains(new Pair<Point2D, Shape>(point, draggedCurve)) && !redo)
						removeObject(dragged);
				}
			}
		}
		return isAdd;
	}

	private void deleteObjectForUndo(Shape imageTmp, ArrayList<Pair<Point2D,Shape>> listForTake, boolean undo) {
		if (imageTmp instanceof PolygonObject)
		{
			PolygonObject object = null;
			for (int i = 0; i < objectInMap.size(); i++) {
				if (objectInMap.get(i).vertexEquals((PolygonObject) imageTmp))
				{
					object = objectInMap.get(i);
					removeObject(objectInMap.get(i));
				}
			}
			if (undo)
			{
				for (int i = 0; i < listForTake.size(); i++) {
					if (listForTake.get(i).getValue() instanceof PolygonObject && object != null)
						if (((PolygonObject) listForTake.get(i).getValue()).getIdObject() == object.getIdObject())
							dragged = (PolygonObject) listForTake.get(i).getValue();
				}
			
				if (dragged != null)
					if (!dragged.equals(object) && object != null)
						addObject(dragged);
			}
		}
		else
		{
			Curve object = null;
			for (int i = 0; i < curveInMap.size(); i++) {
				if (curveInMap.get(i).vertexEquals((Curve) imageTmp))
				{
					object = curveInMap.get(i);
					removeObject(curveInMap.get(i));
				}
			}
			
			if (undo)
			{
				for (int i = 0; i < listForTake.size(); i++) {
					if (listForTake.get(i).getValue() instanceof Curve && object != null)
						if (((Curve) listForTake.get(i).getValue()).getIdObject() == object.getIdObject())
							draggedCurve = (Curve) listForTake.get(i).getValue();
				}
				
				if (draggedCurve != null)
					if (!draggedCurve.equals(object) && object != null)
						addObject(draggedCurve);
			}
		}
	}

	private void removeObject(Shape object) {
		if (object instanceof Curve)
		{
			((PanelForMap)panelForMap).removeCurve((Curve) object);
			if (curveInMap.contains(object))
				curveInMap.remove(object);
		}
		else
		{
			((PanelForMap)panelForMap).removeObject((PolygonObject) object);
			if (objectInMap.contains(object))
				objectInMap.remove(object);
		}
	}
	
	private void addObject(Shape object) {
		if (object instanceof Curve)
		{
			((PanelForMap)panelForMap).addCurve((Curve) object);
			if (!curveInMap.contains((Curve) object))
				curveInMap.add((Curve) object);
		}
		else
		{
			((PanelForMap)panelForMap).addObject((PolygonObject) object);
			if (!objectInMap.contains((PolygonObject) object))
				objectInMap.add((PolygonObject) object);
		}
	}
	
	public void removeObject()
	{
		if (!curveOrPolygon)
		{
			if (dragged != null)
			{
				Point2D point = new Point2D(dragged.getPointsVertex().get(0).getX(), dragged.getPointsVertex().get(0).getY());
				objectToCancelled.add(new Pair<Point2D, Shape>(point, dragged));
				objectMoveInMapForUndo.add(new Pair<Point2D, Shape>(point, dragged));
				
				((PanelForMap)panelForMap).removeObject(dragged);
				objectInMap.remove(dragged);
			}
		}
		else
		{
			if (draggedCurve != null)
			{
				Point2D point = new Point2D(draggedCurve.getRealPoints().get(0).getX(), draggedCurve.getRealPoints().get(0).getY());
				objectToCancelled.add(new Pair<Point2D, Shape>(point, draggedCurve));
				objectMoveInMapForUndo.add(new Pair<Point2D, Shape>(point, draggedCurve));
	
				((PanelForMap)panelForMap).removeCurve(draggedCurve);
				curveInMap.remove(draggedCurve);
			}
		}
	}
	
	public void clearMap()
	{
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
}
