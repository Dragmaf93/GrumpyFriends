package gui;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Scale;


public class ZoomingScrollPane extends ScrollPane {
	
	private Group zoomGroup;
	private Scale scaleTransform;
	private Node content;
	private double scaleValue;
	
	private final static float ZOOM_FACTOR=0.1f;

	public ZoomingScrollPane(Node content) {
		super();
		this.content = content;
		Group contentGroup = new Group();
		zoomGroup = new Group();
		contentGroup.getChildren().add(zoomGroup);
		zoomGroup.getChildren().add(content);
		setContent(contentGroup);
		scaleValue=1;
	    scaleTransform = new Scale(scaleValue, scaleValue, 0, 0);
	    zoomGroup.getTransforms().add(scaleTransform);
		this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	    this.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	    this.setPannable(true);
	    
	    addEventFilter(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {

			@Override
			public void handle(ScrollEvent event) {
				if(event.getDeltaY()<0 ){
					if(scaleValue-ZOOM_FACTOR>0)
						scaleValue-=ZOOM_FACTOR;
				}else if(event.getDeltaY()>0){
					scaleValue+=ZOOM_FACTOR;
				}
				System.out.println(scaleValue);
				scaleTransform.setX(scaleValue);
				scaleTransform.setY(scaleValue);
				event.consume();
			}
		});
	    
	}
}
