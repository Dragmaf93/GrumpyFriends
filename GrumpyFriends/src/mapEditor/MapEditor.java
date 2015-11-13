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
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
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
        
//        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
//
//			@Override
//			public void handle(MouseEvent event) {
//				System.out.println(event.getX()+" "+event.getY());
//			}
//		});
	}
	
	public void changeCursor(Cursor cursor) {
		scene.setCursor(cursor);
	}
	
	public void setUpperAndCopyImage(MouseEvent event)
	{
		for (Shape image : objectInMap) 
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
		if (!objectToMove && quadCurve.contains(new Point2D(event.getX(), event.getY())))
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
	}
	
	public void moveObjectFromPanelObjectInMap(MouseEvent event)
	{
		if (dragged != null || draggedCurve != null)
    	{
			if (!isInTheMap)
			{
				if (curveToMove)
				{
					draggedCurve.modifyPosition(new Point2D(event.getX(), event.getY()),0,0);
					if (!(panelForObject.getChildren().contains(draggedCurve)))
						panelForObject.getChildren().add(draggedCurve);
				}
				else
				{
					dragged.modifyAllVertex(event.getX(), event.getY(),0,0);
					if (!(panelForObject.getChildren().contains(dragged)))
		        		panelForObject.getChildren().add(dragged);
				}
				isInTheMap = false;
			}
			
			if (((dragged != null && event.getX()-dragged.getWidth() > panelForObject.getPrefWidth()) || 
					(draggedCurve != null && event.getX()-draggedCurve.getWidth() > panelForObject.getPrefWidth()))  && 
					((PanelForMap) panelForMap).contains(new Point2D(event.getX(), event.getY())))
        	{
				if (curveToMove)
				{
					draggedCurve.modifyPosition(new Point2D(event.getX()-panelForObject.getPrefWidth(), event.getY()),panelForObject.getPrefWidth(),0);
					if (!((PanelForMap) panelForMap).containsCurve(draggedCurve))
	            		((PanelForMap) panelForMap).addObject(draggedCurve);
				}
				else
				{
					dragged.modifyAllVertex(event.getX(), event.getY(),panelForObject.getPrefWidth(),0);
					if (!((PanelForMap) panelForMap).containsObject(dragged))
	            		((PanelForMap) panelForMap).addObject(dragged);
				}
            	isInTheMap = true;
        	}
    	}
	}

	public void setUpper(MouseEvent event)
	{
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
			if (draggedCurve != null) 
				draggedCurve.modifyPosition(new Point2D(event.getX(), event.getY()),0,0);
		}
		else
		{
			if (dragged != null)
				if (((PanelForMap) panelForMap).getDraggedPressed() &&
						((PanelForMap) panelForMap).contains(new Point2D(event.getX(), event.getY())))	
					dragged.modifyAllVertex(event.getX(), event.getY(),0,0);
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
		if (draggedCurve != null && isInTheMap && !objectToMove)
		{
			int id = draggedCurve.getIdObject();
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
			} else
				try {
					objectMoveInMapForUndo.add(new Pair(point, draggedCurve.clone()));
					((Curve) objectMoveInMapForUndo.get(objectMoveInMapForUndo.size()-1).getValue()).setIdObject(id);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			
			checkStatusButtonUndo();
			if (!curveToMove)
				draggedCurve.setEffect(null);
		}
		if (dragged != null && !curveToMove) {
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
			} else
				try {
					objectMoveInMapForUndo.add(new Pair(point, dragged.clone()));
					((PolygonObject) objectMoveInMapForUndo.get(objectMoveInMapForUndo.size()-1).getValue()).setIdObject(id);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			
			checkStatusButtonUndo();
			if (!objectToMove)
				dragged.setEffect(null);
		}
		isInTheMap = false;
		objectToMove = false;
		curveToMove = false;
		
		System.out.println(objectMoveInMapForUndo);
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
	
	public ArrayList<Curve> getCurveInMap() {
		return curveInMap;
	}

	public void addObjectInMap(Curve object) {
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
			deleteObject(imageTmp);
//			if (imageTmp instanceof PolygonObject)
//			{
//				((PanelForMap)panelForMap).removeObject(dragged);
//				if (objectInMap.contains(imageTmp))
//					objectInMap.remove(imageTmp);
//			}
//			else
//			{
//				((PanelForMap)panelForMap).removeCurve(draggedCurve);
//				if (curveInMap.contains(imageTmp))
//					curveInMap.remove(imageTmp);
//			}
			
			Pair<Point2D,Shape> p = new Pair<Point2D, Shape>(point,imageTmp);

			if (objectToCancelled.contains(p) && objectMoveInMapForUndo.contains(p))
			{
				if (imageTmp instanceof PolygonObject)
				{
					objectToCancelled.add(new Pair<Point2D, Shape>(point, dragged));
					objectMoveInMapForRedo.add(new Pair<Point2D, Shape>(point, dragged));
					actionForUndoRedo(point, dragged, objectMoveInMapForUndo, objectMoveInMapForRedo);
				}
				else
				{
					objectToCancelled.add(new Pair<Point2D, Shape>(point, dragged));
					objectMoveInMapForRedo.add(new Pair<Point2D, Shape>(point, dragged));
					actionForUndoRedo(point, draggedCurve, objectMoveInMapForUndo, objectMoveInMapForRedo);
				}
			}
			else
				actionForUndoRedo(point, imageTmp, objectMoveInMapForUndo, objectMoveInMapForRedo);
		}
		else if (objectMoveInMapForUndo.size() == 1)
		{
			objectMoveInMapForRedo.add(new Pair<Point2D, Shape>(objectMoveInMapForUndo.get(0).getKey(), objectMoveInMapForUndo.get(0).getValue()));
			deleteObject(objectMoveInMapForUndo.get(0).getValue());
//			if (objectMoveInMapForUndo.get(0).getValue() instanceof PolygonObject)
//				((PanelForMap)panelForMap).removeObject(objectInMap.get(0));
//			else
//				((PanelForMap)panelForMap).removeCurve(curveInMap.get(0));
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
		Shape imageTmp = null;
		
		if (objectMoveInMapForRedo.size() > 1)
		{
			imageTmp = objectMoveInMapForRedo.get(objectMoveInMapForRedo.size()-1).getValue();
			point = objectMoveInMapForRedo.remove(objectMoveInMapForRedo.size()-1).getKey();
			
			if (!objectMoveInMapForUndo.contains(new Pair<Point2D, Shape>(point, imageTmp)))
				objectMoveInMapForUndo.add(new Pair<Point2D, Shape>(point, imageTmp));
			
//			TODO rivedere condizione!
			if (imageTmp != dragged || imageTmp != draggedCurve)
			{
				if (imageTmp instanceof PolygonObject)
					((PanelForMap)panelForMap).removeObject(dragged);
				else
					((PanelForMap)panelForMap).removeCurve(draggedCurve);
					
				if (objectInMap.contains(imageTmp))
					objectInMap.remove(imageTmp);
			}
			Pair<Point2D,Shape> p = new Pair<Point2D, Shape>(point,imageTmp);

			if (objectToCancelled.contains(p) && objectMoveInMapForRedo.contains(p))
			{
				if (imageTmp instanceof PolygonObject)
				{
					((PanelForMap)panelForMap).removeObject(dragged);
					objectInMap.remove(dragged);
					objectToCancelled.add(new Pair<Point2D, Shape>(point, dragged));
					objectMoveInMapForUndo.add(new Pair<Point2D, Shape>(point, dragged));
				}
				else
				{
					((PanelForMap)panelForMap).removeCurve(draggedCurve);
					objectInMap.remove(draggedCurve);
					objectToCancelled.add(new Pair<Point2D, Shape>(point, draggedCurve));
					objectMoveInMapForUndo.add(new Pair<Point2D, Shape>(point, draggedCurve));
				}
			}
			else
				actionForUndoRedo(point, imageTmp, objectMoveInMapForRedo, objectMoveInMapForUndo);
		}
		else if (objectMoveInMapForRedo.size() == 1)
			actionForUndoRedo(point, imageTmp, objectMoveInMapForRedo, objectMoveInMapForUndo);

		checkStatusButtonUndo();
		checkStatusButtonRedo();
	}
	
	private void actionForUndoRedo(Point2D point, Shape imageTmp, ArrayList<Pair<Point2D, Shape>> listForTake, ArrayList<Pair<Point2D, Shape>> listForInsert)
	{
		System.out.println("ENTRO NEL METODO UNDO REDO ");
		deleteObject(imageTmp);
		
		point = listForTake.get(listForTake.size()-1).getKey();
		imageTmp = listForTake.get(listForTake.size()-1).getValue();

		if (!listForInsert.contains(new Pair<Point2D, Shape>(point, imageTmp)))
			listForInsert.add(new Pair<Point2D, Shape>(point, imageTmp));
		
		boolean isPresent = false;
		if (imageTmp instanceof PolygonObject)
		{
			dragged = (PolygonObject) imageTmp;
			
			for (int i = 0; i < objectInMap.size(); i++) {
				if (objectInMap.get(i).vertexEquals((PolygonObject) imageTmp))
					isPresent = true;
			}
			if (!isPresent)
			{
				((PanelForMap)panelForMap).addObject(dragged);
				if (!objectInMap.contains(dragged))
					objectInMap.add(dragged);
			}
		}
		else
		{
			draggedCurve = (Curve) imageTmp;
			
			System.out.println("BEFORE TO ADD");
			for (Curve curve : curveInMap) {
				if ((draggedCurve.getStartX() == curve.getStartX() && draggedCurve.getStartY() == curve.getStartY() &&
						draggedCurve.getControlX() == curve.getControlX() && draggedCurve.getControlY() == curve.getControlY() &&
						draggedCurve.getEndX() == curve.getEndX() && draggedCurve.getEndY() == curve.getEndY()))
					isPresent = true;
			}
			
			if (!isPresent)
			{
				((PanelForMap)panelForMap).addObject(draggedCurve);
				if (!curveInMap.contains(draggedCurve))
					curveInMap.add(draggedCurve);
			}
			
			System.out.println("AFTER ADD");
		}
	}
	
	private void deleteObject(Shape imageTmp) {
		if (imageTmp instanceof PolygonObject)
		{
			PolygonObject object = null;
			for (int i = 0; i < objectInMap.size(); i++) {
				if (objectInMap.get(i).vertexEquals((PolygonObject) imageTmp))
				{
					object = objectInMap.get(i);
					((PanelForMap)panelForMap).removeObject(objectInMap.get(i));
					if (objectInMap.contains(objectInMap.get(i)))
						objectInMap.remove(objectInMap.get(i));
				}
			}
			
			for (int i = 0; i < objectMoveInMapForUndo.size(); i++) {
				if (objectMoveInMapForUndo.get(i).getValue() instanceof PolygonObject && object != null)
				{
					System.out.println("VEDIAMO ID: "+((PolygonObject) objectMoveInMapForUndo.get(i).getValue()).getIdObject()+" "+object.getIdObject());
					if (((PolygonObject) objectMoveInMapForUndo.get(i).getValue()).getIdObject() == object.getIdObject())
						dragged = (PolygonObject) objectMoveInMapForUndo.get(i).getValue();
				}
			}
			
			if (!dragged.equals(object) && object != null)
			{
				((PanelForMap)panelForMap).addObject(dragged);
				if (!objectInMap.contains(dragged))
					objectInMap.add(dragged);
			}
		}
		else
		{
			Curve object = null;
			for (int i = 0; i < curveInMap.size(); i++) {
				if ((((QuadCurve) imageTmp).getStartX() == curveInMap.get(i).getStartX() && ((QuadCurve) imageTmp).getStartY() == curveInMap.get(i).getStartY() &&
						((QuadCurve) imageTmp).getControlX() == curveInMap.get(i).getControlX() && ((QuadCurve) imageTmp).getControlY() == curveInMap.get(i).getControlY() &&
								((QuadCurve) imageTmp).getEndX() == curveInMap.get(i).getEndX() && ((QuadCurve) imageTmp).getEndY() == curveInMap.get(i).getEndY()))
				{
					object = curveInMap.get(i);
					((PanelForMap)panelForMap).removeCurve(curveInMap.get(i));
					if (curveInMap.contains(curveInMap.get(i)))
						curveInMap.remove(curveInMap.get(i));
				}
			}
			
			for (int i = 0; i < objectMoveInMapForUndo.size(); i++) {
				if (objectMoveInMapForUndo.get(i).getValue() instanceof Curve && object != null)
				{
					if (((Curve) objectMoveInMapForUndo.get(i).getValue()).getIdObject() == object.getIdObject())
						draggedCurve = (Curve) objectMoveInMapForUndo.get(i).getValue();
				}
			}
			
			if (!draggedCurve.equals(object) && object != null)
			{
				((PanelForMap)panelForMap).addObject(draggedCurve);
				if (!curveInMap.contains(draggedCurve))
					curveInMap.add(draggedCurve);
			}
		}
	}

	public void removeObject()
	{
		if (!curveOrPolygon)
		{
			Point2D point = new Point2D(dragged.getPointsVertex().get(0).getX(), dragged.getPointsVertex().get(0).getY());
			
			if (!objectMoveInMapForRedo.contains(point))
			{
				objectMoveInMapForRedo.add(new Pair<Point2D, Shape>(point, dragged));
				objectMoveInMapForRedo.add(new Pair<Point2D, Shape>(point, dragged));
			}
			objectToCancelled.add(new Pair<Point2D, Shape>(point, dragged));
			objectMoveInMapForUndo.add(new Pair<Point2D, Shape>(point, dragged));
			
			((PanelForMap)panelForMap).removeObject(dragged);
			objectInMap.remove(dragged);
		}
		else
		{
			Point2D point = new Point2D(draggedCurve.getRealPoints().get(0).getX(), draggedCurve.getRealPoints().get(0).getY());
			
			if (!objectMoveInMapForRedo.contains(point))
			{
				objectMoveInMapForRedo.add(new Pair<Point2D, Shape>(point, draggedCurve));
				objectMoveInMapForRedo.add(new Pair<Point2D, Shape>(point, draggedCurve));
			}
			objectToCancelled.add(new Pair<Point2D, Shape>(point, draggedCurve));
			objectMoveInMapForUndo.add(new Pair<Point2D, Shape>(point, draggedCurve));
			
			((PanelForMap)panelForMap).removeCurve(draggedCurve);
			curveInMap.remove(draggedCurve);
		}
	}
	
	private void remove(Pair<Point2D, Shape> object)
	{
		if (object.getValue() instanceof PolygonObject)
		{
			Point2D point = new Point2D(dragged.getPointsVertex().get(0).getX(), dragged.getPointsVertex().get(0).getY());
			
			if (!objectMoveInMapForRedo.contains(point))
				objectMoveInMapForRedo.add(new Pair<Point2D, Shape>(point, dragged));
			
			((PanelForMap)panelForMap).removeObject(dragged);
			objectInMap.remove(dragged);
		}
		else
		{
			Point2D point = new Point2D(draggedCurve.getRealPoints().get(0).getX(), draggedCurve.getRealPoints().get(0).getY());
			
			if (!objectMoveInMapForRedo.contains(point))
				objectMoveInMapForRedo.add(new Pair<Point2D, Shape>(point, draggedCurve));
			
			((PanelForMap)panelForMap).removeCurve(draggedCurve);
			curveInMap.remove(draggedCurve);
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
	
//	private boolean checkLimitForFocusPanelObject(MouseEvent event) {
//		return (event.getX()-panelForObject.getPrefWidth()+ image.getWidth()) < ((PanelForMap) panelForMap).getRealPane().getPrefWidth()
//    			&& (event.getY()-((PanelForObject) panelForObject).getPanelForSubmit().getPrefHeight()-image.getHeight()/3) < ((PanelForMap) panelForMap).getRealPane().getPrefHeight()/2
//    			&& (event.getY()-image.getHeight()/3) >= 0
//    			&& (event.getX()-panelForObject.getPrefWidth()-image.getWidth()) >= 0;
//	}
	
	private boolean checkLimitForFocusPanelObject(MouseEvent event) {
		return (event.getX()) < ((PanelForMap) panelForMap).getRealPane().getPrefWidth()
    			&& (event.getY()) < ((PanelForMap) panelForMap).getRealPane().getPrefHeight()/2
    			&& (event.getY()) >= 0
    			&& (event.getX()) >= 0;
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
}
