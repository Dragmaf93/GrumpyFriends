package test;

import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

import character.Character;
import character.Chewbacca;
import character.Team;
import game.MatchManager;
import physic.PhysicalObjectManager;
import world.WorldBuilder;
import world.WorldDirector;

public class TestGrumpyFriends extends TestbedTest {

	private static TestbedTest instance;
	private MatchManager matchManager;
//	private Character currentPlayer;
	
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
		
		
		matchManager = new MatchManager(world);
		
		Team teamA = new Team("TeamA", 1, matchManager);
//		Team teamB = new Team("TeamB", 1, matchManager);
		
		matchManager.setTeamA(teamA);
//		matchManager.setTeamB(teamB);
		
		Character playerA = new Chewbacca("PlayerA", 100, 20, teamA);
//		Character playerB = new Chewbacca("PlayerB", 110, 20, teamB);
		
		world.addCharacter(playerA);
//		world.addCharacter(playerB);
		
		teamA.addCharcter(playerA);
//		teamB.addCharcter(playerB);
		
		builder.lastSettings();
		
		matchManager.startTest();
		
	}

	@Override
	public void keyPressed(char argKeyChar, int argKeyCode) {
		switch (argKeyChar) {
		case 'w':
			matchManager.getCurrentPlayer().jump();
			break;
		case 'a':
			matchManager.getCurrentPlayer().move(Character.LEFT);
			System.out.println(matchManager.getCurrentPlayer().getPhysicalObject().getX());
			break;
		case 'd':
			matchManager.getCurrentPlayer().move(Character.RIGHT);
			System.out.println(matchManager.getCurrentPlayer().getPhysicalObject().getX());
			break;
		case 'm':
			matchManager.getCurrentPlayer().equipWeapon("SimpleMissile");
			break;
		case 'b':
			matchManager.getCurrentPlayer().equipWeapon("SimpleBomb");
			break;
		case 'u':
			matchManager.getCurrentPlayer().unequipWeapon();
			break;
		case '5':
			matchManager.getCurrentPlayer().attack(speed);

			break;
		case '8':
			speed+=0.5f;
			break;
		case '2':
			speed-=0.5f;
			break;
		case '6':
			matchManager.getCurrentPlayer().changeAim(Character.DECREASE);
			break;
		case '4':
			matchManager.getCurrentPlayer().changeAim(Character.INCREASE);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void step(TestbedSettings settings) {
		super.step(settings);
		addTextLine("Turn " + matchManager.getTurn());
		addTextLine("Current Player "+ matchManager.getCurrentPlayer().getName()+" of "+matchManager.getCurrentTeam().getName());
		addTextLine("Time "+ matchManager.getTimer());
		getCamera().setCamera(matchManager.getCurrentPlayer().getPositionTest());
		
		PhysicalObjectManager.getInstance().destroyBodies();
	}
	@Override
	public void keyReleased(char argKeyChar, int argKeyCode) {
		switch (argKeyChar) {
		case 'a':
			matchManager.getCurrentPlayer().stopToMove();
			break;
		case 'd':
			matchManager.getCurrentPlayer().stopToMove();
			break;
		case '6':
		case '4':
			matchManager.getCurrentPlayer().changeAim(Character.STOP);
			break;
		default:
			break;
		}
	}

}
