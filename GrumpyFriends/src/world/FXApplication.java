package world;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.awt.Toolkit;
import java.util.Iterator;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

import character.AbstractCharacter;
import character.WhiteStormtrooper;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;


public class FXApplication extends Application
{
	private AbstractWorld world;
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	WhiteStormtrooper character;
	ImageView view;
	Pane pane;
	final ScrollPane scrollPane = new ScrollPane();
	
	Lock lock = new ReentrantLock();
	Condition condition = lock.newCondition();
	boolean move = false;
	
    @Override
    public void start(Stage primaryStage) {
        
//        GridPane grid = new GridPane();
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setStyle("-fx-background-color: cyan;");
//        
//        GameWorldBuilder builder = new GameWorldBuilder();
//		WorldDirector director = new WorldDirector(builder);
//		director.createWorld("worldXML/world.xml");
//		world = (AbstractWorld) builder.getWorld();
//		grid.setPrefSize(world.getNumberColumn(), world.getNumberRow());
////		character = new CharacterUI();
//		character = new Chewbacca("Eliana", 40,50, null, null);
//
//
//		
//		pane = new Pane();
////		pane.setPrefSize(larghezza,altezza);
//        pane.setStyle(grid.getStyle());
//      
//    
//		for (int i = 0; i < world.getNumberRow(); i++)
//		{
//			for (int j = 0; j < world.getNumberColumn(); j++) 
//			{
//				if (world.getGround(j, i) instanceof LinearGround)
//				{
//					LinearGround g = world.getGround(j, i);
//		
//					Rectangle rectangle = new Rectangle(g.getHeight(),g.getWidth());
//					System.out.println(g.getX());
//					rectangle.setLayoutX(g.getX());
//					rectangle.setLayoutY(g.getY());
//										
//					rectangle.setFill(null);
//					rectangle.setStroke(Color.BLACK);
//					pane.getChildren().add(rectangle);
//				}
//				
//			}
//		}
////        Ground g =world.getGround(0,13);
////        Rectangle rectangle = new Rectangle(g.getHeight(),g.getWidth());
////		
////		rectangle.relocate(g.getX(),g.getY());
////		rectangle.setFill(null);
////		rectangle.setStroke(Color.BLACK);
////		
////		pane.getChildren().add(rectangle);
////        System.out.println(g.getX());
////        System.out.println(g.getY());
//        Image image = new Image("file:images.png",0, character.getHeight(),true,true);
//
//        view = new ImageView();
//        view.setImage(image);
//        view.relocate(character.getX() , character.getY());
//        pane.getChildren().add(view);
//        
////        scrollPane.setContent(pane);
////        scrollPane.setPrefSize(larghezza, altezza);
////        grid.add(scrollPane,0,0);
////        
////        scrollPane.setOnKeyPressed(null);    
//        grid.add(pane,0,0);
//        Scene scene = new Scene(grid, larghezza, altezza);
//        
//        primaryStage.setTitle("Hello World!");
//        primaryStage.setScene(scene);
//        
//        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            public void handle(KeyEvent ke) {
//            	if (ke.getCode() == KeyCode.RIGHT) 
//				{
//					character.move(AbstractCharacter.RIGHT);
////					PhysicEngine.getInstance().removeElement(character.getChewbacca());
//				}
//		        if (ke.getCode() == KeyCode.LEFT)
//		        {
//		        	character.move(AbstractCharacter.LEFT);
//		        }
//		        if (ke.getCode() == KeyCode.UP)
//		        {
//		        	character.jump();
//		        }
//            }
//        });
//        
//        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
//            public void handle(KeyEvent ke)
//            {
//            	if (ke.getCode() == KeyCode.RIGHT) 
//				{
//					character.stopToMove();
//				}
//		        if (ke.getCode() == KeyCode.LEFT)
//		        {
//		        	character.stopToMove();
//		        }
//            }
//        });
//        
//        
//        new AnimationTimer()
//        {
//            @Override
//            public void handle(long now) 
//            {
//           	    view.relocate(character.getX(), character.getY());
//            	world.step(1.0f/60, 6, 3);            }
//        }.start();
//       
//        primaryStage.show();
// 
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
