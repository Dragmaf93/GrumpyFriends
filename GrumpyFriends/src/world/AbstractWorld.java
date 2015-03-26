package world;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import element.Element;
import element.character.AbstractCharacter;
import element.character.Chewbacca;


public abstract class AbstractWorld implements World 
{
	protected int height,width;
	protected Element[][] worldMatrix;
	protected int numberRow = 0, numberColumn = 0;
	protected HashMap<Vector, AbstractCharacter> characterContainer;
	
	protected static World instanceSon;
	
	public AbstractWorld(String path) 
	{	
		readMatrix("src/world/matrix");
		characterContainer = new HashMap<>();
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
		characterContainer.remove(new Vector(xFirst, yFirst));
		characterContainer.put(new Vector(character.getX(), character.getY()), character);
	}
	
	@Override
	public Element getElementByPoint(int x, int y) {
		return worldMatrix[pointToCellY(y)][pointToCellX(x)];
	}
	
	public void readMatrix(String pathFile)
	{
		try 
		{
			FileReader filein = new FileReader(pathFile);
			int next;
			int currentRow = 0, currentColumn = 0;
			BufferedReader b=new BufferedReader(filein);
			height=Integer.parseInt(b.readLine());
			numberRow = height / SIZE_CELL;
			width=Integer.parseInt(b.readLine());
			numberColumn = width / SIZE_CELL;
			System.out.println(height+ " "+ width);
			System.out.println(numberRow+" "+numberColumn+"\n\n\n");
			worldMatrix = new Element[numberRow][numberColumn];

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
//					case '2':
//						worldMatrix[currentRow][currentColumn] = new Chewbacca(currentColumn, currentRow, 100, null, null);
//						break;
					//TODO implementare il resto
					}
					
					if (currentColumn == numberColumn)
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
		for (int i = 0; i < numberRow; i++)
		{
			String row="";
			for (int j = 0; j < numberColumn; j++)
			{
				if (j == pointToCellX(getPrincipale().getX()) && i == pointToCellY(getPrincipale().getY()))
					row += "CHEW";
				else
					row += worldMatrix[i][j];
				row += " ";
//				System.out.print(worldMatrix[i][j]);
			}
			System.out.println(row);
		}
//		Chewbacca b = (Chewbacca) getPrincipale();
//		World w = b.getWorld();
//		System.out.println(b.getX()+" "+b.getY()+" "+ w.getElement(b.getX(), b.getY()));
	}
	
	public Element getPrincipale()
	{
		for(Entry<Vector, AbstractCharacter> entry : characterContainer.entrySet()) {
		    return entry.getValue();

		}
		return null;
	}
	
	public int getNumberRow()
	{
		return numberRow;
	}

	public int getNumberColumn()
	{
		return numberColumn;
	}
			
	public int pointToCellX(int x)
	{
		return (int) (((float)x * (float)numberColumn) / (float)width);
	}
	
	public int pointToCellY(int y)
	{
		return (int) (((float)y * (float)numberRow) / (float)height);
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
	
	@Override
	public int cellXToPoint(int x)
	{
		return (int) (((float)x * (float)width) / (float)numberColumn);
	}
	
	@Override
	public int cellYToPoint(int y)
	{
		return (int) (((float)y * (float)height) / (float)numberRow);
	}
	
	public static void main(String[] args) {
		AbstractWorld.initializes("world.Planet");
		AbstractWorld world = (AbstractWorld) AbstractWorld.getInstance();
		
		Chewbacca chewbacca = new Chewbacca(30, 30, 100, null, null);
		world.characterContainer.put(new Vector(chewbacca.getX(), chewbacca.getY()),chewbacca);
		chewbacca.setWorld();

		world.print();
		System.out.println("MUOVI");
		final Scanner scanner = new Scanner(System.in);
		System.out.println("inserisci direzione");
		int direction = scanner.nextInt();
		
		while (direction != 9)
		{
			if (direction == 2)
				chewbacca.jump();
			else{
				chewbacca.move(direction);
				chewbacca.stopToMove();
			}
			world.print();
//			if (chewbacca.isFall())
//				System.out.println("NOOOOOOOOOOOOOOOOOOOOO CADOOOOOOOOOOOOOOOOOOOoo");
//			else
//				System.out.println("SONO TROPPO FORTE");
			System.out.println("inserisci direzione");
			direction = scanner.nextInt();
		}
		
	}

	public HashMap<Vector, AbstractCharacter> getCharacters() {
		return characterContainer;
	}
}
