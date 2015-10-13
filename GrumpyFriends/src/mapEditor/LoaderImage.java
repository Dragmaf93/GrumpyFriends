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
		images.put("grass",new ImageForObject(path+"grass.png",mapEditor));
		images.put("box",new ImageForObject(path+"box.png",mapEditor));
		images.put("box1",new ImageForObject(path+"box1.png",mapEditor));
		images.put("ground",new ImageForObject(path+"ground.png",mapEditor));
		images.put("wall",new ImageForObject(path+"wall.png",mapEditor));
		images.put("wall2",new ImageForObject(path+"wall2.png",mapEditor));
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
