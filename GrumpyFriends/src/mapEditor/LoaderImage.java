package mapEditor;

import java.util.HashMap;

import javafx.scene.image.ImageView;

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
		images.put("prova",new ImageForObject(path,mapEditor, index++));
	}
	
	public ImageForObject copyImage(String name)
	{
		ImageForObject imageCopy = null;
		try {
			imageCopy = (ImageForObject) images.get(name).clone();
		
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return imageCopy;
	}
	
	
}
