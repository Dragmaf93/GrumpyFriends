package test;

import javax.swing.JFrame;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.testbed.framework.TestList;
import org.jbox2d.testbed.framework.TestbedFrame;
import org.jbox2d.testbed.framework.TestbedModel;
import org.jbox2d.testbed.framework.TestbedPanel;
import org.jbox2d.testbed.framework.TestbedSetting;
import org.jbox2d.testbed.framework.TestbedSetting.SettingType;
import org.jbox2d.testbed.framework.TestbedTest;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;

import element.character.AbstractCharacter;
import element.character.Chewbacca;
import world.AbstractWorld;
import world.CharacterContactListener;
import world.InclinedGround;
import world.LinearGround;
import world.WorldBuilder;
import world.GameWorldBuilder;
import world.WorldDirector;

public class TestGrumpyFriends extends TestbedTest {

	private static TestbedTest instance;
	
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
//		
//		character = new Chewbacca("Eliana", 40, 10, null, null);
//		Body b = getWorld().createBody(character.getBodyDef());
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
	public void keyPressed(char argKeyChar, int argKeyCode) {
//		switch (argKeyChar) {
//		case 'w':
//			character.jump();
//			System.out.println("jump");
//			break;
//		case 'a':
//			System.out.println("lest");
//			character.move(AbstractCharacter.LEFT);
//			break;
//		case 'd':
//			System.out.println("right");
//			character.move(AbstractCharacter.RIGHT);
//			break;
//		default:
//			break;
//		}

	}
//	@Override
//	public void keyReleased(char argKeyChar, int argKeyCode) {
//		switch (argKeyChar) {
//		case 'a':
//			character.stopToMove();
//			System.out.println("stop");
//			break;
//		case 'd':
//			character.stopToMove();
//			System.out.println("stop");
//			break;
//		default:
//			break;
//		}
//	}

	public static void main(String[] args) {
		TestbedModel model = new TestbedModel(); // create our model

		model.clearTestList();
		TestList.populateModel(model); // populate the provided testbed tests
		model.addCategory("My Super Tests"); // add a category
		model.addTest(new TestGrumpyFriends()); // add our test
		
		model.getSettings().addSetting(new TestbedSetting("My Range Setting", SettingType.ENGINE, 10, 0, 20));
		TestbedPanel panel = new TestPanelJ2D(model);

		JFrame testbed = new TestbedFrame(model, panel); // put both into our
															// testbed frame
		testbed.setVisible(true);
		testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
