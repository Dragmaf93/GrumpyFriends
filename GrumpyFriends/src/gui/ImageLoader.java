package gui;

import java.io.File;
import java.util.HashMap;

import world.World;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageLoader {
	
	private HashMap<String, Image> grounds;
	private HashMap<String, Image> backgrounds;
	
	private HashMap<String, Image> previews;
	private HashMap<String, Image> previewsGround;
	
	private HashMap<String, Image> headBlack;
	private HashMap<String, Image> headWhite;
	
	private final static String PATH_IMAGE="file:image/World/";
	private final static String PATH_IMAGE_HEAD="image/character";
	private String lastTypeWorld;
	
	public ImageLoader() {
		grounds = new HashMap<String, Image>();
		backgrounds = new HashMap<String, Image>();
		previews = new HashMap<String, Image>();
		previewsGround = new HashMap<String, Image>();
		
		headBlack = new HashMap<String, Image>();
		headWhite = new HashMap<String, Image>();
		
		File dir = new File("image/World/");
		String[] typeWorlds = dir.list();

		for (String typeWorld : typeWorlds) {
//			System.out.println(typeWorld);
				previews.put(typeWorld, new Image(PATH_IMAGE+typeWorld+"/preview.png"));
				previewsGround.put(typeWorld, new Image(PATH_IMAGE+typeWorld+"/groundPreview.png"));
		}
		
		dir = new File(PATH_IMAGE_HEAD);
		String[] typeCharacter = dir.list();
		
//		for (String typeHead: typeCharacter){
//			System.out.println(typeHead);
//			headBlack.put(typeHead, new Image(PATH_IMAGE_HEAD+"Black/"+typeHead));
//		}
		
		dir = new File("image/head/White");
		String[] typeHeadWhite = dir.list();

//		for (String typeHead: typeHeadWhite)
//			headWhite.put(typeHead, new Image(PATH_IMAGE_HEAD+"White/"+typeHead));
		
	}
	
	private void loader() {
//		TODO inserire immagini
//		grounds.put("groundPlanetWidth", new Image("file:image/ground/Planet/groundWidth.png"));
		grounds.put("groundPlanetHeight", new Image("file:image/ground/Planet/g.png"));
		grounds.put("groundPlanetWidth", new Image("file:image/ground/Planet/g.png"));
//		grounds.put("groundPlanetCurve", new Image("file:image/ground/Planet/groundCurve.png"));
		
		backgrounds.put("Planet", new Image("file:image/background/backgroundAngry.png",1000,1000,false,false));
	}
	
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
	public static void main(String[] args) {
		File dir = new File(PATH_IMAGE_HEAD);
		String[] typeCharacter = dir.list();
		
		
		for (String character: typeCharacter){
			System.out.println(character);
			
			System.out.println("file:"+PATH_IMAGE_HEAD+"/"+character+"Head/left.png");
			System.out.println("file:"+PATH_IMAGE_HEAD+"/"+character+"Head/right.png");
		}
	}
}
