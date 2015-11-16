package element.ground;

import java.util.List;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import physic.PhysicalObject;
import utils.Vector;

public class PhysicalPolygonObject implements PhysicalObject {

	private Vec2[] vertices;
	private List<Vector> points;
	private float start_x,start_y;
	
	private Body body;
	private BodyDef bodyDef;
	
	public PhysicalPolygonObject(List<Vector> points) {
		this.vertices = new Vec2[points.size()];
		this.points = points;
	
		for (int i = 0; i<points.size();i++) {
			this.vertices[i]=new Vec2(points.get(i).x, points.get(i).y);
		}
		
		bodyDef = new BodyDef();
//		start_x=bodyDef.position.x=this.points[0].x;
//		start_y=bodyDef.position.y=this.points[0].y;
		
		
		
	}
	
	@Override
	public void buildSelf(World world) {
		body = world.createBody(bodyDef);
		
		PolygonShape polygonShape = new PolygonShape();

//		Vec2[] vertices = new Vec2[count];
//		vertices[0] = new Vec2(0, 0);
//		vertices[1] = new Vec2(start_width, 0);
//		vertices[2] = new Vec2(start_width, start_height);
//		vertices[3] = new Vec2(0, start_height);
		System.out.println(vertices);
		polygonShape.set(vertices, vertices.length);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = 0.1f;

		body.createFixture(fixtureDef);
	}
	
	public List<Vector> getPoints(){
		return points;
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
