package physic;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import character.Character;
import element.weaponsManager.ExplosiveObject;
import physic.weapon.PhysicalWeapon;

public class PhysicalContactListener implements ContactListener {

	private HashMap<Character, Integer> characterContact;

	public PhysicalContactListener(List<Character> characters) {

		characterContact = new HashMap<>();

		for (Character character : characters) {
			characterContact.put(character, new Integer(0));
		}

	}

	private Fixture getFeetFixture(Fixture f1, Fixture f2, String characterName) {
		if (f1.getUserData() != null && f1.getUserData().equals(characterName)) {
			return f1;
		} else if (f2.getUserData() != null && f2.getUserData().equals(characterName)) {
			return f2;
		}
		return null;
	}
	
	private Fixture getExplosiveObjectFixture(Fixture f1, Fixture f2){
		if (f1.getUserData() != null && f1.getUserData() instanceof PhysicalWeapon) {
			return f1;
		} else if (f2.getUserData() != null && f2.getUserData()instanceof PhysicalWeapon) {
			return f2;
		}
		return null;
	}
	@Override
	public void beginContact(Contact contact) {
		
		// explosive object contact
		
		Fixture explosiveObjectfixture=getExplosiveObjectFixture(contact.getFixtureA(),contact.getFixtureB());
		if(explosiveObjectfixture!=null){
		
			((ExplosiveObject)explosiveObjectfixture.getUserData()).explode();
			explosiveObjectfixture=null;
		}
		
		//character contact
		Set<Character> characters = characterContact.keySet();
		for (Character character : characters) {
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
