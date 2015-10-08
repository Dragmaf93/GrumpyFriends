package mapEditor;

import java.awt.Toolkit;
import java.util.HashMap;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MapEditor extends Application{

	private Pane panelForObject;
	private Pane panelForMap;
	private GridPane grid;
	Circle circleToMove;
	
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	private boolean move;

	private LoaderImage loaderImage;
	
	private HashMap<Point2D, ImageForObject> images;
	
	int i = 0;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Map Editor");        
		
		grid = new GridPane();
//		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
//		grid.setPadding(new Insets(25, 25, 25, 25));
        
        panelForObject = new Pane();
        panelForObject.setPrefSize(larghezza/4, altezza);
        panelForObject.setStyle("-fx-background-color: cyan;");
        
        panelForMap = new Pane();
        panelForMap.setPrefSize(larghezza-panelForObject.getPrefWidth(), altezza);
        panelForMap.setStyle("-fx-background-color: white;");
        
        grid.add(panelForObject,0,0);
        grid.add(panelForMap,1,0);

        loaderImage = new LoaderImage(this);
        loaderImage.load("file:images.png");
        
        images = new HashMap<Point2D, ImageForObject>();
        
        dragAndDropExample();
        
        BorderPane root = new BorderPane();
        root.setCenter(grid);
        
        primaryStage.setScene(new Scene(root, larghezza, altezza));
        primaryStage.show();
		
	}
	Point2D pointClicked;
	
	private void dragAndDropExample() {

//	    panelForObject.getChildren().add(createCircle(35, 300, Color.RED));
		images.put(loaderImage.getImages().get("prova").getPosition(),loaderImage.getImages().get("prova"));
		panelForObject.getChildren().add(images.get(loaderImage.getImages().get("prova").getPosition()));
		
	    move = false;
	    
	    panelForObject.setOnMouseClicked(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) { 

//	        	if (!MapEditor.this.move)
//	        	{
	        		MapEditor.this.pointClicked = new Point2D(event.getX(),event.getY());
	        		System.out.println("POSSS: "+MapEditor.this.pointClicked.getX()+" "+MapEditor.this.pointClicked.getY());

//	            System.out.println("POSSSS: "+images.get(point).getIndex()+" "+images.get(point).getX());
                	System.out.println("POS_MOUSE_OBJECT: "+event.getX()+" "+event.getY());
                	
//	        	}
	        }
	    });
	    
	    panelForMap.setOnMouseReleased(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) { 
	            if (!MapEditor.this.move) 
	            	MapEditor.this.move = true;
	            else
	            	MapEditor.this.move = false;
	            
	            System.out.println("POS_MOUSE: "+event.getX()+" "+event.getY());
	        }
	    });
	    
	    panelForMap.setOnMouseMoved(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) { 
	        	
	        	Point2D point = new Point2D(event.getX(),event.getY());
	        	
	            if (MapEditor.this.images.get(point) != null && MapEditor.this.move) { 
	            	images.get(i).relocate(point.getX(), point.getY());
                	System.out.println("POS_MOUSE_OBJECT: "+event.getX()+" "+event.getY());

//	            	MapEditor.this.images.get(point).setPosition(event.getX(),event.getY());
	            }

	        }
	    });
	    
	    panelForMap.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
//                System.out.println("onDragOver");
                
                /* accept it only if it is  not dragged from the same node 
                 * and if it has a string data */
                if (event.getGestureSource() != panelForMap && event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                
                event.consume();
            }
        });
        
	    panelForMap.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
//                System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                
                boolean success = false;
                if (db.hasString()) {
                	if (!panelForMap.getChildren().contains(images.get(pointClicked)))
                	{
                		ImageForObject newImage = loaderImage.copyImage("prova");
                		images.get(newImage.getPosition()).setPosition(event.getX(), event.getY());
                		panelForMap.getChildren().add(newImage);
                		System.out.println(event.getX()+" "+event.getY());
                		System.out.println(newImage.getPosition());
                		success = true;
                	}
                	
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);
                
                event.consume();
            }
        });
	    
	}
//
//	private Circle createCircle(double x, double y, Color color) {
//	    final Circle c = new Circle(x, y, 25);
//	    c.setFill(color);
//
//	    c.setOnDragDetected(new EventHandler<MouseEvent>() {
//
//	        @Override
//	        public void handle(MouseEvent event) {
//	            System.out.println("SetOnDragDetected");
////	            c.setFill(Paint.valueOf("blue"));
//	            Dragboard db = c.startDragAndDrop(TransferMode.COPY_OR_MOVE);
//	            ClipboardContent content = new ClipboardContent();
//	            content.putString("foo " + c.hashCode());
//	            db.setContent(content);
//	            event.consume();
//	            circleToMove = c;
//	        }
//	    });
//
//	    c.setOnMouseClicked(new EventHandler<MouseEvent>() {
//
//	        @Override
//	        public void handle(MouseEvent event) {
//	            System.out.println("Mouse clicked");
//                MapEditor.this.move = true;
//                circleToMove.setCenterX(event.getX());
//                circleToMove.setCenterY(event.getY()); 
//	        }
//	    });
//
//	    c.setOnMouseReleased(new EventHandler<MouseEvent>() {
//
//	        @Override
//	        public void handle(MouseEvent event) {
//	            System.out.println("Mouse Released");
////	            circleToMove = null;
//	        }
//	    });
//
//	    c.setOnDragExited(new EventHandler<DragEvent>() {
//
//	        @Override
//	        public void handle(DragEvent event) {
//	            System.out.println("SetOnDragExited");
//	            System.out.println(event.getGestureSource());
//
//	        }
//	    });
//
//	    c.setOnDragOver(new EventHandler<DragEvent>() {
//
//	        @Override
//	        public void handle(DragEvent event) {
//	            System.out.println("setOnDragOver " + c.hashCode());
////	            c.setFill(Paint.valueOf("white"));
//	            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//	            event.consume();
//	        }
//	    });
//
//	    c.setOnDragDropped(new EventHandler<DragEvent>() {
//
//	        @Override
//	        public void handle(DragEvent arg0) {
//	            System.out.println("setOnDragDropped");
////	            c.setFill(Paint.valueOf("black"));
//
//	            if (arg0.getGestureSource() instanceof Circle) {
//
//	                if (arg0.getDragboard().hasString()) {
//	                    System.out.println(c.hashCode() + " hat jetzt " + arg0.getDragboard().getString());
//	                }
//	            }
//	        }
//	    });
//
//	    c.setOnDragEntered(new EventHandler<DragEvent>() {
//
//	        @Override
//	        public void handle(DragEvent event) {
//	            System.out.println("setOnDragEntered");
//	            circleToMove.setCenterX(event.getX());
//                circleToMove.setCenterY(event.getY()); 
//	        }
//	    });
//	    
//	    c.setOnMouseDragged(new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
////                c.relocate(event.getSceneX(), event.getSceneY());
//            	MapEditor.this.move = true;
//            }
//        });
//
//	    return c;
//	}
	
	public static void main(String[] args) {
        launch(args);
    }

	public void setMove(boolean bool) {
		move = bool;
	}
	
}
