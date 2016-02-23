package network.server;

import java.io.IOException;

import javafx.scene.paint.Color;
import character.Character;
import menu.GameBean;
import menu.MenuPage;
import game.AbstractGame;


public class ServerGame extends AbstractGame{
	
	@Override
	public void startGame() throws IOException {
		
		for (GameBean bean : beans) {
			 extractData(bean);
		}

		matchManager = new ServerMatchManager(battlefield);
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

	}
	
	@Override
	public void reset() {
	}

	@Override
	public MenuPage nextPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MenuPage prevPage() {
		// TODO Auto-generated method stub
		return null;
	}

}
