package world;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.FixtureDef;


public class LinearGround extends AbstractStaticElement implements Ground{


	public LinearGround(float x, float y,float width,float height) {
		super(x, y,width,height);
		
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(width,height);
		
		fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		
		body.createFixture(fixtureDef);
	}

	@Override
	public String toString() {
		return ""+Utils.toPixelPosX(body.getPosition().x);
	}
}
