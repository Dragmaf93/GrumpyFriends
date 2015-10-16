package world;

import org.jbox2d.common.Vec2;

public class Vector 
{
	public float x;
	public float y;
	
	
	public Vector(Vec2 vector){
		this.x=vector.x;
		this.y=vector.y;
	}
	
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector() {
		this.x=0;
		this.y=0;
	}

	public void set(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void set(Vec2 vector){
		this.x=vector.x;
		this.y=vector.y;
	}

	public String toString() {
		return "X : "+ x +" Y "+y;
	}

	public Vec2 toVec2() {
		return new Vec2(x, y);
	}
}
