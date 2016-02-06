package mapEditor;

import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import menu.MenuManager;
import javafx.scene.image.ImageView;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PanelForObject extends Pane {
	
	private Pane panelForSubmit;
	private Pane panelForRealObject;
	
	private Label choiseWidth;
	private TextField insertWidth;
	private Label choiseHeight;
	private TextField insertHeight;
	private Button buttonForSubmit;
	private Button buttonForClear;
	private Button buttonBack;
	private Button buttonForUndo;
	
	private MapEditor mapEditor;
	private boolean moved = false;
	private boolean isInsertWidth;
	private boolean isInsertHeight;
	
	private double lastItemInserted;
	private boolean movedObject;
//	private Node okButton;
	
	Font font = Font.font("Comic Sans MS", FontWeight.BOLD, 15);
	Insets insets = new Insets(5, 20, 5, 20);
//	private MenuManager menuManager;
	
	public PanelForObject(MapEditor mapEditor) {
//		this.menuManager = MenuManager.getIstance();
		
		this.setPrefSize(mapEditor.getWidthScreen()/5, mapEditor.getHeightScreen());
        this.setStyle("-fx-background-color: #92a498;");

        this.mapEditor = mapEditor;
        addComponent();
        
	}
	
	private void addComponent() {
		panelForSubmit = new HBox(10);
	    panelForSubmit.setPrefSize(this.getPrefWidth() - 10, 50);
	    panelForSubmit.setLayoutY(this.getPrefHeight()-panelForSubmit.getPrefHeight()-60);
	    
	    panelForRealObject = new Pane();
	    panelForRealObject.setPrefSize(this.getPrefWidth(), this.getPrefHeight()-panelForSubmit.getPrefHeight()-50);

		
	    buttonForUndo = new Button();
	    ImageView imageUndo = new ImageView("file:image/imageButtonMapEditor/undo.png");
	    imageUndo.setFitHeight(20);
	    imageUndo.setFitWidth(20);
	    buttonForUndo.setGraphic(imageUndo);
	    buttonForUndo.setLayoutX(5);
	    buttonForUndo.setLayoutY(5);
	    buttonForUndo.setPrefSize(10, 10);
	    buttonForUndo.setBackground(null);
	    buttonForUndo.setDisable(true);
	    panelForRealObject.getChildren().add(buttonForUndo);
	    
	    buttonForUndo.setOnMouseReleased(new EventHandler<MouseEvent>() {
	    	
	        @Override
	        public void handle(MouseEvent event) { 
	        	PanelForObject.this.mapEditor.undo();
	        }
	    });
	    
		choiseWidth = new Label("Width");
		choiseWidth.setFont(font);
	    choiseWidth.setLayoutX(buttonForUndo.getLayoutX());
	    choiseWidth.setLayoutY(buttonForUndo.getLayoutY()+buttonForUndo.getPrefHeight()+30);
	    panelForRealObject.getChildren().add(choiseWidth);
	    
	    insertWidth = new TextField();
	    insertWidth.setLayoutX(choiseWidth.getLayoutX());
	    insertWidth.setLayoutY(choiseWidth.getLayoutY()+22);
	    panelForRealObject.getChildren().add(insertWidth);
	    
	    insertWidth.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
					if (insertWidth.getText().length() > 0)
					{
						double newWidth = Integer.parseInt(insertWidth.getText());
						if (newWidth != PanelForObject.this.mapEditor.getWidthPanelForMap())
							PanelForObject.this.mapEditor.setWidthPanelForMap(newWidth);
						
						isInsertWidth = true;
					}
					else
						setDimensionStandard(true);
				}
				if (event.getCode().equals(KeyCode.BACK_SPACE)) 
					setDimensionStandard(true);
			}
		});
	    
	    choiseHeight = new Label("Height");
	    choiseHeight.setFont(font);
	    choiseHeight.setLayoutX(insertWidth.getLayoutX());
	    choiseHeight.setLayoutY(insertWidth.getLayoutY()+32);
	    panelForRealObject.getChildren().add(choiseHeight);
	    
	    insertHeight = new TextField();
	    insertHeight.setLayoutX(choiseHeight.getLayoutX());
	    insertHeight.setLayoutY(choiseHeight.getLayoutY()+26);
	    
	    lastItemInserted = insertHeight.getLayoutY()+50;
	    
	    panelForRealObject.getChildren().add(insertHeight);
	    
	    insertHeight.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
					if (insertHeight.getText().length() > 0)
					{
						double newWidth = Integer.parseInt(insertHeight.getText());
						if (newWidth != PanelForObject.this.mapEditor.getHeightPanelForMap())
							PanelForObject.this.mapEditor.setHeightPanelForMap(newWidth);
						
						isInsertHeight = true;
					}
					else
						setDimensionStandard(false);
				}
				if (event.getCode().equals(KeyCode.BACK_SPACE))
					setDimensionStandard(false);
			}
		});
	    
	    buttonForClear = new Button("Clear");
		customizeButton(buttonForClear);
	    buttonForClear.setOnMouseReleased(new EventHandler<MouseEvent>() {
	    	@Override
	    	public void handle(MouseEvent event) { 
	        	PanelForObject.this.mapEditor.clearMap();
	    	}
	    });
	    
	    buttonForSubmit = new Button("Submit");
	    customizeButton(buttonForSubmit);
	    buttonForSubmit.setOnMouseReleased(new EventHandler<MouseEvent>() {
	    	
	        @Override
	        public void handle(MouseEvent event) { 
	        	setAlert();
	        }
	    });
	    
	    buttonBack = new Button("Back");
	    customizeButton(buttonBack);
	    buttonBack.setOnMouseReleased(new EventHandler<MouseEvent>() {
	    	
	    	@Override
	    	public void handle(MouseEvent event) { 
	    		if (event.getButton() == MouseButton.PRIMARY)
					MenuManager.getInstance().goToMainMenu();
	    	}
	    });
	    
	    panelForSubmit.getChildren().add(buttonBack);
	    panelForSubmit.getChildren().add(buttonForClear);
	    panelForSubmit.getChildren().add(buttonForSubmit);

        addListenerPanelForRealObject();
	    
	    this.getChildren().add(panelForSubmit);
	    this.getChildren().add(panelForRealObject);
	}
	
	private void customizeButton(Button button) {
		button.setStyle("-fx-background-color: transparent;");
		button.setFont(font);
		button.setPadding(insets);
		button.setTextFill(Color.web("#323232"));
		
		button.setOnMouseEntered(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				button.setTextFill(Color.web("#0076a3"));
			}
		});
		
		button.setOnMouseExited(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				button.setTextFill(Color.web("#323232"));
			}
		});
	}
	
	private void addListenerPanelForRealObject() {
	  panelForRealObject.setOnMousePressed(new EventHandler<MouseEvent>() {
	
	        @Override
	        public void handle(MouseEvent event) {
	        	PanelForObject.this.mapEditor.setUpperAndCopyImage(event);
	        	movedObject = false;
	        }
	    });
	    
	    panelForRealObject.setOnMouseDragged(new EventHandler<MouseEvent>() {
	
	        @Override
	        public void handle(MouseEvent event) { 
	            PanelForObject.this.mapEditor.moveObjectFromPanelObjectInMap(event);
	            movedObject = true;
	        }
	    });
	    
	    panelForRealObject.setOnMouseReleased(new EventHandler<MouseEvent>() {
	
	        @Override
	        public void handle(MouseEvent event) { 
	        	if (movedObject)
	        		PanelForObject.this.mapEditor.addObjectInListImage();
	        }
	    });
	}

	public double getLastItemInserted() {
		return lastItemInserted;
	}
	
	public Pane getPanelForRealObject() {
		return panelForRealObject;
	}
	
	public Pane getPanelForSubmit() {
		return panelForSubmit;
	}
	
	public void setHeightPanelForRealObject(double height) {
		panelForRealObject.setPrefHeight(height);
	}
	
	public void changePolicyButton(int i, boolean bool) {
		buttonForUndo.setDisable(bool);
	}
	
	public boolean scrolledUsed() {
		return moved;
	}
	
	public void cancTextInTextField() {
		insertWidth.clear();
		insertHeight.clear();
		
		isInsertHeight = false;
		isInsertWidth = false;
	}

	public double getHeightToInsert() {
		if (!isInsertHeight)
			return mapEditor.getHeightPanelForMap();
		return Double.parseDouble(insertHeight.getText());
	}
	
	public double getWidthToInsert() {
		if (!isInsertWidth)
			return mapEditor.getWidthPanelForMap();
		return Double.parseDouble(insertWidth.getText());
	}
	
	private void setDimensionStandard(boolean widthOrHeight) {
		if (widthOrHeight) {
			isInsertWidth = false;
			insertWidth.clear();
			mapEditor.setDimensionStandardPanelMap(widthOrHeight);
		}
		else {
			isInsertHeight = false;
			insertHeight.clear();
			mapEditor.setDimensionStandardPanelMap(widthOrHeight);
		}
	}
	
	public void setAlert() {
		try {
			TextInputDialog dialog = new TextInputDialog("Map");
			dialog.setTitle("Information Submit");
			dialog.setHeaderText("Submit saved with success");
			dialog.setContentText("Please enter a name File:");

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
					mapEditor.saveMap(result.get());
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}
