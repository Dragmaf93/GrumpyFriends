package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import character.Character;
import element.Ground;
import element.ground.GenericGround;
import element.ground.InclinedGround;
import element.ground.LinearGround;
import element.ground.RoundGround;
import physic.PhysicalContactListener;
import utils.Point;
import utils.Vector;


public class TestWorld implements world.World {

	public final static Vec2 GRAVITY = new Vec2(0, -20f);

	private World world;
	
	private List<Character> characters;

	public TestWorld() {
		world = TestGrumpyFriends.getInstance().getWorld();
		world.setGravity(GRAVITY);
		characters = new ArrayList<Character>(8);
	}

	@Override
	public void addCharacter(Character character) {
		this.characters.add(character);
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeDestroyedElement() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addLinearGround(float x, float y, float width, float height) {
		new LinearGround(x, y, width, height);
	}

	@Override
	public void reset() {

	}

	@Override
	public void addInclinedGround(float x, float y, float width, float height,
			int angleRotation) {
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

	
	public List<Character> getCharacters() {
		return characters;
	}

	@Override
	public World getPhysicWorld() {
		return world;
	}

	public void setContactListener() {
		world.setContactListener(new PhysicalContactListener(characters));

	}

	@Override
	public List<Ground> getGrounds() {
		return null;
	}

	@Override
	public List<Character> getAllCharacters() {
		return characters;
	}

	@Override
	public void update() {

	}

	@Override
	public Character getCharacter(String name) {
		return null;
	}

	@Override
	public Map<String, Float> getHitCharacter() {
		return null;
	}

	public void addLinearGround(List<Point> points) {
		new LinearGround(points);
	}

	@Override
	public void addInclinedGround(List<Point> points) {
		new InclinedGround(points);
	}

	@Override
	public void addGenericGround(List<Point> points) {
		new GenericGround(points);
	}

	@Override
	public void addRoundGround(Point start, Point end, Point control) {
		new RoundGround(start, end, control);
	}

	@Override
	public void addRoundGround(List<Point> points) {
	}

	@Override
	public List<Character> getCharacatersOutOfWorld() {
		return null;
	}

	@Override
	public String getType() {
		return null;
	}

}
