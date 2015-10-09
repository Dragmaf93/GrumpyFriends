package world;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.FixtureDef;

public class InclinedGround extends AbstractStaticElement implements Ground {

	private final static float ROTATE_90 = 1.570796326795f;
	private final static float ROTATE_180 = 3.14159265359f;
	private final static float ROTATE_270 = 4.712388980385f;
	private final static float ROTATE_360 = 0.0f;

	private float angleRotation;

	public InclinedGround(float x, float y, float width, float height, int angleRotationDegree) {
		super(x, y, height, width);
		this.height = height;
		this.width = width;

		setAngleRotation(angleRotationDegree);
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.m_vertexCount = 3;
		polygonShape.m_vertices[0].set(0, 0);
		polygonShape.m_vertices[1].set(width, 0);
		polygonShape.m_vertices[2].set(0, height);

		fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;

		body.createFixture(fixtureDef);

		body.setTransform(body.getPosition(), this.angleRotation);
	}

	public InclinedGround(float x, float y, float width, float height, float angleRotationRadiant) {
		super(x, y, height, width);
		this.height = height;
		this.width = width;

		setAngleRototation(angleRotationRadiant);
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.m_vertexCount = 3;
		polygonShape.m_vertices[0].set(0, 0);
		polygonShape.m_vertices[1].set(width, 0);
		polygonShape.m_vertices[2].set(0, height);

		fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;

		body.createFixture(fixtureDef);
		body.setTransform(body.getPosition(), this.angleRotation);
	}

	private void setAngleRototation(float radiants) {
		this.angleRotation = radiants;
	}

	public void setAngleRotation(int degree) {
		switch (degree) {
		case 90:
			angleRotation = ROTATE_90;
			break;
		case 180:
			angleRotation = ROTATE_180;
			break;
		case 270:
			angleRotation = ROTATE_270;
			break;
		case 0:
		default:
			angleRotation = ROTATE_360;
			break;
		}
	}
	
	public static float getRadiantAngleRotation(int degree) {
		switch (degree) {
		case 90:
			return ROTATE_90;
		case 180:
			return  ROTATE_180;
		case 270:
			return ROTATE_270;
		case 0:
		default:
			return ROTATE_360;
	}
		
	}

}
