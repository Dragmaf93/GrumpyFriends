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
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import element.character.AbstractCharacter;
import world.AbstractWorld;
import world.Ground;
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
	
	CharacterUI character;
	
	ImageView view;
	Pane pane;
	final ScrollPane scrollPane = new ScrollPane();
	
	Lock lock = new ReentrantLock();
	Condition condition = lock.newCondition();
	boolean move = false;
	
    @Override
    public void start(Stage primaryStage) {
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-background-color: cyan;");
        
        AbstractWorld.initializes("world.Planet");
		world = (AbstractWorld) AbstractWorld.getInstance();
		grid.setPrefSize(world.getNumberColumn(), world.getNumberRow());
		character = new CharacterUI();
		
		pane = new Pane();
//		pane.setPrefSize(larghezza,altezza);
        pane.setStyle(grid.getStyle());
        
		for (int y = 0; y < world.getNumberRow(); y++)
		{
			for (int x = 0; x < world.getNumberColumn(); x++) 
			{
				if (world.getElement(x, y) instanceof Ground)
				{
					Rectangle rectangle = new Rectangle(20, 20);
					rectangle.relocate(x*20, y*20);
					rectangle.setFill(null);
					rectangle.setStroke(Color.BLACK);
					
					pane.getChildren().add(rectangle);
				}
				
			}
		}
        
        Image image = new Image("/images.png",0, character.getChewbacca().getHeight(),true,true);

        view = new ImageView();
        view.setImage(image);
        view.relocate(character.getChewbacca().getX() , 
        		character.getChewbacca().getY()-character.getChewbacca().getHeight());
        pane.getChildren().add(view);
        
        scrollPane.setContent(pane);
        scrollPane.setPrefSize(larghezza, altezza);
        grid.add(scrollPane,0,0);
        
        scrollPane.setOnKeyPressed(null);
        
        Scene scene = new Scene(grid, larghezza, altezza);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
            	if (ke.getCode() == KeyCode.RIGHT) 
				{
					character.getChewbacca().move(AbstractCharacter.RIGHT);
//					PhysicEngine.getInstance().removeElement(character.getChewbacca());
				}
		        if (ke.getCode() == KeyCode.LEFT)
		        {
		        	character.getChewbacca().move(AbstractCharacter.LEFT);
		        }
		        if (ke.getCode() == KeyCode.UP)
		        {
		        	character.getChewbacca().jump();
		        }
            }
        });
        
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke)
            {
            	if (ke.getCode() == KeyCode.RIGHT) 
				{
					character.getChewbacca().stopToMove();
				}
		        if (ke.getCode() == KeyCode.LEFT)
		        {
		        	character.getChewbacca().stopToMove();
		        }
            }
        });
        
        new AnimationTimer()
        {
            @Override
            public void handle(long now) 
            {
            	view.relocate(character.getChewbacca().getX(), character.getChewbacca().getY()-character.getChewbacca().getHeight());
            }
        }.start();
        
        primaryStage.show();
 
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
