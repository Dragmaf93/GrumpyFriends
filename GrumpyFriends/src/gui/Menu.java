package gui;

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

public class Menu extends Application{

	private int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private Pane panelForBackground;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		panelForBackground = new Pane();
		panelForBackground.setPrefSize(width, height);
		panelForBackground.setStyle("-fx-background: #6b5d5d; -fx-background-color: #71b0f6; ");
		
		ImageView title = new ImageView("file:image/title.png");
		title.setX(panelForBackground.getPrefWidth()/4);
		panelForBackground.getChildren().add(title);
		
		Button play = new Button();
		play.setStyle("-fx-background-color: null; ");
		ImageView imagePlay = new ImageView("file:image/play.png");
		play.setGraphic(imagePlay);
		play.setPrefHeight(imagePlay.getImage().getHeight());
		play.setLayoutX(panelForBackground.getPrefWidth()/2-50);
		play.setLayoutY(title.getImage().getHeight()+title.getImage().getHeight()/9);
		panelForBackground.getChildren().add(play);
		play.setOnMouseReleased(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				
				InsertInformation insertInformation = new InsertInformation();
				try {
					insertInformation.start(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
//				MainApplication mainApplication = new MainApplication();
//				try {
//					mainApplication.start(primaryStage);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
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
					mapEditor.start(primaryStage);
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
				primaryStage.close();
			}
		});
		
        Scene scene = new Scene(panelForBackground);        
        primaryStage.setTitle("GrumpyFriends!");
        primaryStage.setScene(scene);
        primaryStage.show();
	}

	
	public static void main(String[] args) {
		launch(args);
	}
}
