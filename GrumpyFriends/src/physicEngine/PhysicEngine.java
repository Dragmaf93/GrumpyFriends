package physicEngine;
import world.AbstractWorld;
import world.Vector;
import world.World;
import element.Element;


public class PhysicEngine {
	
	private static PhysicEngine instance;
	
	private World world;
	private CollisionManager collisionManager;
	private long time0,time;
	
	private PhysicEngine(){
		world = AbstractWorld.getInstance();
		collisionManager = new CollisionManager();
		time0=System.currentTimeMillis();
	}
	
	public static PhysicEngine getInstace(){
		if(instance == null){
			instance = new PhysicEngine();
		}
		return instance;
	}
	
	public void moveOnAir(Element elementToMove,Vector speed0, Vector position0, long time0){
		
		time = System.currentTimeMillis()-time0;
		
		if(speed0.getX()==0 && speed0.getY()>0){
			if(!collisionManager.collidesBotton(elementToMove,speed0,position0,time)){
				elementToMove.setPosition(makePositionParabolicMotion(speed0, position0, time));
				elementToMove.setCurrentSpeed(makeSpeedParabolicMotion(speed0, time));		
			}
		}
		else if(speed0.getX()==0 && speed0.getY()<0){
			if(!collisionManager.collidesTop(elementToMove, time)){
				elementToMove.setPosition(makePositionParabolicMotion(speed0, position0, time));
				elementToMove.setCurrentSpeed(makeSpeedParabolicMotion(speed0, time));	
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
	
	public void moveOnGround(Element elementToMove)
	{
		Vector speed0 = elementToMove.getCurrentSpeed();
		Vector move = new Vector(speed0.getX(),0);
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
	}
	
	public void sweep(Element elementSweeper,Element elementToSweep){
		
	}
}
