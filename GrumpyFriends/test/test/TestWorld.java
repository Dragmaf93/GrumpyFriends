package test;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import element.character.Character;
import world.CharacterContactListener;
import world.InclinedGround;

public class TestWorld implements world.World{

	public final static Vec2 GRAVITY = new Vec2(0,-9.8f);

	private World world;
	private List<Character> characters;
	
	public TestWorld() {
		world = TestGrumpyFriends.getInstance().getWorld();
		world.setGravity(GRAVITY);
	}
	@Override
	public void addCharacter(Character character) {
		if(characters==null)
			characters= new ArrayList<Character>(8);
	
		this.characters.add(character);
	}
	
	public void complete(){
		world.setContactListener(new CharacterContactListener(characters));
	}
	@Override
	public void addLinearGround(float x, float y, float width, float height) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.x=x;
		bodyDef.position.y=y;
		bodyDef.type = BodyType.STATIC;
		System.out.println(world);
		Body body = world.createBody(bodyDef);
		
		PolygonShape polygonShape = new PolygonShape();

		int count = 4;
		Vec2[] vertices = new Vec2[count];
		vertices[0]=new Vec2(0,0);
		vertices[1]=new Vec2(width,0);
		vertices[2]=new Vec2(width,height);
		vertices[3]=new Vec2(0,height);
		polygonShape.set(vertices, count);

		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = 0.1f;
	
		body.createFixture(fixtureDef);
	}

	@Override
	public void addInclinedGround(float x, float y, float width, float height, int angleRotation) {
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.x=x;
		bodyDef.position.y=y;
		bodyDef.type = BodyType.STATIC;

		Body body = world.createBody(bodyDef);
		
		PolygonShape polygonShape = new PolygonShape();
		int count = 3;
		Vec2[] vertices = new Vec2[count];
		vertices[0]=new Vec2(0,0);
		vertices[1]=new Vec2(width,0);
		vertices[2]=new Vec2(0,height);
		polygonShape.set(vertices, count);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = 0.1f;
		
	
		body.createFixture(fixtureDef);
		body.setTransform(body.getPosition(), 
		InclinedGround.getRadiantAngleRotation(angleRotation));
		System.out.println("Triangle"+body.getAngularVelocity());

	}

	@Override
	public void setDimension(float width, float height) {
		
	}

	@Override
	public float getWidth() {
		return 0;
	}

	@Override
	public float getHeight() {
		return 0;
	}
	
	public List<Character> getCharacters(){
		return characters;
	}
	@Override
	public Character getCharacter() {
		return characters.get(0);
	}

}
