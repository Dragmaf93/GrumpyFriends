package menu.networkMenu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import menu.MenuBackground;

public class NetworkPageProva extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		
		NetworkPage teamPage = new NetworkPage();
		MenuBackground b = new MenuBackground();
		b.getChildren().add(teamPage);
		Scene scene = new Scene(b,Screen.getPrimary().getBounds().getWidth(),Screen.getPrimary().getBounds().getHeight());
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
