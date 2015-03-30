package physicEngine;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import world.AbstractWorld;
import world.Vector;
import world.World;
import element.Element;
import element.character.AbstractCharacter;


public class PhysicEngine {
	
	private static PhysicEngine instance;
	
	private World world;
	private CollisionManager collisionManager;
	private ArrayList<MovableElement> elementsToMove;
	
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	private MovesThread movesThread;
	
	private PhysicEngine(){
		world = AbstractWorld.getInstance();
		collisionManager = new CollisionManager();
		elementsToMove = new ArrayList<>();
	}
	
	public static PhysicEngine getInstance(){
		if(instance == null){
			instance = new PhysicEngine();
		}
		return instance;
	}
	
	public void moveOnAir(MovableElement elementToMove){
		
		Vector speed0 = elementToMove.getSpeed0();
		Vector position0 = elementToMove.getPosition0();
		double time = ((double)System.currentTimeMillis()/2000.0)-elementToMove.getTime0();
		
		int xFirst = elementToMove.getX(), yFirst = elementToMove.getY();
		
		if(speed0.getX()==0 && speed0.getY()>0)
		{
			if(!collisionManager.collidesBottom(elementToMove,time))
			{
				
				elementToMove.setFall(true);
				elementToMove.setPosition(makePositionParabolicMotion(speed0, position0, time));
				elementToMove.setSpeed(makeSpeedParabolicMotion(speed0, time));		
				System.out.println(elementToMove.getSpeed());
			}
			else
			{
				
				elementToMove.setFall(false);
				elementToMove.afterCollision();
			}
		}
		else if(speed0.getX()==0 && speed0.getY()<0)
		{
			if(!collisionManager.collidesTop(elementToMove, time) && !isTimeOfMaxHeight(elementToMove, time))
			{
				elementToMove.setPosition(makePositionParabolicMotion(speed0, position0, time));
				elementToMove.setSpeed(makeSpeedParabolicMotion(speed0, time));	
				
			}
			else if (isTimeOfMaxHeight(elementToMove, time) && !collisionManager.collidesTop(elementToMove, time))
			{
				elementToMove.setJump(false);
				elementToMove.setPosition(makePositionParabolicMotion(speed0, position0, time));
				elementToMove.setSpeed(new Vector(elementToMove.getSpeed().getX(), 0));
				
				movementAfterJumpTop(elementToMove, time);
			}
			else if (collisionManager.collidesTop(elementToMove, time))
			{
				elementToMove.setJump(false);
				elementToMove.afterCollision();
			}
		}
		else if(speed0.getX()>0 && speed0.getY()>0)
		{
			if(!collisionManager.collidesBottomRight(elementToMove, time))
			{
				elementToMove.setFall(true);
				elementToMove.setPosition(makePositionParabolicMotion(speed0, position0, time));
				elementToMove.setSpeed(makeSpeedParabolicMotion(speed0, time));
			}
			else{
				elementToMove.afterCollision();
			}
		}
		else if(speed0.getX()>0 && speed0.getY()<0){
			if(!collisionManager.collidesTopRight(elementToMove, time) && !isTimeOfMaxHeight(elementToMove, time))
			{
				elementToMove.setPosition(makePositionParabolicMotion(speed0, position0, time));
				elementToMove.setSpeed(makeSpeedParabolicMotion(speed0, time));	
			}
			else if (isTimeOfMaxHeight(elementToMove, time) && !collisionManager.collidesTopRight(elementToMove, time))
			{
				elementToMove.setJump(false);
				elementToMove.setPosition(makePositionParabolicMotion(speed0, position0, time));
				elementToMove.setSpeed(new Vector(elementToMove.getSpeed().getX(), 0));
				
				movementAfterJumpTop(elementToMove, time);
			}
			else if (collisionManager.collidesTopRight(elementToMove, time))
			{
				elementToMove.setJump(false);
				elementToMove.afterCollision();
			}
			
		}
		else if(speed0.getX()<0 && speed0.getY()>0)
		{
			if(!collisionManager.collidesBottomLeft(elementToMove, time)){
				elementToMove.setFall(true);
				elementToMove.setPosition(makePositionParabolicMotion(speed0, position0, time));
				elementToMove.setSpeed(makeSpeedParabolicMotion(speed0, time));
			}
			else{
				elementToMove.afterCollision();
			}
		}
		else if(speed0.getX()<0 && speed0.getY()<0)
		{
			if(!collisionManager.collidesTopLeft(elementToMove, time) && !isTimeOfMaxHeight(elementToMove, time))
			{
				elementToMove.setPosition(makePositionParabolicMotion(speed0, position0, time));
				elementToMove.setSpeed(makeSpeedParabolicMotion(speed0, time));	
			}
			else if (isTimeOfMaxHeight(elementToMove, time) && !collisionManager.collidesTopLeft(elementToMove, time))
			{
				elementToMove.setJump(false);
				elementToMove.setPosition(makePositionParabolicMotion(speed0, position0, time));
				elementToMove.setSpeed(new Vector(elementToMove.getSpeed().getX(), 0));
				
				movementAfterJumpTop(elementToMove, time);
			}
			else if (collisionManager.collidesTopLeft(elementToMove, time))
			{
				elementToMove.setJump(false);
				elementToMove.afterCollision();
			}
		}
		
		System.out.println("Pos: "+elementToMove.getPosition());
		
		if (elementToMove instanceof AbstractCharacter)
			world.update((AbstractCharacter) elementToMove, xFirst, yFirst);
		
	}
	
