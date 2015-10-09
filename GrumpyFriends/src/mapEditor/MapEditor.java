package mapEditor;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MapEditor extends Application{

	private GridPane grid;
	private Pane panelForObject;
	private Pane panelForMap;
	private Pane panelForDimension;
	
	private ImageForObject dragged;
	
	private int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	private ImageForObject image;
	
	private LoaderImage loaderImage;
	private Point2D upperLeftCorner;
	
	private double deltaX;
	private double deltaY;
	private Label choiseWidth;
	private TextField insertWidth;
	private Label choiseHeight;
	private TextField insertHeight;
	
	ArrayList<ImageForObject> objectInMap;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Map Editor");        
		
		objectInMap = new ArrayList<ImageForObject>();
		
		grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
        
        panelForObject = new Pane();
        panelForObject.setPrefSize(larghezza/5, altezza);
        panelForObject.setStyle("-fx-background-color: cyan;");
        
        panelForMap = new Pane();
        panelForMap.setPrefSize(larghezza-panelForObject.getPrefWidth(), altezza);
//        BackgroundImage myBI= new BackgroundImage(new Image("file:image/sky.gif",panelForMap.getPrefWidth(),
//        		panelForMap.getPrefHeight(),false,true),
//                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
//                  BackgroundSize.DEFAULT);
//        panelForMap.setBackground(new Background(myBI));
        
        panelForDimension = new Pane();
        panelForDimension.setPrefSize(larghezza/5, altezza/7);
        panelForDimension.setStyle("-fx-background-color: blue;");
        
        grid.add(panelForObject,0,0);
        grid.add(panelForMap,1,0);

        choiseWidth = new Label("Width");
        choiseWidth.setLayoutX(5);
        choiseWidth.setLayoutY(5);
        panelForDimension.getChildren().add(choiseWidth);
        
        insertWidth = new TextField();
        insertWidth.setLayoutX(choiseWidth.getLayoutX());
        insertWidth.setLayoutY(choiseWidth.getLayoutY()+20);
        panelForDimension.getChildren().add(insertWidth);
        
        choiseHeight = new Label("Height");
        choiseHeight.setLayoutX(insertWidth.getLayoutX());
        choiseHeight.setLayoutY(insertWidth.getLayoutY()+25);
        panelForDimension.getChildren().add(choiseHeight);
        
        insertHeight = new TextField();
        insertHeight.setLayoutX(choiseHeight.getLayoutX());
        insertHeight.setLayoutY(choiseHeight.getLayoutY()+20);
        panelForDimension.getChildren().add(insertHeight);
        
        panelForObject.getChildren().add(panelForDimension);
        
        loaderImage = new LoaderImage(this);
        loaderImage.load("file:images.png");
        
        dragAndDrop();
        
        BorderPane root = new BorderPane();
        root.setCenter(grid);
        
        primaryStage.setScene(new Scene(root, larghezza, altezza));
        primaryStage.show();
		
	}
	
	private void dragAndDrop() {

//		circleToMove = createCircle(35, 300, Color.RED);
		image = loaderImage.getImages().get("prova");
	    image.setLayoutX(panelForDimension.getLayoutX());
	    image.setLayoutY(panelForDimension.getLayoutY()+panelForDimension.getPrefHeight()+10);
		panelForObject.getChildren().add(image);
	    
	    panelForObject.setOnMousePressed(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) {
//	        	System.out.println(event.getX()+" "+event.getY()+" "+image.intersects(event.getX(), event.getY(), 1, 1));
//	        	System.out.println(image.getLayoutX()+" "+image.getLayoutY());
	        	if (image.intersects(event.getX(), event.getY()-panelForDimension.getPrefHeight(), 1, 1))
	            {
	            	upperLeftCorner = new Point2D(image.getX()-image.getImage().getWidth(), 
	            			image.getY()-image.getImage().getHeight());
		            
		            deltaX = event.getX() - upperLeftCorner.getX();
		            deltaY = event.getY() - upperLeftCorner.getY();
		            
	            	try {
						dragged = image.clone();
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
	            }
	        }
	    });
	    
	    panelForObject.setOnMouseDragged(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) { 
	            if (dragged != null)
            	{
	            	dragged.setX(event.getX()-deltaX);
	            	dragged.setY(event.getY()-deltaY);
	            	
	            	if (dragged.getX() >= panelForObject.getWidth() && checkLimit(event))
	            	{
	            		dragged.setX(event.getX()-panelForObject.getWidth()-deltaX);
		            	dragged.setY(event.getY());
		            	
		            	if (!panelForMap.getChildren().contains(dragged))
		            		panelForMap.getChildren().add(dragged);
	            	}
            	}
	        }
	    });
	    
	    panelForObject.setOnMouseReleased(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) { 
	        	
	        	objectInMap.add(dragged);
	        }
	    });
	    
	    panelForMap.setOnMouseDragged(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) { 
//	        	System.out.println("entro in maaaaap "+ checkLimit(event));
//	        	System.out.println(panelForMap.getWidth()+" "+event.getX()+" "+event.getY());
	        	if (dragged != null && checkLimit(event))
            	{
	            	dragged.setX(event.getX());
	            	dragged.setY(event.getY());
            	}
	        }
	    });
	    
	    panelForMap.setOnMouseReleased(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) { 
//	        	System.out.println("entro in maaaaap "+ checkLimit(event));
//	        	System.out.println(panelForMap.getWidth()+" "+event.getX()+" "+event.getY());
	        	if (!objectInMap.contains(dragged))
	        		objectInMap.add(dragged);
	        }
	    });
	    
	}
	
	private boolean checkLimit(MouseEvent event)
	{
		return (event.getX()+ image.getImage().getWidth()) < panelForMap.getWidth()
    			&& (event.getY()+image.getImage().getHeight()) < panelForMap.getHeight()
    			&& (event.getY()) >= 0
    			&& (event.getX()) >= 0;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
