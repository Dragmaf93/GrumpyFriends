package test;


import javax.swing.JFrame;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
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
import world.Ground;
import world.WorldBuilder;
import world.WorldDirector;

public class TestCiao extends TestbedTest {
	private Chewbacca character ;
	Body t;
	float an=0f;
	@Override
	public String getTestName() {	return "Test GF"	;}

	@Override
	public void initTest(boolean arg0) {
		WorldBuilder builder = new WorldBuilder();
		WorldDirector director = new WorldDirector(builder);
		director.createWorld("worldXML/world.xml");
		AbstractWorld world=(AbstractWorld) builder.getWorld();
		
		for (int i = 0; i < world.getNumberRow(); i++)
		{
			for (int j = 0; j < world.getNumberColumn(); j++) 
			{
				if (world.getGround(j, i) instanceof Ground)
				{
					Ground g = world.getGround(j, i);
					Body b =getWorld().createBody(g.getBodyDef());
					System.out.println("BODY "+b.getPosition());
					b.createFixture(g.getFixtureDef());
				}
				
			}
		}
		
		 character = new Chewbacca("Eliana", 40,100, null, null);
		 Body b =getWorld().createBody(character.getBodyDef());
		 b.createFixture(character.getFixtureDef());
		 character.setBody(b);
		 
		 PolygonShape polygonShape = new PolygonShape();
		 polygonShape.m_vertexCount=3;
		 polygonShape.m_vertices[0].set(0, 0);
		 polygonShape.m_vertices[1].set(1*10, 0);
		 polygonShape.m_vertices[2].set(0, 1*20);
		 BodyDef bd = new BodyDef();
		 bd.position.x=-80;
		 bd.position.y=100;
		 t = getWorld().createBody(bd);
		 t.createFixture(polygonShape,1);
	}
	@Override
	public void keyPressed(char argKeyChar, int argKeyCode) {
		switch (argKeyChar) {
		case 'w':
			character.jump();
			System.out.println("jump");
			break;
		case 'a':
			System.out.println("lest");
			character.move(AbstractCharacter.LEFT);
			break;
		case 'd':
			System.out.println("right");
			character.move(AbstractCharacter.RIGHT);
		case 'l':
			an+=1.570796326795f;
			
			t.setTransform(t.getPosition(),an);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void keyReleased(char argKeyChar, int argKeyCode) {
		switch (argKeyChar) {
		case 'a':
			character.stopToMove();
			System.out.println("stop");
			break;
		case 'd':
			character.stopToMove();
			System.out.println("stop");
			break;
		default:
			break;
		}
	}
	public static void main(String[] args) {
		TestbedModel model = new TestbedModel();         // create our model

		// add tests
		TestList.populateModel(model);                   // populate the provided testbed tests
		model.addCategory("My Super Tests");             // add a category
		model.addTest(new TestCiao());                // add our test

		// add our custom setting "My Range Setting", with a default value of 10, between 0 and 20
		model.getSettings().addSetting(new TestbedSetting("My Range Setting", SettingType.ENGINE, 10, 0, 20));

		TestbedPanel panel = new TestPanelJ2D(model);    // create our testbed panel

		JFrame testbed = new TestbedFrame(model, panel); // put both into our testbed frame
		// etc
		testbed.setVisible(true);
		testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
