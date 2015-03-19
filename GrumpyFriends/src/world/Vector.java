package world;

public class Vector 
{
	private int x;
	private int y;
	
	public Vector(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}
	
	public void set(Vector vector){
		this.x=vector.x;
		this.y=vector.y;
	}
	public int getX() 
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setX(int x) 
	{
		this.x = x;
	}
	
	public void setY(int y) 
	{
		this.y = y;
	}
	
	@Override
	public Vector clone() 
	{
		Vector temp = new Vector(x, y);
		return temp;
	}
	@Override
	public String toString() {
		return "X : "+ x +" Y "+y;
	}
}
