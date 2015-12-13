package gui;

import java.util.HashMap;

import world.World;
import javafx.scene.image.Image;

public class ImageLoader {
	
	private HashMap<String, Image> grounds;
	private HashMap<String, Image> backgrounds;
	
	private final static String PATH_IMAGE="file:image/World/";
	private String lastTypeWorld;
	
	public ImageLoader() {
		grounds = new HashMap<String, Image>();
		backgrounds = new HashMap<String, Image>();
	}
	
	private void loader() {
//		TODO inserire immagini
//		grounds.put("groundPlanetWidth", new Image("file:image/ground/Planet/groundWidth.png"));
		grounds.put("groundPlanetHeight", new Image("file:image/ground/Planet/g.png"));
		grounds.put("groundPlanetWidth", new Image("file:image/ground/Planet/g.png"));
//		grounds.put("groundPlanetCurve", new Image("file:image/ground/Planet/groundCurve.png"));
		
		backgrounds.put("Planet", new Image("file:image/background/backgroundAngry.png",1000,1000,false,false));
	}
	
	public void loadImage(World world){
		String path = PATH_IMAGE+world.getType();
		lastTypeWorld = world.getType();
		if(!grounds.containsKey(world.getType())){
			grounds.put(world.getType(), new Image(path+"/g.png"));
		}

		if(!backgrounds.containsKey(world.getType())){
			backgrounds.put(world.getType(), new Image(path+"/background.png"));
		}
	}
	
	public Image getImageGrounds(String typeWorld, String widthOrHeight) {
		return grounds.get("ground"+typeWorld+widthOrHeight);
	}
	
	
	public Image getImageGrounds() {
		return grounds.get(lastTypeWorld);
	}
	
	
	public Image getImageBackgrounds(String typeWorld) {
		return backgrounds.get(typeWorld);
	}
	
	public Image getImageBackgrounds() {
		return backgrounds.get(lastTypeWorld);
	}
	
}
