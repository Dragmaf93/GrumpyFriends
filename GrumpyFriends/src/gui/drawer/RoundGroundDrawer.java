package gui.drawer;

import element.Element;
import element.ground.RoundGround;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.QuadCurve;

public class RoundGroundDrawer extends AbstractDrawerObject{

	
	public RoundGroundDrawer(Group pane) {
		super(pane);
	}

	@Override
	public Node getElementToDraw(Element elementToDraw) {
		RoundGround ground = (RoundGround) elementToDraw;
		
		if(ground.isABezierCurve()){
			QuadCurve shape = 
					new QuadCurve(ground.getStart().x,
									ground.getStart().y,
									ground.getControl().x, 
									ground.getControl().y,
									ground.getEnd().x,
									ground.getEnd().y);
			return shape;
			
		}
		return null;
	}

}
