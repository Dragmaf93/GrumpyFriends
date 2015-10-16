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
import physic.CharacterContactListener;
import world.InclinedGround;
import world.LinearGround;

public class TestWorld implements world.World{

	public final static Vec2 GRAVITY = new Vec2(0,-10f);
	
	private World world;
	private List<Character> characters;
	
	public TestWorld() {
		world = TestGrumpyFriends.getInstance().getWorld();
		world.setGravity(GRAVITY);
		characters= new ArrayList<Character>(8);
	}
	@Override
	public void addCharacter(Character character) {
		this.characters.add(character);
	}

	@Override
	public void addLinearGround(float x, float y, float width, float height) {
		new LinearGround(x, y, width, height);
	}

	@Override
	public void addInclinedGround(float x, float y, float width, float height, int angleRotation) {
		new InclinedGround(x, y, width, height, angleRotation);
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
	
	@Override
	public World getPhysicWorld() {
		return world;
	}
	public void setContactListener() {
		world.setContactListener(new CharacterContactListener(characters));
		
	}
	
	
}
