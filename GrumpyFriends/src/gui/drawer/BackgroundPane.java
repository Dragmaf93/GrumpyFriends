package gui.drawer;

import gui.ImageLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

public class BackgroundPane extends Pane{

	private ImageView view;
	private ImageLoader imageLoader;
	
	public BackgroundPane(ImageLoader imageLoader) {
		this.imageLoader = imageLoader;

		view = new ImageView(imageLoader.getImageBackgrounds());
		view.setFitWidth(Screen.getPrimary().getBounds().getWidth()*1.1);
		view.setFitHeight(Screen.getPrimary().getBounds().getHeight()*1.1);
		view.relocate(Screen.getPrimary().getBounds().getWidth()-view.getFitWidth(),
				0);
		this.getChildren().add(view);
	}
}
