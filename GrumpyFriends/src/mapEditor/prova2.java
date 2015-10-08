package mapEditor;

import javafx.event.EventHandler;
import javafx.scene.control.TitledPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class prova2 {
	Pane pane;
	Circle circleToMove;
	
	private void dragAndDropExample() {
	    pane = new Pane();
	    pane.setPrefSize(800, 600);
//	    TitledPane titlePane = new TitledPane("Partial Order", pane);
//	    add(titlePane, 0, 2);

	    pane.getChildren().add(createCircle(350, 300, Color.RED));
	    pane.getChildren().add(createCircle(250, 300, Color.BROWN));

	    pane.setOnMouseMoved(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) { 
	            if (circleToMove != null) { 
	                circleToMove.setCenterX(event.getX());
	                circleToMove.setCenterY(event.getY()); 
	            }

	        }
	    });
	}


	private Circle createCircle(double x, double y, Color color) {
	    final Circle c = new Circle(x, y, 25);
	    c.setFill(color);

	    c.setOnDragDetected(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent arg0) {
	            System.out.println("SetOnDragDetected");
	            c.setFill(Paint.valueOf("blue"));
	            Dragboard db = c.startDragAndDrop(TransferMode.COPY_OR_MOVE);
	            ClipboardContent content = new ClipboardContent();
	            content.putString("foo " + c.hashCode());
	            db.setContent(content);
	            arg0.consume();
	            circleToMove = c;
	        }
	    });

	    c.setOnMouseClicked(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) {
	            System.out.println("Mouse clicked");
	        }
	    });

	    c.setOnMouseReleased(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) {
	            System.out.println("Mouse Released");
	            circleToMove = null;
	        }
	    });

	    c.setOnDragExited(new EventHandler<DragEvent>() {

	        @Override
	        public void handle(DragEvent event) {
	            System.out.println("SetOnDragExited");
	            System.out.println(event.getGestureSource());

	        }
	    });

	    c.setOnDragOver(new EventHandler<DragEvent>() {

	        @Override
	        public void handle(DragEvent event) {
	            System.out.println("setOnDragOver " + c.hashCode());
	            c.setFill(Paint.valueOf("white"));
	            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
	            event.consume();
	        }
	    });

	    c.setOnDragDropped(new EventHandler<DragEvent>() {

	        @Override
	        public void handle(DragEvent arg0) {
	            System.out.println("setOnDragDropped");
	            c.setFill(Paint.valueOf("black"));

	            if (arg0.getGestureSource() instanceof Circle) {

	                if (arg0.getDragboard().hasString()) {
	                    System.out.println(c.hashCode() + " hat jetzt " + arg0.getDragboard().getString());
	                }
	            }
	        }
	    });

	    c.setOnDragEntered(new EventHandler<DragEvent>() {

	        @Override
	        public void handle(DragEvent arg0) {
	            System.out.println("setOnDragEntered");

	        }
	    });

	    return c;
	}
}
