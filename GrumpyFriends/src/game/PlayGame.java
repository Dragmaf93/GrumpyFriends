package game;

import menu.Menu;
import menu.MenuManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PlayGame extends Application {

	private Scene sceneFirst;

	private GameManager gameManager;
	private MenuManager menuManager;

	private Stage stage;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.stage = primaryStage;
		
		Menu menu = new Menu();
		
		gameManager = GameManager.getIstance();
		menuManager = MenuManager.getIstance();
	
//		menuManager.setMenu(menu);
		menuManager.setPlayGame(this);
		
		sceneFirst = new Scene(menu.getPane());   
		
		primaryStage.setTitle("Grumpy Friends");
		primaryStage.setScene(sceneFirst);
		primaryStage.show();
		
	}
	
	public void setScene(Scene scene) {
		stage.setScene(scene);
	}

	public void setFirstScene() {
		stage.setScene(sceneFirst);
	}
	
	public void closeStage() {
		stage.close();
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}


}
