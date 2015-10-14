package physic;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class PhysicalTriangularObject extends PhysicalStaticObject{

	private float angleRotation;
	
	public PhysicalTriangularObject(float x, float y, float width, float height,float angleRotation) {
		super(x, y, width, height);
		this.angleRotation=angleRotation;
	}

	@Override
	public void buildSelf(World world) {
		body = world.createBody(bodyDef);
		
		PolygonShape polygonShape = new PolygonShape();
		int count = 3;
		Vec2[] vertices = new Vec2[count];
		vertices[0]=new Vec2(0,0);
		vertices[1]=new Vec2(start_width,0);
		vertices[2]=new Vec2(0,start_height);
		polygonShape.set(vertices, count);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = 0.1f;
		

		body.createFixture(fixtureDef);
		body.setTransform(body.getPosition(), this.angleRotation);
	}
	
	
}
