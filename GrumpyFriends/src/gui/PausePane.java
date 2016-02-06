package gui;


import game.MatchManager;
import gui.hud.AbstractHudElement;
import menu.MenuButton;
import menu.MenuManager;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;

public class PausePane extends AbstractHudElement{

	private final static double BUTTON_WIDTH=318;
	private final static double BUTTON_HEIGHT=65;
	
	private Text textGamePaused;
	
	private VBox pauseButtons;
	
	private MenuButton resumeButton; 
	private MenuButton restartButton; 
	private MenuButton exitButton;
	
	private Font fontText; 
	
	private MenuButton resumesButton;
	
	private Popup restartPopup;
	private Popup exitPopup;

	private double width;
	private double height;
	
	
	
	public PausePane(MatchPane matchPane, MatchManager matchManager){
		super(matchPane, matchManager);
		this.width = Screen.getPrimary().getBounds().getWidth();
		this.height=Screen.getPrimary().getBounds().getHeight();
		this.setPrefSize(width,height);
		this.setStyle("-fx-background: #ece6e6; -fx-background-color: rgba(25,81,81,0.6);");

		pauseButtons = new VBox(40);
		
		textGamePaused = new Text();
		textGamePaused.setFill(Color.WHITE);
		textGamePaused.setText("GAME PAUSED");
		textGamePaused.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 70));
		textGamePaused.relocate(width/2-textGamePaused.getLayoutBounds().getWidth()/2, 150);
		
		resumeButton = new MenuButton(BUTTON_WIDTH,BUTTON_HEIGHT,"RESUME");
		restartButton = new MenuButton(BUTTON_WIDTH,BUTTON_HEIGHT,"RESTART");
		exitButton = new MenuButton(BUTTON_WIDTH,BUTTON_HEIGHT,"EXIT GAME");
		
		pauseButtons.getChildren().addAll(resumeButton,restartButton,exitButton);
		pauseButtons.relocate(width/2-BUTTON_WIDTH/2,150+BUTTON_HEIGHT*2+pauseButtons.getSpacing());
		
		root.getChildren().addAll(textGamePaused,pauseButtons);
		

		restartPopup = new Popup(300, 200, "Are you sure?", "Cancel", "Accept");
		exitPopup = new Popup(300, 200, "Are you sure?", "Cancel", "Accept");
		
		resumeButton.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton()==MouseButton.PRIMARY)
					matchPane.restartFromPause();
			}
		});
		
		restartButton.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton()==MouseButton.PRIMARY)
				{
					root.getChildren().add(restartPopup);
					pauseButtons.setVisible(false);
				}
			}
		});
		
		exitButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton()==MouseButton.PRIMARY)
				{
					root.getChildren().add(exitPopup);
					pauseButtons.setVisible(false);
				}
			}
		});
		
		
		restartPopup.getRightButton().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton()==MouseButton.PRIMARY){
					root.getChildren().remove(restartPopup);
					pauseButtons.setVisible(true);
					matchPane.restartMatch();
				}
			}
		});
		restartPopup.getLeftButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton()==MouseButton.PRIMARY)
				{
					root.getChildren().remove(restartPopup);
					pauseButtons.setVisible(true);
				}
			}
		});
		
		exitPopup.getRightButton().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton()==MouseButton.PRIMARY)
					MenuManager.getInstance().goToMainMenu();
			}
		});
		exitPopup.getLeftButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton()==MouseButton.PRIMARY)
				{
					root.getChildren().remove(exitPopup);
					pauseButtons.setVisible(true);
				}
			}
		});
		
	}

	@Override
	public void draw() {
		
	}
	
	
}
