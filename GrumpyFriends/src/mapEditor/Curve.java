package mapEditor;

import javafx.geometry.Point2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.QuadCurve;

public class Curve extends QuadCurve {

	private double width;
	private double height;

	private double x;
	private double y;
	private DropShadow borderGlow;
	
	private MapEditor mapEditor;
	private String nameObject;
	private Point2D start;
	private Point2D end;
	private Point2D pointForFirstTime;
	
	public Curve(MapEditor mapEditor, String nameObject, Point2D start, Point2D end) {
		
		this.mapEditor = mapEditor;
		this.nameObject = nameObject;
		this.start = start;
		this.end = end;
		
		setStartX(start.getX());
		setStartY(start.getY());
		setEndX(end.getX());
		setEndY(end.getY());
		setControlX(start.getX()/2);
		setControlY(0.0);
		
		width = end.getX() - start.getX();
		height = 60.0;
		
		borderGlow = new DropShadow();
		borderGlow.setOffsetX(0f);
		borderGlow.setOffsetY(0f);
		borderGlow.setColor(Color.rgb(0, 0, 0));
		borderGlow.setWidth(10f);
		borderGlow.setHeight(10f);
		this.setEffect(borderGlow);
	}
	
	public void modifyPositionFirst(Point2D point) {

		pointForFirstTime = point;
		setStartX(point.getX());
		setStartY(point.getY()+height);
		setEndX(point.getX()+width);
		setEndY(point.getY()+height);
		setControlX(point.getX()+width/2);
		setControlY(point.getY()-height/2);
		
		start = new Point2D(getStartX(), getStartY());
		end = new Point2D(getEndX(), getEndY());
	}

	@Override
	public String toString() {
		return "Curve [width=" + width + ", height=" + height
				+ ", getStartX()=" + getStartX() + ", getStartY()="
				+ getStartY() + ",\n getControlX()=" + getControlX()
				+ ", getControlY()=" + getControlY() + ",\n getEndX()="
				+ getEndX() + ", getEndY()=" + getEndY() + "]";
	}

	public void setX(double d) {
		x = d;
	}

	public void setY(double d) {
		y = d;
	}

	public double getY() {
		return y;
	}

	public double getX() {
		return x;
	}
	
	@Override
	protected Curve clone() throws CloneNotSupportedException {
		Curve curve = new Curve(mapEditor, nameObject, start, end);
		curve.setControlX(pointForFirstTime.getX()+width/2);
		curve.setControlY(pointForFirstTime.getY()-height/2);
		return curve;
	}
	
}
