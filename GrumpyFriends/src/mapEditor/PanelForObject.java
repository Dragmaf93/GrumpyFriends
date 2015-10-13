package mapEditor;

import javafx.scene.image.ImageView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PanelForObject extends Pane {
	
	private Pane panelForDimension;
	private Pane panelForSubmit;
	private Pane panelForRealObject;
	
	private Label choiseWidth;
	private TextField insertWidth;
	private Label choiseHeight;
	private TextField insertHeight;
	private Button buttonForSubmit;
	private Button buttonForClear;
	private Button buttonForRedo;
	private Button buttonForUndo;
	
	private ScrollPane scrollPane;
	
	private MapEditor mapEditor;
	protected boolean moved = false;
	
	public PanelForObject(MapEditor mapEditor) 
	{
		this.setPrefSize(mapEditor.getLarghezza()/5, mapEditor.getAltezza());
        this.setStyle("-fx-background-color: #92a498;");

        this.mapEditor = mapEditor;
        this.scrollPane = new ScrollPane();
        
        addComponent();
        
	}
	
	private void addComponent()
	{
	    panelForDimension = new Pane();
	    panelForDimension.setPrefSize(this.getPrefWidth(), this.getPrefHeight()/6);
		
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
	    panelForDimension.getChildren().add(buttonForUndo);
	    
	    buttonForUndo.setOnMouseReleased(new EventHandler<MouseEvent>() {
	    	
	        @Override
	        public void handle(MouseEvent event) { 
	        	PanelForObject.this.mapEditor.undo();
	        	buttonForRedo.setDisable(false);
	        }
	    });
	    
	    
	    buttonForRedo = new Button();
	    ImageView image = new ImageView("file:image/imageButtonMapEditor/redo.png");
	    image.setFitHeight(20);
	    image.setFitWidth(20);
	    buttonForRedo.setGraphic(image);
	    buttonForRedo.setLayoutX(buttonForUndo.getPrefWidth()+20);
	    buttonForRedo.setLayoutY(5);
	    buttonForRedo.setPrefSize(10, 10);
	    buttonForRedo.setBackground(null);
	    buttonForRedo.setDisable(true);
	    panelForDimension.getChildren().add(buttonForRedo);
	    
	    buttonForRedo.setOnMouseReleased(new EventHandler<MouseEvent>() {
	    	
	        @Override
	        public void handle(MouseEvent event) { 
	        	PanelForObject.this.mapEditor.redo();
	        	buttonForUndo.setDisable(false);
	        }
	    });
	    
		choiseWidth = new Label("Width");
	    choiseWidth.setLayoutX(buttonForUndo.getLayoutX());
	    choiseWidth.setLayoutY(buttonForRedo.getLayoutY()+buttonForRedo.getPrefHeight()+20);
	    panelForDimension.getChildren().add(choiseWidth);
	    
	    insertWidth = new TextField();
	    insertWidth.setLayoutX(choiseWidth.getLayoutX());
	    insertWidth.setLayoutY(choiseWidth.getLayoutY()+20);
	    panelForDimension.getChildren().add(insertWidth);
	    
	    insertWidth.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					double newWidth = Integer.parseInt(insertWidth.getText());
					if (newWidth != PanelForObject.this.mapEditor.getWidthPanelForMap())
						PanelForObject.this.mapEditor.setWidthPanelForMap(newWidth);
				}
			}
		});
	    
	    choiseHeight = new Label("Height");
	    choiseHeight.setLayoutX(insertWidth.getLayoutX());
	    choiseHeight.setLayoutY(insertWidth.getLayoutY()+25);
	    panelForDimension.getChildren().add(choiseHeight);
	    
	    insertHeight = new TextField();
	    insertHeight.setLayoutX(choiseHeight.getLayoutX());
	    insertHeight.setLayoutY(choiseHeight.getLayoutY()+20);
	    panelForDimension.getChildren().add(insertHeight);
	    
	    insertHeight.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					double newWidth = Integer.parseInt(insertHeight.getText());
					if (newWidth != PanelForObject.this.mapEditor.getHeightPanelForMap())
						PanelForObject.this.mapEditor.setHeightPanelForMap(newWidth);
				}
			}
		});
	    
	    this.getChildren().add(panelForDimension);
	    
	    panelForSubmit = new Pane();
	    panelForSubmit.setPrefSize(this.getPrefWidth() - 10, 50);
	    panelForSubmit.setLayoutY(this.getPrefHeight()-panelForSubmit.getPrefHeight()-50);
	   
	    buttonForClear = new Button("Clear");
	    buttonForClear.setOpacity(0.7);
	    buttonForClear.setLayoutX(5);
	    buttonForClear.setLayoutY(panelForSubmit.getPrefHeight()/4);
	    buttonForClear.setOnMouseReleased(new EventHandler<MouseEvent>() {
	    	
	    	@Override
	    	public void handle(MouseEvent event) { 
	        	PanelForObject.this.mapEditor.clearMap();
	    	}
	    });
	    
	    buttonForSubmit = new Button("Submit");
	    buttonForSubmit.setOpacity(0.7);
	    buttonForSubmit.setPrefWidth(buttonForSubmit.getText().length()*12);
	    buttonForSubmit.setLayoutX(panelForSubmit.getPrefWidth()-buttonForSubmit.getPrefWidth());
	    buttonForSubmit.setLayoutY(buttonForClear.getLayoutY());
	    buttonForSubmit.setOnMouseReleased(new EventHandler<MouseEvent>() {
	    	
	        @Override
	        public void handle(MouseEvent event) { 
	        	PanelForObject.this.mapEditor.saveMap();
	        }
	    });
	    
	    
	    panelForSubmit.getChildren().add(buttonForSubmit);
	    panelForSubmit.getChildren().add(buttonForClear);
	
	    panelForRealObject = new Pane();
	    panelForRealObject.setPrefSize(this.getPrefWidth(), this.getPrefHeight()-panelForDimension.getPrefHeight()-panelForSubmit.getPrefHeight()-50);
	    panelForRealObject.setLayoutY(panelForDimension.getPrefHeight());
	    
	    scrollPane.setContent(panelForRealObject);
	    scrollPane.setLayoutY(panelForRealObject.getLayoutY());
	    scrollPane.setPrefSize(panelForRealObject.getPrefWidth(), panelForRealObject.getPrefHeight());
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: #92a498; -fx-background-color: null; ");

