package test;

import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

import element.character.AbstractCharacter;
import element.character.Character;
import element.weaponsManager.Weapon;
import element.weaponsManager.weapons.SimpleBomb;
import physic.PhysicalObjectManager;
import world.WorldBuilder;
import world.WorldDirector;

public class TestGrumpyFriends extends TestbedTest {

	private static TestbedTest instance;
	private Character character;
	
	private float speed=10f;
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
			character.move(Character.LEFT);
			break;
		case 'd':
			character.move(Character.RIGHT);
			break;
		case 'e':
			character.equipWeapon("SimpleMissile");
			break;
		case 'u':
			character.unequipWeapon();
			break;
		case 'b':
			character.getEquipWeapon().finishHit();
			break;
		case '5':
			character.attack(speed);
			break;
		case '8':
			speed+=0.2f;
			break;
		case '2':
			speed-=0.2f;
			break;
		case '6':
			character.changeAngle(Character.DECREASE);
			break;
		case '4':
			character.changeAngle(Character.INCREASE);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void step(TestbedSettings settings) {
		super.step(settings);
//		double a=((TestCharacter)character).launcherJoint.getJointAngle();
//		addTextLine("Angle Launcher " +(int) Math.toDegrees(a));
		addTextLine("Speed Launcher " + speed);
		addTextLine("On Ground "+ character.isGrounded());
		getCamera().setCamera(character.getPositionTest());
		
		PhysicalObjectManager.getInstance().destroyBodies();
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
		case '6':
		case '4':
			character.changeAngle(Character.STOP);
			break;
		default:
			break;
		}
	}

}
