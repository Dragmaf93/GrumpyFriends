import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.awt.Toolkit;

import element.character.AbstractCharacter;
import world.AbstractWorld;
import world.Ground;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;


public class FXApplication extends Application
{
	private AbstractWorld world;
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	Image chewbaccaImage;
	CharacterUI character;
	
	ImageView view;
	
    @Override
    public void start(Stage primaryStage) {
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-background-color: cyan;");
        
        Button btn = new Button();
        btn.setText("Say 0");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        AbstractWorld.initializes("world.Planet");
		world = (AbstractWorld) AbstractWorld.getInstance();
		grid.setPrefSize(world.getWidth(), altezza);
		character = new CharacterUI();
		
		 Pane pane = new Pane();
        //pane.setStyle("-fx-background-color: black;");
        pane.setPrefSize(500, 400);
        
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
        
        grid.add(pane,0,0);
        
        Scene scene = new Scene(grid, larghezza, altezza);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
            	if (ke.getCode() == KeyCode.RIGHT) 
				{
					character.getChewbacca().move(AbstractCharacter.RIGHT);
//					System.out.println("Key Pressed.... va a destra"+ke.getText());
//					PhysicEngine.getInstance().removeElement(character.getChewbacca());
//					view.relocate(character.getChewbacca().getX(), character.getChewbacca().getY());
		        }
		        if (ke.getCode() == KeyCode.LEFT)
		        {
		        	character.getChewbacca().move(AbstractCharacter.LEFT);
//		        	System.out.println("Key Pressed.... va a sinistra"+ke.getText());
//		        	view.relocate(character.getChewbacca().getX(), character.getChewbacca().getY());
		        }
		        if (ke.getCode() == KeyCode.UP)
		        {
		        	character.getChewbacca().jump();
//		        	System.out.println("Key Pressed.... salta"+ke.getText());
//		        	view.relocate(character.getChewbacca().getX(), character.getChewbacca().getY());
		        }
            }
        });
        
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke)
            {
            	if (ke.getCode() == KeyCode.RIGHT) 
				{
					character.getChewbacca().stopToMove();
//					System.out.println("Key Released.... "+ke.getText());
					
//					view.relocate(character.getChewbacca().getX(), character.getChewbacca().getY());
		        }
		        if (ke.getCode() == KeyCode.LEFT)
		        {
		        	character.getChewbacca().stopToMove();
//		        	System.out.println("Key Released.... "+ke.getText());
//		        	view.relocate(character.getChewbacca().getX(), character.getChewbacca().getY());
		        }
            }
        });
        
//       	scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
//            public void handle(KeyEvent ke) {
//                String text = "Key Typed: " + ke.getCharacter();
//                if (ke.isAltDown()) {
//                    text += " , alt down";
//                }
//                if (ke.isControlDown()) {
//                    text += " , ctrl down";
//                }
//                if (ke.isMetaDown()) {
//                    text += " , meta down";
//                }
//                if (ke.isShiftDown()) {
//                    text += " , shift down";
//                }
//                System.out.println(text);
//            }
//        });
        
        primaryStage.show();
 
        start();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

	public void start() {
		new Thread() {
			@Override
			public void run() 
			{
				while(true)
				{
					System.out.println("MUOVIII "+view.getX()+" "+view.getY());
					view.relocate(character.getChewbacca().getX(), character.getChewbacca().getY());
				}	
			}
		}.start();
	}
}
