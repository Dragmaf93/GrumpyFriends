package mapEditor;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.util.Pair;

public class ObjectForUndoRedo {

	private MapEditor mapEditor;
	
	private List<SquarePolygon> objectInMap;
	private List<Pair<Point2D, SquarePolygon>> objectToCancelled;
	private List<Pair<Point2D, SquarePolygon>> objectMoveInMapForUndo;
	
	boolean objectFoundPolygon = false, objectFoundRedoPolygon = false;
	boolean objectFoundCurve = false, objectFoundRedoCurve = false;
	
	public ObjectForUndoRedo(MapEditor mapEditor, List<Pair<Point2D, SquarePolygon>> objectMoveInMapForUndo2,
			List<SquarePolygon> objectInMap2, List<Pair<Point2D, SquarePolygon>> objectToCancelled2) {
		this.mapEditor = mapEditor;
		this.objectMoveInMapForUndo = objectMoveInMapForUndo2;
		this.objectToCancelled = objectToCancelled2;
		
		this.objectInMap = objectInMap2;
	}
	
	public SquarePolygon undoPolygonObject(SquarePolygon imageTmp, Point2D point) {
		
		SquarePolygon dragged = null;
		
		for (int i = 0; i < objectInMap.size(); i++)
			if (objectInMap.get(i).vertexEquals(imageTmp))
				dragged = objectInMap.get(i);
		objectToCancelled.add(new Pair<Point2D, SquarePolygon>(point, imageTmp));
		actionForUndoRedo(point, imageTmp, objectMoveInMapForUndo, true, true, true);
		
		return dragged;
	}
	
	public void checkBooleanForRepaintObjectCancelled(SquarePolygon imageTmp, List<Pair<Point2D, SquarePolygon>> objectToCancelled) {
		for (Pair<Point2D, SquarePolygon> object : objectToCancelled) {
			if ((object.getValue()).vertexEquals(imageTmp)){
				objectFoundPolygon = true;
			}
		}
	}
	
	
	public void actionForUndoRedo(Point2D point, SquarePolygon imageTmp, List<Pair<Point2D, SquarePolygon>> objectMoveInMapForUndo2, 
			boolean undo, boolean insert, boolean redo)
	{
//		deleteObjectForUndo(imageTmp, listForTake, undo);
		mapEditor.removeObject(mapEditor.getDragged());
		point = objectMoveInMapForUndo2.get(objectMoveInMapForUndo2.size()-1).getKey();
		imageTmp = objectMoveInMapForUndo2.get(objectMoveInMapForUndo2.size()-1).getValue();
		
		boolean isPresent = false;
		mapEditor.setDragged(imageTmp);
		
		for (int i = 0; i < objectInMap.size(); i++)
			if (objectInMap.get(i).vertexEquals(imageTmp))
				isPresent = true;
		if (!isPresent)
			mapEditor.addObject(mapEditor.getDragged());
		for (Pair<Point2D, SquarePolygon> object : objectToCancelled) {
			if (((SquarePolygon)object.getValue()).vertexEquals((SquarePolygon) imageTmp))
			{
				point = object.getKey();
				mapEditor.setDragged((SquarePolygon)object.getValue());
			}
		}
		if (objectToCancelled.contains(new Pair<Point2D, SquarePolygon>(point, mapEditor.getDragged())) && !redo )
			mapEditor.removeObject(mapEditor.getDragged());
	}
	

	public void deleteObjectForUndo(SquarePolygon imageTmp, List<Pair<Point2D,SquarePolygon>> listForTake, 
			boolean undo) {
		SquarePolygon object = null;
		for (int i = 0; i < objectInMap.size(); i++) {
			if (objectInMap.get(i).vertexEquals(imageTmp)) {
				
				object = (SquarePolygon) objectInMap.get(i);
				mapEditor.removeObject((SquarePolygon) objectInMap.get(i));
			}
		}
		
		for (int i = 0; i < listForTake.size(); i++) {
			if (object != null)
				if (((SquarePolygon) listForTake.get(i).getValue()).getIdObject() == object.getIdObject())
					mapEditor.setDragged((SquarePolygon) listForTake.get(i).getValue());
		}
	
		if (mapEditor.getDragged() != null)
			if (!mapEditor.getDragged().equals(object) && object != null)
				mapEditor.addObject(mapEditor.getDragged());
	}
	
	public boolean getObjectFoundPolygon() {
		return objectFoundPolygon;
	}
	public boolean getObjectFoundRedoPolygon() {
		return objectFoundRedoPolygon;
	}
	public boolean getObjectFoundCurve() {
		return objectFoundCurve;
	}
	public boolean getObjectFoundRedoCurve() {
		return objectFoundRedoCurve;
	}
	
}
