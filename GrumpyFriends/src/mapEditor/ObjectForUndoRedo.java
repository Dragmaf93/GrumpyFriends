package mapEditor;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;
import javafx.util.Pair;

public class ObjectForUndoRedo {

	private MapEditor mapEditor;
	
	private ArrayList<Pair<Point2D, Shape>> objectMoveInMapForUndo;
	private ArrayList<Pair<Point2D, Shape>> objectMoveInMapForRedo;
	
	private ArrayList<PolygonObject> objectInMap;
	private ArrayList<Curve> curveInMap;
	private ArrayList<Pair<Point2D, Shape>> objectToCancelled;
	
	boolean objectFoundPolygon = false, objectFoundRedoPolygon = false;
	boolean objectFoundCurve = false, objectFoundRedoCurve = false;
	
	public ObjectForUndoRedo(MapEditor mapEditor, ArrayList<Pair<Point2D, Shape>> objectMoveInMapForUndo,
			ArrayList<Pair<Point2D, Shape>> objectMoveInMapForRedo, ArrayList<Curve> curveInMap,
			ArrayList<PolygonObject> objectInMap, ArrayList<Pair<Point2D, Shape>> objectToCancelled) {
		this.mapEditor = mapEditor;
		this.objectMoveInMapForRedo = objectMoveInMapForRedo;
		this.objectMoveInMapForUndo = objectMoveInMapForUndo;
		this.objectToCancelled = objectToCancelled;
		
		this.curveInMap = curveInMap;
		this.objectInMap = objectInMap;
	}
	
	public PolygonObject undoPolygonObject(Shape imageTmp, Point2D point) {
		
		PolygonObject dragged = null;
	
		for (int i = 0; i < objectInMap.size(); i++)
			if (objectInMap.get(i).vertexEquals((PolygonObject) imageTmp))
				dragged = (PolygonObject) objectInMap.get(i);
//			objectToCancelled.add(new Pair<Point2D, Shape>(point, imageTmp));
//			objectMoveInMapForRedo.add(new Pair<Point2D, Shape>(point, imageTmp));
		actionForUndoRedo(point, imageTmp, objectMoveInMapForUndo, objectMoveInMapForRedo, true, true, true);
		
		return dragged;
	}
	
