package physic;

import java.util.List;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import utils.Vector;

public class PhysicalRectangularObject extends PhysicalStaticObject {

	public PhysicalRectangularObject(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	@Override
	public void buildSelf(World world) {
		body = world.createBody(bodyDef);

		PolygonShape polygonShape = new PolygonShape();

		int count = 4;
		Vec2[] vertices = new Vec2[count];
		vertices[0] = new Vec2(0, 0);
		vertices[1] = new Vec2(start_width, 0);
		vertices[2] = new Vec2(start_width, start_height);
		vertices[3] = new Vec2(0, start_height);
		polygonShape.set(vertices, count);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = 0.1f;

		body.createFixture(fixtureDef);
	}
	
	
	@Override
	public float getY() {
		return body.getWorldPoint(new Vec2(0, start_height)).y;
	}
}
