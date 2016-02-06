package mapEditor;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

public class ObjectMapEditor {

	private MapEditor mapEditor;
	private Pane panelForObject;
	private ScrollPane panelForMap;
	
	private ArrayList<SquarePolygon> objectInMap;
	
	public ObjectMapEditor(MapEditor mapEditor, Pane panelForObject2, ScrollPane panelForMap2,
			ArrayList<SquarePolygon> objectInMap) {
		this.mapEditor = mapEditor;
		this.panelForObject = panelForObject2;
		this.panelForMap = panelForMap2;
		
		this.objectInMap = objectInMap;
	}
	
	public boolean selectObject(ArrayList<SquarePolygon> objectToSelect, double x, double y, 
			Effect borderGlow) {
		for (SquarePolygon image : objectToSelect) {
			if (image.containsPoint(new Point2D(x, y)))
	        {
	            mapEditor.setMouse(x,y);

				mapEditor.setDragged((image).clone());
				((Node) mapEditor.getDragged()).setEffect(borderGlow);
	        	mapEditor.getDragged().computeDistanceVertex();
	        	return true;
	        }
		}
		return false;
	}
	
	public boolean selectObjectToMoveInMap(double x, double y, Effect borderGlow) {
		for (SquarePolygon image : objectInMap) {
			((Node) image).setEffect(null);
			if (((Node) image).contains(new Point2D(x, y))) {
	            mapEditor.setMouse(x, y);
	            
	           	mapEditor.setDragged(image);
	            mapEditor.getDragged().computeDistanceVertex();
	            
	            ((Node) mapEditor.getDragged()).setEffect(borderGlow);
	            return true;
	        }
		}
		return false;
	}
	
	public void moveObjectInPanelObject(SquarePolygon dragged, double x, double y) {
		if (dragged != null)
		{
			dragged.modifyAllVertex(x, y,0,0);
			if (!(panelForObject.getChildren().contains(dragged)))
        		panelForObject.getChildren().add((Node) dragged);
		}
	}
	
	
	public void moveObjectInPanelMap(SquarePolygon dragged, double x, double y) {
		if (dragged instanceof Curve) {
			dragged.modifyPosition(new Point2D(x-panelForObject.getPrefWidth(), 
					y-((PanelForMap) panelForMap).getValueScroll()),panelForObject.getPrefWidth(),0);
		}
		else {
			dragged.modifyAllVertex(x, y-((PanelForMap) panelForMap).getValueScroll(),
					panelForObject.getPrefWidth(),((PanelForMap) panelForMap).getValueScroll());
		}
		if (!((PanelForMap) panelForMap).containsObject(dragged))
			((PanelForMap) panelForMap).addObject(dragged);
	}
	
	
	public void moveObjectInMap(double x, double y,
		SquarePolygon dragged, PanelForMap panelForMap) {
		if (dragged != null) {
			if (panelForMap.getDraggedPressed() && panelForMap.containsPoints(new Point2D(x, y)))
				dragged.modifyAllVertex(x, y,0,0);
		}
	}
	
	public void addPolygonObjectReleased(SquarePolygon dragged, boolean isInTheMap, 
			boolean objectToMove, ArrayList<Pair<Point2D, SquarePolygon>> objectMoveInMapForUndo) {
		int id = dragged.getIdObject();
		Point2D point = new Point2D(dragged.getPointsVertex().get(0).getX(), dragged.getPointsVertex().get(0).getY());
		
		if (!objectInMap.contains(dragged) && isInTheMap) {
			objectInMap.add(dragged);
			((Node) dragged).setEffect(null);
		}
		if (panelForObject.contains(point) && !isInTheMap) {
			panelForObject.getChildren().remove(dragged);
			if (objectMoveInMapForUndo.size() > 1)
				dragged = (SquarePolygon) objectMoveInMapForUndo.get(objectMoveInMapForUndo.size()-1).getValue();
		}
		else {
			((PanelForMap) panelForMap).addObject(dragged);
			objectMoveInMapForUndo.add(new Pair<Point2D, SquarePolygon>(point, dragged.clone()));
			if (dragged instanceof Curve)
				((Curve) objectMoveInMapForUndo.get(objectMoveInMapForUndo.size()-1).getValue()).setPointWithExistingObject((Curve) dragged);
			else
				(objectMoveInMapForUndo.get(objectMoveInMapForUndo.size()-1).getValue()).setIdObject(id);
		}
		if (!objectToMove)
			((Node) dragged).setEffect(null);
	}
	
	public void removePolygon(PanelForMap panelForMap, 
			ArrayList<Pair<Point2D, SquarePolygon>> objectToCancelled, 
			ArrayList<Pair<Point2D, SquarePolygon>> objectMoveInMapForUndo) {
		
		Point2D point = new Point2D(mapEditor.getDragged().getPointsVertex().get(0).getX(), 
				mapEditor.getDragged().getPointsVertex().get(0).getY());
		objectToCancelled.add(new Pair<Point2D, SquarePolygon>(point, mapEditor.getDragged()));
		objectMoveInMapForUndo.add(new Pair<Point2D, SquarePolygon>(point, mapEditor.getDragged()));
		
		panelForMap.removeObject(mapEditor.getDragged());
		objectInMap.remove(mapEditor.getDragged());
	}
	
}
