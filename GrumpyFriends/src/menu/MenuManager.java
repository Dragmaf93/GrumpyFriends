package menu;


import javafx.scene.Scene;
import menu.teamMenu.PlayerName;
import menu.teamMenu.TeamPage;
import menu.teamMenu.TeamType;
import menu.worldMenu.WorldPage;
import game.GameManager;
import game.PlayGame;
import gui.ImageLoader;

public class MenuManager {

	private static MenuManager instance;
	
	private TeamPage teamPageA;
	private TeamPage teamPageB;
	private WorldPage worldPage;
	
	private ImageLoader imageLoader;
	
	private PlayerName playerName;
	private TeamType teamType;
	
	private GameManager gameManager;

	private PlayGame playGame;
	
	public static MenuManager getIstance(){
    	if(instance == null)
    		instance = new MenuManager();
    	return instance;
    }
	
	private MenuManager() {
		imageLoader = new ImageLoader();
		gameManager = GameManager.getIstance();
	}

	public Scene getTeamPageAScene() {
		if (teamPageA == null)
			teamPageA = new TeamPage(true);
		
		return teamPageA.getScene();
	}
	
	public Scene getTeamPageBScene() {
		if (teamPageB == null)
			teamPageB = new TeamPage(false);
		
		return teamPageB.getScene();
	}

	public void setMenuScene() {
		playGame.setFirstScene();
	}

	public void setSceneForChangePage(Scene scene) {
		playGame.setScene(scene);
	}
	
	public void closeStage() {
		playGame.closeStage();
	}
	
	public Scene getWorldScene() {
		if (worldPage == null)
			worldPage = new WorldPage(imageLoader);
		
		return worldPage.getScene();
	}
	
	
	public void startGame() {
		gameManager.startMainApplication(teamPageA.getTeamName().getName(), teamPageA.getPlayerName().getPlayerName(),
				teamPageA.getTeamType().getType(),
				teamPageB.getTeamName().getName(), teamPageB.getPlayerName().getPlayerName(),
				teamPageB.getTeamType().getType(),
				worldPage.getWorldChoosed());
	}
	
	
	public void setTeamType(String teamType) {
		if (teamType.equals("teamA"))
			this.teamType = TeamType.WHITE;
		else
			this.teamType = TeamType.BLACK;
		
	}
	public void setTeamTypeAfter(String teamType) {
		setTeamType(teamType);
		playerName.remove();
	}
	public TeamType getTeamType() {
		return teamType;
	}

	public void setPlayerName(PlayerName playerName) {
		this.playerName = playerName;
	}

	
	
	public void setAllNull() {
		this.teamPageA = null;
		this.teamPageB = null;
		this.worldPage = null;
	}
	
	public void setPlayGame(PlayGame playGame) {
		this.playGame = playGame;
		gameManager.setPlayGame(playGame);
	}
	
}
