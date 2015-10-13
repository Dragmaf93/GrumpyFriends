package world;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import element.character.Character;

public class CharacterContactListener implements ContactListener {

	private HashMap<Character, Integer> characterContact;

	public CharacterContactListener(List<Character> characters) {

		characterContact = new HashMap<>();

		for (Character character : characters) {
			characterContact.put(character, new Integer(0));
		}

	}

	private Fixture getFeetFixture(Fixture f1, Fixture f2, String characterName) {
//		System.out.println(characterName);
		if (f1.getUserData() != null && f1.getUserData().equals(characterName)) {
			return f1;
		} else if (f2.getUserData() != null && f2.getUserData().equals(characterName)) {
			return f2;
		}
		return null;
	}

	@Override
	public void beginContact(Contact contact) {

		Set<Character> characters = characterContact.keySet();
		for (Character character : characters) {
//			System.out.println("CONTACT" +character.getName());
			if (getFeetFixture(contact.getFixtureA(), contact.getFixtureB(), character.getName()) != null) {
				Integer tmp = characterContact.get(character) + 1;
				characterContact.put(character, tmp);
			}

			if (characterContact.get(character) > 0)
				character.setGrounded(true);
		}

	}

	@Override
	public void endContact(Contact contact) {
		
		Set<Character> characters = characterContact.keySet();

		for (Character character : characters) {
			if (getFeetFixture(contact.getFixtureA(), contact.getFixtureB(),character.getName()) != null){
				Integer tmp = characterContact.get(character) - 1;
				characterContact.put(character, tmp);			
			}
			
			if (characterContact.get(character)<= 0)
				character.setGrounded(false);
		}
	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub

	}

}
