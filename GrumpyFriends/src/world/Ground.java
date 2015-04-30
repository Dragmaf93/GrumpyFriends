package world;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.FixtureDef;

import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

public class Ground extends AbstractStaticElement {

	public final static float WIDTH_JD2BOX = 3f;
	public final static float WIDTH = (Utils.toPixelHeight(WIDTH_JD2BOX))*2;
	public static final float HEIGHT_JD2BOX = 3f;
	public final static float HEIGHT = (Utils.toPixelHeight(HEIGHT_JD2BOX))*2;

	public Ground(float x, float y) {
		super(x, y,HEIGHT_JD2BOX,WIDTH_JD2BOX);
		
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(WIDTH_JD2BOX, HEIGHT_JD2BOX);
		
		fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		System.out.println(bodyDef.position);
		
		body.createFixture(fixtureDef);
	}

	@Override
	public String toString() {
		return ""+Utils.toPixelPosX(body.getPosition().x);
	}

	@Override
	public float getHeight() {
		return HEIGHT;
	}

	@Override
	public float getWidth() {
		return WIDTH;
	}

}
