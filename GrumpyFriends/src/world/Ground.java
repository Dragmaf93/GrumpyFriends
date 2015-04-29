package world;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.FixtureDef;

import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

public class Ground extends AbstractStaticElement {

	private final static float WIDTH_JD2BOX = 3f;
	public final static float WIDTH = Utils.toPixelHeight(WIDTH_JD2BOX);
	private static final float HEIGHT_JD2BOX = 3f;
	public final static float HEIGHT = Utils.toPixelHeight(HEIGHT_JD2BOX);

	public Ground(float x, float y) {
		super(x, y);
		
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(WIDTH_JD2BOX, HEIGHT_JD2BOX);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		
		body.createFixture(fixtureDef);
	}

	@Override
	public String toString() {
		return ""+body.getPosition();
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
