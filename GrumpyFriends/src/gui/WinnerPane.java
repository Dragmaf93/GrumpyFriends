package gui;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class WinnerPane extends Pane{

	private final static double BUTTON_WIDTH=318;
	private final static double BUTTON_HEIGHT=65;
	
	private MatchPane parent;
	
	private Group root;
	
	private Text textGameWinner;
	
	private VBox winnerButtons;
	
	private MenuButton restartButton; 
	private MenuButton exitButton;
	
	private Popup restartPopup;
	private Popup exitPopup;
	
	private ImageView teamWinner;
	private ImageView teamLoser;
	
	public WinnerPane(MatchPane pane, double width,double height, String winner, String teamWin, String teamLose){
		root = new Group();
		parent = pane;
		this.setPrefSize(width,height);
		this.setStyle("-fx-background: #ece6e6; -fx-background-color: rgba(25,81,81,1);");

		winnerButtons = new VBox(40);
		
		textGameWinner = new Text();
		textGameWinner.setFill(Color.WHITE);
		textGameWinner.setText(winner);
		textGameWinner.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 70));
		textGameWinner.relocate(width/2-textGameWinner.getLayoutBounds().getWidth()/2, 150);
		
		restartButton = new MenuButton(BUTTON_WIDTH,BUTTON_HEIGHT,"RESTART");
		exitButton = new MenuButton(BUTTON_WIDTH,BUTTON_HEIGHT,"EXIT GAME");
		
		winnerButtons.getChildren().addAll(restartButton,exitButton);
		winnerButtons.relocate(width/2-BUTTON_WIDTH/2,150+BUTTON_HEIGHT*2+winnerButtons.getSpacing());
		
		teamWinner = new ImageView("file:image/winner/"+teamWin+".png");
		teamWinner.relocate(40, height/2);
		
		teamLoser = new ImageView("file:image/winner/"+teamLose+".png");
		teamLoser.relocate(width-450, height/2);
		
		root.getChildren().addAll(textGameWinner, winnerButtons, teamWinner, teamLoser);
		
		this.getChildren().add(root);

		restartPopup = new Popup(300, 200, "Are you sure?", "Cancel", "Accept");
		exitPopup = new Popup(300, 200, "Are you sure?", "Cancel", "Accept");
		
		restartButton.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton()==MouseButton.PRIMARY)
				{
					root.getChildren().add(restartPopup);
					winnerButtons.setVisible(false);
				}
			}
		});
		
		exitButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton()==MouseButton.PRIMARY)
				{
					root.getChildren().add(exitPopup);
					winnerButtons.setVisible(false);
				}
			}
		});
		
		
		restartPopup.getRightButton().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton()==MouseButton.PRIMARY)
					parent.startMainApplication();
			}
		});
		restartPopup.getLeftButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton()==MouseButton.PRIMARY)
				{
					root.getChildren().remove(restartPopup);
					winnerButtons.setVisible(true);
				}
			}
		});
		
		exitPopup.getRightButton().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton()==MouseButton.PRIMARY)
					parent.startMenu();
			}
		});
		exitPopup.getLeftButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton()==MouseButton.PRIMARY)
				{
					root.getChildren().remove(exitPopup);
					winnerButtons.setVisible(true);
				}
			}
		});
		
	}
	
	
}

