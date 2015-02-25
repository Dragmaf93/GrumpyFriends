package object.character;

import java.util.ArrayList;

public class Team extends ArrayList<AbstractCharacter>
{
	private String name;
	private int numberOfCharacter;
	
	
	public Team(String name, int numberOfCharacter){
		super();
		this.name = name;
		this.numberOfCharacter = numberOfCharacter;
		if(numberOfCharacter >4 || numberOfCharacter <= 0)
			this.numberOfCharacter =4;
		
	}

	public boolean addCharcter(AbstractCharacter character) {
		
		if( size() < numberOfCharacter){
			super.add(character);
			return true;
		}
		return false;
	}
	
	public boolean removeCharacter(AbstractCharacter  character){
		
		return super.remove(character);
	}
	
	public boolean isLose(){
		
		return size()==0;
	}
	
	public String getName(){
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
