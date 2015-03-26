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
	
	public boolean collidesRight(MovableElement element,Vector move){
		
		boolean simpleMove=true;
		boolean climbMove=false;
		
		for (int i = 0; i < element.getHeight(); i+=world.SIZE_CELL) 
		{
			if (world.getElementByPoint(element.getX()+element.getWidth()+move.getX(), element.getY()-i) != null){
				simpleMove = false;
				break;
			}
		}
		
		if(!simpleMove){
			climbMove = true;
			for (int i = world.SIZE_CELL; i < element.getHeight(); i+=world.SIZE_CELL) 
			{
				if (((world.getElementByPoint(element.getX()+element.getWidth(),element.getY())) instanceof Ground ||
						world.getElementByPoint(element.getX()+element.getWidth(), element.getY()) == null) && (
						world.getElementByPoint(element.getX()+element.getWidth()+move.getX(),element.getY()-i)) != null){
					climbMove = false;
					break;
				}
			}
			if(climbMove)
				move.setY(-world.SIZE_CELL);
		}
//		System.out.println(simpleMove || climbMove);
		return !(simpleMove || climbMove);
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
	
	public boolean collidesTop(MovableElement element,long time) {

		if(world.getElementByPoint(element.getX(),element.getY()+element.getSpeed().getY()-element.getHeight())!=null ||
			world.getElementByPoint(element.getX()+element.getWidth(),element.getY()+element.getSpeed().getY()-element.getHeight())!=null){
				return true;
		}
		return false;
	}
	
	public boolean collidesBottom(MovableElement element, long time) {
		
		int coordinateX = element.getX();
		int coordinateY = (int) (element.getY() + element.getSpeed().getY());
		
		if (world.getElementByPoint(coordinateX, coordinateY) != null ||
				world.getElementByPoint(coordinateX+element.getWidth(),coordinateY) != null){
				return true;
		}
		return false;
	}
	
	public boolean collidesTopRight(MovableElement element, long time){
		
		if (world.getElementByPoint(element.getX()+element.getSpeed().getX(),
				element.getY()+element.getSpeed().getY()-element.getHeight())!=null ||
			 world.getElementByPoint(element.getX()+element.getWidth()+element.getSpeed().getX(),
					 element.getY()+element.getSpeed().getY()-element.getHeight())!=null){
				return true;
		}
		
		for (int i = 0; i < element.getHeight(); i+=world.SIZE_CELL) 
		{
			if (world.getElementByPoint(element.getX()+element.getWidth()+element.getSpeed().getX(), 
					element.getY()+element.getSpeed().getY()-i) != null){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean collidesTopLeft(MovableElement element, long time){
		
		if (world.getElementByPoint(element.getX()+element.getSpeed().getX(),
				element.getY()+element.getSpeed().getY()-element.getHeight())!=null ||
			 world.getElementByPoint(element.getX()+ element.getWidth() +element.getSpeed().getX(),
					 element.getY()+element.getSpeed().getY()-element.getHeight())!=null){
			
				return true;
		}
		
		for (int i = 0; i < element.getHeight(); i+=world.SIZE_CELL) 
		{
			if (world.getElementByPoint(element.getX()+element.getSpeed().getX(), 
					element.getY()+element.getSpeed().getY()-i) != null){
				
				return true;
			}
		}
		return false;
	}
	
	public boolean collidesBottomRight(MovableElement element,long time){
		
		if (world.getElementByPoint(element.getX()+element.getSpeed().getX(), element.getY()+element.getSpeed().getY()) != null ||
				world.getElementByPoint(element.getX()+element.getSpeed().getX()+element.getWidth(),
						element.getY()+element.getSpeed().getY()) != null){
				return true;
		}
		
		for (int i = 0; i < element.getHeight(); i+=world.SIZE_CELL) 
		{
			if (world.getElementByPoint(element.getX()+element.getSpeed().getX()+element.getWidth(), 
					element.getY()+element.getSpeed().getY()-i) != null){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean collidesBottomLeft(MovableElement element, long time){
		
		if (world.getElementByPoint(element.getX()+element.getSpeed().getX(), element.getY()+element.getSpeed().getY()) != null ||
				world.getElementByPoint(element.getX()+element.getSpeed().getX()+element.getWidth(),
						element.getY()+element.getSpeed().getY()) != null){
				return true;
		}
		
		for (int i = 0; i < element.getHeight(); i+=world.SIZE_CELL) 
		{
			if (world.getElementByPoint(element.getX()+element.getSpeed().getX(), 
					element.getY()+element.getSpeed().getY()-i) != null){
				return true;
			}
		}
		
		return false;
	}
}
