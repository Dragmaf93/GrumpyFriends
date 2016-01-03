package menu.teamMenu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ProvaTeamMenu extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		
		TeamPage teamPage = new TeamPage(true);
		
		Scene scene = new Scene(teamPage,Screen.getPrimary().getBounds().getWidth(),Screen.getPrimary().getBounds().getHeight());
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
