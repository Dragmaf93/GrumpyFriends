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
//		grounds.put("groundPlanetWidth", new Image("file:image/ground/Planet/groundWidth.png"));
		grounds.put("groundPlanetHeight", new Image("file:image/ground/Planet/groundHeight.png"));
		grounds.put("groundPlanetWidth", new Image("file:image/ground/Planet/groundWidth.png"));
//		grounds.put("groundPlanetCurve", new Image("file:image/ground/Planet/groundCurve.png"));
		
		backgrounds.put("Planet", new Image("file:image/background/Planet.jpg"));
	}
	
	public Image getImageGrounds(String typeWorld, String widthOrHeight) {
		return grounds.get("ground"+typeWorld+widthOrHeight);
	}
	
	public Image getImageBackgrounds(String typeWorld) {
		return backgrounds.get(typeWorld);
	}
	
}
