package menu.networkMenu;

import java.util.ArrayList;
import java.util.List;

import network.InfoMatch;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import menu.MenuBackground;

public class NetworkPageProva extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		
		NetworkPage teamPage = new NetworkPage();
		List<InfoMatch> i = new ArrayList<>();
		
		i.add(new InfoMatch("Ciao", "Ricca", 2, true, "a"));
		i.add(new InfoMatch("Sono", "SADFb", 2, true, "b"));
		i.add(new InfoMatch("Ricca", "AFSAF", 2, true, "c"));	
		i.add(new InfoMatch("Ciao", "Ricca", 2, true, "a"));
		i.add(new InfoMatch("Sono", "SADFb", 2, true, "b"));
		i.add(new InfoMatch("Ricca", "AFSAF", 2, true, "c"));	
		i.add(new InfoMatch("Ciao", "Ricca", 2, true, "a"));
		i.add(new InfoMatch("Sono", "SADFb", 2, true, "b"));
		i.add(new InfoMatch("Ricca", "AFSAF", 2, true, "c"));	
		i.add(new InfoMatch("Ciao", "Ricca", 2, true, "a"));
		i.add(new InfoMatch("Sono", "SADFb", 2, true, "b"));
		i.add(new InfoMatch("Ricca", "AFSAF", 2, true, "c"));	
		i.add(new InfoMatch("Ciao", "Ricca", 2, true, "a"));
		i.add(new InfoMatch("Sono", "SADFb", 2, true, "b"));
		i.add(new InfoMatch("Ricca", "AFSAF", 2, true, "c"));	
		
		teamPage.setList(i);
		
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
