package gui;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class PaneFileChooser extends Pane {
	
	private static final String FONT_PATH = "font/clockFont.ttf";
	private Font fontTurnNext;
	
	private int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3;
	private int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()/4;
	
	String mapChoose;
	ImageView validation;
	ArrayList<Button> buttonList;
	
	public PaneFileChooser() {
	
		super();
		
		this.setPrefSize(width, height);
		this.setStyle("-fx-border-color: black; -fx-border-width: 2;"
				+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
				+ " -fx-background-radius: 8,7,6; -fx-background-insets: 0,1,2;"); 
		
		validation = new ImageView(new Image("file:image/validation.png",20,20,false,false));
		
		buttonList = new ArrayList<Button>();
		
		File dir = new File("worldXML/");
	    String[] children = dir.list();
	    if (children != null)
	    {
	        for (int i=0; i < children.length; i++) {
	        	
	        	String[] splitLine = children[i].split("\\.");
	        	Button button = new Button(splitLine[0]);
	        	try {
					fontTurnNext= Font.loadFont(new FileInputStream(FONT_PATH),20);
					button.setFont(fontTurnNext);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
	        	button.setPrefHeight(fontTurnNext.getSize()*2);
	        	
	        	if (i > 0)
	        		button.setLayoutY(buttonList.get(i-1).getLayoutY()+buttonList.get(i-1).getPrefHeight());
	        	else
	        		button.setLayoutY(10);
	        	
	        	button.setLayoutX(30);
	        	button.setStyle("-fx-background-color: null");
	        	
	        	buttonList.add(button);
	        	this.getChildren().add(button);
	        	
	        	button.setOnMouseReleased(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						if (PaneFileChooser.this.getChildren().contains(validation))
							PaneFileChooser.this.getChildren().remove(validation);
						
						mapChoose = button.getText()+".xml";
						
						validation.setLayoutX(10);
						validation.setLayoutY(button.getLayoutY()+button.getPrefWidth()+10);
						PaneFileChooser.this.getChildren().add(validation);
					}
				});
	        }
	    }
	}
	
	public String getMapChoosed() {
		return mapChoose;
	}
	
	
}
