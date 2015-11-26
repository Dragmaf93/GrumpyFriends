package gui;

import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class ImageLoader {
	
	HashMap<String, Image> grounds;
	
	public ImageLoader() {
		grounds = new HashMap<String, Image>();
		
		loader();
		
	}
	
	private void loader() {
//		TODO inserire immagini
		grounds.put("groundPlanet", new Image("file:image/ground/groundPlanet.png"));
	}
	
	public Image getImage(String typeWorld) {
		return grounds.get("ground"+typeWorld);
	}
	
}
