package utils;

public class Point {
	
	public double x;
	public double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point() {
		this.x=0;
		this.y=0;
	}

	public void set(double x, double y){
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return "X : "+ x +" Y "+y;
	}
}
