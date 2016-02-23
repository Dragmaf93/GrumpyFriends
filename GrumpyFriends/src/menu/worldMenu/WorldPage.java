package menu.worldMenu;

import menu.AbstractMenuPage;
import menu.GameBean;
import menu.MenuButton;
import menu.MenuManager;
import menu.MenuPage;
import menu.PageComponent;
import gui.ImageLoader;
import gui.Popup;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.util.Duration;

public class WorldPage extends AbstractMenuPage implements MenuPage {

	private String[] worldTypes;

	private final static double SPACING_BUTTON = 30;

	private final static double BUTTON_HEIGHT = 60;
	private final static double BUTTON_WIDTH = 200;

	private MapSelector mapList;
	private WorldTypeSelector worldTypeSelector;
	private PreviewWorldViewer previewWorldViewer;

	private MenuButton showPreviewButton;
	private MenuButton continueButton;
	private MenuButton backButton;

	private Pane buttons;

	private Popup messagePopup;

	protected Rectangle rectangleBackground;

	public WorldPage() {

		mapList = new MapSelector(this);
		worldTypeSelector = new WorldTypeSelector(this, MenuManager
				.getInstance().getImageLoader());
		previewWorldViewer = new PreviewWorldViewer(this, MenuManager
				.getInstance().getImageLoader());

		showPreviewButton = new MenuButton(BUTTON_WIDTH, BUTTON_HEIGHT,
				"PREVIEW");
		continueButton = new MenuButton(BUTTON_WIDTH, BUTTON_HEIGHT, "CONTINUE");
		backButton = new MenuButton(BUTTON_WIDTH, BUTTON_HEIGHT, "BACK");

		buttons = new VBox(SPACING_BUTTON);
		buttons.getChildren().addAll(showPreviewButton, continueButton,
				backButton);

		root.getChildren().addAll(mapList, worldTypeSelector,
				previewWorldViewer, buttons);
		// root.setPrefSize(Screen.getPrimary().getBounds().getWidth()-PADDING_WIDTH*2,
		// Screen.getPrimary().getBounds().getHeight()-PADDING_HEIGHT*2);
		worldTypeSelector.relocate(
				root.getPrefWidth() - worldTypeSelector.getWidthComponent(),
				worldTypeSelector.getLayoutY());
		previewWorldViewer.relocate(previewWorldViewer.getLayoutX(),
				root.getPrefHeight() - previewWorldViewer.getHeightComponent());
		buttons.relocate(root.getPrefWidth() - BUTTON_WIDTH - 30,
				root.getPrefHeight() - (BUTTON_HEIGHT * 3 + SPACING_BUTTON * 2)
						- 10);

		//
		showPreviewButton.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					if (mapList.getMapSelected() != null)
						previewWorldViewer.showPreview(
								mapList.getMapSelected(),
								worldTypeSelector.getTypeWorld());
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
		return mapList.getMapSelected() + ".xml";
	}

	@Override
	public void reset() {
		mapList.reset();
		worldTypeSelector.reset();
		previewWorldViewer.reset();
	}

	@Override
	public void nextPage() {
		// if (mapList.getMapSelected() != null)
		// {
		// messagePopup = new Popup(300, 200, "Are you sure?", "Cancel",
		// "Accept");
		// messagePopup.changeColor();
		// root.getChildren().add(messagePopup);
		// messagePopup.getRightButton().setOnMouseReleased(new
		// EventHandler<Event>() {
		//
		// @Override
		// public void handle(Event event) {
		// menuManager.startGame();
		// root.getChildren().remove(messagePopup);
		// }
		// });
		// messagePopup.getLeftButton().setOnMouseReleased(new
		// EventHandler<Event>() {
		//
		// @Override
		// public void handle(Event event) {
		// root.getChildren().remove(messagePopup);
		// }
		// });
		// }
		if (mapList.getMapSelected() == null) {
			messagePopup = new Popup(300, 200, "Choose Map", "", "Ok");
			messagePopup.relocate(root.getWidth()/2 - messagePopup.getLayoutBounds().getWidth()/2, 
					root.getHeight()/2 - messagePopup.getLayoutBounds().getHeight()/2);
			root.getChildren().add(messagePopup);
			messagePopup.getRightButton().setOnMouseReleased(
					new EventHandler<Event>() {

						@Override
						public void handle(Event event) {
							root.getChildren().remove(messagePopup);
						}
					});
		}
		else 
			MenuManager.getInstance().nextPage();
	}

	@Override
	public void backPage() {
		MenuManager.getInstance().previousPage();
	}

	@Override
	public GameBean getGameBean() {
		GameBean bean = new GameBean("World");

		bean.addData("WorldType", worldTypeSelector.getValues()[0]);
		bean.addData("WorldMap", mapList.getValues()[0]);

		return bean;
	}

	@Override
	public void update() {

	}

}
