package gui;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class InsertNamesPlayers extends ScrollPane {

	private static final double DIMENSION = 20;
	private static final double WIDTH = 220;
	
	Pane realPane;
	
	ArrayList<TextField> namesPlayer;
	ImageView addPlayer;
	int indexNumberPlayer;
	
	public InsertNamesPlayers(double x, double y) {
		
		realPane = new Pane();
		this.setPrefSize(300, 100);
		this.setStyle("-fx-background: #71b0f6; -fx-background-color: #71b0f6; -fx-border-color: blue; -fx-border-width: 1;");
		
		this.setLayoutX(x);
		this.setLayoutY(y);
		
		this.setContent(realPane);
		
		indexNumberPlayer = 0;
		
		namesPlayer = new ArrayList<TextField>();
		namesPlayer.add(new TextField("Manuel"));
		namesPlayer.get(indexNumberPlayer).setPrefWidth(WIDTH);
		namesPlayer.get(indexNumberPlayer).setPrefHeight(DIMENSION);;
		namesPlayer.get(indexNumberPlayer).setLayoutX(10);
		namesPlayer.get(indexNumberPlayer).setLayoutY(10);
		realPane.getChildren().add(namesPlayer.get(indexNumberPlayer));
		
		addPlayer = new ImageView(new Image("file:image/plus.png",DIMENSION,DIMENSION,false,false));
		addPlayer.setX(namesPlayer.get(indexNumberPlayer).getLayoutX() + namesPlayer.get(indexNumberPlayer).getPrefWidth()+20);
		addPlayer.setY(namesPlayer.get(indexNumberPlayer).getLayoutY());
		realPane.getChildren().add(addPlayer);
		
		addPlayer.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				indexNumberPlayer++;
				namesPlayer.add(new TextField());
				namesPlayer.get(indexNumberPlayer).setPrefWidth(namesPlayer.get(0).getPrefWidth());
				namesPlayer.get(indexNumberPlayer).setPrefHeight(namesPlayer.get(0).getPrefHeight());
				namesPlayer.get(indexNumberPlayer).setLayoutX(namesPlayer.get(indexNumberPlayer-1).getLayoutX());
				namesPlayer.get(indexNumberPlayer).setLayoutY(namesPlayer.get(indexNumberPlayer-1).getLayoutY() + 
						namesPlayer.get(indexNumberPlayer-1).getPrefHeight()+10);
				
				InsertNamesPlayers.this.realPane.getChildren().add(namesPlayer.get(indexNumberPlayer));
				if (indexNumberPlayer < 4)
				{
					addPlayer.setX(namesPlayer.get(indexNumberPlayer).getLayoutX() + namesPlayer.get(indexNumberPlayer).getPrefWidth()+20);
					addPlayer.setY(namesPlayer.get(indexNumberPlayer).getLayoutY());
				}
				else
					InsertNamesPlayers.this.realPane.getChildren().remove(addPlayer);
			}
		});
	}
	
	public ArrayList<String> getNamesPlayer() {
		
		ArrayList<String> listToReturn = new ArrayList<String>();
		for (TextField name : namesPlayer) {
			if (name.getText().length() != 0)
				listToReturn.add(name.getText());
		}
		
		return listToReturn;
	}
	
}
