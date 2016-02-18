package menu.networkMenu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import menu.MenuBackground;
import network.InfoMatch;
import network.StatusMatch;

public class ProvaWaitingPage extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		InfoMatch match = new InfoMatch("ciao", "ciao", 2, true, "Planet");
		match.setStatusMatch(StatusMatch.WAITING);
		WaitingPage w = new WaitingPage();
		MenuBackground menu = new MenuBackground();
		menu.getChildren().add(w);
		w.setText("Connessione in corso");
		Scene scene = new Scene(menu,Screen.getPrimary().getBounds().getWidth(),Screen.getPrimary().getBounds().getHeight());
		stage.setScene(scene);
		stage.show();
	}


}
