package world;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import element.Element;

public abstract class AbstractStaticElement implements Element {
	
	protected Body body;
	protected FixtureDef fixtureDef;
	protected BodyDef bodyDef;
	
	public AbstractStaticElement(float x, float y,float height, float width) {
		bodyDef = new BodyDef();
		bodyDef.position.x=x+width;
		bodyDef.position.y=y;
		System.out.println(bodyDef.position.x+ "   "+bodyDef.position.y);
		bodyDef.type = BodyType.STATIC;
		body = ((World)AbstractWorld.getInstance()).createBody(bodyDef);
	}
	@Override
	public BodyDef getBodyDef() {
		return bodyDef;
	}
	@Override
	public FixtureDef getFixtureDef() {
		return fixtureDef;
	}
	
	@Override
	public void setBody(Body b) {
		body=b;
	}
	@Override
	public float getX() {
		return Utils.toPixelPosX(body.getPosition().x)-getWidth()/2;
	}
	@Override
	public float getY() {
		return Utils.toPixelPosY(body.getPosition().y)-getHeight()/2;
	}
	
	@Override
	public Body getBody() {
		return body;
	}
	
	
}
