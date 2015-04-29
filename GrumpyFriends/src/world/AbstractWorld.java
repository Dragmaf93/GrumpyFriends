package world;



import java.lang.reflect.Constructor;
import java.util.HashMap;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import element.Element;
import element.character.AbstractCharacter;
import element.character.Character;


public abstract class AbstractWorld extends org.jbox2d.dynamics.World
{

	protected int height,width;
	protected int numberRow = 0, numberColumn = 0;

	protected HashMap<String, Character> characterContainer;
	
	protected Ground[][] worldMatrix;
	
	protected static World instanceSon;
	
	public AbstractWorld(Vec2 gravity, boolean doSleep) {
		super(gravity, doSleep);
	}

	
	public static void initializes(String typeWorld){
		if(instanceSon == null)
			try {
			Class<?> factoryClass = Class.forName("world."+typeWorld);
			
			Constructor<AbstractWorld> constructorSon = (Constructor<AbstractWorld>) factoryClass.getDeclaredConstructor();
			constructorSon.setAccessible(true);
			AbstractWorld son = constructorSon.newInstance();
			instanceSon = (World) son;
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


	public Character getCharacter(String name) 
	{
		return characterContainer.get(name);
	}

	
	public void update(AbstractCharacter character, int xFirst, int yFirst) 
	{
		
	}
	
	public Ground getGround(int x , int y){
		return worldMatrix[y][x];
	}
	public Element getElementByPoint(int x, int y) {
		return worldMatrix[pointToCellY(y)][pointToCellX(x)];
	}
	
	public void setDimension(int height, int width){
		this.height = height;
		this.width = width;
				
		this.numberColumn = (int) Math.ceil((double) (width/Ground.WIDTH));
		this.numberRow = (int) Math.ceil((double) (height/Ground.HEIGHT));
		System.out.println("Righe "+numberRow);
		System.out.println("Colonne " +numberColumn);
		this.worldMatrix= new Ground[numberRow][numberColumn];
	}
	
	public void addCharacter(Character character){
		characterContainer.put(character.getName(), character);
	}
	
	public int getNumberRow()
	{
		return numberRow;
	}

	public int getNumberColumn()
	{
		return numberColumn;
	}
			
	public int pointToCellX(float x)
	{
		int n= (int)(((float)x * (float)numberColumn) / (float)width);
		if(n == numberColumn) return n -1;
		return n;
	}
	
	public int pointToCellY(float y)
	{
		int n = (int)(((float)y * (float)numberRow) / (float)height);
		if(n == numberRow) return n -1;
		return n;
	}


	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}


	public void addGround(float x, float y)
	{
		
		int j = pointToCellX(x);
		int i = pointToCellY(y);
		if(worldMatrix[i][j] != null) return;
				
		worldMatrix[i][j] = new Ground(x, y);
	}
	
	public void print(){
		for(int i = 0; i < numberRow;i++){
			for(int j = 0; j < numberColumn;j++){
				System.out.print(worldMatrix[i][j]+" ");
				
			}
			System.out.println();
			}
	
		System.out.println("FINITO");
	}
}
