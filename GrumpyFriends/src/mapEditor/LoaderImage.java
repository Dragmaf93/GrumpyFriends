package mapEditor;

import java.util.HashMap;

public class LoaderImage 
{
	HashMap<String, ImageForObject> images;
	MapEditor mapEditor;
	int index = 0;
	
	public LoaderImage(MapEditor mapEditor) 
	{
		images = new HashMap<String, ImageForObject>();
		
		this.mapEditor = mapEditor;
	}
	
	public HashMap<String, ImageForObject> getImages()
	{
		return images;
	}
	
	public void load(String path)
	{
//		images.put("prova",new ImageForObject(path+"images.png",mapEditor));
//		images.put("grass",new ImageForObject(path+"grass.png",mapEditor,"grass",50, 70, "null"));
//		images.put("box",new ImageForObject(path+"box.png",mapEditor,"box",50, 70, "null"));
//		images.put("inclinedGround",new ImageForObject(path+"box1.png",mapEditor,"inclinedGround",50, 70, "20"));
//		images.put("ground",new ImageForObject(path+"ground.png",mapEditor,"ground",50, 70, "null"));
//		images.put("linearGround",new ImageForObject(path+"wall.png",mapEditor,"linearGround",50, 70, "null"));
//		images.put("wall2",new ImageForObject(path+"wall2.png",mapEditor,"wall2",50, 70, "null"));
		images.put("quadrato",new ImageForObject(path+"quadrato.png",mapEditor,"linearGround",50, 70));
		images.put("triangolo1",new ImageForObject(path+"triangolo1.png",mapEditor,"inclinedGround",50, 70));
		images.put("triangolo2",new ImageForObject(path+"triangolo2.png",mapEditor,"inclinedGround",50, 70));
	}
	
	public ImageForObject copyImage(String name)
	{
		ImageForObject imageCopy = null;
		try {
			imageCopy = (ImageForObject) images.get(name).clone();
		
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return imageCopy;
	}
	
	
}
