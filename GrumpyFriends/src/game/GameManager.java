package game;

import gui.MainApplication;
import gui.Menu;
import javafx.stage.Stage;

public class GameManager {

	private Stage stage;
	private Menu menu;
	private MainApplication mainApplication;
	
	private static GameManager instance;
	
	public static GameManager getIstance(){
    	if(instance == null)
    		instance = new GameManager();
    	return instance;
    }
	
	private GameManager() {
		stage = new Stage();
		menu = new Menu();
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void setMainApplication(MainApplication mainApplication) {
		this.mainApplication = mainApplication;
	}
	
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	public void startMainApplication() {
		try {
			if (mainApplication == null)
				mainApplication = new MainApplication();
			mainApplication.closeStage();
			menu.closeStage();
			mainApplication.start(mainApplication.getStage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void startMenu() {
		try {
			mainApplication.closeStage();
			menu.closeStage();
			menu.start(menu.getStage());
			mainApplication = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
