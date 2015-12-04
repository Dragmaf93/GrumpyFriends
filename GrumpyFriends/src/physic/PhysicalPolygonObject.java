package physic;

import java.util.List;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import utils.Point;
import utils.Utils;

public class PhysicalPolygonObject implements PhysicalObject {

	private Vec2[] vertices;
	
	private Body body;
	private BodyDef bodyDef;
	
	public PhysicalPolygonObject(List<Point> points) {
		this.vertices = new Vec2[points.size()];
	
		for (int i = 0; i<points.size();i++) {
			this.vertices[i]=new Vec2((float)Utils.xFromJavaFxToJbox2d(points.get(i).x),
					(float) Utils.yFromJavaFxToJbox2d( points.get(i).y));
		}
		
		bodyDef = new BodyDef();
	}
	
	@Override
	public void buildSelf(World world) {
		body = world.createBody(bodyDef);
		
//		PolygonShape polygonShape = new PolygonShape();
//
//		FixtureDef fixtureDef = new FixtureDef();
//		fixtureDef.shape = polygonShape;
//		fixtureDef.density = 1.0f;
//		fixtureDef.friction = 0.3f;
//		fixtureDef.restitution = 0.1f;

//		body.createFixture(fixtureDef);
		
		for(int i = 1 ; i < vertices.length; i++)
		{
			EdgeShape shape = new EdgeShape();
			shape.set(vertices[i-1], vertices[i]);
			body.createFixture(shape,1.0f).setFriction(1);
			
		}
		
		EdgeShape shape = new EdgeShape();
		shape.set(vertices[0], vertices[vertices.length-1]);
		body.createFixture(shape, 1.0f).setFriction(1);
	}
	
	@Override
	public Body getBody() {
		return body;
	}

	@Override
	public float getX() {
		return vertices[0].x;
	}

	@Override
	public float getY() {
		return vertices[0].y;
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
