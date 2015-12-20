package gui.drawer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import clipper.ClipType;
import clipper.Clipper;
import clipper.IntPoint;
import clipper.PolyFillType;
import clipper.internal.PolyType;
import element.Ground;
import gui.ImageLoader;
import utils.Point;
import world.World;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;


public class WorldDrawer {
	
	private Group root;
	private World world;
	
	private PolygonGroundDrawer polygonGroundDrawer;
	private final static int INT_DOUBLE_MULTIPLIER = 10;
	
	private final static String PATH_WORLD="file:image/World/";
	private Image seaImage = new Image(PATH_WORLD+"Planet/sea.png");
	private Image groundImage;
	private ImageLoader imageLoader;
	
	public WorldDrawer(World world, ImageLoader imageLoader) {
		this.world = world;
		this.imageLoader = imageLoader;
		root = new Group();
		
		polygonGroundDrawer = new PolygonGroundDrawer(root,imageLoader);
	}
	
	public Node createWorld(){
		
		List<Ground> grounds = world.getGrounds();
		
		List<List<Point> > polygons = getPolygonToDraw(grounds);
		
		drawSea(world.getHeight()-20);
		for (List<Point> points : polygons) {
			polygonGroundDrawer.draw(points);
			
//			for (Point point : points) {
//				polygon.getPoints().add(point.x);
//				polygon.getPoints().add(point.y);
//			}
//			
//			root.getChildren().add(polygon);
		}
		drawSea(world.getHeight()+20);
		
		return root;
	}
	
	private void drawSea(double y){
		double widthImage = seaImage.getWidth();
		Group sea = new Group();
		for(double i= -widthImage;i<world.getWidth()+widthImage;i+=widthImage){
			ImageView imageView = new ImageView(seaImage);
			imageView.setY(y);
			imageView.setX(i);
			sea.getChildren().add(imageView);			
		}

		TranslateTransition transition = new TranslateTransition(Duration.millis(1500),sea);
		transition.setByX(20);
		transition.setCycleCount(TranslateTransition.INDEFINITE);
		transition.setAutoReverse(true);
		transition.play();
		root.getChildren().add(sea);
	}
	
	private List< List<Point> > getPolygonToDraw(List<Ground> grounds){
		
		List<clipper.Polygon> polygons = new ArrayList<clipper.Polygon>();
		int i = 0;
		
		for (Ground ground : grounds) {
			polygons.add(new clipper.Polygon());
			
			List<Point> points = ground.getPoints();
			for (Point point : points) {
				polygons.get(i).add((long)(point.x*INT_DOUBLE_MULTIPLIER),
						(long) (point.y*INT_DOUBLE_MULTIPLIER));
			}
			i++;
		}
		
		List< List<Point> > pointsToReturn = new ArrayList<List<Point>>();
		List<clipper.Polygon> polygonAfterUnion = new ArrayList<clipper.Polygon>();
		
		Clipper clipper = new Clipper();
		clipper.addPolygons(polygons, PolyType.ptSubject);
		clipper.execute(ClipType.UNION, polygonAfterUnion, PolyFillType.NONZERO, PolyFillType.NONZERO);

		i=0;
		for (clipper.Polygon polygon : polygonAfterUnion) {
			List<IntPoint> points = polygon.getPoints();
			pointsToReturn.add(new ArrayList<Point>());
			
			for (IntPoint intPoint : points) {
				pointsToReturn.get(i).add(
						new Point(intPoint.x/INT_DOUBLE_MULTIPLIER, intPoint.y/INT_DOUBLE_MULTIPLIER));
			}
			i++;
		}
		
		return pointsToReturn;
	}
	
	public Node getWorldNode(){
		return root;
	}
}
