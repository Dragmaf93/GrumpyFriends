package mapEditor;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ImageForObject extends ImageView
{
	String path;
	final MapEditor mapEditor;
	Point2D upperLeftPosition;
	Point2D bottomLeftPosition;
	Point2D upperRightPosition;
	
	boolean toCancelled = false;
	
	public ImageForObject(String path, MapEditor mapEditor) 
	{
		super(new Image(path,50, 100,false,false));
		this.path = path;
		this.mapEditor = mapEditor;
		
	}
	
	@Override
	protected ImageForObject clone() throws CloneNotSupportedException {
		
		ImageForObject newImage = new ImageForObject(this.path, this.mapEditor);
		
		newImage.setX(this.getX());
		newImage.setY(this.getY());
		
		newImage.setStartPosition(this.upperLeftPosition);
		newImage.setEndPosition(this.bottomLeftPosition);
		newImage.setStartRightPosition(this.upperRightPosition);
		
		newImage.setToCancelled(toCancelled);
		
		return newImage;
	}
	
	public Point2D getStartPosition() {
		return upperLeftPosition;
	}
	
	public Point2D getEndPosition() {
		return bottomLeftPosition;
	}
	
	public Point2D getStartRightPosition() {
		return upperRightPosition;
	}
	
	public void setStartPosition(Point2D startPosition) {
		this.upperLeftPosition = startPosition;
	}
	
	public void setEndPosition(Point2D endPosition) {
		this.bottomLeftPosition = endPosition;
	}
	
	public void setStartRightPosition(Point2D startRightPosition) {
		this.upperRightPosition = startRightPosition;
	}
		
	public void modifyPosition(Point2D point,double width, double height)
	{
		this.upperLeftPosition = new Point2D(point.getX(), point.getY());
		this.upperRightPosition = new Point2D(point.getX()+width, point.getY());
		this.bottomLeftPosition = new Point2D(point.getX(), point.getY()+height);
	}
	
	public boolean isInTheArea(MouseEvent event)
	{
		return (event.getY() > upperLeftPosition.getY() && event.getY() < bottomLeftPosition.getY()) 
				&& (event.getX() > upperLeftPosition.getX() && event.getX() < upperRightPosition.getX());
		
//		return ((event.getX() > startPosition1.getX() && event.getY() > startPosition1.getY())
//				||(event.getX() < startPosition2.getX() && event.getY() > startPosition2.getY())
//				|| (event.getX() > endPosition1.getX() && event.getY() < endPosition1.getY())
//				|| (event.getX() < endPosition2.getX() && event.getY() < endPosition2.getY()));
	}

	@Override
	public String toString() {
		return "ImageForObject "+toCancelled;
//				+ "[path=" + path + ", mapEditor=" + mapEditor
//				+ ", startPosition=" + upperLeftPosition + ", endPosition="
//				+ bottomLeftPosition ;
	}
	
	public void setToCancelled(boolean bool) {
		toCancelled = bool;
	}
	
	public boolean getToCancelled() {
		return toCancelled;
	}
	
}

