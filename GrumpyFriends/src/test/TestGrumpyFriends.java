package test;

import javafx.scene.paint.Color;

import org.jbox2d.common.Vec2;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

import character.Character;
import character.Chewbacca;
import character.Team;
import element.weaponsManager.Weapon;
import game.MatchManager;
import physic.PhysicalObjectManager;
import world.WorldBuilder;
import world.WorldDirector;

public class TestGrumpyFriends extends TestbedTest {

	private static TestbedTest instance;
	private MatchManager matchManager;
//	private Character currentPlayer;
	private Weapon weapon;
	private float speed=10f;
	private world.World world;
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
		director.createWorld("worldXML/Map.xml");
		world = builder.getWorld();
		
		
		matchManager = new MatchManager(world);
		
		Team teamA = new Team("TEAM BLUE", 4, matchManager);
		Team teamB = new Team("TEAM RED ", 4, matchManager);
		
		teamA.setColorTeam(Color.CRIMSON);
		teamB.setColorTeam(Color.STEELBLUE);
		
		matchManager.setTeamA(teamA);
		matchManager.setTeamB(teamB);
		
		
//		
		Character playerA1 = new Chewbacca("Player A1", 50, 100, teamA,world);
		Character playerB1 = new Chewbacca("Player B1", 30, 100, teamB,world);
		
		Character playerA2 = new Chewbacca("Player A2", 90, 100, teamA, world);
		Character playerB2 = new Chewbacca("Player B2", 130, 100, teamB, world);

		Character playerA3 = new Chewbacca("Player A3", 120, 100, teamA,world);
		Character playerB3 = new Chewbacca("Player B3", 90, 100, teamB,world);
		
		Character playerA4 = new Chewbacca("Player A4", 160, 100, teamA,world);
		Character playerB4 = new Chewbacca("Player B4", 150, 100, teamB,world);
		
		world.addCharacter(playerA1);
		world.addCharacter(playerB1);
		world.addCharacter(playerA2);
		world.addCharacter(playerB2);
		world.addCharacter(playerA3);
		world.addCharacter(playerB3);
		world.addCharacter(playerA4);
		world.addCharacter(playerB4);
//		
		teamA.addCharcter(playerA1);
		teamA.addCharcter(playerA2);
		teamA.addCharcter(playerA3);
		teamA.addCharcter(playerA4);
		
		teamB.addCharcter(playerB1);
		teamB.addCharcter(playerB2);
		teamB.addCharcter(playerB3);
		teamB.addCharcter(playerB4);
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
			weapon=matchManager.getCurrentPlayer().getEquipWeapon();
			break;
		case 'b':
			matchManager.getCurrentPlayer().equipWeapon("SimpleBomb");
			weapon=matchManager.getCurrentPlayer().getEquipWeapon();
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
		world.update();
		
		getCamera().setCamera(new Vec2((float)matchManager.getCurrentPlayer().getPhysicalObject().getBody().getPosition().x,
				(float)matchManager.getCurrentPlayer().getPhysicalObject().getBody().getPosition().y));
//		addTextLine("Turn " + matchManager.getTurn());
//		addTextLine("Current Player "+ matchManager.getCurrentPlayer().getName()+" of "+matchManager.getCurrentTeam().getName());
		
		if(weapon!=null)
			weapon.update();
		System.out.println(weapon);
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
