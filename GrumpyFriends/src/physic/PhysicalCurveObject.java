package physic;

import java.util.List;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import utils.Point;
import utils.Utils;

public class PhysicalCurveObject implements PhysicalObject {

	private Vec2[] points;

	private Body body;
	private BodyDef bodyDef;

	public PhysicalCurveObject(List<Point> points) {

		this.points = new Vec2[points.size()];
		
		for (int i = 0; i<points.size();i++) {
			this.points[i]=new Vec2((float)Utils.xFromJavaFxToJbox2d(points.get(i).x),
					(float) Utils.yFromJavaFxToJbox2d( points.get(i).y));
		}
		bodyDef = new BodyDef();

	}

	@Override
	public void buildSelf(World world) {
		body = world.createBody(bodyDef);
		
		for (int i = 1; i < points.length;i++) {
			EdgeShape shape = new EdgeShape();
			shape.set(points[i-1],points[i]);
			body.createFixture(shape, 1.0f);
		}
		EdgeShape shape = new EdgeShape();
		shape.set(points[0], points[points.length-1]);
		body.createFixture(shape,1.0f);
	}
	//

	@Override
	public Body getBody() {
		return body;
	}

	@Override
	public float getX() {
		return 0;
	}

	@Override
	public float getY() {
		return 0;
	}

	@Override
	public float getHeight() {
		return 0;
	}

	@Override
	public float getWidth() {
		return 0;
	}

}
