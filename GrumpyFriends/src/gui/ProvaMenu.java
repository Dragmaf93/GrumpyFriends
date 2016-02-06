package gui;

import menu.Menu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ProvaMenu extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		
		Menu menu  = new  Menu();
		
		Scene scene = new Scene(menu,Screen.getPrimary().getBounds().getWidth(),Screen.getPrimary().getBounds().getHeight());
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
