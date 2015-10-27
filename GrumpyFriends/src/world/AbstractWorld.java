package world;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import character.Character;
import element.Ground;
import element.ground.InclinedGround;
import element.ground.LinearGround;
import physic.PhysicalContactListener;

public abstract class AbstractWorld extends org.jbox2d.dynamics.World implements world.World {

	protected float height, width;

	protected HashMap<String, Character> characterContainer;
	protected List<Ground> grounds;

	private List<Character> characters;
		
	protected static World instanceSon;

	public AbstractWorld(Vec2 gravity, boolean doSleep) {
		super(gravity);
		characterContainer = new HashMap<>();
		grounds = new ArrayList<Ground>();
		characters = new ArrayList<Character>();
	}

	public static void initializes(String typeWorld) {
		if (instanceSon == null)
			try {
				Class<?> factoryClass = Class.forName("world." + typeWorld);

				Constructor<AbstractWorld> constructorSon = (Constructor<AbstractWorld>) factoryClass
						.getDeclaredConstructor();
				constructorSon.setAccessible(true);
				AbstractWorld son = constructorSon.newInstance();
				instanceSon = (World) son;
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	@Override
	public World getPhysicWorld() {
		return this;
	}
	@Override
	public Character getCharacter() {
		// TODO Auto-generated method stub
		return null;
	}

	public static World getInstance() {
		return instanceSon;
	}

	public static void setInstanceNull() {
		instanceSon = null;
	}

	public String[] getCharacterName() {
		return (String[]) characterContainer.keySet().toArray();
	}

	public Character getCharacter(String name) {
		return characterContainer.get(name);
	}

	public Ground getGround(int x, int y) {
		for (Ground ground : grounds) {
			if (ground.getX() == x && ground.getY() == y)
				return ground;
		}
		return null;
	}
	@Override
	public List<Character> getAllCharacters() {
		return characters;
	}
	@Override
	public void addCharacter(Character character) {
		characterContainer.put(character.getName(), character);
		characters.add(character);
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public void addInclinedGround(float x, float y, float width, float height, int angleRotation) {
		grounds.add(new InclinedGround(x, y, width, height, angleRotation));
	}

	@Override
	public void addLinearGround(float x, float y, float width, float height) {
		grounds.add(new LinearGround(x, y, width, height));
	}

	@Override
	public void setDimension(float width, float height) {
		this.width = width;
		this.width = width;
	}
	
	@Override
	public List<Ground> getGrounds() {
		return grounds;
	}
	
	@Override
	public void update() {
		super.step(1.0f/60, 6, 3);  
	}

	public void setContactListener() {
		super.setContactListener(new PhysicalContactListener(characters));
	}
}
