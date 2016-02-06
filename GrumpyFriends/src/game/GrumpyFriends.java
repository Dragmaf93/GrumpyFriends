package game;

import gui.MainScene;
import menu.MenuManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GrumpyFriends extends Application {

	private Scene mainScene;

	private Stage stage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.stage = primaryStage;
		
		MenuManager.getInstance().initialize(this);
		mainScene = new MainScene(MenuManager.getInstance().getRoot(), Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight()); 

		primaryStage.setTitle("Grumpy Friends");
		primaryStage.setScene(mainScene);
//		primaryStage.setFullScreen(true);
		primaryStage.show();
		
	}
	
	public MainScene getScene(){
		return (MainScene)mainScene;
	}
	
	public void setNullMenu() {
//		menu = null;
//		menuManager.setAllNull();
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
