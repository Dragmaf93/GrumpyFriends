package gui.drawer;

import element.Element;
import element.ground.RoundGround;
import gui.ImageLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.QuadCurve;

public class RoundGroundDrawer extends AbstractDrawerObject{

	ImageLoader imageLoader;
	
	public RoundGroundDrawer(Group pane, ImageLoader imageLoader) {
		super(pane);
		
		this.imageLoader = imageLoader;
	}

	@Override
	public Node getElementToDraw(Element elementToDraw) {
		RoundGround ground = (RoundGround) elementToDraw;
		
		if(ground.isABezierCurve()){
			QuadCurve shape = 
					new QuadCurve(ground.getStart().x - ground.getX(),
									ground.getStart().y - ground.getY(),
									ground.getControl().x - ground.getX(), 
									ground.getControl().y - ground.getY(),
									ground.getEnd().x - ground.getX(),
									ground.getEnd().y - ground.getY());
			
			ImageView imageView = new ImageView(imageLoader.getImageGrounds("Planet","Width"));

			imageView.resize(ground.getWidth(), ground.getHeight());
			imageView.setClip(shape);
			imageView.setLayoutX(ground.getX());
			imageView.setLayoutY(ground.getY());
			return imageView;
			
		}
		return null;
	}

}
