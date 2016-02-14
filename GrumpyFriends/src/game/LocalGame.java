package game;



import java.util.List;

import javafx.scene.paint.Color;
import character.Character;
import menu.MenuPage;
import menu.SequencePage;
import menu.teamMenu.TeamPage;
import menu.worldMenu.WorldPage;

public class LocalGame extends AbtractGame{
	
	public LocalGame() {
		sequencePages =  new SequencePage(new TeamPage(),new TeamPage(),
				new WorldPage());
	}
	
	@Override
	public void startGame() {
		List<MenuPage> menuPages = sequencePages.getMenuPages();
		
		for (MenuPage menuPage : menuPages) {
			 extractData(menuPage.getGameBeans());
		}
		
		matchManager = new LocalMatchManager(battlefield);
		teams.get(0).setMatchManager(matchManager);
		teams.get(0).setColorTeam(Color.CRIMSON);
		teams.get(1).setMatchManager(matchManager);
		teams.get(1).setColorTeam(Color.STEELBLUE);
	
		matchManager.setTeamA(teams.get(0));
		matchManager.setTeamB(teams.get(1));
		
		for (Character character :characters) {
			character.setWorld(battlefield);
			battlefield.addCharacter(character);
		}
		
		
		positionCharacter();
		
		matchManager.startMatch();
		
	}

	@Override
	public void reset() {
		battlefield=null;
		teams.clear();
		characters.clear();
	}
}
