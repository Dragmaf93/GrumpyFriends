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
	
	protected float height;
	protected float width;
	
	public AbstractStaticElement(float x, float y,float width, float height) {
		bodyDef = new BodyDef();
		bodyDef.position.x=x;
		bodyDef.position.y=y;
//		System.out.println(bodyDef.position.x+ "   "+bodyDef.position.y);
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
		return bodyDef.position.x;
	}
	@Override
	public float getY() {
		return bodyDef.position.y;
	}
	
	@Override
	public Body getBody() {
		return body;
	}
	@Override
	public float getHeight() {
		return height;
	}
	@Override
	public float getWidth() {
		return width;
	}
	
}
