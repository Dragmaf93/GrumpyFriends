import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import world.AbstractWorld;
import world.Ground;
import element.character.AbstractCharacter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class FXApplication extends Application
{
	private AbstractWorld world;
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	Image chewbaccaImage;
	CharacterUI character;
	
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
		
		
//		this.addKeyListener(new KeyAdapter() 
//		{
//			public void keyPressed (KeyEvent e)
//			{
//				if (e.getKeyCode() == KeyEvent.VK_RIGHT) 
//				{
//					character.getChewbacca().move(AbstractCharacter.RIGHT);
////					PhysicEngine.getInstance().removeElement(character.getChewbacca());
//		        }
//		        if (e.getKeyCode() == KeyEvent.VK_LEFT)
//		        {
//		        	character.getChewbacca().move(AbstractCharacter.LEFT);
//		        }
//		        if (e.getKeyCode() == KeyEvent.VK_UP)
//		        {
//		        	character.getChewbacca().jump();
//		        }
//			}
//			
//			@Override
//			public void keyReleased(KeyEvent e) 
//			{
//				if (e.getKeyCode() == KeyEvent.VK_RIGHT) 
//				{
//					character.getChewbacca().stopToMove();
//		        }
//		        if (e.getKeyCode() == KeyEvent.VK_LEFT)
//		        {
//		        	character.getChewbacca().stopToMove();
//		        }
//			}
//		});
//        
        
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

        ImageView view = new ImageView();
        view.setImage(image);
        view.relocate(character.getChewbacca().getX() , 
				character.getChewbacca().getY()-character.getChewbacca().getHeight());
        pane.getChildren().add(view);

        
        
        grid.add(pane,0,0);
        
        Scene scene = new Scene(grid, larghezza, altezza);
//        scene.addEventHandler("CheckBox Action (selected: " + checkBox.isSelected() + ")\n");
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    
}
