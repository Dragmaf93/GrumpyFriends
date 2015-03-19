package physicEngine;
import element.Element;
import world.AbstractWorld;
import world.Ground;
import world.Vector;
import world.World;


public class CollisionManager {

	private World world;
	
	public CollisionManager(){
		world = AbstractWorld.getInstance();
	}
	
	public boolean collidesRight(Element element,Vector move){
		
		boolean simpleMove=true;
		boolean climbeMove=false;
		
		for (int i = 0; i < element.getHeight(); i+=world.SIZE_CELL) 
		{
			if (world.getElementByPoint(element.getX()+element.getWidth()+move.getX(), element.getY()-i) != null){
				simpleMove = false;
				break;
			}
		}
		
		if(!simpleMove){
			climbeMove = true;
			for (int i = world.SIZE_CELL; i < element.getHeight(); i+=world.SIZE_CELL) 
			{
				if (((world.getElementByPoint(element.getX()+element.getWidth(),element.getY())) instanceof Ground ||
						world.getElementByPoint(element.getX()+element.getWidth(), element.getY()) == null) && (
						world.getElementByPoint(element.getX()+element.getWidth()+move.getX(),element.getY()-i)) != null){
					climbeMove = false;
					break;
				}
			}
			if(climbeMove)
				move.setY(-world.SIZE_CELL);
		}
		System.out.println(simpleMove || climbeMove);
		return !(simpleMove || climbeMove);
	}
	
	public boolean collidesLeft(Element element, Vector move){
		
		boolean simpleMove=true;
		boolean climbeMove=false;
		
		for (int i = 0; i < element.getHeight(); i+=world.SIZE_CELL) 
		{
			if (world.getElementByPoint(element.getX()+move.getX(), element.getY()-i) != null){
				simpleMove = false;
				break;
			}
		}
		
		if(!simpleMove){
			climbeMove = true;
			for (int i = world.SIZE_CELL; i < element.getHeight(); i+=world.SIZE_CELL) 
			{
				if (((world.getElementByPoint(element.getX()+move.getX(),element.getY())) instanceof Ground ||
						world.getElementByPoint(element.getX()+move.getX(), element.getY()) == null) && (
						world.getElementByPoint(element.getX()+move.getX(),element.getY()-i)) != null){
					climbeMove = false;
					break;
				}
			}
			if(climbeMove)
				move.setY(-world.SIZE_CELL);
		}
		
		return !(simpleMove || climbeMove);
	}
	
	public boolean collidesTop(Element element,long time){

		if(world.getElementByPoint(element.getX(),element.getY()+element.getCurrentSpeed().getY()-element.getHeight())!=null ||
			world.getElementByPoint(element.getX()+element.getWidth(),element.getY()+element.getCurrentSpeed().getY()-element.getHeight())!=null){
				return true;
		}
		return false;
	}
	
	public boolean collidesBotton(Element element, Vector speed0, Vector position0,long time){
		int coordinateX = position0.getX();
		int coordinateY = (int) (position0.getY() + (speed0.getY() * time) + (0.5 * world.getGravity().getY() * time));
		
		if (world.getElementByPoint(coordinateX, coordinateY) != null ||
				world.getElementByPoint(coordinateX+element.getWidth(),coordinateY) != null){
				return true;
		}
		return false;
	}
	
	public boolean collidesTopRight(Element element, long time){
		return false;
	}
	
	public boolean collidesTopLeft(Element element, long time){
		return false;
	}
	
	public boolean collidesBottonRight(Element element,long time){
		return false;
	}
	
	public boolean collidesBottonLeft(Element element, long time){
		return false;
	}
}
