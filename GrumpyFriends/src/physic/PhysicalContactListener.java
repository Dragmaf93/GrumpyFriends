package physic;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.text.DefaultEditorKit.CutAction;

import org.apache.log4j.chainsaw.Main;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.ManifoldPoint;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import character.Character;
import element.weaponsManager.ExplosiveObject;
import physic.weapon.PhysicalWeapon;

public class PhysicalContactListener implements ContactListener {

	private HashMap<Character, Integer> characterContact;
	private List<Character> charactersList;

	public PhysicalContactListener(List<Character> characters) {

		characterContact = new HashMap<>();
		charactersList = characters;
		for (Character character : characters) {
			characterContact.put(character, new Integer(0));
		}

	}

	private Fixture getFeetFixture(Fixture f1, Fixture f2, String characterName) {
		if (f1.getUserData() != null && f1.getUserData().equals(characterName)) {
			return f1;
		} else if (f2.getUserData() != null
				&& f2.getUserData().equals(characterName)) {
			return f2;
		}
		return null;
	}

	private Fixture getBodyFixture(Fixture f1, Fixture f2,
			String currentCharacter, String characterName) {
		if (f1.getUserData() != null
				&& f1.getUserData().equals(characterName + "Body")
				&& f2.getUserData() != null
				&& f2.getUserData().equals(currentCharacter + "Body")) {
			return f1;
		} else if (f2.getUserData() != null
				&& f2.getUserData().equals(characterName + "Body")
				&& f1.getUserData() != null
				&& f1.getUserData().equals(currentCharacter + "Body")) {
			return f2;
		}
		return null;
	}

	private Fixture getExplosiveObjectFixture(Fixture f1, Fixture f2) {
		if (f1.getUserData() != null
				&& f1.getUserData() instanceof PhysicalWeapon) {
			return f1;
		} else if (f2.getUserData() != null
				&& f2.getUserData() instanceof PhysicalWeapon) {
			return f2;
		}
		return null;
	}

	@Override
	public void beginContact(Contact contact) {

		// explosive object contact

		Fixture explosiveObjectfixture = getExplosiveObjectFixture(
				contact.getFixtureA(), contact.getFixtureB());
		if (explosiveObjectfixture != null) {

			((ExplosiveObject) explosiveObjectfixture.getUserData()).explode();
			explosiveObjectfixture = null;
		}

		{
			// character contact
			Set<Character> characters = characterContact.keySet();
			for (Character character : characters) {
				if (!character.isDead() && !character.isOutWorld()) {
					if (getFeetFixture(contact.getFixtureA(),
							contact.getFixtureB(), character.getName()) != null) {
						Integer tmp = characterContact.get(character) + 1;
						characterContact.put(character, tmp);
					}

					if (characterContact.get(character) > 0)
						character.setGrounded(true);
				}
			}
		}
//		{
//			Set<Character> characters = characterContact.keySet();
//			Character currentCharacter = charactersList.get(0).getTeam()
//					.getMatchManager().getCurrentPlayer();
//
//			for (Character character : characters) {
//				if (character != currentCharacter && !character.isDead()
//						&& !character.isOutWorld()) {
//					Fixture fixture;
//					if ((fixture = getBodyFixture(contact.getFixtureA(),
//							contact.getFixtureB(), currentCharacter.getName(),
//							character.getName())) != null) {
//						System.out.println("Fixture " + fixture.getUserData());
//					}
//
//				}
//			}
//		}
	}

	@Override
	public void endContact(Contact contact) {

		Set<Character> characters = characterContact.keySet();

		for (Character character : characters) {
			if (getFeetFixture(contact.getFixtureA(), contact.getFixtureB(),
					character.getName()) != null) {
				Integer tmp = characterContact.get(character) - 1;
				characterContact.put(character, tmp);
			}

			if (characterContact.get(character) <= 0)
				character.setGrounded(false);
		}
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse contactImpulse) {
//		{
//			Set<Character> characters = characterContact.keySet();
//			Character currentCharacter = charactersList.get(0).getTeam()
//					.getMatchManager().getCurrentPlayer();
//
//			for (Character character : characters) {
//				if (character != currentCharacter && !character.isDead()
//						&& !character.isOutWorld()) {
//					Fixture fixture;
//					if ((fixture = getBodyFixture(contact.getFixtureA(),
//							contact.getFixtureB(), currentCharacter.getName(),
//							character.getName())) != null) {
//						System.out.println(contactImpulse.normalImpulses);
//					}
//
//				}
//			}
//		}
	}

	@Override
	public void preSolve(Contact contact, Manifold manifold) {

//		Set<Character> characters = characterContact.keySet();
//		Character currentCharacter = charactersList.get(0).getTeam()
//				.getMatchManager().getCurrentPlayer();
//
//		for (Character character : characters) {
//			if (character != currentCharacter && !character.isDead()
//					&& !character.isOutWorld()) {
//				Fixture fixture;
//				if ((fixture = getBodyFixture(contact.getFixtureA(),
//						contact.getFixtureB(), currentCharacter.getName(),
//						character.getName())) != null) {
//					contact.setEnabled(false);
////					fixture.getBody().applyForceToCenter(new Vec2(-manifold.localNormal.x*5000,0));
//				}
//
//			}
//		}
	}

}
