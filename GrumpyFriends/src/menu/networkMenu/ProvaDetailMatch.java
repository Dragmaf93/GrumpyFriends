package menu.networkMenu;

import network.InfoMatch;
import network.StatusMatch;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ProvaDetailMatch extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		InfoMatch match = new InfoMatch("ciao", "ciao", 2, true, "Planet");
		match.setStatusMatch(StatusMatch.WAITING);
		DetailMatch d = new DetailMatch(match);
		Scene scene = new Scene(d,Screen.getPrimary().getBounds().getWidth(),Screen.getPrimary().getBounds().getHeight());
		stage.setScene(scene);
		stage.show();
	}

}
