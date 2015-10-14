package test;

import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

import element.character.AbstractCharacter;
import element.character.Character;
import world.WorldBuilder;
import world.WorldDirector;

public class TestGrumpyFriends extends TestbedTest {

	private static TestbedTest instance;
	private Character character;
	private float speed=10f;
	private float angle=3.14f;
	public static TestbedTest getInstance(){
		if(instance==null)
			instance = new TestGrumpyFriends();
		return instance;
	}
	
	private TestGrumpyFriends() {
	}
	
	
	@Override
	public String getTestName() {
		return "Test GrumpyFriends";
	}
	
	@Override
	public void initTest(boolean arg0) {

		WorldBuilder builder = new TestWorldBuilder();
		WorldDirector director = new WorldDirector(builder);
		director.createWorld("worldXML/world.xml");
		world.World world = builder.getWorld();
		character = world.getCharacter();

	}

	@Override
	public void keyPressed(char argKeyChar, int argKeyCode) {
		switch (argKeyChar) {
		case 'w':
			character.jump();
			break;
		case 'a':
			character.move(AbstractCharacter.LEFT);
			break;
		case 'd':
			character.move(AbstractCharacter.RIGHT);
			break;
		case 't':
//			character.takeBomb();
			break;
		case 's':
//			character.throwBomb(speed,angle);
			break;
		case '8':
			speed*=2.0f;
			break;
		case '2':
			speed/=2f;
			break;
		case '6':
			angle-=0.05;
			break;
		case '4':
			angle+=0.05;
			break;
		default:
			break;
		}
	}
	
	@Override
	public void step(TestbedSettings settings) {
		super.step(settings);
		addTextLine("Speed Bomb "+ speed);
		addTextLine("Angle Bomb "+ angle);
		addTextLine("On Ground "+ character.isGrounded());
//		getCamera().setCamera(((TestCharacter) character).getPosition());

	}
	@Override
	public void keyReleased(char argKeyChar, int argKeyCode) {
		switch (argKeyChar) {
		case 'a':
			character.stopToMove();
			break;
		case 'd':
			character.stopToMove();
			break;
		default:
			break;
		}
	}

}
