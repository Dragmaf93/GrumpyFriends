package menu.worldMenu;

import menu.MenuBackground;
import gui.ImageLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ProvaMenuViewer extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		
		ImageLoader imageLoader = new ImageLoader();
		MenuBackground b = new MenuBackground();

		WorldPage worldPage = new WorldPage();
		b.getChildren().add(worldPage);
		
		Scene scene = new Scene(b,Screen.getPrimary().getBounds().getWidth(),Screen.getPrimary().getBounds().getHeight());
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
	}
	public static void main(String[] args) {
		launch(args);
		
	}
}
