package world;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Scanner;

import element.Element;
import element.Position;
import element.character.AbstractCharacter;
import element.character.Chewbacca;


public abstract class AbstractWorld implements World 
{
	protected int height,width;
	protected Element[][] worldMatrix;
	
	protected static World instanceSon;
	
	public AbstractWorld(String path) 
	{	
		readMatrix("src/world/matrix");
	}
	
	public static void initializes(String typeWorld){
		if(instanceSon == null)
			try {
			Class<?> factoryClass = Class.forName(typeWorld);
			
			Constructor<AbstractWorld> constructorSon = (Constructor<AbstractWorld>) factoryClass.getDeclaredConstructor(String.class);
			constructorSon.setAccessible(true);
			AbstractWorld son = constructorSon.newInstance(typeWorld);
			instanceSon = son;
			} catch (Exception e) {
				e.printStackTrace();
			}	
	}
	
	public static World getInstance(){
		return instanceSon;
	}
	
	public static void setInstanceNull(){
		instanceSon = null;
	}
	@Override
	public Element getElement(int x, int y) 
	{
		return worldMatrix[y][x];
	}

	@Override
	public void update(AbstractCharacter character, int xFirst, int yFirst) 
	{
		worldMatrix[yFirst][xFirst]=null;
		worldMatrix[character.getY()][character.getX()]=character;
//		System.out.println(worldMatrix[xFirst][yFirst]+" "+xFirst+" "+yFirst);
//		System.out.println(worldMatrix[character.getX()][character.getY()]+" "+character.getX()+" "+character.getY());
	}

	public void readMatrix(String pathFile)
	{
		int xPrincipale = 0, yPrincipale = 0;
		try 
		{
			FileReader filein = new FileReader(pathFile);
			int next;
			int currentRow = 0, currentColumn = 0;
			BufferedReader b=new BufferedReader(filein);
			height=Integer.parseInt(b.readLine());
			width=Integer.parseInt(b.readLine());
			worldMatrix = new Element[height][width];

			do
			{
				next = b.read();
				if (next != -1 )
				{
					char nextc = (char) next;
					switch(nextc)
					{
					case '0':
						worldMatrix[currentRow][currentColumn] = null;
						break;
					case '1':
						worldMatrix[currentRow][currentColumn] = new Ground(currentColumn,currentRow);
						break;
					case '2':
						worldMatrix[currentRow][currentColumn] = new Chewbacca(currentColumn, currentRow, 100, null, null);
						break;
					//TODO implementare il resto
					}
					
					if (currentColumn == width)
					{
						currentRow ++;
						currentColumn = 0;
					}
					else
						currentColumn ++;
				}
			}
			while (next != -1);
			
			filein.close();
			b.close();
		} catch (IOException e) 
		{
			System.out.println(e);
			System.out.println("File non esistente");
		}
	}
	
	public void print ()
	{
		for (int i = 0; i < height; i++)
		{
			String row="";
			for (int j = 0; j < width; j++)
			{
				row += worldMatrix[i][j];
				row += " ";
//				System.out.print(worldMatrix[i][j]);
			}
			System.out.println(row);
			//System.out.println();
		}
//		Chewbacca b = (Chewbacca) getPrincipale();
//		World w = b.getWorld();
//		System.out.println(b.getX()+" "+b.getY()+" "+ w.getElement(b.getX(), b.getY()));
	}
	
	public Element getPrincipale()
	{
		for (int i=0; i< height; i++)
		{
			for (int j=0; j<width; j++)
			{
				if (worldMatrix[i][j] instanceof Chewbacca)
					return worldMatrix[i][j];
			}
		}
		return null;
	}
	
	@Override
	public int getHeight() 
	{
		return height;
	}

	@Override
	public int getWidth() 
	{
		return width;
	}
	
	public static void main(String[] args) {
		AbstractWorld.initializes("world.Planet");
		AbstractWorld world = (AbstractWorld) AbstractWorld.getInstance();
		world.print();
		
		Chewbacca chewbacca = (Chewbacca) world.getPrincipale();
		chewbacca.setWorld();

		System.out.println("MUOVI");
		final Scanner scanner = new Scanner(System.in);
		System.out.println("inserisci direzione");
		int direction = scanner.nextInt();
		
		while (direction != 9)
		{
			if (direction == 2)
				chewbacca.jump();
			else
				chewbacca.move(direction);
			world.print();
			System.out.println("inserisci direzione");
			direction = scanner.nextInt();
		}
		
	}
}
