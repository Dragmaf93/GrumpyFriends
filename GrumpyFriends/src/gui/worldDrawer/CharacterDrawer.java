package gui.worldDrawer;

import character.Character;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class CharacterDrawer{

	private Character character;
	private Shape shape;
	
	public CharacterDrawer(Character character) {
		this.character = character;
		shape = new Rectangle(
				character.getX(),
				character.getY(), 
				character.getWidth(),
				character.getHeight());
	}
	
	public Shape getShape(){
		return shape; 
	}
	
	public void relocate() {
		System.out.println(character.getX()+" "+character.getY());
		shape.relocate(character.getX(), character.getY());
	}

}
