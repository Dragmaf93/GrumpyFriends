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
	
	public ImagePattern getImage(String typeWorld) {
		ImagePattern image = new ImagePattern(grounds.get("ground"+typeWorld),0.2, 0.2, 5.8,5.8, true);
		
		return image;
	}
	
}
