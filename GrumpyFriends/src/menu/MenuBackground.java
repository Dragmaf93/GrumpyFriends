package menu;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Screen;
import javafx.util.Duration;

public class MenuBackground extends Pane{

	private final static String PATH_IMAGE ="file:image/Menu/background.jpg";

	private ImageView view;
	
	public MenuBackground() {
		
		view = new ImageView( new Image(PATH_IMAGE));
		view.setFitWidth(Screen.getPrimary().getBounds().getWidth()*1.5);
		view.setFitHeight(Screen.getPrimary().getBounds().getHeight()*1.5);

		double centerX=Screen.getPrimary().getBounds().getWidth()/2,
				centerY=Screen.getPrimary().getBounds().getHeight()/2,
				radius = 100;
		ArcTo arcTo = new ArcTo();
        arcTo.setX(centerX - radius + 1); 
        arcTo.setY(centerY-radius);
        arcTo.setSweepFlag(false);
        arcTo.setLargeArcFlag(true);
        arcTo.setRadiusX(radius);
        arcTo.setRadiusY(radius);
        arcTo.setXAxisRotation(0);
        
		Path path = new Path();
		path.getElements().add(new MoveTo(centerX-radius,centerY-radius));
		path.getElements().add(arcTo);
		path.getElements().add(new ClosePath());
		
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.seconds(15));
		pathTransition.setPath(path);
		pathTransition.setNode(view);
		pathTransition.setOrientation(PathTransition.OrientationType.NONE);
		pathTransition.setCycleCount(Timeline.INDEFINITE);
		pathTransition.setAutoReverse(true);
		pathTransition.play();

		this.getChildren().add(view);
	}
	
}
