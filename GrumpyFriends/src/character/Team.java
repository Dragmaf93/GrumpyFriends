package character;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import game.MatchManager;

public class Team
{
	private String name;
	private int numberOfCharacter;
	private List<Character> characters;
	private List<Character> charactersInGame;
	
	private int currentPlayer;
	
	private MatchManager matchManager;
	

	public Team(String name, int numberOfCharacter, MatchManager matchManager){
		this.name = name;
		this.numberOfCharacter = numberOfCharacter;
		if(numberOfCharacter >4 || numberOfCharacter <= 0)
			this.numberOfCharacter =4;
		
		this.characters = new LinkedList<>();
		this.charactersInGame = new LinkedList<>();
		this.currentPlayer=0;
		this.matchManager = matchManager;
	}

	public boolean addCharcter(Character character) {
		
		if( characters.size() < numberOfCharacter){
			characters.add(character);
			return true;
		}
		return false;
	}
	
	public Character get(int i){
		return characters.get(i);
	}
	
	public void setUpForTheMatch(){
		for (Character character : characters) {
			charactersInGame.add(character);
		}
		Collections.shuffle(charactersInGame);
		currentPlayer=0;
	}
	public Character nextPlayer(){
		
		currentPlayer = (currentPlayer+1) % charactersInGame.size();
		return charactersInGame.get(currentPlayer);
	}
	
	public boolean removeCharacterInGame(Character character){
		return charactersInGame.remove(character);
	}
	
	public boolean removeCharacter(Character  character){
		return characters.remove(character);
	}
	
	public void mixUp(){
		Collections.shuffle(charactersInGame);
	}
	
	public boolean isLose(){
		
		return charactersInGame.size()==0;
	}
	
	public String getName(){
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public MatchManager getMatchManager() {
		return matchManager;
	}
	
	public void setMatchManager(MatchManager matchManager) {
		this.matchManager = matchManager;
	}
}
