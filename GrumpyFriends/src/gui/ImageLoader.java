package gui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

public class ImageLoader {
	
	private Map<String, Image> grounds;
	private Map<String, Image> backgrounds;
	
	private Map<String, Image> previews;
	private Map<String, Image> previewsGround;
	
	private final static String PATH_IMAGE="file:image/World/";
	private String lastTypeWorld;
	
	public ImageLoader() {
		grounds = new HashMap<String, Image>();
		backgrounds = new HashMap<String, Image>();
		previews = new HashMap<String, Image>();
		previewsGround = new HashMap<String, Image>();
		
		File dir = new File("image/World/");
		String[] typeWorlds = dir.list();

		for (String typeWorld : typeWorlds) {
				previews.put(typeWorld, new Image(PATH_IMAGE+typeWorld+"/preview.png"));
				previewsGround.put(typeWorld, new Image(PATH_IMAGE+typeWorld+"/groundPreview.png"));
		}
		
	}	
//	private void loader() {
////		grounds.put("groundPlanetWidth", new Image("file:image/ground/Planet/groundWidth.png"));
//		grounds.put("groundPlanetHeight", new Image("file:image/ground/Planet/g.png"));
//		grounds.put("groundPlanetWidth", new Image("file:image/ground/Planet/g.png"));
////		grounds.put("groundPlanetCurve", new Image("file:image/ground/Planet/groundCurve.png"));
//		
//		backgrounds.put("Planet", new Image("file:image/background/backgroundAngry.png",1000,1000,false,false));
//	}
	
	public void loadImage(String typeWorld){
		String path = PATH_IMAGE+typeWorld;
		lastTypeWorld = typeWorld;
		if(!grounds.containsKey(typeWorld)){
			grounds.put(typeWorld, new Image(path+"/ground.png"));
		}

		if(!backgrounds.containsKey(typeWorld)){
			backgrounds.put(typeWorld, new Image(path+"/background.png"));
		}
		
	}
	public Image getImageGrounds(String typeWorld, String widthOrHeight) {
		return grounds.get("ground"+typeWorld+widthOrHeight);
	}
	
	public Image getGroundPreview(String world){
		return previewsGround.get(world);
	}
	
	public Image getImageGrounds() {
		return grounds.get(lastTypeWorld);
	}
	
	public Image getPreview(String typeWorld){
		return previews.get(typeWorld);
	}
	
	public Image getImageBackgrounds(String typeWorld) {
		if(!backgrounds.containsKey(typeWorld)){
			backgrounds.put(typeWorld, new Image(PATH_IMAGE+typeWorld+"/background.png"));
		}
		return backgrounds.get(typeWorld);
	}
	
	public Image getImageBackgrounds() {
		return backgrounds.get(lastTypeWorld);
	}
}
