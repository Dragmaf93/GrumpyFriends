package menu;

import game.GameManager;

import java.awt.Toolkit;

import mapEditor.MapEditor;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Menu {

	private GameManager gameManager;
	private MenuManager menuManager;
	
	private int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private Pane panelForBackground;
	private Scene scene;
	
	public Menu() {
//	}
	
//	@Override
//	public void start(Stage primaryStage) throws Exception {
	
		gameManager = GameManager.getIstance();
		menuManager = MenuManager.getIstance();
		
//		this.stage = primaryStage;
		
		panelForBackground = new Pane();
		panelForBackground.setPrefSize(width, height);
		panelForBackground.setStyle("-fx-background: #6b5d5d; -fx-background-color: #71b0f6; ");
		
		ImageView title = new ImageView("file:image/title.png");
		title.setX(panelForBackground.getPrefWidth()/4);
		panelForBackground.getChildren().add(title);
		
		Button play = new Button();
		play.setStyle("-fx-background-color: null;");
		ImageView imagePlay = new ImageView("file:image/play.png");
		play.setGraphic(imagePlay);
		play.setPrefHeight(imagePlay.getImage().getHeight());
		play.setLayoutX(panelForBackground.getPrefWidth()/2-50);
		play.setLayoutY(title.getImage().getHeight()+title.getImage().getHeight()/9);
		panelForBackground.getChildren().add(play);
		play.setOnMouseReleased(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				
				scene = menuManager.getTeamPageAScene();
				menuManager.setSceneForChangePage(scene);
			}
		});
		
		Button mapEditor = new Button();
		mapEditor.setStyle(play.getStyle());
		ImageView imageMapEditor = new ImageView("file:image/mapEditor.png");
		mapEditor.setGraphic(imageMapEditor);
		mapEditor.setPrefHeight(imageMapEditor.getImage().getHeight());
		mapEditor.setLayoutX(play.getLayoutX());
		mapEditor.setLayoutY(play.getLayoutY()+play.getPrefHeight()+20);
		panelForBackground.getChildren().add(mapEditor);
		mapEditor.setOnMouseReleased(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				MapEditor mapEditor = new MapEditor();
				try {
					scene = mapEditor.getScene();
					menuManager.setSceneForChangePage(scene);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		Button exit = new Button();
		exit.setStyle(play.getStyle());
		exit.setGraphic(new ImageView("file:image/exit.png"));
		exit.setLayoutX(play.getLayoutX());
		exit.setLayoutY(mapEditor.getLayoutY()+mapEditor.getPrefHeight()+20);
		panelForBackground.getChildren().add(exit);
		exit.setOnMouseReleased(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				menuManager.closeStage();
			}
		});
        
	}
	
	public Pane getPane() {
		return panelForBackground;
	}

}