//        scrollPane.setStyle("file:styles/customScrollBar.css");
        
        scrollPane.vvalueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> event,
					Number oldValue, Number newValue) {
				if (oldValue.doubleValue() < newValue.doubleValue())
					moved = true;
				else
					moved = false;
			}
		});
        
        addListenerPanelForRealObject();
	    
	    this.getChildren().add(panelForSubmit);
	    this.getChildren().add(scrollPane);
	}
	
	private void addListenerPanelForRealObject()
	{
	  panelForRealObject.setOnMousePressed(new EventHandler<MouseEvent>() {
	
	        @Override
	        public void handle(MouseEvent event) {
	        	PanelForObject.this.mapEditor.setUpperAndCopyImage(event);
	        }
	    });
	    
	    panelForRealObject.setOnMouseDragged(new EventHandler<MouseEvent>() {
	
	        @Override
	        public void handle(MouseEvent event) { 
	            PanelForObject.this.mapEditor.moveObjectFromPanelObjectInMap(event);
	        }
	    });
	    
	    panelForRealObject.setOnMouseReleased(new EventHandler<MouseEvent>() {
	
	        @Override
	        public void handle(MouseEvent event) { 
	        	PanelForObject.this.mapEditor.addObjectInListImage();
	        }
	    });
	}
	
	public Pane getPanelForDimension() {
		return panelForDimension;
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
		if (i == 0)
			buttonForRedo.setDisable(bool);
		else
			buttonForUndo.setDisable(bool);
	}
	
	public boolean scrolledUsed() {
		return moved;
	}
	
	public void cancTextInTextField() {
		insertWidth.clear();
		insertHeight.clear();
	}
}