	public Curve undoCurveObject(Shape imageTmp, Point2D point) {
		Curve draggedCurve = null;
		for (Curve curve : curveInMap)
			if (((Curve) imageTmp).vertexEquals(curve))
				draggedCurve = curve;
//		objectToCancelled.add(new Pair<Point2D, Shape>(point, draggedCurve));
//		objectMoveInMapForRedo.add(new Pair<Point2D, Shape>(point, draggedCurve));
		actionForUndoRedo(point, imageTmp, objectMoveInMapForUndo, objectMoveInMapForRedo, true, true, true);
		return draggedCurve;
	}
	
	
	public void checkBooleanForRepaintObjectCancelled(Shape imageTmp, ArrayList<Pair<Point2D, Shape>> objectToCancelled) {
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
	
	public boolean checkCurvePresent(Curve draggedCurve) {
		for (Curve curve : curveInMap)
			if (draggedCurve.vertexEquals(curve))
				return true;
		return false;
	}
	
	
	public void removePolygonObjectAndRedo(ArrayList<PolygonObject> objectInMap, 
			Shape imageTmp, PolygonObject dragged, Point2D point) {
		for (int i = 0; i < objectInMap.size(); i++)
			if (objectInMap.get(i).vertexEquals((PolygonObject) imageTmp))
				dragged = (PolygonObject) objectInMap.get(i);
		mapEditor.removeObject(dragged);
//		objectToCancelled.add(new Pair<Point2D, Shape>(point, imageTmp));
		int i = 0;
		for (Pair<Point2D, Shape> object : objectMoveInMapForRedo) {
			if (object.getValue() instanceof PolygonObject && ((PolygonObject) object.getValue()).vertexEquals(dragged))
				i++;
		}
		if (i > 1)
			actionForUndoRedo(point, imageTmp, objectMoveInMapForRedo, objectMoveInMapForUndo, false, true, true);
		else
			actionForUndoRedo(point, imageTmp, objectMoveInMapForRedo, objectMoveInMapForUndo, false, true, false);
//		objectMoveInMapForUndo.add(new Pair<Point2D, Shape>(point, imageTmp));
	}
	
	public void removeCurveAndRedo(Curve draggedCurve, Shape imageTmp, Point2D point) {
		draggedCurve = (Curve) imageTmp;
		mapEditor.removeObject(draggedCurve);
		
		int i = 0;
		for (Pair<Point2D, Shape> object : objectMoveInMapForRedo) {
			if (object.getValue() instanceof Curve && ((Curve) object.getValue()).vertexEquals(draggedCurve))
				i++;
		}
		if (i > 1)
			actionForUndoRedo(point, imageTmp, objectMoveInMapForRedo, objectMoveInMapForUndo, false, true, true);
		else
			actionForUndoRedo(point, imageTmp, objectMoveInMapForRedo, objectMoveInMapForUndo, false, true, false);
//		objectMoveInMapForUndo.add(new Pair<Point2D, Shape>(point, draggedCurve));
	}
	
	
	public void actionForUndoRedo(Point2D point, Shape imageTmp, ArrayList<Pair<Point2D, Shape>> listForTake, 
			ArrayList<Pair<Point2D, Shape>> listForInsert, boolean undo, boolean insert, boolean redo)
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
				mapEditor.setDragged((PolygonObject) imageTmp);
				
				for (int i = 0; i < objectInMap.size(); i++)
					if (objectInMap.get(i).vertexEquals((PolygonObject) imageTmp))
						isPresent = true;
				if (!isPresent)
					mapEditor.addObject(mapEditor.getDragged());
				for (Pair<Point2D, Shape> object : objectToCancelled) {
					if (object.getValue() instanceof PolygonObject && ((PolygonObject)object.getValue()).vertexEquals((PolygonObject) imageTmp))
					{
						point = object.getKey();
						mapEditor.setDragged((PolygonObject)object.getValue());
					}
				}
				if (objectToCancelled.contains(new Pair<Point2D, Shape>(point, mapEditor.getDragged())) && !redo )
					mapEditor.removeObject(mapEditor.getDragged());
			}
			else
			{
				mapEditor.setDraggedCurve((Curve) imageTmp);
				
				for (Curve curve : curveInMap)
					if (mapEditor.getDraggedCurve().vertexEquals(curve))
						isPresent = true;
				if (!isPresent)
					mapEditor.addObject(mapEditor.getDraggedCurve());
				for (Pair<Point2D, Shape> object : objectToCancelled) {
					if (object.getValue() instanceof Curve && 
							((Curve)object.getValue()).vertexEquals((Curve) imageTmp))
					{
						point = object.getKey();
						mapEditor.setDraggedCurve((Curve)object.getValue());
					}
				}
				if (objectToCancelled.contains(new Pair<Point2D, Shape>(point, mapEditor.getDraggedCurve())) 
						&& !redo )
					mapEditor.removeObject(mapEditor.getDraggedCurve());
			}
		}
	}
	

	public boolean deleteObjectForRedo(Point2D point, Shape imageTmp,
			ArrayList<Pair<Point2D, Shape>> listForTake, boolean undo, boolean redo) {
		boolean isAdd = false;
		if (imageTmp instanceof PolygonObject)
		{
			for (int j = 0; j < objectInMap.size(); j++) {
				if (((PolygonObject) imageTmp).getIdObject() == objectInMap.get(j).getIdObject())
				{
					mapEditor.removeObject(objectInMap.get(j));
					mapEditor.addObject((PolygonObject) imageTmp);
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
					mapEditor.removeObject(curveInMap.get(j));
//					isAdd = true;
					for (Pair<Point2D, Shape> object : objectToCancelled) {
						if (object.getValue() instanceof Curve && 
								((Curve)object.getValue()).vertexEquals((Curve) imageTmp))
						{
							point = object.getKey();
							mapEditor.setDraggedCurve((Curve)object.getValue());
						}
					}
					if (objectToCancelled.contains(new Pair<Point2D, Shape>(point,
							mapEditor.getDraggedCurve())) && !redo)
						mapEditor.removeObject(mapEditor.getDraggedCurve());
				}
			}
		}
		return isAdd;
	}
	
	public void deleteObjectForUndo(Shape imageTmp, ArrayList<Pair<Point2D,Shape>> listForTake, 
			boolean undo) {
		if (imageTmp instanceof PolygonObject)
		{
			PolygonObject object = null;
			for (int i = 0; i < objectInMap.size(); i++) {
				if (objectInMap.get(i).vertexEquals((PolygonObject) imageTmp))
				{
					object = objectInMap.get(i);
					mapEditor.removeObject(objectInMap.get(i));
				}
			}
			if (undo)
			{
				for (int i = 0; i < listForTake.size(); i++) {
					if (listForTake.get(i).getValue() instanceof PolygonObject && object != null)
						if (((PolygonObject) listForTake.get(i).getValue()).getIdObject() == object.getIdObject())
							mapEditor.setDragged((PolygonObject) listForTake.get(i).getValue());
				}
			
				if (mapEditor.getDragged() != null)
					if (!mapEditor.getDragged().equals(object) && object != null)
						mapEditor.addObject(mapEditor.getDragged());
			}
		}
		else
		{
			Curve object = null;
			for (int i = 0; i < curveInMap.size(); i++) {
				if (curveInMap.get(i).vertexEquals((Curve) imageTmp))
				{
					object = curveInMap.get(i);
					mapEditor.removeObject(curveInMap.get(i));
				}
			}
			
			if (undo)
			{
				for (int i = 0; i < listForTake.size(); i++) {
					if (listForTake.get(i).getValue() instanceof Curve && object != null)
						if (((Curve) listForTake.get(i).getValue()).getIdObject() == object.getIdObject())
							mapEditor.setDraggedCurve((Curve) listForTake.get(i).getValue());
				}
				
				if (mapEditor.getDraggedCurve() != null)
					if (!mapEditor.getDraggedCurve().equals(object) && object != null)
						mapEditor.addObject(mapEditor.getDraggedCurve());
			}
		}
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
