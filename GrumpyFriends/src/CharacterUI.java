import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import world.AbstractWorld;
import world.Vector;
import world.World;
import element.character.Chewbacca;


public class CharacterUI 
{
	private Chewbacca chewbacca;
	private AbstractWorld world;
	private Image chewbaccaImage;

	public CharacterUI() 
	{
		try {
		world = (AbstractWorld) AbstractWorld.getInstance();
		
		BufferedImage imageChewbacca;
			imageChewbacca = ImageIO.read(new File("images.png"));
		chewbacca = new Chewbacca(30, 30, 100, null, null);
		this.world.getCharacters().put(new Vector(chewbacca.getX(), chewbacca.getY()),chewbacca);
		chewbacca.setWorld();
		
		chewbaccaImage = imageChewbacca.getScaledInstance(chewbacca.getWidth(), chewbacca.getHeight(), 
				Image.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Chewbacca getChewbacca()
	{
		return chewbacca;
	}
	
	public Image getImage()
	{
		return chewbaccaImage;
	}

	
}
