import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javafx.scene.transform.Scale;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import element.character.AbstractCharacter;
import element.character.Chewbacca;
import world.AbstractWorld;
import world.Ground;
import world.Vector;


public class MyPanel extends JPanel
{
	private AbstractWorld world;
	int larghezza;
	int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	Image chewbaccaImage;
	CharacterUI character;
	
	public MyPanel() 
	{
		super();
		this.setBackground(Color.CYAN);
		
		AbstractWorld.initializes("world.Planet");
		world = (AbstractWorld) AbstractWorld.getInstance();
		this.setPreferredSize(new Dimension(world.getWidth(), altezza));
		this.setFocusable(true);
		character = new CharacterUI();
		
		this.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed (KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					character.getChewbacca().move(AbstractCharacter.RIGHT);
		        }
		        if (e.getKeyCode() == KeyEvent.VK_LEFT)
		        {
		        	character.getChewbacca().move(AbstractCharacter.LEFT);
		        }
		        MyPanel.this.repaint();
			}
			
			@Override
			public void keyReleased(KeyEvent e) 
			{
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					character.getChewbacca().stopToMove();
		        }
		        if (e.getKeyCode() == KeyEvent.VK_LEFT)
		        {
		        	character.getChewbacca().stopToMove();
		        }
		        MyPanel.this.repaint();
			}
			
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
		for (int y = 0; y < world.getNumberRow(); y++)
		{
			for (int x = 0; x < world.getNumberColumn(); x++) 
			{
				if (world.getElement(x, y) instanceof Ground)
					g.drawRect(x*20, y*20, 20, 20);
				
			}
		}
		
		g.drawImage(character.getImage(), character.getChewbacca().getX() , 
				character.getChewbacca().getY()-character.getChewbacca().getHeight(), this);
	}
//	
//	@Override
//	public void keyTyped(KeyEvent e) 
//	{
//		 if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//	            System.out.println("Right key typed");
//	        }
//	        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//	            System.out.println("Left key typed");
//	        }
//	}
//
//	@Override
//	public void keyPressed(KeyEvent e) {
//		
//	}
//
//	@Override
//	public void keyReleased(KeyEvent e) {
//		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//            System.out.println("Right key typed");
//        }
//        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//            System.out.println("Left key typed");
//        }
//	}
	
}
