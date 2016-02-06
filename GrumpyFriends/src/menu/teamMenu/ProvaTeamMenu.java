package menu.teamMenu;

import menu.MenuBackground;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ProvaTeamMenu extends Application
{

	@Override
	public void start(Stage stage) throws Exception {
		
		TeamPage teamPage = new TeamPage();
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
