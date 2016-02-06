package menu.worldMenu;

import gui.ImageLoader;

import java.util.HashMap;
import java.util.List;

import menu.AbstractPageComponent;
import utils.Point;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class PreviewWorldViewer extends AbstractPageComponent {

	private Pane previewPane;
	private ImageLoader imageLoader;
	
	private PreviewLoaderMap loaderMap;

	private HashMap<String, HashMap<String,Pane> > previewContainer;
	private HashMap<String, ImageView> previewBackground;
	private ImageView imageBackground;
	private Pane lastWorld;
	
	public PreviewWorldViewer(WorldPage worldPage,ImageLoader imageLoader) {
		super(worldPage);
		this.imageLoader=imageLoader;
		
		loaderMap = new PreviewLoaderMap(rectangleBackground.getWidth()-20,
				rectangleBackground.getHeight()-20);
		previewPane = new StackPane();
		
		previewContainer = new HashMap<String, HashMap<String,Pane>>();
		previewBackground = new HashMap<String, ImageView>();
		
		imageBackground = new ImageView();
		imageBackground.setFitWidth(rectangleBackground.getWidth()-2);
		imageBackground.setFitHeight(rectangleBackground.getHeight()-2);	
		
		root.getChildren().add(imageBackground);
		imageBackground.toBack();
		root.getChildren().add(previewPane);
	}

	public void showPreview(String map,String typeWorld) {
		imageBackground.setImage(imageLoader.getImageBackgrounds(typeWorld));		
		
		if (!previewContainer.containsKey(map)) {
			previewContainer.put(map, new HashMap<String,Pane>());
		}

		if(!previewContainer.get(map).containsKey(typeWorld)){
			List<List<Point>> polygonPoints = loaderMap.getPolygonPoints(map);
			lastWorld = new Pane();
			
			for (List<Point> points : polygonPoints) {
				Polygon shape = new Polygon();
				for (Point point : points) {
					shape.getPoints().add(point.x);
					shape.getPoints().add(point.y);
				}
				ImagePattern i = new ImagePattern(imageLoader.getGroundPreview(typeWorld),0,0,133,133,false);
				shape.setFill(i);
				lastWorld.getChildren().add(shape);
			}
			
			previewContainer.get(map).put(typeWorld, lastWorld);
			
		}
		
		if (!previewPane.getChildren().contains(previewContainer.get(map).get(typeWorld))) {
			if (!previewPane.getChildren().isEmpty())
				previewPane.getChildren().remove(0);
			previewPane.getChildren().add(previewContainer.get(map).get(typeWorld));
		}

	}
	
	@Override
	public void reset() {
		imageBackground.setImage(null);
		previewPane.getChildren().remove(lastWorld);
	}
	
	public double getHeightComponent() {
		return 300;
	}

	@Override
	public double getWidthComponent() {
		return 650;
	}

	@Override
	public String getNameComponent() {
		return "PREVIEW MAP";
	}

	@Override
	public String[] getValues() {
		return null;
	}

}
