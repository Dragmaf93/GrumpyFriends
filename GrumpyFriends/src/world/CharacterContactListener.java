package world;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import element.character.Character;


public class CharacterContactListener implements ContactListener{

	private Character character;
	private int nFinctureContact;
	public CharacterContactListener(Character character){
		this.character = character;
		nFinctureContact=0;
	}
	
	private Fixture getFeetFixture(Fixture f1, Fixture f2) {
//		System.out.println(f2.m_userData+"ASDDDDDDDDDDDDDDDDDDDDDDDDD     "+f1.m_userData);
        if (f1.getUserData() != null &&  f1.getUserData().equals(character.getName())) {
            return f1;
        } else if (f2.getUserData() != null && f2.getUserData().equals(character.getName())) {
            return f2;
        }
        return null;
    }
	
	@Override
	public void beginContact(Contact contact) {
//        System.out.println("BEGIN CONTACT");
        if (getFeetFixture(contact.getFixtureA(),
                contact.getFixtureB()) != null)
        		nFinctureContact++;
        if(nFinctureContact>0)
        			character.setGrounded(true);
			
				
	}

	@Override
	public void endContact(Contact contact) {
//		System.out.println("END CONTACT");
		if (getFeetFixture(contact.getFixtureA(),
                contact.getFixtureB()) != null)
			nFinctureContact--;
		if(nFinctureContact<=0)
					character.setGrounded(false);	
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
