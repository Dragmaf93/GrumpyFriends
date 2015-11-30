package gui;

import java.util.HashMap;

import javafx.scene.image.Image;

public class ImageLoader {
	
	HashMap<String, Image> grounds;
	HashMap<String, Image> backgrounds;
	
	public ImageLoader() {
		grounds = new HashMap<String, Image>();
		backgrounds = new HashMap<String, Image>();
		
		loader();
	}
	
	private void loader() {
//		TODO inserire immagini
		grounds.put("groundPlanet", new Image("file:image/ground/g3.png"));
		
		backgrounds.put("Planet", new Image("file:image/background/Planet.jpg"));
	}
	
	public Image getImageGrounds(String typeWorld) {
		return grounds.get("ground"+typeWorld);
	}
	
	public Image getImageBackgrounds(String typeWorld) {
		return backgrounds.get(typeWorld);
	}
	
}
