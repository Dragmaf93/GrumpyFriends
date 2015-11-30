package gui;

import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;

public class ImageLoader {
	
	HashMap<String, ImageView> grounds;
	
	public ImageLoader() {
		grounds = new HashMap<String, ImageView>();
		
		loader();
		
	}
	
	private void loader() {
//		TODO inserire immagini
		grounds.put("groundPlanet", new ImageView("file:image/ground/groundPlanet.png"));
	}
	
	public ImageView getImage(String typeWorld) {
		return grounds.get("ground"+typeWorld);
	}
	
}
