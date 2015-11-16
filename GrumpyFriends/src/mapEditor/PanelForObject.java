package mapEditor;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import javafx.scene.image.ImageView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PanelForObject extends Pane {
	
//	private Pane panelForDimension;
	private Pane panelForSubmit;
	private Pane panelForRealObject;
	
	private Label choiseTypeWorld;
	private ChoiceBox<String> choiceType;
	private ArrayList<String> typeWorld;
	private int typeWorldChoise;
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
	private boolean moved = false;
	private boolean isInsertWidth;
	private boolean isInsertHeight;
	
	private double lastItemInserted;
	protected boolean movedObject;
	
	public PanelForObject(MapEditor mapEditor) 
	{
		this.setPrefSize(mapEditor.getLarghezza()/5, mapEditor.getAltezza());
        this.setStyle("-fx-background-color: #92a498;");

        this.mapEditor = mapEditor;
        typeWorld = new ArrayList<String>();
        typeWorld.add("Planet");
        
        addComponent();
        
	}
	
	private void addComponent()
	{
//	    panelForDimension = new Pane();
//	    panelForDimension.setPrefSize(this.getPrefWidth(), this.getPrefHeight()/4);
		
		panelForSubmit = new Pane();
	    panelForSubmit.setPrefSize(this.getPrefWidth() - 10, 50);
	    panelForSubmit.setLayoutY(this.getPrefHeight()-panelForSubmit.getPrefHeight()-50);
	    
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
	        	buttonForRedo.setDisable(false);
	        }
	    });
	    
	    
	    buttonForRedo = new Button();
	    ImageView image = new ImageView("file:image/imageButtonMapEditor/redo.png");
	    image.setFitHeight(20);
	    image.setFitWidth(20);
	    buttonForRedo.setGraphic(image);
	    buttonForRedo.setLayoutX(buttonForUndo.getPrefWidth()*3);
	    buttonForRedo.setLayoutY(5);
	    buttonForRedo.setPrefSize(10, 10);
	    buttonForRedo.setBackground(null);
	    buttonForRedo.setDisable(true);
	    panelForRealObject.getChildren().add(buttonForRedo);
	    
	    buttonForRedo.setOnMouseReleased(new EventHandler<MouseEvent>() {
	    	
	        @Override
	        public void handle(MouseEvent event) { 
	        	PanelForObject.this.mapEditor.redo();
	        	buttonForUndo.setDisable(false);
	        }
	    });
	    
	    choiseTypeWorld = new Label("Type World");
	    choiseTypeWorld.setLayoutX(buttonForUndo.getLayoutX());
	    choiseTypeWorld.setLayoutY(buttonForRedo.getLayoutY()+buttonForRedo.getPrefHeight()+20);
	    panelForRealObject.getChildren().add(choiseTypeWorld);
	    
	    choiceType = new ChoiceBox<String>(FXCollections.observableArrayList(typeWorld));
	    choiceType.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
				typeWorldChoise = newValue.intValue();
			}
		});
	    
	    choiceType.setLayoutX(buttonForUndo.getLayoutX());
	    choiceType.setLayoutY(choiseTypeWorld.getLayoutY()+choiseTypeWorld.getPrefHeight()+20);
	    choiceType.setOpacity(0.7);
	    panelForRealObject.getChildren().add(choiceType);
	    
		choiseWidth = new Label("Width");
	    choiseWidth.setLayoutX(buttonForUndo.getLayoutX());
	    choiseWidth.setLayoutY(choiceType.getLayoutY()+choiceType.getPrefHeight()+30);
	    panelForRealObject.getChildren().add(choiseWidth);
	    
	    insertWidth = new TextField();
	    insertWidth.setLayoutX(choiseWidth.getLayoutX());
	    insertWidth.setLayoutY(choiseWidth.getLayoutY()+20);
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
	    choiseHeight.setLayoutX(insertWidth.getLayoutX());
	    choiseHeight.setLayoutY(insertWidth.getLayoutY()+25);
	    panelForRealObject.getChildren().add(choiseHeight);
	    
	    insertHeight = new TextField();
	    insertHeight.setLayoutX(choiseHeight.getLayoutX());
	    insertHeight.setLayoutY(choiseHeight.getLayoutY()+20);
	    
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
	        	try {
					PanelForObject.this.mapEditor.saveMap();
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (TransformerException e) {
					e.printStackTrace();
				}
	        }
	    });
	    
	    
	    panelForSubmit.getChildren().add(buttonForSubmit);
	    panelForSubmit.getChildren().add(buttonForClear);
	    
//	    scrollPane = new ScrollPane(panelForRealObject);
//	    scrollPane.setLayoutY(panelForRealObject.getLayoutY());
//	    scrollPane.setPrefSize(panelForRealObject.getPrefWidth(), panelForRealObject.getPrefHeight());
//        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
//        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
//
//        scrollPane.getStylesheets().add("file:styles/customScrollBarForPanelObject.css");
////        scrollPane.setStyle("-fx-background: #92a498; -fx-background-color: null; ");
//        scrollPane.vvalueProperty().addListener(new ChangeListener<Number>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Number> event,
//					Number oldValue, Number newValue) {
//				if (oldValue.doubleValue() < newValue.doubleValue())
//					moved = true;
//				else
//					moved = false;
//			}
//		});

        addListenerPanelForRealObject();
	    
	    this.getChildren().add(panelForSubmit);
	    this.getChildren().add(panelForRealObject);
	}
	
	private void addListenerPanelForRealObject()
	{
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
		
		isInsertHeight = false;
		isInsertWidth = false;
	}
	
	public String getTypeWorld() {
		return typeWorld.get(typeWorldChoise);
	}

	public String getHeightToInsert() {
		if (!isInsertHeight)
			return Double.toString(mapEditor.getHeightPanelForMap());
		return insertHeight.getText();
	}
	
	public String getWidthToInsert() {
		if (!isInsertWidth)
			return Double.toString(mapEditor.getWidthPanelForMap());
		return insertWidth.getText();
	}
	
	private void setDimensionStandard(boolean widthOrHeight) {
		if (widthOrHeight)
		{
			isInsertWidth = false;
			insertWidth.clear();
			mapEditor.setDimensionStandardPanelMap(widthOrHeight);
		}
		else
		{
			isInsertHeight = false;
			insertHeight.clear();
			mapEditor.setDimensionStandardPanelMap(widthOrHeight);
		}
	}
}
