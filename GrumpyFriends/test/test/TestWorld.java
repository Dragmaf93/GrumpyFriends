package test;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import element.character.Character;
import world.InclinedGround;

public class TestWorld implements world.World{

	private World world;
	
	public TestWorld() {
		world = TestGrumpyFriends.getInstance().getWorld();
	}
	
	@Override
	public void addCharacter(Character character) {
		Body b= world.createBody(character.getBodyDef());
//		FixtureDef f1 = new FixtureDef();
//		f1.shape = character.getBody().getFixtureList().getShape();
//
//		setCachedCameraPos(b.getPosition());
//
//		Fixture f = character.getBody().getFixtureList().getNext();
//
//		FixtureDef f2 = new FixtureDef();
//		f2.shape = f.getShape();
//		b.createFixture(f1).setUserData("Eliana");
//		;
//		b.createFixture(f2);
//
//		character.setBody(b);
//		String[] names = new String[1];
//		names[0] = "Eliana";
//		world.addCharacter(character);
//		getWorld().setContactListener(new CharacterContactListener(character));
	}
	
	@Override
	public void addLinearGround(float x, float y, float width, float height) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.x=x;
		bodyDef.position.y=y;
		bodyDef.type = BodyType.STATIC;

		Body body = world.createBody(bodyDef);
		
		PolygonShape polygonShape = new PolygonShape();
//		polygonShape.setAsBox(width,height);
		polygonShape.m_vertexCount=4;
		polygonShape.m_vertices[0].set(0,0);
		polygonShape.m_vertices[1].set(width,0);
		polygonShape.m_vertices[2].set(width,height);
		polygonShape.m_vertices[3].set(0,height);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		body.createFixture(fixtureDef);
		System.out.println("Rectangle"+body.getPosition());
	}

	@Override
	public void addInclinedGround(float x, float y, float width, float height, int angleRotation) {
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.x=x;
		bodyDef.position.y=y;
		bodyDef.type = BodyType.STATIC;

		Body body = world.createBody(bodyDef);
		
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.m_vertexCount = 3;
		polygonShape.m_vertices[0].set(0, 0);
		polygonShape.m_vertices[1].set(width, 0);
		polygonShape.m_vertices[2].set(0, height);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		
		body.createFixture(fixtureDef);
		body.setTransform(body.getPosition(), 
		InclinedGround.getRadiantAngleRotation(angleRotation));
		System.out.println("Triangle"+body.getPosition());

	}

	@Override
	public void setDimension(float width, float height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

}
