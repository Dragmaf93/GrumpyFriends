package mapEditor;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.effect.Light.Point;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class ImageForObject extends ImageView
{
	String path;
	final MapEditor mapEditor;
	int index;
	Point2D position;
	
	public ImageForObject(String path, MapEditor mapEditor,int index) 
	{
		super(new Image(path,50, 100,false,false));
		this.path = path;
		this.mapEditor = mapEditor;
		this.index = index;
		
		position = new Point2D(0, 0);
		
		this.setOnDragDetected(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) {
//	            System.out.println("SetOnDragDetected");
	            Dragboard db = startDragAndDrop(TransferMode.COPY_OR_MOVE);
	            ClipboardContent content = new ClipboardContent();
	            content.putString("foo " + this.hashCode());
	            db.setContent(content);
	            event.consume();
	        }
	    });

		this.setOnMouseReleased(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) {
//	            System.out.println("Mouse clicked");
	            ImageForObject.this.mapEditor.setMove(true);
	            relocate(event.getX(), event.getY());
	        }
	    });

		this.setOnDragOver(new EventHandler<DragEvent>() {

	        @Override
	        public void handle(DragEvent event) {
	            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
	            event.consume();
	        }
	    });

		this.setOnDragEntered(new EventHandler<DragEvent>() {

	        @Override
	        public void handle(DragEvent event) {
//	            System.out.println("setOnDragEntered");
	            relocate(event.getX(), event.getY());
	        }
	    });
	    
		this.setOnMouseDragged(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
//                c.relocate(event.getSceneX(), event.getSceneY());
            	ImageForObject.this.mapEditor.setMove(true);
            }
        });
		
	}
	
	@Override
	protected ImageForObject clone() throws CloneNotSupportedException {
		
		ImageForObject newImage = new ImageForObject(path, mapEditor, index++);
		newImage.setPosition(0, 0);
		return newImage;
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public Point2D getPosition()
	{
		return position;
	}
	
	public void setPosition(double x,double y)
	{
		position = new Point2D(x, y);
	}
	
}
