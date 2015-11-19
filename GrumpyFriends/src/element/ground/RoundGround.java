package element.ground;

import java.util.ArrayList;

import physic.PhysicalCurveObject;
import physic.PhysicalObjectManager;
import utils.Point;
import utils.Utils;

public class RoundGround extends AbstractGround {

	private Point start;
	private Point end;
	private Point control;
	
	private boolean bezierCurve;
	
	public RoundGround(Point start, Point end, Point control) {
		this.start = start;
		this.end = end;
		this.control = control;
		
		bezierCurve = true;
		
		points = new ArrayList<Point>();
		
		for (double t = 0.0; t <= 1; t += 0.05) {

			Point point = new Point();

			point.set(
					(float) ((1 - t) * (1 - t) * start.x + 2 * (1 - t) * t * control.x + t * t * end.x),
					(float) ((1 - t) * (1 - t) * start.y + 2 * (1 - t) * t * control.y + t * t * end.y));
			points.add(point);
		}
		
		physicalObject = new PhysicalCurveObject(points);
		PhysicalObjectManager.getInstance().buildPhysicObject(physicalObject);
	}

	public boolean isABezierCurve(){
		return bezierCurve;
	}
	
	public Point getEnd() {
		return end;
	}
	public Point getStart() {
		return start;
	}
	public Point getControl() {
		return control;
	}
	@Override
	public void setPosition() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSize() {
		// TODO Auto-generated method stub

	}

}
