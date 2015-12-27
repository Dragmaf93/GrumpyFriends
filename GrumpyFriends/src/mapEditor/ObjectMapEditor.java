package mapEditor;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.util.Pair;


public class ObjectMapEditor {

	private MapEditor mapEditor;
	private Pane panelForObject;
	private ScrollPane panelForMap;
	
	private ArrayList<PolygonObject> objectInMap;
	
	public ObjectMapEditor(MapEditor mapEditor, Pane panelForObject2, ScrollPane panelForMap2,
			ArrayList<PolygonObject> objectInMap) {
		this.mapEditor = mapEditor;
		this.panelForObject = panelForObject2;
		this.panelForMap = panelForMap2;
		
		this.objectInMap = objectInMap;
	}
	
	public boolean selectObject(ArrayList<PolygonObject> objectToSelect, double x, double y, 
			Effect borderGlow, Curve quadCurve) {
		for (PolygonObject image : objectToSelect) {
			if (image.containsPoint(new Point2D(x, y)))
	        {
	            mapEditor.setMouse(x,y);

	            try {
					mapEditor.setDragged(image.clone());
					mapEditor.getDragged().setEffect(borderGlow);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
	        	mapEditor.getDragged().computeDistanceVertex();
	        	return true;
	        }
		}
		if (quadCurve.contains(new Point2D(x, y)))
		{
			mapEditor.setMouse(x, y);

            try {
				mapEditor.setDraggedCurve(quadCurve.clone());
				
				mapEditor.getDraggedCurve().computeDistanceVertex();
				mapEditor.getDraggedCurve().setEffect(borderGlow);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
            return false;
		}
		return false;
	}
	
	public boolean selectObjectToMoveInMap(double x, double y, Effect borderGlow, 
			ArrayList<Curve> curveInMap) {
		for (PolygonObject image : objectInMap) {
			image.setEffect(null);
			if (image.contains(new Point2D(x, y)))
	        {
	            mapEditor.setMouse(x, y);
	            
	           	mapEditor.setDragged((PolygonObject) image);
	            mapEditor.getDragged().computeDistanceVertex();
	            
	            mapEditor.getDragged().setEffect(borderGlow);
	            return true;
	        }
		}
		
		for (Curve curve : curveInMap) {
			curve.setEffect(null);
			if (curve.contains(new Point2D(x, y)))
			{
				mapEditor.setMouse(x, y);

				mapEditor.setDraggedCurve(curve);
				mapEditor.getDraggedCurve().computeDistanceVertex();
				mapEditor.getDraggedCurve().setEffect(borderGlow);
			}
			return false;
		}
		return false;
	}
	
	public void moveCurveInPanelObject(PolygonObject dragged, Curve draggedCurve, double x, double y) {
		if (draggedCurve != null)
		{
			draggedCurve.modifyPosition(new Point2D(x, y),0,0);
			if (!(panelForObject.getChildren().contains(draggedCurve)))
				panelForObject.getChildren().add(draggedCurve);
		}
		else if (dragged != null)
		{
			dragged.modifyAllVertex(x, y,0,0);
			if (!(panelForObject.getChildren().contains(dragged)))
        		panelForObject.getChildren().add(dragged);
		}
	}
	
	
	public void moveCurveInPanelMap(PolygonObject dragged, Curve draggedCurve, double x, double y) {
		if (draggedCurve != null) {
			draggedCurve.modifyPosition(new Point2D(x-panelForObject.getPrefWidth(), 
					y-((PanelForMap) panelForMap).getValueScroll()),panelForObject.getPrefWidth(),0);
			
			if (!((PanelForMap) panelForMap).containsCurve(draggedCurve))
	    		((PanelForMap) panelForMap).addCurve(draggedCurve);
		}
		else if (dragged != null) {
			dragged.modifyAllVertex(x, y-((PanelForMap) panelForMap).getValueScroll(),
					panelForObject.getPrefWidth(),((PanelForMap) panelForMap).getValueScroll());
			
			if (!((PanelForMap) panelForMap).containsObject(dragged))
        		((PanelForMap) panelForMap).addObject(dragged);
		}
	}
	
	
	public void moveObjectInMap(double x, double y, Curve draggedCurve, 
			PolygonObject dragged, PanelForMap panelForMap) {
		if (draggedCurve != null &&
				panelForMap.containsPoints(new Point2D(x, y)))
			draggedCurve.modifyPosition(new Point2D(x, y),0,0);
		else if (dragged != null)
		{
			if (panelForMap.getDraggedPressed() && panelForMap.containsPoints(new Point2D(x, y)))
				dragged.modifyAllVertex(x, y,0,0);
		}
	}
	
	
	public void addCurveRelease(Curve draggedCurve, ArrayList<Curve> curveInMap, boolean isInTheMap, 
			ArrayList<Pair<Point2D, Shape>> objectMoveInMapForUndo, boolean curveToMove) {
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
	
	
	public void addPolygonObjectReleased(PolygonObject dragged, boolean isInTheMap, 
			ArrayList<Pair<Point2D, Shape>> objectMoveInMapForUndo, boolean objectToMove) {
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
	
	public void removePolygon(PanelForMap panelForMap, ArrayList<Pair<Point2D, Shape>> objectToCancelled,
			ArrayList<Pair<Point2D, Shape>> objectMoveInMapForUndo) {
		
		Point2D point = new Point2D(mapEditor.getDragged().getPointsVertex().get(0).getX(), 
				mapEditor.getDragged().getPointsVertex().get(0).getY());
		objectToCancelled.add(new Pair<Point2D, Shape>(point, mapEditor.getDragged()));
		objectMoveInMapForUndo.add(new Pair<Point2D, Shape>(point, mapEditor.getDragged()));
		
		panelForMap.removeObject(mapEditor.getDragged());
		objectInMap.remove(mapEditor.getDragged());
	}
	
	public void removeCurve(PanelForMap panelForMap, ArrayList<Pair<Point2D, Shape>> objectToCancelled,
			ArrayList<Pair<Point2D, Shape>> objectMoveInMapForUndo, ArrayList<Curve> curveInMap) {
		
		Point2D point = new Point2D(mapEditor.getDraggedCurve().getRealPoints().get(0).getX(),
				mapEditor.getDraggedCurve().getRealPoints().get(0).getY());
		objectToCancelled.add(new Pair<Point2D, Shape>(point, mapEditor.getDraggedCurve()));
		objectMoveInMapForUndo.add(new Pair<Point2D, Shape>(point, mapEditor.getDraggedCurve()));

		((PanelForMap)panelForMap).removeCurve(mapEditor.getDraggedCurve());
		curveInMap.remove(mapEditor.getDraggedCurve());
	}
	
}
