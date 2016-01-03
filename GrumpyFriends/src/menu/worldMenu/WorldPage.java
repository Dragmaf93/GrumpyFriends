package menu.worldMenu;


import menu.MenuButton;
import menu.MenuManager;
import menu.MenuPage;
import gui.ImageLoader;
import gui.Popup;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

public class WorldPage extends Pane implements MenuPage{
	
	private String[] worldTypes;
	
	private final static double PADDING_HEIGHT = 100;
	private final static double PADDING_WIDTH = 150;
	
	private final static double SPACING_BUTTON = 30;
	
	private final static double BUTTON_HEIGHT = 60;
	private final static double BUTTON_WIDTH = 	200;
	
	private MapSelector mapList;
	private WorldTypeSelector worldTypeSelector;
	private PreviewWorldViewer previewWorldViewer;
	
	private MenuButton showPreviewButton;
	private MenuButton continueButton;
	private MenuButton backButton;
	
	private Pane buttons;
	private Pane root;

	private MenuManager menuManager;
	private Scene scene;

	private Popup messagePopup;
	
	public WorldPage(ImageLoader imageLoader) {
		
		menuManager = MenuManager.getIstance();
		
		//robe grafiche
		this.setStyle("-fx-background: #ece6e6; -fx-background-color: rgba(25,81,81,0.6);");
		
		mapList = new MapSelector(this);
		worldTypeSelector = new WorldTypeSelector(this,imageLoader);
		previewWorldViewer = new PreviewWorldViewer(this,imageLoader);
		
		showPreviewButton = new MenuButton(BUTTON_WIDTH, BUTTON_HEIGHT, "PREVIEW");
		continueButton = new MenuButton(BUTTON_WIDTH, BUTTON_HEIGHT, "CONTINUE");
		backButton = new MenuButton(BUTTON_WIDTH, BUTTON_HEIGHT, "BACK");

		buttons = new VBox(SPACING_BUTTON);
		buttons.getChildren().addAll(showPreviewButton,continueButton,backButton);
		
		root = new Pane(mapList,worldTypeSelector,previewWorldViewer,buttons);
		root.setPrefSize(Screen.getPrimary().getBounds().getWidth()-PADDING_WIDTH*2, Screen.getPrimary().getBounds().getHeight()-PADDING_HEIGHT*2);
		worldTypeSelector.relocate(root.getPrefWidth()-worldTypeSelector.getWidthComponent(), worldTypeSelector.getLayoutY());
		previewWorldViewer.relocate(previewWorldViewer.getLayoutX(), root.getPrefHeight()-previewWorldViewer.getHeightComponent());
		buttons.relocate(root.getPrefWidth()-BUTTON_WIDTH-30, root.getPrefHeight()-(BUTTON_HEIGHT*3+SPACING_BUTTON*2)-10);
		
		
		StackPane p = new StackPane(root);
		p.setPadding(new Insets(PADDING_HEIGHT-20,PADDING_WIDTH,PADDING_HEIGHT,PADDING_WIDTH));
		
		this.getChildren().add(new StackPane(p));
		
		scene = new Scene(this,Screen.getPrimary().getBounds().getWidth(),
				Screen.getPrimary().getBounds().getHeight());
		
//		eventi 
		showPreviewButton.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton()==MouseButton.PRIMARY)
				{
					if(mapList.getMapSelected()!=null)
						previewWorldViewer.showPreview(mapList.getMapSelected(),worldTypeSelector.getTypeWorld());
				}
			}
		});
		
		backButton.setOnMouseReleased(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				backPage();
			}
		});
		
		continueButton.setOnMouseReleased(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				nextPage();
			}
		});
	}
	
	public String getWorldChoosed() {
		return mapList.getMapSelected()+".xml";
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextPage() {
		if (mapList.getMapSelected() != null)
		{
			messagePopup = new Popup(300, 200, "Are you sure?", "Cancel", "Accept");
			messagePopup.changeColor();
			root.getChildren().add(messagePopup);
			messagePopup.getRightButton().setOnMouseReleased(new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					menuManager.startGame();
					root.getChildren().remove(messagePopup);
				}
			});
			messagePopup.getLeftButton().setOnMouseReleased(new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					root.getChildren().remove(messagePopup);
				}
			});
		}
		else
		{
			messagePopup = new Popup(300, 200, "Choose Map", "", "Ok");
			messagePopup.changeColorForMissingValue();
			root.getChildren().add(messagePopup);
			messagePopup.getRightButton().setOnMouseReleased(new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					root.getChildren().remove(messagePopup);
				}
			});
		}
		
	}

	@Override
	public void backPage() {
		menuManager.setSceneForChangePage(menuManager.getTeamPageBScene());
	}

}
