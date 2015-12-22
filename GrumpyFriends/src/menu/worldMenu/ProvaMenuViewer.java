package menu.worldMenu;

import gui.ImageLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ProvaMenuViewer extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		
		ImageLoader imageLoader = new ImageLoader();
		WorldPage worldPage = new WorldPage(imageLoader);
		
		Scene scene = new Scene(worldPage,Screen.getPrimary().getBounds().getWidth(),Screen.getPrimary().getBounds().getHeight());
		stage.setScene(scene);
		stage.show();
	}
	public static void main(String[] args) {
		launch(args);
		
	}
}
