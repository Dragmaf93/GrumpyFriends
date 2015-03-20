package physicEngine;
import java.util.ArrayList;
import java.util.HashMap;

import world.AbstractWorld;
import world.Vector;
import world.World;
import element.Element;
import element.character.AbstractCharacter;


public class PhysicEngine {
	
	private static PhysicEngine instance;
	
	private World world;
	private CollisionManager collisionManager;
	private long time0;
	private ArrayList<Element> elementsToMove;
	
	private PhysicEngine(){
		world = AbstractWorld.getInstance();
		collisionManager = new CollisionManager();
		time0=System.currentTimeMillis();
		
		elementsToMove = new ArrayList<>();
	}
	
	public static PhysicEngine getInstace(){
		if(instance == null){
			instance = new PhysicEngine();
		}
		return instance;
	}
	
	public void moveOnAir(MovableElement elementToMove,Vector speed0, Vector position0, long time0){
		
		long time = System.currentTimeMillis()-time0;
		
		int xFirst = elementToMove.getX(), yFirst = elementToMove.getY();
		
		if(speed0.getX()==0 && speed0.getY()>0){
			if(!collisionManager.collidesBotton(elementToMove,time)){
				elementToMove.setPosition(makePositionParabolicMotion(speed0, position0, time));
				elementToMove.setSpeed(makeSpeedParabolicMotion(speed0, time));		
			}
		}
		else if(speed0.getX()==0 && speed0.getY()<0){
			if(!collisionManager.collidesTop(elementToMove, time)){
				elementToMove.setPosition(makePositionParabolicMotion(speed0, position0, time));
				elementToMove.setSpeed(makeSpeedParabolicMotion(speed0, time));	
			}
		}
		else if(speed0.getX()>0 && speed0.getY()>0){
			if(!collisionManager.collidesBottonRight(elementToMove, time)){
				
			}
			
		}
		else if(speed0.getX()>0 && speed0.getY()<0){
			if(!collisionManager.collidesTopRight(elementToMove, time)){
				
			}
			
		}
		else if(speed0.getX()<0 && speed0.getY()>0){
			if(!collisionManager.collidesBottonLeft(elementToMove, time)){
				
			}
		}
		else if(speed0.getX()<0 && speed0.getY()<0){
			if(!collisionManager.collidesTopLeft(elementToMove, time)){
				
			}
		}
		
		if (elementToMove instanceof AbstractCharacter)
			world.update((AbstractCharacter) elementToMove, xFirst, yFirst);
		
	}
	
	public Vector makePositionParabolicMotion(Vector speed0, Vector position0, long time){
		int x = (int) (position0.getX() + (speed0.getX() * time));
		int y = (int) (position0.getY() + (speed0.getY() * time) + (0.5 * world.getGravity().getY() * Math.pow(time, 2)));
		return new Vector(x,y);
	}
	
	public Vector makeSpeedParabolicMotion(Vector speed0, long time){
		int Sy = (int) (speed0.getY() + (world.getGravity().getY() * time));
		return new Vector(speed0.getX(),Sy);
	}
	
	public void moveOnGround(MovableElement elementToMove)
	{
		Vector speed0 = elementToMove.getSpeed();
		Vector move = new Vector(speed0.getX(),0);
		int xFirst = elementToMove.getX(), yFirst = elementToMove.getY();
		
		if(speed0.getX() > 0){
			if(!collisionManager.collidesRight(elementToMove,move)){
				speed0.set(move);
				elementToMove.setPosition(new Vector(elementToMove.getX()+speed0.getX(), elementToMove.getY()+speed0.getY()));
				System.out.println(speed0);
			}
		}
		else if(speed0.getX() < 0){
			if(!collisionManager.collidesLeft(elementToMove, move)){
				speed0.set(move);
				elementToMove.setPosition(new Vector(elementToMove.getX()+speed0.getX(), elementToMove.getY()+speed0.getY()));
			}
		}
		
		if (elementToMove instanceof AbstractCharacter)
			world.update((AbstractCharacter) elementToMove, xFirst, yFirst);
	}
	
	
	public void sweep(Element elementSweeper,Element elementToSweep){
		
	}
	void movesElement(int i){
		Element elementToMove = elementsToMove.get(i);
		
	}
	public void addElementToMove(Element element)
	{
		if(!elementsToMove.contains(element)){
			elementsToMove.add(element);
		}
	}
}
