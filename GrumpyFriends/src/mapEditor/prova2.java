package mapEditor;

import java.awt.Toolkit;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
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
import javafx.stage.Stage;

public class prova2 extends Application {
	private Pane panelForObject;
	private Pane panelForMap;
	private GridPane grid;
	Circle circleToMove;
	
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	protected boolean move;

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

        dragAndDropExample();
        
        BorderPane root = new BorderPane();
        root.setCenter(grid);
        
        primaryStage.setScene(new Scene(root, larghezza, altezza));
        primaryStage.show();
		
	}
	
	private void dragAndDropExample() {

	    panelForObject.getChildren().add(createCircle(35, 300, Color.RED));
	    move = false;
	    
	    panelForMap.setOnMouseClicked(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) { 
	            if (!prova2.this.move) 
	            	prova2.this.move = true;
	            else
	            	prova2.this.move = false;

	        }
	    });
	    
	    panelForMap.setOnMouseMoved(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) { 
	            if (circleToMove != null && prova2.this.move) { 
	                circleToMove.setCenterX(event.getX());
	                circleToMove.setCenterY(event.getY()); 
	            }

	        }
	    });
	    
	    panelForMap.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                System.out.println("onDragOver");
                
                /* accept it only if it is  not dragged from the same node 
                 * and if it has a string data */
                if (event.getGestureSource() != panelForMap &&
                        event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                
                event.consume();
            }
        });

	    panelForMap.setOnDragEntered(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture entered the target */
                System.out.println("onDragEntered");
                /* show to the user that it is an actual gesture target */
                if (event.getGestureSource() != panelForMap &&
                        event.getDragboard().hasString()) {
                	System.out.println("DEVO CAPIRE");
                }
                
                event.consume();
            }
        });

	    panelForMap.setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
//                target.setFill(Color.BLACK);
                
                event.consume();
            }
        });
        
	    panelForMap.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                	if (!panelForMap.getChildren().contains(circleToMove))
                	{
                		panelForMap.getChildren().add(circleToMove);
                		circleToMove.setCenterX(event.getX());
    	                circleToMove.setCenterY(event.getY()); 
                	}
                	
//                    target.setText(db.getString());
                    success = true;
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);
                
                event.consume();
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
//	            c.setFill(Paint.valueOf("blue"));
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
                prova2.this.move = true;
                circleToMove.setCenterX(event.getX());
                circleToMove.setCenterY(event.getY()); 
	        }
	    });

	    c.setOnMouseReleased(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) {
	            System.out.println("Mouse Released");
//	            circleToMove = null;
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
//	            c.setFill(Paint.valueOf("white"));
	            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
	            event.consume();
	        }
	    });

	    c.setOnDragDropped(new EventHandler<DragEvent>() {

	        @Override
	        public void handle(DragEvent arg0) {
	            System.out.println("setOnDragDropped");
//	            c.setFill(Paint.valueOf("black"));

	            if (arg0.getGestureSource() instanceof Circle) {

	                if (arg0.getDragboard().hasString()) {
	                    System.out.println(c.hashCode() + " hat jetzt " + arg0.getDragboard().getString());
	                }
	            }
	        }
	    });

	    c.setOnDragEntered(new EventHandler<DragEvent>() {

	        @Override
	        public void handle(DragEvent event) {
	            System.out.println("setOnDragEntered");
	            circleToMove.setCenterX(event.getX());
                circleToMove.setCenterY(event.getY()); 
	        }
	    });
	    
	    c.setOnMouseDragged(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
//                c.relocate(event.getSceneX(), event.getSceneY());
                prova2.this.move = true;
            }
        });

	    return c;
	}
	
	public static void main(String[] args) {
        launch(args);
    }
}
