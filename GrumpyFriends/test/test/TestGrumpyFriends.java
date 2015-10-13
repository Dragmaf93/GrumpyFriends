package test;

import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

import element.character.AbstractCharacter;
import world.WorldBuilder;
import world.WorldDirector;

public class TestGrumpyFriends extends TestbedTest {

	private static TestbedTest instance;
	private TestCharacter character;
	
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
		character = (TestCharacter) world.getCharacter();

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
		default:
			break;
		}
	}
	@Override
	public void step(TestbedSettings settings) {
		super.step(settings);
		addTextLine("Speed "+ character.feet.getAngularVelocity());
		addTextLine("On Ground "+ character.isGrounded());
		getCamera().setCamera(((TestCharacter) character).getPosition());

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
