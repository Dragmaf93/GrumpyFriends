package mapEditor;

import javafx.geometry.Point2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class ImageForObject extends Polygon
{
	private String path;
	private String nameObject;
	private final MapEditor mapEditor;
	private Point2D upperLeftPosition;
	private Point2D upperRightPosition;
	private Point2D bottomLeftPosition;
	private Point2D bottomRightPosition;
	
	private double width;
	private double height;
	private double angleRotation;
	
	private DropShadow borderGlow;
	
	public ImageForObject(String path, MapEditor mapEditor, String nameObject, Point2D point1, Point2D point2, Point2D point3) 
	{
		super();

		this.width = point3.getX();
		this.height = point2.getY();
		
		this.upperLeftPosition = point1;
		this.bottomLeftPosition = point2;
		this.bottomRightPosition = point3;
		
		if (nameObject.equals("inclinedGround"))
			this.angleRotation = angleRotation;
//		TODO vedere come si calcola l'angolo avendo gli estremi
		
		this.path = path;
		this.mapEditor = mapEditor;
		
		this.nameObject = nameObject;
		
		borderGlow = new DropShadow();
		borderGlow.setOffsetX(0f);
		borderGlow.setOffsetY(0f);
		borderGlow.setColor(Color.rgb(0, 0, 0));
		borderGlow.setWidth(10f);
		borderGlow.setHeight(10f);
		this.setEffect(borderGlow);
//		this.setEffect(new Reflection());
	}
	
	@Override
	protected ImageForObject clone() throws CloneNotSupportedException {
		
		ImageForObject newImage = new ImageForObject(this.path, this.mapEditor, this.nameObject, this.upperLeftPosition, this.bottomLeftPosition, this.bottomRightPosition);
		
		newImage.setLayoutX(this.getLayoutX());
		newImage.setLayoutY(this.getLayoutY());
//		
//		newImage.setUpperLeftPosition(this.upperLeftPosition);
//		newImage.setUpperRightPosition(this.upperRightPosition);
//		newImage.setBottomLeftPosition(this.bottomLeftPosition);
//		newImage.setBottomRightPosition(this.bottomRightPosition);
		
		if (this.nameObject.equals("inclinedGround"))
			newImage.angleRotation = this.angleRotation;
		return newImage;
	}
	
	public String getNameObject() {
		return nameObject;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public String getAngleRotation() {
		return Integer.toString((int) angleRotation);
//		TODO VEdere se è possibile trasformare un double in string e non un int
	}
	
	public Point2D getUpperLeftPosition() {
		return upperLeftPosition;
	}
	
	public Point2D getUpperRightPosition() {
		return upperRightPosition;
	}

	public Point2D getBottomLeftPosition() {
		return bottomLeftPosition;
	}
	
	public Point2D getBottomRightPosition() {
		return bottomRightPosition;
	}
	
	public void setUpperLeftPosition(Point2D startPosition) {
		this.upperLeftPosition = startPosition;
	}
	
	public void setUpperRightPosition(Point2D startRightPosition) {
		this.upperRightPosition = startRightPosition;
	}

	public void setBottomLeftPosition(Point2D endPosition) {
		this.bottomLeftPosition = endPosition;
	}
	
	public void setBottomRightPosition(Point2D bottomRightPosition) {
		this.bottomRightPosition = bottomRightPosition;
	}
		
	public void modifyPosition(Point2D point,double width, double height) {
		this.upperLeftPosition = new Point2D(point.getX(), point.getY());
		this.upperRightPosition = new Point2D(point.getX()+width, point.getY());
		this.bottomLeftPosition = new Point2D(point.getX(), point.getY()+height);
		this.bottomRightPosition = new Point2D(point.getX()+width, point.getY()+height);
		
//		System.out.println(upperLeftPosition+" ------- "+point.getX()+" "+point.getY());
	}
	
	public boolean isInTheArea(MouseEvent event) {
		return (event.getY() > upperLeftPosition.getY() && event.getY() < bottomLeftPosition.getY()) 
				&& (event.getX() > upperLeftPosition.getX() && event.getX() < upperRightPosition.getX());
	}

	public boolean isInTheLimit(MouseEvent event) {
		return ((int)event.getX() == (int)upperLeftPosition.getX() ||
				(int)event.getX() == (int)upperRightPosition.getX() || 
				(int)event.getY() == (int)upperLeftPosition.getY() ||
				(int)event.getY() == (int)bottomLeftPosition.getY());
	}
	
	public void setWidth(double width) {
		this.width = width;
//		this.setImage(new Image(path,width,this.getHeight(),false,false));
//		System.out.println("YO: "+this.getImage().getWidth());
	}
	
	public void setHeight(double height) {
		this.height = height;
//		this.setImage(new Image(path,this.getWidth(),height,false,false));
	}

	@Override
	public String toString() {
		return "ImageForObject [nameObject=" + nameObject
				+ ", upperLeftPosition=" + upperLeftPosition
				+ "\n, bottomLeftPosition=" + bottomLeftPosition
				+ "\n, upperRightPosition=" + upperRightPosition + ", width="
				+ width + ", height=" + height + "]";
	}
	
	
	
}