	public boolean isTimeOfMaxHeight(MovableElement element, double time)
	{
		int timeForMaxHeight = -(element.getSpeed0().getY() / world.getGravity().getY());
		
//		System.out.println("TIME: "+time+ " "+timeForMaxHeight);
		
		if (time >= timeForMaxHeight)
			return true;		
		return false;
	}
	
	private void movementAfterJumpTop(MovableElement element, double time)
	{
		element.setPosition0(element.getPosition());
		element.resetTime0();
		element.setSpeed0(new Vector(element.getSpeed0().getX(), (int) (world.getGravity().getY() * time)));
		element.setSpeed(new Vector(element.getSpeed().getX(), (int) (world.getGravity().getY() * time)));
		element.setFall(true);
		System.out.println( "movement after jump" + element.getSpeed0());
	}
	
	
	public Vector makePositionParabolicMotion(Vector speed0, Vector position0, double time){
		System.out.println(time+ "  "+(double)speed0.getX()+
				" "+(double)speed0.getX() * time);
		int x = (int) (position0.getX() + (((double)speed0.getX()) * time));
		int y = (int) (position0.getY() + (((double)speed0.getY()) * time) + (0.5 * ((double)world.getGravity().getY()) * Math.pow(time, 2)));
		return new Vector(x,y);
	}
	
	public Vector makeSpeedParabolicMotion(Vector speed0, double time){
		int Sy = (int) ((double)speed0.getY() + ((double)world.getGravity().getY()) * time);
		return new Vector(speed0.getX(),Sy);
	}
	
	
	public void moveOnGround(MovableElement elementToMove)
	{
		Vector speed0 = elementToMove.getSpeed();
		Vector move = new Vector(speed0.getX(),0);
		int xFirst = elementToMove.getX(), yFirst = elementToMove.getY();
		
//		TODO gestire caduta in assenza di terreno
		if(speed0.getX() > 0){
			if(!collisionManager.collidesRight(elementToMove,move)){
				speed0.set(move);
				System.out.println(elementToMove.getX()+speed0.getX());
				elementToMove.setPosition(new Vector(elementToMove.getX()+speed0.getX(), elementToMove.getY()+speed0.getY()));
//				System.out.println(speed0);
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
	
	public ArrayList<MovableElement> getElementsToMove() {
		return elementsToMove;
	}

	void movesElement(int i)
	{
		MovableElement elementToMove = elementsToMove.get(i);
		elementToMove.doSingleMove();
	}
	
	void stopElementsMove()
	{
		try {
			lock.lock();
			
			while (elementsToMove.isEmpty())
				condition.await();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	public void removeElement(MovableElement element)
	{
		lock.lock();
		elementsToMove.remove(element);
		lock.unlock();
	}
	
	public void addElementToMove(MovableElement element)
	{	
		lock.lock();
		if(!elementsToMove.contains(element)){
			elementsToMove.add(element);
			element.setPosition0(element.getPosition());
			element.setSpeed0(element.getSpeed0());
			element.resetTime0();
		}
		
		if(movesThread == null){
			movesThread = new MovesThread();
			movesThread.start();
		}
		
		if (!elementsToMove.isEmpty())
			condition.signalAll();
		
		lock.unlock();
	}
}
