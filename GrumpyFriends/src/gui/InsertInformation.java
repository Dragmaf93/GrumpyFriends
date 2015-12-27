package gui;

import java.awt.Toolkit;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class InsertInformation extends Application {

	private int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private Pane paneForInsertInformation;
	
	private TextField nameTeamA;
	private InsertNamesPlayers namesPlayerTeamA;
	private TextField nameTeamB;
	private InsertNamesPlayers namesPlayerTeamB;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		paneForInsertInformation = new Pane();
		paneForInsertInformation.setPrefSize(width, height);
		paneForInsertInformation.setStyle("-fx-background: #6b5d5d; -fx-background-color: #71b0f6; ");
		
		ImageView nameTeam = new ImageView("file:image/teamNames.png");
		nameTeam.setX(paneForInsertInformation.getPrefWidth()/3);
		paneForInsertInformation.getChildren().add(nameTeam);
		
		
//		TEAM A
		ImageView teamA = new ImageView("file:image/teamA.png");
		teamA.setX(10);
		teamA.setY(nameTeam.getY() + nameTeam.getImage().getHeight());
		paneForInsertInformation.getChildren().add(teamA);
		
		nameTeamA = new TextField("TeamA");
		nameTeamA.setPrefHeight(20);
		nameTeamA.setLayoutX(teamA.getX());
		nameTeamA.setLayoutY(teamA.getY()+teamA.getImage().getHeight());
		paneForInsertInformation.getChildren().add(nameTeamA);

		ImageView playerTeamA = new ImageView("file:image/player.png");
		playerTeamA.setX(teamA.getX()+teamA.getImage().getWidth()/2);
		playerTeamA.setY(nameTeamA.getLayoutY()+nameTeamA.getPrefHeight()+10);
		paneForInsertInformation.getChildren().add(playerTeamA);
		
		namesPlayerTeamA = new InsertNamesPlayers(playerTeamA.getX(), playerTeamA.getY()+playerTeamA.getImage().getHeight());
		paneForInsertInformation.getChildren().add(namesPlayerTeamA);
		
		
//		TEAM B
		ImageView teamB = new ImageView("file:image/teamB.png");
		teamB.setX(paneForInsertInformation.getPrefWidth() - teamB.getImage().getWidth()*4);
		teamB.setY(nameTeam.getY() + nameTeam.getImage().getHeight());
		paneForInsertInformation.getChildren().add(teamB);
		
		nameTeamB = new TextField("TeamB");
		nameTeamB.setPrefHeight(20);
		nameTeamB.setLayoutX(teamB.getX());
		nameTeamB.setLayoutY(teamB.getY()+teamB.getImage().getHeight());
		paneForInsertInformation.getChildren().add(nameTeamB);
		
		ImageView playerTeamB = new ImageView("file:image/player.png");
		playerTeamB.setX(teamB.getX()+teamB.getImage().getWidth()/2);
		playerTeamB.setY(nameTeamB.getLayoutY()+nameTeamB.getPrefHeight()+10);
		paneForInsertInformation.getChildren().add(playerTeamB);
		
		namesPlayerTeamB = new InsertNamesPlayers(playerTeamB.getX(), playerTeamB.getY()+playerTeamB.getImage().getHeight());
		paneForInsertInformation.getChildren().add(namesPlayerTeamB);
		
		
//		FILE CHOOSER
		ImageView chooseMap = new ImageView("file:image/chooseMap.png");
		chooseMap.setX(nameTeam.getX());
		chooseMap.setY(namesPlayerTeamA.getLayoutY()+namesPlayerTeamA.getPrefHeight()+10);
		paneForInsertInformation.getChildren().add(chooseMap);
		
		PaneFileChooser fileChooser = new PaneFileChooser();
		fileChooser.setLayoutX(nameTeamA.getLayoutX());
		fileChooser.setLayoutY(chooseMap.getY()+chooseMap.getImage().getHeight()+10);
		paneForInsertInformation.getChildren().add(fileChooser);
		
		
//		TASTI CANCEL/SUBMIT
		Button submit = new Button();
		submit.setGraphic(new ImageView("file:image/submit.png"));
		submit.setStyle(paneForInsertInformation.getStyle());
		submit.setLayoutX(paneForInsertInformation.getPrefWidth()-((ImageView)submit.getGraphic()).getImage().getWidth()-50);
		submit.setLayoutY(paneForInsertInformation.getPrefHeight()-((ImageView)submit.getGraphic()).getImage().getHeight()-80);
		paneForInsertInformation.getChildren().add(submit);
		
		submit.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (nameTeamA.getText().length() == 0 || namesPlayerTeamA.getNamesPlayer().isEmpty() || 
						nameTeamB.getText().length() == 0 || namesPlayerTeamB.getNamesPlayer().isEmpty() ||
						fileChooser.getMapChoosed() == null)
				{
	            	Alert alert = new Alert(AlertType.WARNING);
	        		alert.setTitle("Information Submit");
	        		alert.setHeaderText(null);
	        		alert.setContentText("Missed value, Check out");
	        		alert.showAndWait();
				}
				else
				{
					MainApplication mainApplication = new MainApplication(nameTeamA.getText(), namesPlayerTeamA.getNamesPlayer(), nameTeamB.getText(),
							namesPlayerTeamB.getNamesPlayer(), fileChooser.getMapChoosed());
					
					try {
						mainApplication.start(primaryStage);
					} catch (Exception e) {
						e.printStackTrace();
					
					}
				}
			}
		});
		
		Button cancel = new Button();
		cancel.setGraphic(new ImageView("file:image/cancel.png"));
		cancel.setStyle(paneForInsertInformation.getStyle());
		cancel.setLayoutX(submit.getLayoutX()-((ImageView)cancel.getGraphic()).getImage().getWidth());
		cancel.setLayoutY(submit.getLayoutY());
		paneForInsertInformation.getChildren().add(cancel);
		
		cancel.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
//				Menu menu = new Menu();
				try {
//					menu.start(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		Scene scene = new Scene(paneForInsertInformation);        
        primaryStage.setTitle("After Menu!");
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
