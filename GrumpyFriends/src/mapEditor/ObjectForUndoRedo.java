package mapEditor;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.util.Pair;

public class ObjectForUndoRedo {

	private MapEditor mapEditor;
	
	private ArrayList<SquarePolygon> objectInMap;
	private ArrayList<Pair<Point2D, SquarePolygon>> objectToCancelled;
	private ArrayList<Pair<Point2D, SquarePolygon>> objectMoveInMapForUndo;
	
	boolean objectFoundPolygon = false, objectFoundRedoPolygon = false;
	boolean objectFoundCurve = false, objectFoundRedoCurve = false;
	
	public ObjectForUndoRedo(MapEditor mapEditor, ArrayList<Pair<Point2D, SquarePolygon>> objectMoveInMapForUndo,
			ArrayList<SquarePolygon> objectInMap, ArrayList<Pair<Point2D, SquarePolygon>> objectToCancelled) {
		this.mapEditor = mapEditor;
		this.objectMoveInMapForUndo = objectMoveInMapForUndo;
		this.objectToCancelled = objectToCancelled;
		
		this.objectInMap = objectInMap;
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
	
	public void checkBooleanForRepaintObjectCancelled(SquarePolygon imageTmp, ArrayList<Pair<Point2D, SquarePolygon>> objectToCancelled) {
		for (Pair<Point2D, SquarePolygon> object : objectToCancelled) {
			if ((object.getValue()).vertexEquals(imageTmp)){
				objectFoundPolygon = true;
			}
		}
	}
	
	
	public void actionForUndoRedo(Point2D point, SquarePolygon imageTmp, ArrayList<Pair<Point2D, SquarePolygon>> listForTake, 
			boolean undo, boolean insert, boolean redo)
	{
//		deleteObjectForUndo(imageTmp, listForTake, undo);
		mapEditor.removeObject(mapEditor.getDragged());
		point = listForTake.get(listForTake.size()-1).getKey();
		imageTmp = listForTake.get(listForTake.size()-1).getValue();
		
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
	

	public void deleteObjectForUndo(SquarePolygon imageTmp, ArrayList<Pair<Point2D,SquarePolygon>> listForTake, 
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
