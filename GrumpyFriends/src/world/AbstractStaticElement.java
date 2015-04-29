package world;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import element.Element;

public abstract class AbstractStaticElement implements Element {
	
	protected Body body;

	public AbstractStaticElement(float x, float y) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.x=Utils.toPosX(x);
		bodyDef.position.y=Utils.toPosY(y);
		bodyDef.type = BodyType.STATIC;
		body = ((World)AbstractWorld.getInstance()).createBody(bodyDef);
	}
	
	@Override
	public float getX() {
		return Utils.toPixelPosX(body.getPosition().x);
	}
	@Override
	public float getY() {
		return Utils.toPixelPosY(body.getPosition().y);
	}
	
	@Override
	public Body getBody() {
		return body;
	}
	
	
}
